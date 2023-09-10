package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDados {
	public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(ConfiguracaoBancoDados.URL, ConfiguracaoBancoDados.USUARIO, ConfiguracaoBancoDados.SENHA);
    }
}
