/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dao.AlunoDao;
import helpers.Alerta;
import helpers.Validador;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Orlando
 */
public class Aluno extends Pessoa {

    private String plano;
    private AlunoDao dao;
    private HashMap<Modalidade, Integer> modalidades;
    private ObservableList<Pagamento> pagamentos;
    private ObservableList<Frequencia> frequencia;

    public Aluno() throws SQLException {
        this.dao = new AlunoDao();
    }

    public int persistir() throws SQLException {

        int retorno;
        
        // Verifica se já existe um aluno com esse CPF
        ObservableList<Aluno> alunos = this.listarAlunos();
        for (Aluno aluno : alunos) {
            if((aluno.getCpf().equals(this.getCpf()) && aluno.getMatricula() != this.getMatricula())){
                return -1;
            }
        }
      
        retorno = this.dao.persistir(this);
        return retorno;
    }

    public int deletar() throws SQLException {

        // VERIFICAR SE NÃO HÁ PAGAMENTOS PENDENTES
        this.dao.deletar(this.getMatricula());
        Alerta.informar("Dados excluídos com sucesso.");
        return 0;
    }

    public ObservableList<Aluno> listarAlunos() throws SQLException {
        ObservableList<Aluno> alunos = FXCollections.observableArrayList();
        ResultSet linhas = this.dao.listar();
        while (linhas.next()) {

            // Inicializa um objeto
            Aluno aluno = new Aluno();
            aluno.setNome(linhas.getString("nome"));
            aluno.setMatricula(linhas.getInt("id"));
            aluno.setPlano(linhas.getString("plano"));
            aluno.setCpf(linhas.getString("cpf"));

            // Adiciona o objeto ao retorno
            alunos.add(aluno);
        }
        return alunos;
    }

    public String validarModelo() {

        if (this.getPlano().isEmpty()) {
            return "Os campos em vermelho são de preenchimehto obrigatório.";
        }

        if (this.getNome().isEmpty()) {
            return "Os campos em vermelho são de preenchimehto obrigatório.";
        }

        if (this.getCpf().isEmpty()) {
            return "Os campos em vermelho são de preenchimehto obrigatório.";
        } else {
            if (!Validador.validarCPF(this.getCpf())) {
                return "O CPF informado não é válido.";
            }
        }

        return "0";
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

}
