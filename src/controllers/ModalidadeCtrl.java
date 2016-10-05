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
import models.Modalidade;

public class ModalidadeCtrl implements Initializable {

    // ASSOCIAÇÔES
    private Main application;
    private Modalidade model;

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
    private TextField campoValor;

    // TAB PESQUISA
    @FXML
    private ObservableList<Modalidade> funcionarios = FXCollections.observableArrayList();

    @FXML
    private TableView<Modalidade> tabelaModalidades;

    @FXML
    TableColumn<Modalidade, String> colunaNome;

    @FXML
    TableColumn<Modalidade, String> colunaValor;

    public ModalidadeCtrl() throws SQLException {
        this.model = new Modalidade();
    }

    // DISPARADORES DA VIEW
    @FXML
    private void salvarModalidade() throws SQLException, NoSuchAlgorithmException {

        boolean formValido = false;

        // Pega o valor dos campos
        String nome = campoNome.getText();
        int valor = Integer.parseInt(campoValor.getText());

        // VALIDA O FORMULARIO
        // nome em branco
        if (nome.isEmpty()) {
            formValido = false;
            Alerta.informar("Os campos em vermelho são de preenchimento obrigatório");
            return;
        }
        
        if (valor < 1) {
            formValido = false;
            Alerta.informar("Os campos em vermelho são de preenchimento obrigatório");
            return;
        }

        // Se já houver um item associado ao modelo, atualizá-lo
        if (this.model.getMatricula()> 0) {
            System.out.println("Atualizar: " + this.model.getNome());

            // Atualiza o model com os dados do formulario
            this.model.setNome(nome);
            this.model.setValor(valor);

            // Atualiza o intem no banco de dados
            this.model.atualizarModalidade(this.model);

            // Remove a linha com a informação antiga
            ObservableList<Modalidade> itemSelecionado, lista;
            lista = tabelaModalidades.getItems();
            itemSelecionado = tabelaModalidades.getSelectionModel().getSelectedItems();
            Modalidade remover = itemSelecionado.get(0);
            itemSelecionado.forEach(lista::remove);
            tabelaModalidades.refresh();

            // Remove o item desatualizado da tabela
            // tabelaModalidades.getItems().remove(this.model);
        } else {
            // Caso não haja, insere um novo item
            this.model = new Modalidade();
            this.model.setNome(nome);
            this.model.setValor(valor);
            this.model.setMatricula(0);

            // Executa o método de cadastro
            this.model.setMatricula(this.model.inserirModalidade(this.model));

            // Avisa o usuário
            System.out.println("Cadastrou: " + this.model.getNome());

        }

        // Atualiza a tabela com o novo item
//          tabelaModalidades.getItems().add(this.model);
//          tabelaModalidades.refresh();
        desenharTabela();

        // Limpa os campos
        campoNome.clear();
        campoValor.clear();
        
        SingleSelectionModel<Tab> selectionModel = painelAbas.getSelectionModel();
        selectionModel.select(1);
        
        // Reinicializa o model
        this.model = new Modalidade();

        // Informa ao usuário
        Alerta.informar("Dados atualizados com sucesso.");
    }

    @FXML
    private void deletarModalidade() throws SQLException {

        if (Alerta.confirmar("Confirma a exclusão do item?")) {

            // Retorna o item da linha selecionada
            ObservableList<Modalidade> itemSelecionado, lista;
            lista = tabelaModalidades.getItems();
            itemSelecionado = tabelaModalidades.getSelectionModel().getSelectedItems();
            Modalidade remover = itemSelecionado.get(0);

            // Avisa no console 
            System.out.println("Deletou: " + remover.getNome());

            // Remove da tabela da view
            itemSelecionado.forEach(lista::remove);
            tabelaModalidades.refresh();

            // Remove do DB
            this.model.deletarModalidade(remover);

            // Reinicializa o model
            this.model = new Modalidade();

        }

    }

    @FXML
    private void editarModalidade() throws SQLException {

        // Retorna o item da linha selecionada
        ObservableList<Modalidade> itemSelecionado, lista;
        lista = tabelaModalidades.getItems();
        itemSelecionado = tabelaModalidades.getSelectionModel().getSelectedItems();
        Modalidade editar = itemSelecionado.get(0);

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
        tabelaModalidades.getColumns().clear();
        colunaNome = new TableColumn<>("Nome");
        colunaNome.setMinWidth(200);
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        colunaValor = new TableColumn<>("Valor");
        colunaValor.setMinWidth(200);
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        ObservableList<Modalidade> lista = model.listarModalidade();
        tabelaModalidades.setItems(lista);
        tabelaModalidades.getColumns().addAll(colunaNome, colunaValor);
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
            Logger.getLogger(ModalidadeCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
