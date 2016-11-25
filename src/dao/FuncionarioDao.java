/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public int persistir(Funcionario funcionario) throws SQLException, NoSuchAlgorithmException {
        String sql;
        ResultSet linhas;
        int retorno = 0;
        String novaSenha = "";

        if (funcionario.getMatricula() > 0) {
            // Atualiza um registro
            sql = "UPDATE pessoas SET nome = '" + funcionario.getNome() + "', cpf = '" + funcionario.getCpf() + "', ctps = '" + funcionario.getCtps() + "', funcao = '" + funcionario.getFuncao() + "' WHERE id = '" + funcionario.getMatricula() + "'";
            this.execute(sql);
            retorno = funcionario.getMatricula();

        } else {
            // Insere novo registro
            sql = "INSERT into pessoas (nome, cpf, funcao, tipo, ctps) values ('" + funcionario.getNome() + "', '" + funcionario.getCpf() + "','" + funcionario.getFuncao() + "', 'Funcionario', '" + funcionario.getCtps() + "')";
            this.execute(sql);

            // Retorna o id do novo funcionario
            sql = "SELECT id FROM pessoas WHERE cpf = '" + funcionario.getCpf() + "'";
            linhas = this.select(sql);
            if (linhas.next()) {
                retorno = linhas.getInt("id");
            }
            
            // Cria a nova senha
            novaSenha = Validador.converterMD5(Integer.toString(retorno));
            sql = "UPDATE pessoas set senha = '"+novaSenha+"' where id = "+retorno;
            this.execute(sql);
            
        }
        return retorno;
    }

    public int deletar(int matricula) throws SQLException {

        // VERIFICAR SE NÃO HÁ PAGAMENTOS PENDENTES        
        String sql = "DELETE FROM pessoas WHERE id = " + matricula;
        try {
            this.execute(sql);
            return matricula;
        } catch (SQLException e) {

        }
        return 0;
    }

    public ResultSet listar() throws SQLException {
        ResultSet itens = this.select("select * from pessoas where tipo = 'Funcionario'");
        return itens;
    }

    public String verificarCredenciais(int matricula, String senha) throws NoSuchAlgorithmException, SQLException {
        
        String mensagem = "0";
        
        String query = "select id from pessoas where id = '" + matricula + "' and senha = '" + Validador.converterMD5(senha) + "' and tipo like 'Funcionario'";
        System.out.println(query);
        ResultSet linhas = this.select(query);
        if (linhas.next()) {
            mensagem = "1";
        } else {
            mensagem = "Senha Icorreta";           
        }
        
        return mensagem;
    }

}
