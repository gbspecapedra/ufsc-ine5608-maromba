package dao;

import helpers.Alerta;
import java.sql.ResultSet;
import java.sql.SQLException;
import libs.Dao;
import models.Log;

/**
 *
 * @author orlando
 */
public class LogDao extends Dao {

    public LogDao() throws SQLException {
    }

    public int inserirLogDAO(Log log) throws SQLException {
        String query = "INSERT INTO logs (descricao, idpessoa, data) VALUES ('" + log.getDescricao() + "', " + log.getIdusuario() + ", now())";
        Alerta.log(query);
        this.execute(query);
        int retorno = 0;
        ResultSet linhas = this.select("select max(id) as id from logs");
        if (linhas.next()) {
            retorno = linhas.getInt("id");
        }
        return retorno;
    }

    public ResultSet listarLogsDAO() throws SQLException {
        ResultSet resultados = this.select("select * from logs order by id desc");
        return resultados;
    }

    public ResultSet retornarLogDAO(int id) throws SQLException {
        ResultSet resultados = this.select("select * from logs where id = " + id);
        return resultados;
    }
}
