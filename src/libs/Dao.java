package libs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dao {

    private static Connection conexao;
    private static Statement statement = null;

    //  CONFIGURAÇÃO DO BANCO DE DADOS
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //  static final String DB_URL = "jdbc:mysql://192.168.79.65/cellfix?useSSL=false";
    //  static final String DB_URL = "jdbc:mysql://150.162.79.1:1404/cellfix?useSSL=false";
//    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/cellfix?useSSL=false";
    static final String DB_URL = "jdbc:mysql://msxacademic.mysql.dbaas.com.br:3306/msxacademic?useSSL=false";
    
            
//    static final String USER = "root";
//    static final String PASS = "zorariver";
    
    static final String USER = "msxacademic";
    static final String PASS = "underwood";
    

    private static void conecta() throws SQLException {
//        System.out.println("Conectando...");
        Dao.conexao = DriverManager.getConnection(DB_URL, USER, PASS);
        Dao.statement = Dao.conexao.createStatement();
//        System.out.println("Conexão efetuada com sucesso");
    }

    public static ResultSet select(String query) throws SQLException {
        Dao.conecta();
        ResultSet resultados = statement.executeQuery(query);
        return resultados;
    }

    public static void execute(String query) throws SQLException {
        Dao.conecta();
        Dao.statement.execute(query);
        Dao.conexao.close();
    }
}
