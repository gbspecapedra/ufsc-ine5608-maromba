package models;

import dao.FuncionarioDao;
import helpers.Alerta;
import helpers.Validador;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Funcionario extends Pessoa {

    // ATRIBUTOS    
    private String funcao;
    private String ctps;
    private FuncionarioDao dao;

    public Funcionario() throws SQLException {
        this.dao = new FuncionarioDao();

    }

    public Funcionario montarFuncionario(int matricula) throws SQLException {
        ObservableList<Funcionario> funcionarios = this.listarFuncionarios();
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getMatricula() == matricula) {
                return funcionario;
            }
        }
        return null;
    }

    public boolean verificarCredenciaisBD(int matricula, String senha) throws SQLException, NoSuchAlgorithmException {

        String mensagem = "";
        boolean retorno = false;
        boolean matriculaValida = false;

        // Verifica se a matrícula informada é válida
        ObservableList<Funcionario> funcionarios = this.listarFuncionarios();
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getMatricula() == matricula) {
                matriculaValida = true;
            }
        }

        if (!matriculaValida) {
            mensagem = "Matrícula Inexistente";
        } else {
            mensagem = this.dao.verificarCredenciais(matricula, senha);
        }

        if (!mensagem.equals("1")) {
            Alerta.informar(mensagem);
            return false;
        } else {
            return true;
        }

    }

    public Funcionario verificarCredenciais(String matricula, String senha) throws SQLException, NoSuchAlgorithmException {
        Funcionario funcionarioAutenticado = new Funcionario();
        if (this.verificarCredenciaisBD(Integer.parseInt(matricula), senha)) {
            funcionarioAutenticado = this.montarFuncionario(Integer.parseInt(matricula));
        }
        return funcionarioAutenticado;
    }

    public int persistir() throws SQLException, NoSuchAlgorithmException {

        int retorno;

        // Verifica se já existe um funcionario com esse CPF
        ObservableList<Funcionario> funcionarios = this.listarFuncionarios();
        for (Funcionario funcionario : funcionarios) {
            if ((funcionario.getCpf().equals(this.getCpf()) && funcionario.getMatricula() != this.getMatricula())) {
                return -1;
            }
        }

        retorno = this.dao.persistir(this);
        return retorno;
    }

    public int deletar() throws SQLException {

        this.dao.deletar(this.getMatricula());
        Alerta.informar("Dados excluídos com sucesso.");
        return 0;
    }

    public ObservableList<Funcionario> listarFuncionarios() throws SQLException {
        ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
        ResultSet linhas = this.dao.listar();

        while (linhas.next()) {

            // Inicializa um objeto
            Funcionario funcionario = new Funcionario();
            
            funcionario.setNome(linhas.getString("nome"));
            funcionario.setMatricula(linhas.getInt("id"));
            funcionario.setFuncao(linhas.getString("funcao"));
            funcionario.setCtps(linhas.getString("ctps"));
            funcionario.setCpf(linhas.getString("cpf"));

            // Adiciona o objeto ao retorno
            funcionarios.add(funcionario);
        }
        return funcionarios;
    }

    public String validarModelo() {

        if (this.getFuncao().isEmpty()) {
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

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

}
