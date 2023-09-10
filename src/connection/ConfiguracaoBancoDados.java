package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfiguracaoBancoDados {
    public static final String URL = "jdbc:postgresql://localhost:8745/gestao_vendas";
    public static final String USUARIO = "postgres";
    public static final String SENHA = "";
    
    
    public static Connection obterConexao() {
        Connection conexao = null;
        try {
            String url = "jdbc:postgresql://localhost:8745/gestao_vendas"; 
            String usuario = "postgres";
            String senha = "";
            conexao = DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexao;
    }
}


