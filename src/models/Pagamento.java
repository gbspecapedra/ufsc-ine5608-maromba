/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

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

//    public Pagamento(Date dtVencimento, int matriculaAluno, Double valor){
//        this.dtVencimento = dtVencimento;
//        this.matriculaAluno = matriculaAluno;
//        this.valor = valor;
//    }
    public Pagamento() {

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
