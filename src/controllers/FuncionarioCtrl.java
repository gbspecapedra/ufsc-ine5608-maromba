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
    private TextField campoCtps;

    // TAB PESQUISA
    @FXML
    private ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();

    @FXML
    private TableView<Funcionario> tabelaFuncionarios;

    @FXML
    TableColumn<Funcionario, String> colunaNome;

    @FXML
    TableColumn<Funcionario, String> colunaFuncao;

    public FuncionarioCtrl() throws SQLException {
        this.model = new Funcionario();
    }

    // DISPARADORES DA VIEW
    @FXML
    private void salvarFuncionario() throws SQLException, NoSuchAlgorithmException {

        boolean formValido = true;

        // Pega o valor dos campos
        String nome = campoNome.getText();
        String ctps = campoCtps.getText();

        // VALIDA O FORMULARIO
        // nome em branco
        if (nome.isEmpty()) {
            formValido = false;
            Alerta.informar("Os campos em vermelho são de preenchimento obrigatório");
            return;
        }
        
        if (ctps.isEmpty()) {
            formValido = false;
            Alerta.informar("Os campos em vermelho são de preenchimento obrigatório");
            return;
        }

        // Se já houver um item associado ao modelo, atualizá-lo
        if (this.model.getMatricula() > 0) {
            System.out.println("Atualizar: " + this.model.getNome());

            // Atualiza o model com os dados do formulario
            this.model.setNome(nome);

            // Atualiza o intem no banco de dados
            this.model.atualizarFuncionario(this.model);

            // Remove a linha com a informação antiga
            ObservableList<Funcionario> itemSelecionado, lista;
            lista = tabelaFuncionarios.getItems();
            itemSelecionado = tabelaFuncionarios.getSelectionModel().getSelectedItems();
            Funcionario remover = itemSelecionado.get(0);
            itemSelecionado.forEach(lista::remove);
            tabelaFuncionarios.refresh();

            // Remove o item desatualizado da tabela
            // tabelaFuncionarios.getItems().remove(this.model);
        } else {
            // Caso não haja, insere um novo item
            this.model = new Funcionario();
            this.model.setNome(nome);
            this.model.setMatricula(0);

            // Executa o método de cadastro
            this.model.setMatricula(this.model.inserirFuncionario(this.model));

            // Avisa o usuário
            System.out.println("Cadastrou: " + this.model.getNome());

        }

        // Atualiza a tabela com o novo item
//          tabelaFuncionarios.getItems().add(this.model);
//          tabelaFuncionarios.refresh();
        desenharTabela();

        // Limpa os campos
        campoNome.clear();
        campoCtps.clear();
        
        SingleSelectionModel<Tab> selectionModel = painelAbas.getSelectionModel();
        selectionModel.select(1);
        
        // Reinicializa o model
        this.model = new Funcionario();

        // Informa ao usuário
        Alerta.informar("Dados atualizados com sucesso.");
    }

    @FXML
    private void deletarFuncionario() throws SQLException {

        if (Alerta.confirmar("Confirma a exclusão do item?")) {

            // Retorna o item da linha selecionada
            ObservableList<Funcionario> itemSelecionado, lista;
            lista = tabelaFuncionarios.getItems();
            itemSelecionado = tabelaFuncionarios.getSelectionModel().getSelectedItems();
            Funcionario remover = itemSelecionado.get(0);

            // Avisa no console 
            System.out.println("Deletou: " + remover.getNome());

            // Remove da tabela da view
            itemSelecionado.forEach(lista::remove);
            tabelaFuncionarios.refresh();

            // Remove do DB
            this.model.deletarFuncionario(remover);

            // Reinicializa o model
            this.model = new Funcionario();

        }

    }

    @FXML
    private void editarFuncionario() throws SQLException {

        // Retorna o item da linha selecionada
        ObservableList<Funcionario> itemSelecionado, lista;
        lista = tabelaFuncionarios.getItems();
        itemSelecionado = tabelaFuncionarios.getSelectionModel().getSelectedItems();
        Funcionario editar = itemSelecionado.get(0);

        // Avisa no console 
        System.out.println("Editar: " + editar.getNome() + " " + editar.getMatricula());

        // Preeche o campo com os dados para edição
        campoNome.setText(editar.getNome());

        // Associa o item que deve ser editado ao model atual
        this.model = editar;

        // Altera para a aba de inserção/edição
        SingleSelectionModel<Tab> selectionModel = painelAbas.getSelectionModel();
        selectionModel.select(0);
    }
    
    
    @FXML
    private void novo() throws SQLException {
        SingleSelectionModel<Tab> selectionModel = painelAbas.getSelectionModel();
        selectionModel.select(0);
    }
    
    

    public void desenharTabela() throws SQLException {
        tabelaFuncionarios.getColumns().clear();
        colunaNome = new TableColumn<>("Nome");
        colunaNome.setMinWidth(200);
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        colunaFuncao = new TableColumn<>("Função");
        colunaFuncao.setMinWidth(200);
        colunaFuncao.setCellValueFactory(new PropertyValueFactory<>("funcao"));

        ObservableList<Funcionario> lista = model.listarFuncionario();
        tabelaFuncionarios.setItems(lista);
        tabelaFuncionarios.getColumns().addAll(colunaNome, colunaFuncao);
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
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
