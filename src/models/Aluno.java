/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dao.AlunoDao;
import helpers.Alerta;
import helpers.Validador;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Orlando
 */
public class Aluno extends Pessoa {

    private String plano;
    private AlunoDao dao;
    private ArrayList<Modalidade> modalidades;
    private ArrayList<Pagamento> pagamentos;
    private ArrayList<Frequencia> frequencia;

    public Aluno() throws SQLException {
        this.dao = new AlunoDao();
        this.modalidades = new ArrayList<Modalidade>();
        this.pagamentos = new ArrayList<Pagamento>();
        this.frequencia = new ArrayList<Frequencia>();
    }

    public int persistir() throws SQLException, ParseException {

        int retorno;

        // Verifica se já existe um aluno com esse CPF
        ObservableList<Aluno> alunos = this.listarAlunos();
        for (Aluno aluno : alunos) {
            if ((aluno.getCpf().equals(this.getCpf()) && aluno.getMatricula() != this.getMatricula())) {
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
    
    
    public Aluno verificarCredenciais(String matricula) throws SQLException, NoSuchAlgorithmException {
        Aluno alunoAutenticado = new Aluno();
        if (this.verificarCredenciaisBD(Integer.parseInt(matricula))) {
            alunoAutenticado = this.montarAluno(Integer.parseInt(matricula));
        }
        return alunoAutenticado;
    }
    
    public Aluno montarAluno(int matricula) throws SQLException {
        ObservableList<Aluno> alunos = this.listarAlunos();
        for (Aluno a : alunos) {
            if (a.getMatricula() == matricula) {
                return a;
            }
        }
        return null;
    }
    
    
    public boolean verificarCredenciaisBD(int matricula) throws SQLException, NoSuchAlgorithmException {

        String mensagem = "";
        boolean retorno = false;
        boolean matriculaValida = false;

        // Verifica se a matrícula informada é válida
        ObservableList<Aluno> alunos = this.listarAlunos();
        for (Aluno a : alunos) {
            if (a.getMatricula() == matricula) {
                matriculaValida = true;
            }
        }

        if (!matriculaValida) {
            mensagem = "Matrícula Inexistente";
            Alerta.informar(mensagem);
            return false;
        } else {
             return true;
        }

       

    }
    
    
    
    public ObservableList<Aluno> listarAlunos() throws SQLException {
        ObservableList<Aluno> alunos = FXCollections.observableArrayList();
//        ObservableList<Modalidade> modalidades = FXCollections.observableArrayList();
//        ArrayList<Modalidade> modalidadesBase = new ArrayList<>();
//        ObservableList<Pagamento> pagamentos = FXCollections.observableArrayList();
//        ObservableList<Aluno> alunos = FXCollections.observableArrayList();
        ResultSet linhas = this.dao.listar();
        while (linhas.next()) {

            // Inicializa um objeto
            Aluno aluno = new Aluno();
            Modalidade modalidade = new Modalidade();
            Pagamento pagamento = new Pagamento();

            aluno.setNome(linhas.getString("nome"));
            aluno.setMatricula(linhas.getInt("id"));
            aluno.setPlano(linhas.getString("plano"));
            aluno.setCpf(linhas.getString("cpf"));

            // Modalidades
            ResultSet linhasModalidades = this.dao.listarModalidades(aluno);
            while (linhasModalidades.next()) {
                modalidade.setId(linhasModalidades.getInt("id"));
                modalidade.setNome(linhasModalidades.getString("nome"));
                modalidade.setValor(linhasModalidades.getDouble("valor"));
                modalidade.setDiasSemana(linhasModalidades.getString("diasSemana"));
                aluno.getModalidades().add(modalidade);
            }

//            
            ResultSet linhasPagamentos = this.dao.listarPagamentos(aluno);
            while (linhasPagamentos.next()) {

                pagamento.setValor(linhasPagamentos.getDouble("valor"));
                pagamento.setDtPagamento(linhasPagamentos.getDate("dtPagamento"));
                pagamento.setDtVencimento(linhasPagamentos.getDate("dtVencimento"));
                pagamento.setMatriculaAluno(linhasPagamentos.getInt("idAluno"));

                aluno.getPagamentos().add(pagamento);
            }

            // implementar            
            aluno.setFrequencia(new ArrayList<>());

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
        } else if (!Validador.validarCPF(this.getCpf())) {
            return "O CPF informado não é válido.";
        }

        return "0";
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public ArrayList<Modalidade> getModalidades() {
        return modalidades;
    }

    public void setModalidades(ArrayList<Modalidade> modalidades) {
        this.modalidades = modalidades;
    }

    public ArrayList<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(ArrayList<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public ArrayList<Frequencia> getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(ArrayList<Frequencia> frequencia) {
        this.frequencia = frequencia;
    }

}
