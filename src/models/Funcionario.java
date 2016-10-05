package models;

import dao.FuncionarioDao;
import helpers.Validador;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import libs.Dao;

public class Funcionario extends Pessoa {
    
    // ASSOCIAÇÕES
    private final Dao dao;
    
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
//      ResultSet linha = this.dao.retornarFuncionarioDAO(id);
        ResultSet linha = this.dao.select("select * from pessoas where id = " + id);
        linha.next();
        Funcionario retorno = new Funcionario();
        retorno.setNome(linha.getString("nome"));
        retorno.setMatricula(linha.getInt("id"));
        retorno.setFuncao(linha.getString("funcao"));
        return retorno;
    }
    
    
    public int verificarCredenciaisDAO(String usuario, String senha) throws SQLException, NoSuchAlgorithmException {
        String query = "select id from pessoas where usuario = '" + usuario + "' and senha = '" + Validador.converterMD5(senha) + "' and (funcao = 't' or funcao = 'g')";
        ResultSet retorno = this.dao.select(query);
        int i = 0;
        int id = 0;
        while (retorno.next()) {
            i++;
            id = retorno.getInt("id");
        }
        if (i == 1) {
            return id;
        }
        return 0;
    }
    
    
    // MÈTODOS do CRUD
//    public int inserirFuncionario(Funcionario funcionario) throws SQLException, NoSuchAlgorithmException {
//        return this.dao.inserirFuncionarioDAO(funcionario);
//    }
//
//    public boolean atualizarFuncionario(Funcionario funcionario) throws SQLException {
//        return this.dao.atualizarFuncionarioDAO(funcionario);
//    }
//
//    public boolean deletarFuncionario(Funcionario funcionario) throws SQLException {
//        return this.dao.deletarFuncionarioDAO(funcionario);
//    }
//
//    
//    
//    public ObservableList<Funcionario> listarFuncionario() throws SQLException {
//        ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
//        ResultSet linhas = this.dao.listarFuncionariosDAO();
//        while (linhas.next()) {
//
//            // Lê os parametros retornados nas tuplas
//            String linhaNome = linhas.getString("nome");
//            String linhaFuncao = linhas.getString("funcao");
//            int linhaMatricula = linhas.getInt("id");
//            
//            
//            // Inicializa um objeto
//            Funcionario funcionario = new Funcionario();
//            funcionario.setNome(linhaNome);
//            funcionario.setMatricula(linhaMatricula);
//            
//            if(linhaFuncao.equals("t")){
//                funcionario.setFuncao("Técnico");
//            } else {
//                funcionario.setFuncao("Gerente");
//            }
//
//            // Adiciona o objeto ao retorno
//            funcionarios.add(funcionario);
//        }
//        return funcionarios;
//    }
    
//    public ObservableList<Funcionario> listarTecnico() throws SQLException {
//        ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
//        ResultSet linhas = this.dao.listarTecnicosDAO();
//        while (linhas.next()) {
//
//            // Lê os parametros retornados nas tuplas
//            String linhaNome = linhas.getString("nome");
//            String linhaFuncao = linhas.getString("funcao");
//            int linhaMatricula = linhas.getInt("id");
//            
//            
//            // Inicializa um objeto
//            Funcionario funcionario = new Funcionario();
//            funcionario.setNome(linhaNome);
//            funcionario.setMatricula(linhaMatricula);
//            
//            // Adiciona o objeto ao retorno
//            funcionarios.add(funcionario);
//        }
//        return funcionarios;
//    }
    
    public Funcionario verificarCredenciais(String usuario, String senha) throws SQLException, NoSuchAlgorithmException {
        Funcionario funcionarioAutenticado = new Funcionario();
        funcionarioAutenticado.setMatricula(1);
        
        int id = this.verificarCredenciaisDAO(usuario, senha);
        if (id > 0) {
            funcionarioAutenticado = this.montarFuncionario(id);
        }
        funcionarioAutenticado.setNome("Aeee");
        return funcionarioAutenticado;
    }
    
//    public boolean atualizarSenha(int id, String senha){
//        return this.dao.atualizarSenhaDAO(id, senha);
//    }
    
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
