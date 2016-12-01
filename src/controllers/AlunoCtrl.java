package controllers;

import main.Main;
import helpers.Alerta;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
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
import models.Aluno;

public class AlunoCtrl implements Initializable {

    // ASSOCIAÇÔES
    private Main application;
    private Aluno model;

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

    @FXML
    private Tab tabInadimplente;

    // TAB CADASTRO
    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoCPF;

    @FXML
    private ComboBox comboPlano;

    @FXML
    private ObservableList<Aluno> listaAlunos = FXCollections.observableArrayList();

    // TAB PESQUISA
    @FXML
    private TableView<Aluno> tabelaAlunos;

    @FXML
    private TableView<Aluno> tabelaAlunosInadimplentes;

    @FXML
    TableColumn<Aluno, String> colunaMatricula;

    @FXML
    TableColumn<Aluno, String> colunaNome;

    @FXML
    TableColumn<Aluno, String> colunaCPF;

    @FXML
    TableColumn<Aluno, String> colunaPlano;

    @FXML
    TableColumn<Aluno, String> colunaMatriculaInadimplente;

    @FXML
    TableColumn<Aluno, String> colunaNomeInadimplente;

    @FXML
    TableColumn<Aluno, String> colunaCPFInadimplente;

    @FXML
    TableColumn<Aluno, String> colunaPlanoInadimplente;

    public AlunoCtrl() throws SQLException {
        this.model = new Aluno();
    }

    @FXML
    private void teste() {
        System.out.println(this.application.getFuncionarioLogado().getNome());
    }

    // DISPARADORES DA VIEW
    @FXML
    private void salvar() throws SQLException, NoSuchAlgorithmException, ParseException {

        boolean sucesso = true;
        boolean edicao = false;
        String mensagem = "";

        if (this.model.getMatricula() > 0) {
            edicao = true;
        }

        // Recolhe os dados do formulário
        this.model.setNome(campoNome.getText());
        this.model.setCpf(campoCPF.getText());
        this.model.setPlano(comboPlano.getSelectionModel().getSelectedItem().toString());

        // Alerta.informar(this.model.getPlano());
        // Valida e persiste o modelo
        if (this.model.validarModelo().equals("0")) {
            this.model.setMatricula(this.model.persistir());
            if (this.model.getMatricula() == -1) {
                mensagem = "Já existe um aluno cadastrado com o CPF informado.";
                sucesso = false;
            } else {
                if (edicao) {
                    mensagem = "Dados atualizados com sucesso.";
                } else {
                    mensagem = "Dados incluídos com sucesso. Sua matrícula é " + this.model.getMatricula() + "";
                }
            }
        } else {
            mensagem = this.model.validarModelo();
            sucesso = false;
        }

        Alerta.informar(mensagem);

        if (sucesso) {
            // Altera para a aba de inserção/edição
            this.desenharTabela();
            SingleSelectionModel<Tab> selectionModel = painelAbas.getSelectionModel();
            selectionModel.select(0);
            this.model = new Aluno();
            this.limparCampos();
        }

    }

    @FXML
    private void deletar() throws SQLException {

        if (Alerta.confirmar("Confirma a exclusão do aluno?")) {

            // Retorna o item da linha selecionada
            ObservableList<Aluno> itemSelecionado, lista;
            lista = tabelaAlunos.getItems();
            itemSelecionado = tabelaAlunos.getSelectionModel().getSelectedItems();
            Aluno remover = itemSelecionado.get(0);

            this.model = remover;

            // Avisa no console 
            System.out.println("Deletou: " + remover.getNome());

            // Remove da tabela da view
            itemSelecionado.forEach(lista::remove);
            tabelaAlunos.refresh();

            // Remove do DB
            this.model.deletar();

            // Reinicializa o model
            this.model = new Aluno();

        }

    }

    public void setPerfilReepcao() {
        tabInadimplente.setDisable(true);
        tabInadimplente.setText("");
    }

    @FXML
    public void liberarAcessoAcademia(Aluno aluno) throws SQLException {

//        Alerta.informar("AlunoCtrl");
        this.model = aluno;
        String mensagem = "";
        boolean adimplente = this.model.verificarAdimplente();
        boolean diaPermitido = this.model.verificarDiaModalidade();

        boolean dadosValidos = true;

        if (!adimplente) {
            mensagem = "Acesso Bloqueado por Inadimplência.";
            Alerta.informar(mensagem);
            return;
        }

        // Alerta.informar(Data.diaDaSemana());
        if (!diaPermitido) {
            mensagem = "Acesso não permitido.";
            Alerta.informar(mensagem);
            return;
        }

        if (dadosValidos) {
            this.model.registrarFrequencia();
            // Nota: nesse ponto seria enviado o sinal de liberação à catraca
        }

    }

    @FXML
    private void editar() throws SQLException {

        // Retorna o item da linha selecionada
        ObservableList<Aluno> itemSelecionado, lista;
        lista = tabelaAlunos.getItems();
        itemSelecionado = tabelaAlunos.getSelectionModel().getSelectedItems();
        Aluno editar = itemSelecionado.get(0);

        // Avisa no console 
        // System.out.println("Editar: " + editar.getNome() + " " + editar.getMatricula());
        // Preeche o campo com os dados para edição
        campoNome.setText(editar.getNome());
        campoCPF.setText(editar.getCpf());
        comboPlano.getSelectionModel().select(editar.getPlano());

        // Associa o item que deve ser editado ao model atual
        this.model = editar;

        // Altera para a aba de inserção/edição
        SingleSelectionModel<Tab> selectionModel = painelAbas.getSelectionModel();
        selectionModel.select(1);
    }

    @FXML
    private void novo() throws SQLException {

        this.model = new Aluno();

        campoNome.setText("");
        campoCPF.setText("");
        comboPlano.getSelectionModel().select("Mensal");

        SingleSelectionModel<Tab> selectionModel = painelAbas.getSelectionModel();
        selectionModel.select(1);
    }

    public void limparCampos() {
        campoNome.setText("");
        campoCPF.setText("");
        comboPlano.getSelectionModel().select("Mensal");
    }

    public void desenharTabela() throws SQLException {
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

    public void desenharTabelaInadimplentes() throws SQLException {
        tabelaAlunosInadimplentes.getColumns().clear();

        colunaMatriculaInadimplente = new TableColumn<>("Matr.");
        colunaMatriculaInadimplente.setMinWidth(50);
        colunaMatriculaInadimplente.setCellValueFactory(new PropertyValueFactory<>("matricula"));

        colunaNomeInadimplente = new TableColumn<>("Nome");
        colunaNomeInadimplente.setMinWidth(195);
        colunaNomeInadimplente.setCellValueFactory(new PropertyValueFactory<>("nome"));

        colunaCPFInadimplente = new TableColumn<>("CPF");
        colunaCPFInadimplente.setMinWidth(175);
        colunaCPFInadimplente.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        colunaPlanoInadimplente = new TableColumn<>("Plano");
        colunaPlanoInadimplente.setMinWidth(175);
        colunaPlanoInadimplente.setCellValueFactory(new PropertyValueFactory<>("plano"));

        ObservableList<Aluno> lista = model.listarAlunosInadimplentes();
        tabelaAlunosInadimplentes.setItems(lista);
        tabelaAlunosInadimplentes.getColumns().addAll(colunaMatriculaInadimplente, colunaNomeInadimplente, colunaCPFInadimplente, colunaPlanoInadimplente);
    }

    public MenuCtrl getMenuController() {
        return menuController;
    }

    public void setMenuController(MenuCtrl menuController) {
        this.menuController = menuController;
    }

    public Aluno getModel() {
        return model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.desenharTabela();
            this.desenharTabelaInadimplentes();
            // INICIALIZA O COMBOBOX
            comboPlano.getItems().addAll("Mensal", "Trimestral", "Semestral", "Anual");
            comboPlano.getSelectionModel().select("Mensal");
            this.desenharTabela();
        } catch (SQLException ex) {
            Logger.getLogger(AlunoCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
