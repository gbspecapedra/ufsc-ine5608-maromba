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
    int idPagamento;
    Date dtPagamento;
    Date dtVencimento;
    int matriculaAluno;
    boolean quitado;
    Double valor;
}
