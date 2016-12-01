package controllers;

import helpers.Alerta;
import main.Main;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import models.Aluno;
import models.Frequencia;

public class FrequenciaCtrl implements Initializable {

    // ASSOCIAÇÔES
    private Main application;
    private Frequencia model;
    private AlunoCtrl alunoCtrl;

    @FXML
    private MenuCtrl menuController;

    // SETTERS PARA O SINGLETON APP
    public void setApp(Main application) {
        this.application = application;
    }

    public void setMenuApp(Main application) {
        this.menuController.setApp(application);
    }

    @FXML
    private Button botaoInicio;

    @FXML
    HBox topo;

    // MAPEAMENTOS JAVAFX FXML
    @FXML
    private TabPane painelAbas;

    // TAB CADASTRO
    @FXML
    private TextField campoMatricula;

    @FXML
    private Label labelNome;

    @FXML
    private ObservableList<Frequencia> listaFrequencias = FXCollections.observableArrayList();

    // TAB PESQUISA
    @FXML
    private TableView<Frequencia> tabelaFrequencias;

    @FXML
    TableColumn<Frequencia, Date> colunaData;

    public FrequenciaCtrl() throws SQLException {
        this.model = new Frequencia();
    }

    @FXML
    private void exibirMenuPrincipal() throws SQLException {
        this.application.exibirViewInicioAluno();

    }

    @FXML
    private void pesquisar() throws SQLException {
        int matricula;
        Aluno alunoSelecionado = new Aluno();
        matricula = Integer.parseInt(campoMatricula.getText());

        if (matricula > 0) {
            alunoSelecionado = this.alunoCtrl.getModel().montarAluno(matricula);
            if (alunoSelecionado == null) {
                Alerta.informar("Aluno não localizado.");
            } else if (alunoSelecionado.getFrequencia().isEmpty()) {
                Alerta.informar("Não há histórico de frequêcia registrada para esse aluno");
            } else {
                labelNome.setText(alunoSelecionado.getNome());
                this.desenharTabela(alunoSelecionado);
            }
        } else {
            Alerta.informar("Informe a matrícula do aluno.");
        }

    }

    // DISPARADORES DA VIEW
    public void limparCampos() {
        campoMatricula.setText("");
    }

    public void desenharTabela(Aluno aluno) throws SQLException {
        
        aluno = aluno.montarAluno(aluno.getMatricula());
        tabelaFrequencias.getColumns().clear();
        ObservableList<Frequencia> lista = FXCollections.observableArrayList(aluno.getFrequencia());

        colunaData = new TableColumn<>("Data");
        colunaData.setMinWidth(195);
        colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));

        tabelaFrequencias.setItems(lista);
        tabelaFrequencias.getColumns().addAll(colunaData);
    }

    public MenuCtrl getMenuController() {
        return menuController;
    }

    public void setMenuController(MenuCtrl menuController) {
        this.menuController = menuController;
    }

    public void setPerfilAluno() {
        topo.setVisible(false);
        tabelaFrequencias.setTranslateY(-75);
        botaoInicio.setVisible(true);
        botaoInicio.setTranslateY(-75);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            botaoInicio.setVisible(false);
            this.alunoCtrl = new AlunoCtrl();
        } catch (SQLException ex) {
            Logger.getLogger(FrequenciaCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
