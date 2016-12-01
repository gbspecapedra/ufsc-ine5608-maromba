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
            Alerta.log(sql);
            this.execute(sql);

            this.atualizarParcelas(aluno);
            this.atualizarModalidades(aluno);
            retorno = aluno.getMatricula();

        } else {
            // Insere novo registro
            sql = "INSERT into pessoas (nome, cpf, plano, tipo) values ('" + aluno.getNome() + "', '" + aluno.getCpf() + "','" + aluno.getPlano() + "', 'Aluno')";
            Alerta.log(sql);
            this.execute(sql);

            // Retorna o id do novo aluno
            sql = "SELECT id FROM pessoas WHERE cpf = '" + aluno.getCpf() + "'";
            Alerta.log(sql);
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
        Alerta.log(sql);
        try {
            this.execute(sql);
            return matricula;
        } catch (SQLException e) {

        }
        return 0;
    }

    public ResultSet listarPagamentos(Aluno aluno) throws SQLException {
        String sql = "select * from pagamento where idAluno = " + aluno.getMatricula();
        ResultSet itens = this.select(sql);
        Alerta.log(sql);
        return itens;
    }

    public ResultSet listarModalidades(Aluno aluno) throws SQLException {
        String sql = "select * from aluno_modalidade am join modalidades m on am.idModalidade = m.id where am.idAluno = " + aluno.getMatricula();
        ResultSet itens = this.select(sql);
        Alerta.log(sql);
        return itens;
    }

    public ResultSet listar() throws SQLException {
        String sql = "select * from pessoas where tipo = 'Aluno'";
        ResultSet itens = this.select(sql);
        Alerta.log(sql);
        return itens;
    }

    public boolean verificarAdimplenteDao(Aluno aluno) throws SQLException {
        String sql = "select * from pagamento where idAluno = " + aluno.getMatricula() + " and dtPagamento > (MONTH(CURRENT_DATE - INTERVAL 1 MONTH))";
        Alerta.log(sql);
        ResultSet itens = this.select(sql);
        return itens.next();
    }

    public boolean verificarModalidadeDao(Aluno aluno) throws SQLException {
        String diaSemana = "";
        diaSemana = Data.diaDaSemana();
        
        if(diaSemana.equals("Dom")){
            return false;
        }
        
        String sql = "select p.nome from pessoas p \n"
                + "join aluno_modalidade am on p.id = am.idAluno\n"
                + "join modalidades m on am.idModalidade = m.id\n"
                + "where p.tipo = 'Aluno'\n"
                + "and p.id = " + aluno.getMatricula() + "\n"
                + "and m.diasSemana like '%" + diaSemana + "%'";
        Alerta.log(sql);
        ResultSet itens = this.select(sql);
        return itens.next();
    }

    public boolean registrarFrequenciaDao(Aluno aluno) throws SQLException {
        String sql;
        sql = "INSERT into frequencia (idAluno, data) values (" + aluno.getMatricula() + ", NOW())";
        Alerta.log(sql);
        try {
            this.execute(sql);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void baixarPagamentoDao(Pagamento pagamento) throws SQLException {
        String sql;
        sql = "update pagamento set dtPagamento = NOW() where id = " + pagamento.getIdPagamento();
        Alerta.log(sql);
        this.execute(sql);
    }

    public ResultSet listarFrequencia(Aluno aluno) throws SQLException {
        String sql;
        sql = "select * from frequencia where idAluno = " + aluno.getMatricula();
        Alerta.log(sql);
        ResultSet itens = this.select(sql);
        return itens;
    }

    public ResultSet listarInadimplentes() throws SQLException {
        String sql = "select distinct (a.id), a.nome, a.plano, a.cpf from pessoas a join pagamento p on p.idAluno = a.id where tipo = 'Aluno' and  a.id not in (SELECT idAluno FROM pagamento WHERE dtPagamento BETWEEN DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW())";
        ResultSet itens = this.select(sql);
        Alerta.log(sql);
        return itens;
    }

}
