package util;

import java.util.ArrayList;
import java.util.List;

import entities.ClienteVenda;
import entities.Produto;
import entities.Venda;

public class SistemaDeGestaoDeVendas {
	
	  
    private List<Produto> produtos = new ArrayList<>();
    private List<Venda> vendas = new ArrayList<>();
    private List<ClienteVenda> clientes = new ArrayList<>();
    
    
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }


	public List<Venda> getVendas() {
		return vendas;
	}


	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}


	public List<ClienteVenda> getClientes() {
		return clientes;
	}


	public void setClientes(List<ClienteVenda> clientes) {
		this.clientes = clientes;
	}

}

