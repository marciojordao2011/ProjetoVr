package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venda {
    private int id;
    private Date data;
    private ClienteVenda cliente;
    private double valorTotal;
    private String status;
    private List<ItemVenda> itensVenda;

    private static int proximoId = 1;

    public Venda(ClienteVenda cliente) {
        this.id = proximoId;
        proximoId++; 
        this.cliente = cliente;
        this.data = new Date(); 
        this.status = "digitando";
        this.valorTotal = 0;
        this.itensVenda = new ArrayList<>();
    }

    public Venda(int novaId, ClienteVenda cliente) {
        this.id = novaId;
        this.cliente = cliente;
        this.data = new Date(); 
        this.status = "digitando";
        this.valorTotal = 0;
        this.itensVenda = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public ClienteVenda getCliente() {
        return cliente;
    }

    public void setCliente(ClienteVenda cliente) {
        this.cliente = cliente;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void adicionarItemVenda(ItemVenda item) {
        if ("digitando".equals(status)) {
            itensVenda.add(item);
            valorTotal += item.getValorTotal();
        } else {
            System.out.println("Não é possível adicionar itens a uma venda efetivada.");
        }
    }

    public void efetivarVenda() {
        if ("digitando".equals(status)) {
            status = "efetivada";
        } else {
            System.out.println("A venda já foi efetivada.");
        }
    }

    public void estornarVenda() {
        if ("efetivada".equals(status)) {
            status = "digitando";
        } else {
            System.out.println("A venda não pode ser estornada.");
        }
    }

    
    public void atualizarValorTotal() {
        double novoValorTotal = 0;
        for (ItemVenda item : itensVenda) {
            novoValorTotal += item.getValorTotal();
        }
        setValorTotal(novoValorTotal);
    }

    @Override
    public String toString() {
        return "Venda [id=" + id + ", data=" + data + ", cliente=" + cliente + ", valorTotal=" + valorTotal
                + ", status=" + status + ", itensVenda=" + itensVenda + "]";
    }
}

