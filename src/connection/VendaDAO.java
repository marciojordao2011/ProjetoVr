package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import entities.Venda;

public class VendaDAO {
    private Connection conexao;

    public VendaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserirVenda(Venda venda) {
        if (venda.getCliente() != null) {
            
            String sql = "INSERT INTO venda (data, cliente_id, valor_total, status) VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setDate(1, new java.sql.Date(venda.getData().getTime()));
                stmt.setInt(2, venda.getCliente().getId());
                stmt.setDouble(3, venda.getValorTotal());
                stmt.setString(4, venda.getStatus());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            
            System.out.println("Cliente da venda é nulo. A venda não será inserida.");
        }
    }
}

