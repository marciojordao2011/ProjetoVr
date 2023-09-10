package application;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import connection.ConfiguracaoBancoDados;
import connection.VendaDAO;
import entities.ClienteVenda;
import entities.ItemVenda;
import entities.Produto;
import entities.Venda;
import util.ClienteTableModel;
import util.SelecionarClienteDialog;
import util.ServicoDeVenda;

public class Aplicacao {

    private static ClienteVenda clienteSelecionado;

    private static ClienteVenda abrirDialogoSelecionarCliente(List<ClienteVenda> listaDeClientes, JFrame parent) {
        SelecionarClienteDialog dialog = new SelecionarClienteDialog(parent, listaDeClientes);
        dialog.setVisible(true);
        return dialog.getClienteSelecionado();
    }

    public static void main(String[] args) {
        Connection conexao = ConfiguracaoBancoDados.obterConexao();

        List<ClienteVenda> listaDeClientes = obterClientesDoBancoDeDados(conexao);

        ClienteTableModel clienteTableModel = new ClienteTableModel(listaDeClientes);

        JTable table = new JTable(clienteTableModel);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestão de Vendas");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2));

            JButton criarVendaButton = new JButton("Criar Venda");
            JButton adicionarItemButton = new JButton("Adicionar Item");
            JButton efetivarVendaButton = new JButton("Efetivar Venda");
            JButton estornarVendaButton = new JButton("Estornar Venda");
            JButton consultarVendasButton = new JButton("Consultar Vendas");

            panel.add(criarVendaButton);
            panel.add(adicionarItemButton);
            panel.add(efetivarVendaButton);
            panel.add(estornarVendaButton);
            panel.add(consultarVendasButton);

            frame.add(panel);

            ServicoDeVenda servicoDeVenda = new ServicoDeVenda(conexao);
            
            

            adicionarItemButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String vendaId = JOptionPane.showInputDialog(frame, "Digite o ID da venda:");
                    if (vendaId != null && !vendaId.isEmpty()) {
                        int idProduto = Integer.parseInt(JOptionPane.showInputDialog(frame, "Digite o ID do produto:"));
                        int quantidade = Integer.parseInt(JOptionPane.showInputDialog(frame, "Digite a quantidade:"));
                        Produto produto = servicoDeVenda.obterProdutoPorId(idProduto);
                        if (produto != null) {
                            Venda venda = servicoDeVenda.obterVendaPorId(Integer.parseInt(vendaId));
                            if (venda != null) {
                                
                                servicoDeVenda.adicionarItemVenda(venda, produto, quantidade);

                                
                                VendaDAO vendaDAO = new VendaDAO(conexao);
                                vendaDAO.inserirVenda(venda);

                                
                                produto.atualizarEstoque(-quantidade);

                                JOptionPane.showMessageDialog(frame, "Item adicionado à venda.");
                            } else {
                                JOptionPane.showMessageDialog(frame, "Venda não encontrada.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Produto não encontrado.");
                        }
                    }
                }
            });

            criarVendaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clienteSelecionado = abrirDialogoSelecionarCliente(listaDeClientes, frame);

                    if (clienteSelecionado != null) {
                        Venda venda = servicoDeVenda.criarNovaVenda(clienteSelecionado);

                        if (venda != null) {
                            VendaDAO vendaDAO = new VendaDAO(conexao);
                            vendaDAO.inserirVenda(venda);
                            JOptionPane.showMessageDialog(frame, "Venda criada com ID: " + venda.getId());
                        } else {
                            JOptionPane.showMessageDialog(frame, "Falha ao criar a venda. Verifique se o cliente é válido.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Nenhum cliente selecionado.");
                    }
                }
            });
            
            


            efetivarVendaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String vendaId = JOptionPane.showInputDialog(frame, "Digite o ID da venda:");
                    if (vendaId != null && !vendaId.isEmpty()) {
                        Venda venda = servicoDeVenda.obterVendaPorId(Integer.parseInt(vendaId));
                        if (venda != null) {
                            List<ItemVenda> itens = venda.getItensVenda();
                            boolean estoqueSuficiente = true;

                            for (ItemVenda item : itens) {
                                Produto produto = item.getProduto(servicoDeVenda);
                                int quantidade = item.getQuantidade();

                                
                                if (produto != null && produto.getEstoque() >= quantidade) {
                                    
                                    produto.atualizarEstoque(quantidade);
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Produto fora de estoque.");
                                    estoqueSuficiente = false;
                                    break; 
                                }
                            }

                            if (estoqueSuficiente) {
                                servicoDeVenda.efetivarVenda(venda);

                                
                                VendaDAO vendaDAO = new VendaDAO(conexao);
                                vendaDAO.inserirVenda(venda);

                                JOptionPane.showMessageDialog(frame, "Venda efetivada.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Venda não encontrada.");
                        }
                    }
                }
            });

            estornarVendaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String vendaId = JOptionPane.showInputDialog(frame, "Digite o ID da venda:");
                    if (vendaId != null && !vendaId.isEmpty()) {
                        Venda venda = servicoDeVenda.obterVendaPorId(Integer.parseInt(vendaId));
                        if (venda != null) {
                            servicoDeVenda.estornarVenda(venda);

                            List<ItemVenda> itens = venda.getItensVenda();

                            for (ItemVenda item : itens) {
                                Produto produto = servicoDeVenda.obterProdutoPorId(item.getProdutoId());
                                int quantidade = item.getQuantidade();

                                
                                produto.atualizarEstoque(quantidade);
                            }

                            
                            VendaDAO vendaDAO = new VendaDAO(conexao);
                            vendaDAO.inserirVenda(venda);

                            JOptionPane.showMessageDialog(frame, "Venda estornada.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Venda não encontrada.");
                        }
                    }
                }
            });

            consultarVendasButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date dataInicio = null;
                    Date dataFim = null;
                    try {
                        dataInicio = dateFormat.parse(JOptionPane.showInputDialog(frame, "Digite a data de início (dd/MM/yyyy):"));
                        dataFim = dateFormat.parse(JOptionPane.showInputDialog(frame, "Digite a data de fim (dd/MM/yyyy):"));
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    if (dataInicio != null && dataFim != null) {
                        List<Venda> vendasNoPeriodo = servicoDeVenda.consultarVendasPorData(dataInicio, dataFim);
                        if (!vendasNoPeriodo.isEmpty()) {
                            StringBuilder mensagem = new StringBuilder("Vendas no período de ")
                                    .append(dateFormat.format(dataInicio))
                                    .append(" a ")
                                    .append(dateFormat.format(dataFim))
                                    .append(":\n");
                            for (Venda venda : vendasNoPeriodo) {
                                mensagem.append(venda.toString()).append("\n");
                            }
                            JOptionPane.showMessageDialog(frame, mensagem.toString());
                        } else {
                            JOptionPane.showMessageDialog(frame, "Nenhuma venda encontrada no período especificado.");
                        }
                    }
                }
            });

            frame.setVisible(true);
        });
    }

    private static List<ClienteVenda> obterClientesDoBancoDeDados(Connection conexao) {
        List<ClienteVenda> clientes = new ArrayList<>();

        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ClienteVenda");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                clientes.add(new ClienteVenda(id, nome));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }
}
