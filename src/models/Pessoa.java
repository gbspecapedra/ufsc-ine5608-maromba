/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import libs.Model;

/**
 *
 * @author orlando
 */
public class Pessoa extends Model {

    private int matricula;
    private String cpf;
    private String nome;
    private String tipo;

    public Pessoa() throws SQLException {
    }

    public boolean verificarCpfCadastrado() throws SQLException {

        String sql;
        ResultSet linhas;

//        sql = "select id from pessoas where cpf = '" + this.getCpf() + "' and id <> '" + this.getMatricula() + "' and tipo = '"+ this.getTipo()+"'";
//        linhas = Dao.select(sql);
//        return linhas.next();
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
