/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import helpers.Alerta;
import helpers.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import libs.Dao;
import models.Aluno;
import models.Modalidade;
import models.Pagamento;

/**
 *
 * @author orlando
 */
public class AlunoDao extends Dao {

    public int persistir(Aluno aluno) throws SQLException, ParseException {
        String sql;
        ResultSet linhas;
        int retorno = 0;

        if (aluno.getMatricula() > 0) {
            // Atualiza um registro
            sql = "UPDATE pessoas SET nome = '" + aluno.getNome() + "', cpf = '" + aluno.getCpf() + "', plano = '" + aluno.getPlano() + "' WHERE id = '" + aluno.getMatricula() + "'";
            this.execute(sql);

            this.atualizarParcelas(aluno);
            this.atualizarModalidades(aluno);
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

    public void atualizarParcelas(Aluno aluno) throws SQLException, ParseException {
        // Limpa as parcelas à vencer
        String sql;
        sql = "DELETE from pagamento where idaluno = " + aluno.getMatricula() + " and dtPagamento is null";
        this.execute(sql);
        Alerta.log(sql);

        // Cadastra as novas parcelas
        for (Pagamento p : aluno.getPagamentos()) {
            String dt = Data.converterParaMysql(p.getDtVencimento());
            sql = "INSERT into pagamento (idAluno, dtVencimento, valor) values (" + aluno.getMatricula() + ", '" + dt + "', " + p.getValor() + ")";
            this.execute(sql);
            Alerta.log(sql);
        }
        Alerta.log("...");
    }

    public void atualizarModalidades(Aluno aluno) throws SQLException, ParseException {
        // Limpa as parcelas à vencer
        String sql;
        sql = "DELETE from aluno_modalidade where idaluno = " + aluno.getMatricula();
        this.execute(sql);
        Alerta.log(sql);

        // Cadastra as novas parcelas
        for (Modalidade m : aluno.getModalidades()) {
            sql = "INSERT into aluno_modalidade (idAluno, idModalidade) values (" + aluno.getMatricula() + ", " + m.getId() + ")";
            Alerta.log(sql);
            this.execute(sql);

        }
        Alerta.log("...");
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

    public ResultSet listarPagamentos(Aluno aluno) throws SQLException {
        ResultSet itens = this.select("select * from pagamento where idAluno = " + aluno.getMatricula());
        return itens;
    }

    public ResultSet listarModalidades(Aluno aluno) throws SQLException {
        String sql = "select * from aluno_modalidade am join modalidades m on am.idModalidade = m.id where am.idAluno = " + aluno.getMatricula();
        ResultSet itens = this.select(sql);
        Alerta.log(sql);
        return itens;
    }

    public ResultSet listar() throws SQLException {
        ResultSet itens = this.select("select * from pessoas where tipo = 'Aluno'");
        return itens;
    }

}
