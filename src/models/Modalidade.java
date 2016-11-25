/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dao.ModalidadeDao;
import helpers.Alerta;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Orlando
 */
public class Modalidade extends Pessoa {

    private int Id;
    private String diasSemana;
    private Double valor;
    private ModalidadeDao dao;

    public Modalidade() throws SQLException {
        this.dao = new ModalidadeDao();

    }

    public int persistir() throws SQLException {
        
        // Verifica se já existe um aluno com esse CPF
        ObservableList<Modalidade> modalidades = this.listarModalidades();
        for (Modalidade modalidade : modalidades) {
            if((this.getNome().equals(modalidade.getNome())) && this.getId() == 0){                
                return -2;
            }
        }
        
        
        return this.dao.persistir(this);
    }

    public int deletar() throws SQLException {

        // VERIFICAR SE NÃO HÁ ALUNOS MATRICULADOS
        this.dao.deletar(this.getId());
        Alerta.informar("Dados excluídos com sucesso.");
        return 0;
    }

    public ObservableList<Modalidade> listarModalidades() throws SQLException {
        ObservableList<Modalidade> modalidades = FXCollections.observableArrayList();
        ResultSet linhas = this.dao.listar();
        while (linhas.next()) {

            // Inicializa um objeto
            Modalidade modalidade = new Modalidade();
            modalidade.setId(linhas.getInt("id"));
            modalidade.setNome(linhas.getString("nome"));
            modalidade.setValor(linhas.getDouble("valor"));
            modalidade.setDiasSemana(linhas.getString("diasSemana"));

            // Adiciona o objeto ao retorno
            modalidades.add(modalidade);
        }
        return modalidades;
    }

    public String validarModelo() {

        
        if (this.getNome().isEmpty()) {
            return "Os campos em vermelho são de preenchimehto obrigatório.";
        }
        
        if (this.getValor() <= 0) {
            return "O valor deve ser maior do que zero.";
        }
        
        if (this.getDiasSemana().isEmpty()) {
            return "Selecione ao menos um dia da semana.";
            
        }

        return "0";
    }

    public String getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(String diasSemana) {
        this.diasSemana = diasSemana;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }
            
}
