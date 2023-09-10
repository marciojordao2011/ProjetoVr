package util;


import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.ClienteVenda;
import entities.Produto;
import entities.Venda;

public class ServicoDeVendaTest {

    private ServicoDeVenda servicoDeVenda;
    private Connection conexao;

    @Before
    public void setUp() {
        

        
        servicoDeVenda = new ServicoDeVenda(conexao);
    }

    @Test
    public void testeAdicionarItemVenda() {
        
        ClienteVenda cliente = new ClienteVenda(1, "Cliente de Teste");
        Venda venda = servicoDeVenda.criarNovaVenda(cliente);
        
        
        Produto produto = new Produto(1, "Produto de Teste", 10.0, 100, conexao);
        
        
        int quantidade = 5;
        servicoDeVenda.adicionarItemVenda(venda, produto, quantidade);
        
        
        assertEquals(1, venda.getItensVenda().size());
        
        
        assertEquals(95, produto.getEstoque());
    }

    

    @After
    public void tearDown() {
        
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


