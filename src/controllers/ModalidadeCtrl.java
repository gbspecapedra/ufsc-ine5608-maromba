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
import javafx.scene.control.CheckBox;
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

    @FXML
    private ObservableList<Modalidade> listaModalidades = FXCollections.observableArrayList();

    @FXML
    private CheckBox segunda;

    @FXML
    private CheckBox terca;

    @FXML
    private CheckBox quarta;

    @FXML
    private CheckBox quinta;

    @FXML
    private CheckBox sexta;

    @FXML
    private CheckBox sabado;

    // TAB PESQUISA
    @FXML
    private TableView<Modalidade> tabelaModalidades;

    @FXML
    TableColumn<Modalidade, String> colunaNome;

    @FXML
    TableColumn<Modalidade, String> colunaValor;

    public ModalidadeCtrl() throws SQLException {
        this.model = new Modalidade();
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
        Double valor;
        String diasSemana = "";

        if (segunda.isSelected()) {
            diasSemana = diasSemana + "Seg,";
        }

        if (terca.isSelected()) {
            diasSemana = diasSemana + "Ter,";
        }

        if (quarta.isSelected()) {
            diasSemana = diasSemana + "Qua,";
        }

        if (quinta.isSelected()) {
            diasSemana = diasSemana + "Qui,";
        }

        if (sexta.isSelected()) {
            diasSemana = diasSemana + "Sex,";
        }

        if (sabado.isSelected()) {
            diasSemana = diasSemana + "Sab,";
        }

        if (campoValor.getText().isEmpty()) {
            valor = 0.00;
        } else {
            valor = Double.parseDouble(campoValor.getText());
        }

        if (this.model.getId() > 0) {
            edicao = true;
        }

        // Recolhe os dados do formulário
        this.model.setNome(campoNome.getText());
        this.model.setValor(valor);
        this.model.setDiasSemana(diasSemana);

//        this.model.setCpf(campoCPF.getText());
//        this.model.setPlano(comboPlano.getSelectionModel().getSelectedItem().toString());
        // Alerta.informar(this.model.getPlano());
        // Valida e persiste o modelo
        if (this.model.validarModelo().equals("0")) {
            this.model.setId(this.model.persistir());
            
            if (edicao) {
                mensagem = "Dados atualizados com sucesso.";
            } else {
                mensagem = "Dados incluídos com sucesso.";
            }
            
            if(this.model.getId() == -2){
                mensagem = "Já existe modalidade cadastrada com esse nome";
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
            this.model = new Modalidade();
            this.limparCampos();
        }

    }

    @FXML
    private void deletar() throws SQLException {

        if (Alerta.confirmar("Confirma a exclusão do item?")) {

            // Retorna o item da linha selecionada
            ObservableList<Modalidade> itemSelecionado, lista;
            lista = tabelaModalidades.getItems();
            itemSelecionado = tabelaModalidades.getSelectionModel().getSelectedItems();
            Modalidade remover = itemSelecionado.get(0);

            this.model = remover;

            // Avisa no console 
            System.out.println("Deletou: " + remover.getNome());

            // Remove da tabela da view
            itemSelecionado.forEach(lista::remove);
            tabelaModalidades.refresh();

            // Remove do DB
            this.model.deletar();

            // Reinicializa o model
            this.model = new Modalidade();

        }

    }

    @FXML
    private void editar() throws SQLException {

        // Retorna o item da linha selecionada
        ObservableList<Modalidade> itemSelecionado, lista;
        lista = tabelaModalidades.getItems();
        itemSelecionado = tabelaModalidades.getSelectionModel().getSelectedItems();
        Modalidade editar = itemSelecionado.get(0);

        // Avisa no console 
        // System.out.println("Editar: " + editar.getNome() + " " + editar.getId());
        // Preeche o campo com os dados para edição
        campoNome.setText(editar.getNome());
        campoValor.setText(Double.toString(editar.getValor()));
//        campoCPF.setText(editar.getCpf());
//        comboPlano.getSelectionModel().select(editar.getPlano());



        if(editar.getDiasSemana().contains("Seg,")){
            segunda.setSelected(true);
        }



        // Associa o item que deve ser editado ao model atual
        this.model = editar;

        // Altera para a aba de inserção/edição
        SingleSelectionModel<Tab> selectionModel = painelAbas.getSelectionModel();
        selectionModel.select(1);
    }

    @FXML
    private void novo() throws SQLException {
        this.model = new Modalidade();
        this.limparCampos();
        SingleSelectionModel<Tab> selectionModel = painelAbas.getSelectionModel();
        selectionModel.select(1);
    }

    public void limparCampos() {
        campoNome.setText("");
        campoValor.setText("");
        segunda.setSelected(false);
        terca.setSelected(false);
        quarta.setSelected(false);
        quinta.setSelected(false);
        sexta.setSelected(false);
        sabado.setSelected(false);

    }

    public void desenharTabela() throws SQLException {
        tabelaModalidades.getColumns().clear();

        colunaNome = new TableColumn<>("Nome");
        colunaNome.setMinWidth(195);
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        colunaValor = new TableColumn<>("Valor");
        colunaValor.setMinWidth(175);
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        ObservableList<Modalidade> lista = model.listarModalidades();
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
