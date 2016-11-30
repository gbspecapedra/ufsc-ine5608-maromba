package libs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dao {

    private Connection conexao;
    private Statement statement = null;

    //  CONFIGURAÇÃO DO BANCO DE DADOS
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //  static final String DB_URL = "jdbc:mysql://192.168.79.65/cellfix?useSSL=false";
    //  static final String DB_URL = "jdbc:mysql://150.162.79.1:1404/cellfix?useSSL=false";
//    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/cellfix?useSSL=false";
    static final String DB_URL = "jdbc:mysql://msxacademic.mysql.dbaas.com.br:3306/msxacademic?useSSL=false";
//    static final String DB_URL = "jdbc:mysql://192.168.79.32:3306/s02?useSSL=false";
    
            
//    static final String USER = "root";
//    static final String PASS = "zorariver";
    
    static final String USER = "msxacademic";
    static final String PASS = "underwood";
    
//     static final String USER = "sa";
//    static final String PASS = "fithos0932";
    

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

    public Connection getConexao() {
        return conexao;
    }

    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
    
    
    
}
