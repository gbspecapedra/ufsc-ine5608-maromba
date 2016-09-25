/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dao.ModalidadeDao;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author orlando
 */
public class Modalidade extends Pessoa {
    
    // ASSOCIAÇÕES
    private final ModalidadeDao dao;
    
    // ATRIBUTOS    
    private String nome;
    private int valor;
    private String diasSemana;
    
    // CONSTRUTOR
    public Modalidade() throws SQLException {
        this.dao = new ModalidadeDao();
    }

    // MONTA
    public Modalidade montarModalidade(int id) throws SQLException {
        ResultSet linha = this.dao.retornarModalidadeDAO(id);
        linha.next();
        Modalidade retorno = new Modalidade();
        retorno.setNome(linha.getString("nome"));
        retorno.setValor(linha.getInt("valor"));
        retorno.setDiasSemana(linha.getString("diasSemana"));
        return retorno;
    }

    // MÈTODOS do CRUD
    public int inserirModalidade(Modalidade modalidade) throws SQLException, NoSuchAlgorithmException {
        return this.dao.inserirModalidadeDAO(modalidade);
    }

    public boolean atualizarModalidade(Modalidade modalidade) throws SQLException {
        return this.dao.atualizarModalidadeDAO(modalidade);
    }

    public boolean deletarModalidade(Modalidade modalidade) throws SQLException {
        return this.dao.deletarModalidadeDAO(modalidade);
    }

    
    
    public ObservableList<Modalidade> listarModalidade() throws SQLException {
        ObservableList<Modalidade> modalidades = FXCollections.observableArrayList();
        ResultSet linhas = this.dao.listarModalidadesDAO();
        while (linhas.next()) {

            // Lê os parametros retornados nas tuplas
            String linhaNome = linhas.getString("nome");
            int linhaValor = linhas.getInt("valor");
//            int linhaMatricula = linhas.getInt("id");
            
            
            // Inicializa um objeto
            Modalidade modalidade = new Modalidade();
            modalidade.setNome(linhaNome);
            

            // Adiciona o objeto ao retorno
            modalidades.add(modalidade);
        }
        return modalidades;
    }
    
    public ObservableList<Modalidade> listarTecnico() throws SQLException {
        ObservableList<Modalidade> modalidades = FXCollections.observableArrayList();
        ResultSet linhas = this.dao.listarTecnicosDAO();
        while (linhas.next()) {

            // Lê os parametros retornados nas tuplas
            String linhaNome = linhas.getString("nome");
            String linhaValor = linhas.getString("valor");
            int linhaMatricula = linhas.getInt("id");
            
            
            // Inicializa um objeto
            Modalidade modalidade = new Modalidade();
            modalidade.setNome(linhaNome);
           
            
            // Adiciona o objeto ao retorno
            modalidades.add(modalidade);
        }
        return modalidades;
    }
    
    
    
    public boolean atualizarSenha(int id, String senha){
        return this.dao.atualizarSenhaDAO(id, senha);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(String diasSemana) {
        this.diasSemana = diasSemana;
    }
   
}
