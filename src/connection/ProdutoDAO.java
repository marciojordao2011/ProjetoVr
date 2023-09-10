package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Produto;

public class ProdutoDAO {
    private Connection conexao;

    
    public ProdutoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    
    public void inserirProduto(Produto produto) {
        String sql = "INSERT INTO produto (descricao, preco, quantidade) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, produto.getDescricao());
            preparedStatement.setDouble(2, produto.getPreco());
            preparedStatement.setInt(3, produto.getQuantidade());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }

    
    public List<Produto> listarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Produto produto = new Produto(0, sql, 0, 0, conexao);
                produto.setId(resultSet.getInt("id"));
                produto.setDescricao(resultSet.getString("descricao"));
                produto.setPreco(resultSet.getDouble("preco"));
                produto.setQuantidade(resultSet.getInt("quantidade"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
        }

        return produtos;
    }
    
    
}
