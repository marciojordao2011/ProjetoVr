package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Produto {
    private int id;
    private String descricao;
    private double preco;
    private int quantidade;
    private int estoque;
    private Connection conexao;

    public Produto(int id, String descricao, double preco, int quantidade, Connection conexao) {
        this.conexao = conexao;
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.estoque = quantidade; 
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    private void atualizarEstoqueBancoDeDados(Produto produto) {
        try {
            String sql = "UPDATE produto SET estoque = ? WHERE id = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, produto.getEstoque());
            stmt.setInt(2, produto.getId());
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Estoque atualizado no banco de dados.");
            } else {
                System.out.println("Nenhuma linha afetada. A atualização pode ter falhado.");
            }

            
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void atualizarEstoque(int quantidade) {
        this.estoque += quantidade;
        atualizarEstoqueBancoDeDados(this); 
    }

    @Override
    public String toString() {
        return "Produto [id=" + id + ", descricao=" + descricao + ", preco=" + preco + ", quantidade=" + quantidade
                + ", estoque=" + estoque + "]";
    }
}
