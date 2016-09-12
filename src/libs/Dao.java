package libs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author orlando
 */
public class Dao {

    private Connection conexao;
    private Statement statement = null;

//     CONFIGURAÇÃO DO BANCO DE DADOS
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    // static final String DB_URL = "jdbc:mysql://192.168.79.65/cellfix?useSSL=false";
    //  static final String DB_URL = "jdbc:mysql://150.162.79.1:1404/cellfix?useSSL=false";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/cellfix?useSSL=false";
    static final String USER = "root";
    static final String PASS = "zorariver";

    private void conecta() throws SQLException {
//        System.out.println("Conectando...");
        this.conexao = DriverManager.getConnection(DB_URL, USER, PASS);
        this.statement = this.conexao.createStatement();
//        System.out.println("Conexão efetuada com sucesso");
    }

    public ResultSet select(String query) throws SQLException {
        this.conecta();
        ResultSet resultados = statement.executeQuery(query);
        return resultados;
    }

    public void execute(String query) throws SQLException {
        this.conecta();
        this.statement.execute(query);
        this.conexao.close();
    }
}
