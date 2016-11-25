/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import libs.Dao;
import models.Aluno;

/**
 *
 * @author orlando
 */
public class AlunoDao extends Dao {

    public int persistir(Aluno aluno) throws SQLException {
        String sql;
        ResultSet linhas;
        int retorno = 0;

        if (aluno.getMatricula() > 0) {
            // Atualiza um registro
            sql = "UPDATE pessoas SET nome = '" + aluno.getNome() + "', cpf = '" + aluno.getCpf() + "', plano = '" + aluno.getPlano() + "' WHERE id = '" + aluno.getMatricula() + "'";
            this.execute(sql);
            retorno = aluno.getMatricula();

        } else {
            // Insere novo registro
            sql = "INSERT into pessoas (nome, cpf, plano, tipo) values ('" + aluno.getNome() + "', '" + aluno.getCpf() + "','" + aluno.getPlano() + "', 'Aluno')";
            this.execute(sql);

            // Retorna o id do novo aluno
            sql = "SELECT id FROM pessoas WHERE cpf = '" + aluno.getCpf() + "'";
            linhas = this.select(sql);
            if (linhas.next()) {
                retorno = linhas.getInt("id");
            }
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
        ResultSet itens = this.select("select * from pessoas where tipo = 'Aluno'");
        return itens;
    }

}
