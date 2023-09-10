package entities;

import util.ServicoDeVenda;

public class ItemVenda {
    private int idProduto;
    private int quantidade;
    private double preco;
    private double valorTotal;

    public ItemVenda(int idProduto, int quantidade, double preco) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.preco = preco;
        this.valorTotal = preco * quantidade;
    }

    public ItemVenda() {
        this.idProduto = 0;
        this.quantidade = 0;
        this.preco = 0.0;
        this.valorTotal = 0.0;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.valorTotal = preco * quantidade; 
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getProdutoId() {
        return idProduto;
    }

    public Produto getProduto(ServicoDeVenda servicoDeVenda) {
         
        Produto produto = servicoDeVenda.obterProdutoPorId(idProduto);

        return produto;
    }

	public int getIdVenda() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
