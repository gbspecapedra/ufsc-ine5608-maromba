package controllers;

import main.Main;
import helpers.Alerta;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Funcionario;

public class FuncionarioCtrl implements Initializable {

    // ASSOCIAÇÔES
    private Main application;
    private Funcionario model;

    @FXML
    private MenuCtrl menuController;

    // SETTERS PARA O SINGLETON APP
    public void setApp(Main application) {
        this.application = application;
    }

    public void setMenuApp(Main application) {
        this.menuController.setApp(application);
    }

    // MAPEAMENTOS JAVAFX FXML
    @FXML
    private TabPane painelAbas;

    // TAB CADASTRO
    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoCPF;

    @FXML
    private TextField campoCTPS;

    @FXML
    private ComboBox comboFuncao;

    @FXML
    private ObservableList<Funcionario> listaFuncionarios = FXCollections.observableArrayList();

    // TAB PESQUISA
    @FXML
    private TableView<Funcionario> tabelaFuncionarios;

    @FXML
    TableColumn<Funcionario, String> colunaMatricula;

    @FXML
    TableColumn<Funcionario, String> colunaNome;

    @FXML
    TableColumn<Funcionario, String> colunaCPF;

    @FXML
    TableColumn<Funcionario, String> colunaCTPS;

    @FXML
    TableColumn<Funcionario, String> colunaFuncao;

    public FuncionarioCtrl() throws SQLException {
        this.model = new Funcionario();
    }

    @FXML
    private void teste() {
        System.out.println(this.application.getFuncionarioLogado().getNome());
    }

    // DISPARADORES DA VIEW
    @FXML
    private void salvar() throws SQLException, NoSuchAlgorithmException {

        boolean sucesso = true;
        boolean edicao = false;
        String mensagem = "";
        String modeloValido = "";
        SingleSelectionModel<Tab> modeloSelecionado;

        if (this.model.getMatricula() > 0) {
            edicao = true;
        }

        // Recolhe os dados do formulário
        this.model.setNome(campoNome.getText());
        this.model.setCpf(campoCPF.getText());
        this.model.setCtps(campoCTPS.getText());
        this.model.setFuncao(comboFuncao.getSelectionModel().getSelectedItem().toString());

        // Alerta.informar(this.model.getFuncao());
        // Valida e persiste o modelo
        modeloValido = this.model.validarModelo();

        if (modeloValido.equals("0")) {
            this.model.setMatricula(this.model.persistir());
            if (this.model.getMatricula() == -1) {
                mensagem = "Já existe um funcionario cadastrado com o CPF informado.";
                sucesso = false;
            } else {
                if (edicao) {
                    mensagem = "Dados atualizados com sucesso.";
                } else {
                    mensagem = "Dados incluídos com sucesso. Sua matrícula é " + this.model.getMatricula() + "";
                }
            }
        } else {
            mensagem = modeloValido;
            sucesso = false;
        }

        Alerta.informar(mensagem);

        if (sucesso) {

            this.desenharTabela();
            // Altera para a aba de inserção/edição
            modeloSelecionado = painelAbas.getSelectionModel();
            modeloSelecionado.select(0);
            this.model = new Funcionario();
            this.limparCampos();
        }

    }

    @FXML
    private void deletar() throws SQLException {

        if (Alerta.confirmar("Confirma a exclusão do funcionario?")) {

            // Retorna o item da linha selecionada
            ObservableList<Funcionario> funcionarioSelecionado, lista;
            lista = tabelaFuncionarios.getItems();
            funcionarioSelecionado = tabelaFuncionarios.getSelectionModel().getSelectedItems();
            Funcionario remover = funcionarioSelecionado.get(0);

            this.model = remover;

            // Avisa no console 
            System.out.println("Deletou: " + remover.getNome());

            // Remove da tabela da view
            funcionarioSelecionado.forEach(lista::remove);
            tabelaFuncionarios.refresh();

            // Remove do DB
            this.model.deletar();

            // Reinicializa o model
            this.model = new Funcionario();

        }

    }

    @FXML
    private void editar() throws SQLException {

        // Retorna o item da linha selecionada
        ObservableList<Funcionario> itemSelecionado, lista;
        lista = tabelaFuncionarios.getItems();
        itemSelecionado = tabelaFuncionarios.getSelectionModel().getSelectedItems();
        Funcionario editar = itemSelecionado.get(0);

        // Avisa no console 
        // System.out.println("Editar: " + editar.getNome() + " " + editar.getMatricula());
        // Preeche o campo com os dados para edição
        campoNome.setText(editar.getNome());
        campoCPF.setText(editar.getCpf());
        campoCTPS.setText(editar.getCtps());
        comboFuncao.getSelectionModel().select(editar.getFuncao());

        // Associa o item que deve ser editado ao model atual
        this.model = editar;

        // Altera para a aba de inserção/edição
        SingleSelectionModel<Tab> modeloSelecionado = painelAbas.getSelectionModel();
        modeloSelecionado.select(1);
    }

    @FXML
    private void novo() throws SQLException {

        this.model = new Funcionario();

        campoNome.setText("");
        campoCPF.setText("");
        campoCTPS.setText("");
        comboFuncao.getSelectionModel().select("Professor");

        SingleSelectionModel<Tab> modeloSelecionado = painelAbas.getSelectionModel();
        modeloSelecionado.select(1);
    }

    public void limparCampos() {
        campoNome.setText("");
        campoCPF.setText("");
        campoCTPS.setText("");
        comboFuncao.getSelectionModel().select("Professor");
    }

    public void desenharTabela() throws SQLException {
        tabelaFuncionarios.getColumns().clear();

        colunaMatricula = new TableColumn<>("Matr.");
        colunaMatricula.setMinWidth(50);
        colunaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));

        colunaNome = new TableColumn<>("Nome");
        colunaNome.setMinWidth(200);
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        colunaCPF = new TableColumn<>("CPF");
        colunaCPF.setMinWidth(100);
        colunaCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        colunaCTPS = new TableColumn<>("CTPS");
        colunaCTPS.setMinWidth(100);
        colunaCTPS.setCellValueFactory(new PropertyValueFactory<>("ctps"));

        colunaFuncao = new TableColumn<>("Funcao");
        colunaFuncao.setMinWidth(145);
        colunaFuncao.setCellValueFactory(new PropertyValueFactory<>("funcao"));

        ObservableList<Funcionario> lista = model.listarFuncionarios();
        tabelaFuncionarios.setItems(lista);
        tabelaFuncionarios.getColumns().addAll(colunaMatricula, colunaNome, colunaCPF, colunaCTPS, colunaFuncao);
    }

    public MenuCtrl getMenuController() {
        return menuController;
    }

    public void setMenuController(MenuCtrl menuController) {
        this.menuController = menuController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.desenharTabela();
            // INICIALIZA O COMBOBOX
            comboFuncao.getItems().addAll("Gerente", "Professor", "Recepcionista");
            comboFuncao.getSelectionModel().select("Professor");
            this.desenharTabela();
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
