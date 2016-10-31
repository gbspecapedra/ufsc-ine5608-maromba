package models;

import helpers.Alerta;
import helpers.Validador;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import libs.Dao;

public class Funcionario extends Pessoa {

    // ATRIBUTOS    
    private String funcao;
    private String ctps;

    public Funcionario montarFuncionario(int id) throws SQLException {
//      ResultSet linha = this.dao.retornarFuncionarioDAO(id);
        ResultSet linha = Dao.select("select * from pessoas where id = " + id);
        linha.next();
        Funcionario retorno = new Funcionario();
        retorno.setNome(linha.getString("nome"));
        retorno.setMatricula(linha.getInt("id"));
        retorno.setFuncao(linha.getString("funcao"));
        retorno.setCtps(linha.getString("ctps"));
        return retorno;
    }

    public boolean verificarMatriculaFuncionario(String matricula) throws SQLException, NoSuchAlgorithmException {
        String query = "select id from pessoas where id = " + matricula + " and tipo not like 'Aluno'";
        System.out.println(query);
        ResultSet retorno = Dao.select(query);
        if (retorno.next()) {
            return true;
        }
        return false;
    }

    public boolean verificarCredenciaisBD(String matricula, String senha) throws SQLException, NoSuchAlgorithmException {

        String mensagem = "";
        boolean retorno = false;

        if (!verificarMatriculaFuncionario(matricula)) {
            mensagem = "Matrícula Inexistente";
        } else {
            String query = "select id from pessoas where id = '" + matricula + "' and senha = '" + Validador.converterMD5(senha) + "' and tipo not like 'Aluno'";
            System.out.println(query);
            ResultSet linhas = Dao.select(query);
            if (linhas.next()) {
                retorno = true;
            } else {
                mensagem = "Senha Icorreta";
            }
        }

        if (!retorno) {
            Alerta.informar(mensagem);
        }

        return retorno;
    }

    public Funcionario verificarCredenciais(String matricula, String senha) throws SQLException, NoSuchAlgorithmException {
        Funcionario funcionarioAutenticado = new Funcionario();
        if (this.verificarCredenciaisBD(matricula, senha)) {
            funcionarioAutenticado = this.montarFuncionario(Integer.parseInt(matricula));
        }
        return funcionarioAutenticado;
    }

    public int salvar() throws SQLException {

        String sql;
        ResultSet linhas;
        int retorno = 0;

        // Verifica se já existe um funcionario com esse CPF
        if (verificarCpfCadastrado()) {
            return -1;
        }

        if (this.getMatricula() > 0) {
            // Atualiza um registro
            sql = "UPDATE pessoas SET nome = '" + this.getNome() + "', cpf = '" + this.getCpf() + "', ctps = '" + this.getCtps()+ "', funcao = '" + this.getFuncao() + "' WHERE id = '" + this.getMatricula() + "'";
            Dao.execute(sql);
            retorno = this.getMatricula();

        } else {
            // Insere novo registro
            sql = "INSERT into pessoas (nome, cpf, funcao, tipo, ctps) values ('" + this.getNome() + "', '" + this.getCpf() + "','" + this.getFuncao() + "', 'Funcionario', '" + this.getCtps() + "')";
            Dao.execute(sql);

            // Retorna o id do novo funcionario
            sql = "SELECT id FROM pessoas WHERE cpf = '" + this.getCpf() + "'";
            linhas = Dao.select(sql);
            if (linhas.next()) {
                retorno = linhas.getInt("id");
            }
        }
        return retorno;
    }

    public int deletar() throws SQLException {

        // VERIFICAR SE NÃO HÁ PAGAMENTOS PENDENTES                
        String sql = "DELETE FROM pessoas WHERE id = " + this.getMatricula() + "";
        Dao.execute(sql);
        Alerta.informar("Dados excluídos com sucesso.");
        return 0;
    }

    public ObservableList<Funcionario> listarFuncionarios() throws SQLException {
        ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
        ResultSet linhas = Dao.select("select * from pessoas where tipo = 'Funcionario'");
        while (linhas.next()) {
            // Inicializa um objeto
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(linhas.getString("nome"));
            funcionario.setMatricula(linhas.getInt("id"));
            funcionario.setFuncao(linhas.getString("funcao"));
            funcionario.setCpf(linhas.getString("cpf"));
            funcionario.setCtps(linhas.getString("ctps"));
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

    // CONSTRUTOR
    public Funcionario() throws SQLException {
        //  this.dao = new _FuncionarioDao();
    }

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    
}
