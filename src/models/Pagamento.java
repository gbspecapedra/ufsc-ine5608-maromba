/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dao.PagamentoDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author orlando
 */
public class Pagamento {

    int matriculaAluno;
    int idPagamento;
    Date dtPagamento;
    Date dtVencimento;
    Double valor;
    String situacao;

    private PagamentoDao dao;

//    public Pagamento(Date dtVencimento, int matriculaAluno, Double valor){
//        this.dtVencimento = dtVencimento;
//        this.matriculaAluno = matriculaAluno;
//        this.valor = valor;
//    }
    public Pagamento() {
        this.dao = new PagamentoDao();
    }

    public ArrayList<Pagamento> listarPagamentosPorPeriodo(String dtIni, String dtFim) throws SQLException, ParseException {
        ResultSet linhas = this.dao.listarPagamentosPorPeriodoDao(dtIni, dtFim);
        ArrayList<Pagamento> retorno = new ArrayList<>();
        Pagamento pagamento = new Pagamento();

        while (linhas.next()) {
            pagamento = new Pagamento();
            pagamento.setIdPagamento(linhas.getInt("id"));
            pagamento.setValor(linhas.getDouble("valor"));
            pagamento.setDtPagamento(linhas.getDate("dtPagamento"));
            pagamento.setDtVencimento(linhas.getDate("dtVencimento"));
            pagamento.setMatriculaAluno(linhas.getInt("idAluno"));
            retorno.add(pagamento);
        }
        return retorno;

    }

    public Date getDtPagamento() {
        return dtPagamento;
    }

    public void setDtPagamento(Date dtPagamento) {
        this.dtPagamento = dtPagamento;
        if (this.dtPagamento != null) {
            this.situacao = "Quitado";
        } else {
            this.situacao = "Pendente";
        }
    }

    public Date getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(Date dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public int getMatriculaAluno() {
        return matriculaAluno;
    }

    public void setMatriculaAluno(int matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public int getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

}
