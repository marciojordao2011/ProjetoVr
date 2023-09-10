package junit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfiguracaoBancoDadosTest {
    public static Connection obterConexao() throws SQLException {
        String url = "jdbc:postgresql://localhost:8745/gestao_vendas";
        String usuario = "postgres";
        String senha = "";
        
        return DriverManager.getConnection(url, usuario, senha);
    }
}
