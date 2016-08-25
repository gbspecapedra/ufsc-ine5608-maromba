/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dao.FuncionarioDao;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author orlando
 */
public class Funcionario extends Pessoa {
    
    // ASSOCIAÇÕES
    private final FuncionarioDao dao;
    
    // ATRIBUTOS    
    private Date dtAdmissao;
    private String funcao;
    private int matricula;
    private String senha;
    
    // CONSTRUTOR
    public Funcionario() throws SQLException {
        this.dao = new FuncionarioDao();
    }

    // MONTA
    public Funcionario montarFuncionario(int id) throws SQLException {
        ResultSet linha = this.dao.retornarFuncionarioDAO(id);
        linha.next();
        Funcionario retorno = new Funcionario();
        retorno.setNome(linha.getString("nome"));
        retorno.setMatricula(linha.getInt("id"));
        retorno.setFuncao(linha.getString("funcao"));
        return retorno;
    }

    // MÈTODOS do CRUD
    public int inserirFuncionario(Funcionario funcionario) throws SQLException, NoSuchAlgorithmException {
        return this.dao.inserirFuncionarioDAO(funcionario);
    }

    public boolean atualizarFuncionario(Funcionario funcionario) throws SQLException {
        return this.dao.atualizarFuncionarioDAO(funcionario);
    }

    public boolean deletarFuncionario(Funcionario funcionario) throws SQLException {
        return this.dao.deletarFuncionarioDAO(funcionario);
    }

    
    
    public ObservableList<Funcionario> listarFuncionario() throws SQLException {
        ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
        ResultSet linhas = this.dao.listarFuncionariosDAO();
        while (linhas.next()) {

            // Lê os parametros retornados nas tuplas
            String linhaNome = linhas.getString("nome");
            String linhaFuncao = linhas.getString("funcao");
            int linhaMatricula = linhas.getInt("id");
            
            
            // Inicializa um objeto
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(linhaNome);
            funcionario.setMatricula(linhaMatricula);
            
            if(linhaFuncao.equals("t")){
                funcionario.setFuncao("Técnico");
            } else {
                funcionario.setFuncao("Gerente");
            }

            // Adiciona o objeto ao retorno
            funcionarios.add(funcionario);
        }
        return funcionarios;
    }
    
    public ObservableList<Funcionario> listarTecnico() throws SQLException {
        ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
        ResultSet linhas = this.dao.listarTecnicosDAO();
        while (linhas.next()) {

            // Lê os parametros retornados nas tuplas
            String linhaNome = linhas.getString("nome");
            String linhaFuncao = linhas.getString("funcao");
            int linhaMatricula = linhas.getInt("id");
            
            
            // Inicializa um objeto
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(linhaNome);
            funcionario.setMatricula(linhaMatricula);
            
            // Adiciona o objeto ao retorno
            funcionarios.add(funcionario);
        }
        return funcionarios;
    }
    
    public Funcionario verificarCredenciais(String usuario, String senha) throws SQLException, NoSuchAlgorithmException {
        Funcionario funcionarioAutenticado = new Funcionario();
        int id = this.dao.verificarCredenciaisDAO(usuario, senha);
        if (id > 0) {
            funcionarioAutenticado = this.montarFuncionario(id);
        }
        return funcionarioAutenticado;
    }
    
    public boolean atualizarSenha(int id, String senha){
        return this.dao.atualizarSenhaDAO(id, senha);
    }
    
    public Date getDtAdmissao() {
        return dtAdmissao;
    }

    public void setDtAdmissao(Date dtAdmissao) {
        this.dtAdmissao = dtAdmissao;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
