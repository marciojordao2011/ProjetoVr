package application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import entities.ClienteVenda;
import entities.Produto;
import entities.Venda;
import util.ServicoDeVenda;
import util.TelaCadastroCliente;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestão de Vendas");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2));

            JButton criarVendaButton = new JButton("Criar Venda");
            JButton adicionarItemButton = new JButton("Adicionar Item");
            JButton efetivarVendaButton = new JButton("Efetivar Venda");
            JButton estornarVendaButton = new JButton("Estornar Venda");
            JButton consultarVendasButton = new JButton("Consultar Vendas");
            JButton novoClienteButton = new JButton("Novo Cliente");

            panel.add(criarVendaButton);
            panel.add(adicionarItemButton);
            panel.add(efetivarVendaButton);
            panel.add(estornarVendaButton);
            panel.add(consultarVendasButton);
            panel.add(novoClienteButton);

            frame.add(panel);

            ServicoDeVenda servicoDeVenda = new ServicoDeVenda(null);

            criarVendaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ClienteVenda cliente = new ClienteVenda(1, "Cliente 1");
                    Venda venda = servicoDeVenda.criarNovaVenda(cliente);
                    JOptionPane.showMessageDialog(frame, "Venda criada com ID: " + venda.getId());
                }
            });

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
                            servicoDeVenda.adicionarItemVenda(venda, produto, quantidade);
                            JOptionPane.showMessageDialog(frame, "Item adicionado à venda.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Produto não encontrado.");
                        }
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
                            servicoDeVenda.efetivarVenda(venda);
                            JOptionPane.showMessageDialog(frame, "Venda efetivada.");
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

            novoClienteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new TelaCadastroCliente();
                }
            });

          
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID Venda");
            model.addColumn("Cliente");
            model.addColumn("Data");

         
            JTable tabela = new JTable(model);

            
            JScrollPane scrollPane = new JScrollPane(tabela);
            scrollPane.setPreferredSize(new Dimension(700, 300));

            
            frame.add(scrollPane, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}








