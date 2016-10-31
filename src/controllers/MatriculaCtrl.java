package controllers;

import main.Main;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Aluno;
import models.Modalidade;

public class MatriculaCtrl implements Initializable {

    // ASSOCIAÇÔES
    private Main application;
    private Aluno model;
    private ArrayList<Modalidade> modalidades;

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
    private ComboBox comboPlano;

    @FXML
    private ObservableList<Aluno> listaAlunos = FXCollections.observableArrayList();

    // TAB PESQUISA
    @FXML
    private TableView<Aluno> tabelaAlunos;
    
    @FXML
    TableColumn<Aluno, String> colunaMatricula;
    
    @FXML
    TableColumn<Aluno, String> colunaNome;

    @FXML
    TableColumn<Aluno, String> colunaCPF;
    
    @FXML
    TableColumn<Aluno, String> colunaPlano;

    public MatriculaCtrl() throws SQLException {
        this.model = new Aluno();
    }

    
    // DISPARADORES DA VIEW
    @FXML
    private void salvar() throws SQLException, NoSuchAlgorithmException {


    }

    @FXML
    private void deletar() throws SQLException {


    }

    @FXML
    private void editar() throws SQLException {

    }

    @FXML
    private void novo() throws SQLException {

    }

    public void limparCampos(){    
        campoNome.setText("");
        comboPlano.getSelectionModel().select("Mensal");        
    }
    
    
    public void desenharTabelaModalidades() throws SQLException {
        tabelaAlunos.getColumns().clear();
        
        colunaMatricula = new TableColumn<>("Matr.");
        colunaMatricula.setMinWidth(50);
        colunaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        
        colunaNome = new TableColumn<>("Nome");
        colunaNome.setMinWidth(195);
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        colunaCPF = new TableColumn<>("CPF");
        colunaCPF.setMinWidth(175);
        colunaCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        
        colunaPlano = new TableColumn<>("Plano");
        colunaPlano.setMinWidth(175);
        colunaPlano.setCellValueFactory(new PropertyValueFactory<>("plano"));

        ObservableList<Aluno> lista = model.listarAlunos();
        tabelaAlunos.setItems(lista);
        tabelaAlunos.getColumns().addAll(colunaMatricula, colunaNome, colunaCPF, colunaPlano);
    }
    
    
    public void desenharTabelaPagamentos() throws SQLException {
        tabelaAlunos.getColumns().clear();
        
        colunaMatricula = new TableColumn<>("Matr.");
        colunaMatricula.setMinWidth(50);
        colunaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        
        colunaNome = new TableColumn<>("Nome");
        colunaNome.setMinWidth(195);
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        colunaCPF = new TableColumn<>("CPF");
        colunaCPF.setMinWidth(175);
        colunaCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        
        colunaPlano = new TableColumn<>("Plano");
        colunaPlano.setMinWidth(175);
        colunaPlano.setCellValueFactory(new PropertyValueFactory<>("plano"));

        ObservableList<Aluno> lista = model.listarAlunos();
        tabelaAlunos.setItems(lista);
        tabelaAlunos.getColumns().addAll(colunaMatricula, colunaNome, colunaCPF, colunaPlano);
    }

    public MenuCtrl getMenuController() {
        return menuController;
    }

    public void setMenuController(MenuCtrl menuController) {
        this.menuController = menuController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
