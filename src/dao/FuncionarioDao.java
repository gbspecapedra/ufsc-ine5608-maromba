package dao;

import helpers.Validador;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import libs.Dao;
import models.Funcionario;

/**
 *
 * @author orlando
 */
public class FuncionarioDao extends Dao {

    public FuncionarioDao() throws SQLException {
    }

    public int inserirFuncionarioDAO(Funcionario funcionario) throws SQLException, NoSuchAlgorithmException {
        this.execute("INSERT INTO pessoas (nome, funcao) VALUES ('" + funcionario.getNome() + "', 't')");
        int retorno = 0;
        ResultSet linhas = this.select("select id from pessoas order by id desc limit 1");
        if (linhas.next()) {
            retorno = linhas.getInt("id");
        }
        this.atualizarSenhaDAO(retorno, Validador.converterMD5(String.valueOf(retorno)));
        return retorno;
    }

    public ResultSet listarFuncionariosDAO() throws SQLException {
        ResultSet resultados = this.select("select * from pessoas where funcao = 'g' or funcao = 't' order by nome desc");
        return resultados;
    }
    
    public ResultSet listarTecnicosDAO() throws SQLException {
        ResultSet resultados = this.select("select * from pessoas where funcao = 't' order by nome desc");
        return resultados;
    }

    public boolean atualizarFuncionarioDAO(Funcionario funcionario) throws SQLException {
        try {
            this.execute("UPDATE pessoas SET nome = '" + funcionario.getNome() + "' WHERE id = " + funcionario.getMatricula() + "");
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean deletarFuncionarioDAO(Funcionario funcionario) throws SQLException {
        try {
            String query = "DELETE from pessoas WHERE id = " + funcionario.getMatricula() + "";
            System.out.println(query);
            this.execute(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ResultSet retornarFuncionarioDAO(int id) throws SQLException {
        ResultSet resultados = this.select("select * from pessoas where id = " + id);
        return resultados;
    }

    public int verificarCredenciaisDAO(String usuario, String senha) throws SQLException, NoSuchAlgorithmException {

        String query = "select id from pessoas where usuario = '" + usuario + "' and senha = '" + Validador.converterMD5(senha) + "' and (funcao = 't' or funcao = 'g')";

        ResultSet retorno = this.select(query);

        int i = 0;
        int id = 0;

        while (retorno.next()) {
            i++;
            id = retorno.getInt("id");
        }

        if (i == 1) {
            return id;
        }

        return 0;
    }
    
    public boolean atualizarSenhaDAO(int id, String senha){
        try {           
            this.execute("UPDATE pessoas SET senha = '" + senha + "', usuario = "+id+" WHERE id = " + id + "");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
