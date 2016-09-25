package dao;

import helpers.Validador;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import libs.Dao;
import models.Modalidade;

/**
 *
 * @author orlando
 */
public class ModalidadeDao extends Dao {

    public ModalidadeDao() throws SQLException {
    }

    public int inserirModalidadeDAO(Modalidade modalidade) throws SQLException, NoSuchAlgorithmException {
        this.execute("INSERT INTO modalidades (nome, valor, diasSemana ) VALUES ('" + modalidade.getNome() + "', '" + modalidade.getValor() + "', '" + modalidade.getDiasSemana()+ "')");
        int retorno = 0;
        ResultSet linhas = this.select("select id from modalidades order by id desc limit 1");
        if (linhas.next()) {
            retorno = linhas.getInt("id");
        }
        this.atualizarSenhaDAO(retorno, Validador.converterMD5(String.valueOf(retorno)));
        return retorno;
    }

    public ResultSet listarModalidadesDAO() throws SQLException {
        ResultSet resultados = this.select("select * from modalidades order by nome desc");
        return resultados;
    }
    
    public ResultSet listarTecnicosDAO() throws SQLException {
        ResultSet resultados = this.select("select * from modalidades order by nome desc");
        return resultados;
    }

    public boolean atualizarModalidadeDAO(Modalidade modalidade) throws SQLException {
        try {
            this.execute("UPDATE modalidades SET nome = '" + modalidade.getNome() + "' WHERE id = " + modalidade.getId() + "");
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean deletarModalidadeDAO(Modalidade modalidade) throws SQLException {
        try {
            String query = "DELETE from modalidades WHERE id = " + modalidade.getId() + "";
            System.out.println(query);
            this.execute(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ResultSet retornarModalidadeDAO(int id) throws SQLException {
        ResultSet resultados = this.select("select * from modalidades where id = " + id);
        return resultados;
    }

    
    
    public boolean atualizarSenhaDAO(int id, String senha){
        try {           
            this.execute("UPDATE modalidades SET senha = '" + senha + "', usuario = "+id+" WHERE id = " + id + "");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
