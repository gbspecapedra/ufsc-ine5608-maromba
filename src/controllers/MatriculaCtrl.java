package controllers;

import helpers.Alerta;
import main.Main;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import models.Aluno;
import models.Modalidade;
import models.Pagamento;

public class MatriculaCtrl implements Initializable {

    // ASSOCIAÇÔES
    private Main application;
    private Aluno model;
    private Modalidade modalidade;

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
    TabPane painelAbas;

    // TAB CADASTRO
    @FXML
    Label plano;

    @FXML
    ObservableList<Aluno> listaAlunos = FXCollections.observableArrayList();

    @FXML
    ObservableList<Modalidade> listaModalidadeCombo = FXCollections.observableArrayList();

    @FXML
    ObservableList<Modalidade> listaModalidadeTabela = FXCollections.observableArrayList();

    @FXML
    ObservableList<Pagamento> listaPagamentoTabela = FXCollections.observableArrayList();

    // TAB PESQUISA
    @FXML
    TableView<Aluno> tabelaAlunos;

    @FXML
    TableView<Modalidade> tabelaModalidades;

    @FXML
    TableColumn<Modalidade, String> colunaNomeModalidade;

    @FXML
    TableColumn<Modalidade, Float> colunaValorModalidade;

    @FXML
    TableColumn<Aluno, String> colunaMatricula;

    @FXML
    TableColumn<Aluno, String> colunaNome;

    @FXML
    TableColumn<Aluno, String> colunaCPF;

    @FXML
    TableColumn<Aluno, String> colunaPlano;

    @FXML
    ComboBox<Aluno> comboAluno;

    @FXML
    ComboBox<Modalidade> comboModalidade;

    public MatriculaCtrl() throws SQLException {
        this.model = new Aluno();
        this.modalidade = new Modalidade();
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
//        this.model = new Modalidade();
        this.limparCampos();
        SingleSelectionModel<Tab> selectionModel = painelAbas.getSelectionModel();
        selectionModel.select(1);
    }

    @FXML
    private void adicionarModalidade() throws SQLException {

        String mensagem = "";
        boolean dadosValidos = true;

        Modalidade modalidadeSelecionada = new Modalidade();
        if (comboModalidade.getSelectionModel().isEmpty()) {
            mensagem = "Selecione uma modalidade.";
            dadosValidos = false;
        } else {
            modalidadeSelecionada = comboModalidade.getSelectionModel().getSelectedItem();
        }

        Aluno alunoSelecionado = new Aluno();
        if (comboAluno.getSelectionModel().isEmpty()) {
            mensagem = "Selecione o Aluno.";
            dadosValidos = false;
        } else {
            alunoSelecionado = comboAluno.getSelectionModel().getSelectedItem();
            plano.setText(alunoSelecionado.getPlano());
        }

        // Verificar se a modalidade já não foi selecionada previamente
        boolean jatem = listaModalidadeTabela.contains(modalidadeSelecionada);
        if (jatem) {
            mensagem = "A modalidade selecionada já encontra-se na lista.";
            dadosValidos = false;
        }

        if (!dadosValidos) {
            Alerta.informar(mensagem);
        } else {

            comboAluno.setDisable(true);
            this.listaModalidadeTabela.add(modalidadeSelecionada);
            this.desenharTabelaModalidades();
        }

    }

    @FXML
    private void gerarPagamentos() {

        Double valorTotal = 0.00;

        for (Modalidade m : listaModalidadeTabela) {
            valorTotal += m.getValor();
        }
        
        Alerta.informar(Double.toString(valorTotal));
        
        
        Aluno alunoSelecionado;
        alunoSelecionado = comboAluno.getSelectionModel().getSelectedItem();
//        Alerta.informar(alunoSelecionado.getPlano());

        boolean dadosValidos = true;
        String mensagem = "";

        if (listaModalidadeTabela.isEmpty()) {
            mensagem = "Selecione ao menos uma modalidade.";
            dadosValidos = false;
        }

        if (!dadosValidos) {
            Alerta.informar(mensagem);
        }
    }

    @FXML
    private void removerModalidade() throws SQLException {

        String mensagem = "";
        boolean dadosValidos = true;
        Modalidade modalidadeSelecionada;

        int linhaSelecionada = tabelaModalidades.getSelectionModel().getSelectedIndex();

        if (linhaSelecionada >= 0) {
            this.listaModalidadeTabela.remove(tabelaModalidades.getSelectionModel().getSelectedItem());
            this.desenharTabelaModalidades();

            if (this.listaModalidadeTabela.isEmpty()) {
                comboAluno.setDisable(false);
            }

        } else {
            mensagem = "Você deve selecionar uma modalidade na tabela.";
            dadosValidos = false;
        }

        if (!dadosValidos) {
            Alerta.informar(mensagem);
        }

//        String mensagem = "";
//        boolean dadosValidos = true;
//        
//        Modalidade modalidadeSelecionada = new Modalidade();
//        if (comboModalidade.getSelectionModel().isEmpty()) {
//            mensagem = "Selecione uma modalidade.";
//            dadosValidos = false;
//        } else {
//            modalidadeSelecionada = comboModalidade.getSelectionModel().getSelectedItem();
//        }
//
//
//        if (!dadosValidos) {
//            Alerta.informar(mensagem);
//        }
//        
//        comboAluno.setDisable(true);
//        comboAluno.setStyle("-fx-opacity: 1;");
//        
//        this.listaModalidadeTabela.add(modalidadeSelecionada);
//        this.desenharTabelaModalidades();
    }

    public void limparCampos() {
//        campoNome.setText("");
//        comboPlano.getSelectionModel().select("Mensal");
    }

    public void desenharTabelaModalidades() throws SQLException {
        tabelaModalidades.getColumns().clear();

        // Coluna com o Nome da Modalidade
        colunaNomeModalidade = new TableColumn<>("Nome");
        colunaNomeModalidade.setMinWidth(195);
        colunaNomeModalidade.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // Coluna com o Valor da Modalidade
        colunaValorModalidade = new TableColumn<>("Valor");
        colunaValorModalidade.setMinWidth(195);
        colunaValorModalidade.setCellValueFactory(new PropertyValueFactory<>("valor"));

        tabelaModalidades.setItems(listaModalidadeTabela);
        tabelaModalidades.getColumns().addAll(colunaNomeModalidade, colunaValorModalidade);
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

        // Ajusta o label referente ao Plano
        comboAluno.setOnAction((event) -> {
            Aluno alunoSelecionado = comboAluno.getSelectionModel().getSelectedItem();
            plano.setText(alunoSelecionado.getPlano());
        });

        try {
            // Preenche o combo de alunos
            comboAluno.setPromptText("Selecione o Aluno");

            ObservableList<Aluno> listaAluno;

            listaAluno = this.model.listarAlunos();

            comboAluno.setCellFactory(new Callback<ListView<Aluno>, ListCell<Aluno>>() {
                @Override
                public ListCell<Aluno> call(ListView<Aluno> param) {

                    return new ListCell<Aluno>() {
                        @Override
                        public void updateItem(Aluno item, boolean empty) {
                            super.updateItem(item, empty);
                            if (!empty) {
                                setText(item.getNome());
                                setGraphic(null);
                            } else {
                                setText(null);
                            }
                        }
                    };
                }
            });

            comboAluno.setItems(listaAluno);

            // Preenche o combo de modalidades
            comboModalidade.setPromptText("Selecione a Modalidade");

            ObservableList<Modalidade> listaModalidade;

            listaModalidadeCombo = this.modalidade.listarModalidades();

            comboModalidade.setCellFactory(new Callback<ListView<Modalidade>, ListCell<Modalidade>>() {
                @Override
                public ListCell<Modalidade> call(ListView<Modalidade> param) {

                    return new ListCell<Modalidade>() {
                        @Override
                        public void updateItem(Modalidade item, boolean empty) {
                            super.updateItem(item, empty);
                            if (!empty) {
                                setText(item.getNome());
                                setGraphic(null);
                            } else {
                                setText(null);
                            }
                        }
                    };
                }
            });

            comboModalidade.setItems(listaModalidadeCombo);

        } catch (SQLException ex) {
            Logger.getLogger(MatriculaCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
