package models;

import dao.LogDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author orlando
 */
public class Log {

    // ASSOCIAÇÕES
    private LogDao dao;

    // ATRIBUTOS    
    private int id;
    private String descricao;
    private int idusuario;
    private Date data;

    // CONSTRUTOR
    public Log() throws SQLException {
        this.dao = new LogDao();
    }
    
    // MONTA
    public Log montarLog(int id) throws SQLException {
        ResultSet linha = this.dao.retornarLogDAO(id);
        linha.next();
        Log retorno = new Log();
        retorno.setDescricao(linha.getString("descricao"));
        retorno.setId(linha.getInt("id"));
        return retorno;
    }

    // MÈTODOS do CRUD
    public int inserirLog(Log log) throws SQLException {
        return this.dao.inserirLogDAO(log);
    }


    public ObservableList<Log> listarLog() throws SQLException {
        ObservableList<Log> logs = FXCollections.observableArrayList();
        ResultSet linhas = this.dao.listarLogsDAO();
        while (linhas.next()) {

            // Lê os parametros retornados nas tuplas
            String linhaDescricao = linhas.getString("descricao");
            int linhaId = linhas.getInt("id");

            // Inicializa um objeto
            Log log = new Log();
            log.setDescricao(linhaDescricao);
            log.setId(linhaId);

            // Adiciona o objeto ao retorno
            logs.add(log);
        }
        return logs;
    }

    @Override
    public String toString() {
        return descricao;
    }

    // GETTERS E SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }  

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    
}
