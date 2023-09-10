package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import entities.ItemVenda;

public class ItemVendaDAO {
    private Connection conexao;

    public ItemVendaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserirItemVenda(ItemVenda itemVenda) {
        String sql = "INSERT INTO item_venda (id_venda, id_produto, quantidade, preco, valor_total) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, itemVenda.getIdVenda());
            ps.setInt(2, itemVenda.getIdProduto());
            ps.setInt(3, itemVenda.getQuantidade());
            ps.setDouble(4, itemVenda.getPreco());
            ps.setDouble(5, itemVenda.getValorTotal());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
