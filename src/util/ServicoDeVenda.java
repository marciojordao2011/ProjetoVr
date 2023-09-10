package util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.ClienteVenda;
import entities.ItemVenda;
import entities.Produto;
import entities.Venda;

public class ServicoDeVenda {
    private List<Produto> produtos = new ArrayList<>();
    private List<ClienteVenda> clientes = new ArrayList<>();
    private List<Venda> vendas = new ArrayList<>();

    public Venda obterVendaPorId(int id) {
        for (Venda venda : vendas) {
            if (venda.getId() == id) {
                return venda;
            }
        }
        return null;
    }
    

    public ServicoDeVenda(Connection conexao) {
        produtos.add(new Produto(1, "Produto 1", 7.99, 1000, conexao));
        produtos.add(new Produto(2, "Produto 2", 8.99, 1000, conexao));
        produtos.add(new Produto(3, "Produto 2", 8.99, 1000, conexao));
    }

    public Venda criarNovaVenda(ClienteVenda cliente) {
        int novaId = gerarIdVenda();
        Venda novaVenda = new Venda(novaId, cliente);
        vendas.add(novaVenda);
        return novaVenda;
    }

    private int gerarIdVenda() {
        return vendas.size() + 1;
    }

    public void adicionarItemVenda(Venda venda, Produto produto, int quantidade) {
        if ("digitando".equals(venda.getStatus())) {
            
            if (produto.getEstoque() >= quantidade) {
                ItemVenda itemVenda = new ItemVenda(produto.getId(), quantidade, produto.getPreco());
                venda.adicionarItemVenda(itemVenda);

                
                produto.atualizarEstoque(-quantidade);
                venda.atualizarValorTotal(); 

                System.out.println("Item adicionado à venda.");
            } else {
                System.out.println("Produto fora de estoque.");
            }
        } else {
            System.out.println("Não é possível adicionar itens a uma venda efetivada.");
        }
    }


    public void efetivarVenda(Venda venda) {
        if ("digitando".equals(venda.getStatus())) {
            venda.efetivarVenda();
            atualizarEstoque(venda);
        } else {
            System.out.println("A venda já foi efetivada.");
        }
    }

    public void estornarVenda(Venda venda) {
        if ("efetivada".equals(venda.getStatus())) {
            venda.estornarVenda();
            restaurarEstoque(venda);
        } else {
            System.out.println("A venda não pode ser estornada.");
        }
    }

    public List<Venda> consultarVendasPorData(Date dataInicio, Date dataFim) {
        List<Venda> vendasNoPeriodo = new ArrayList<>();
        for (Venda venda : vendas) {
            if (venda.getData().after(dataInicio) && venda.getData().before(dataFim)) {
                vendasNoPeriodo.add(venda);
            }
        }
        return vendasNoPeriodo;
    }

    private void atualizarEstoque(Venda venda) {
        for (ItemVenda item : venda.getItensVenda()) {
            Produto produto = obterProdutoPorId(item.getIdProduto());
            if (produto != null) {
                produto.atualizarEstoque(-item.getQuantidade());
            }
        }
    }

    private void restaurarEstoque(Venda venda) {
        for (ItemVenda item : venda.getItensVenda()) {
            Produto produto = obterProdutoPorId(item.getIdProduto());
            if (produto != null) {
                produto.atualizarEstoque(item.getQuantidade());
            }
        }
    }

    public Produto obterProdutoPorId(int idProduto) {
        for (Produto produto : produtos) {
            if (produto.getId() == idProduto) {
                return produto;
            }
        }
        return null;
    }

    public List<ClienteVenda> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteVenda> clientes) {
        this.clientes = clientes;
    }
}

