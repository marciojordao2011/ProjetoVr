package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConexaoPostgreSQL {
    public static void main(String[] args) {
        
        String url = "jdbc:postgresql://localhost:8745/gestao_vendas"; 
        String user = "postgres"; 
        String password = ""; 

        Connection conn = null;

        try {
            
            conn = DriverManager.getConnection(url, user, password);

            
            System.out.println("Conexão com o PostgreSQL estabelecida com sucesso!");
        } catch (SQLException e) {
            
            System.err.println("Erro ao conectar ao PostgreSQL: " + e.getMessage());
        } finally {
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar a conexão: " + e.getMessage());
                }
            }
        }
    }
}
