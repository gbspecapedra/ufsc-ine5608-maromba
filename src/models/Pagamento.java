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
    Date dtPagamento;
    Date dtVencimento;
    int matriculaAluno;
    Double valor;
    
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
    
    
}
