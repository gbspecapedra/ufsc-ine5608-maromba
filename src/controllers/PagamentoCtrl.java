package controllers;

import helpers.Alerta;
import main.Main;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Aluno;
import models.Pagamento;

public class PagamentoCtrl implements Initializable {

    // ASSOCIAÇÔES
    private Main application;
    private Pagamento model;
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

    // MAPEAMENTOS JAVAFX FXML
    @FXML
    private TabPane painelAbas;

    // TAB CADASTRO
    @FXML
    private TextField campoMatricula;
    
    @FXML
    private TextField campoDtIni;
    
    @FXML
    private TextField campoDtFim;
    
    @FXML
    private Label labelNome;
    
    @FXML
    private Label labelTotal;
    
    @FXML
    private ObservableList<Pagamento> listaPagamentos = FXCollections.observableArrayList();

    // TAB PESQUISA
    @FXML
    private TableView<Pagamento> tabelaPagamentos;
    
    @FXML
    private TableView<Pagamento> tabelaPagamentosPeriodo;
    
    @FXML
    TableColumn<Pagamento, Date> colunaDataV;
    
    @FXML
    TableColumn<Pagamento, Date> colunaDataP;
    
    @FXML
    TableColumn<Pagamento, Date> colunaSituacao;
    
    @FXML
    TableColumn<Pagamento, Date> colunaValor;
    
    @FXML
    TableColumn<Pagamento, Date> colunaDataVP;
    
    @FXML
    TableColumn<Pagamento, Date> colunaDataPP;
    
    @FXML
    TableColumn<Pagamento, Date> colunaSituacaoP;
    
    @FXML
    TableColumn<Pagamento, Date> colunaValorP;
    
    public PagamentoCtrl() throws SQLException {
        this.model = new Pagamento();
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
            } else if (alunoSelecionado.getPagamentos().isEmpty()) {
                Alerta.informar("Não há pagamentos cadastrados para o aluno selecionado.");
            } else {
                labelNome.setText(alunoSelecionado.getNome());
                this.desenharTabela(alunoSelecionado);
            }
        } else {
            Alerta.informar("Informe a matrícula do aluno.");
        }
        
    }
    
    
    
     @FXML
    private void pesquisarPeriodo() throws SQLException {
        String dtIni;
        String dtFim;
        
        dtIni = campoDtIni.getText();
        dtFim = campoDtFim.getText();
        Double total = 0.0;
        
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        pagamentos = this.model.listarPagamentosPorPeriodo(dtIni, dtFim);
        
        this.desenharTabelaPeriodo(pagamentos);
        
        
        for(Pagamento p:pagamentos){
            total = total + p.getValor();
        }
        
        labelTotal.setText("Total: "+total);
        
    }
    
    
    
    
    @FXML
    private void darBaixa() throws SQLException {
        
        Pagamento pagamentoSelecionado = new Pagamento();
        pagamentoSelecionado = tabelaPagamentos.getSelectionModel().getSelectedItem();
        
        if("Quitado".equals(pagamentoSelecionado.getSituacao())){
            Alerta.informar("O pagamento selecionado já está quitado.");
        } else {
            Aluno aluno = new Aluno();
            aluno.baixarPagamento(pagamentoSelecionado);
        }

        this.pesquisar();
    }

    // DISPARADORES DA VIEW
    public void limparCampos() {
        campoMatricula.setText("");
    }
    
    public void desenharTabela(Aluno aluno) throws SQLException {
        tabelaPagamentos.getColumns().clear();
        ObservableList<Pagamento> lista = FXCollections.observableArrayList(aluno.getPagamentos());

        colunaDataV = new TableColumn<>("Vencimento");
        colunaDataV.setMinWidth(100);
        colunaDataV.setCellValueFactory(new PropertyValueFactory<>("dtVencimento"));
        
        colunaDataP = new TableColumn<>("Pagamento");
        colunaDataP.setMinWidth(100);
        colunaDataP.setCellValueFactory(new PropertyValueFactory<>("dtPagamento"));
        
        colunaValor = new TableColumn<>("Valor");
        colunaValor.setMinWidth(80);
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        
        colunaSituacao = new TableColumn<>("Situação");
        colunaSituacao.setMinWidth(195);
        colunaSituacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));

        tabelaPagamentos.setItems(lista);
        tabelaPagamentos.getColumns().addAll(colunaDataV, colunaDataP, colunaValor, colunaSituacao);
    }
    
    
    public void desenharTabelaPeriodo(ArrayList<Pagamento> pagamentos) throws SQLException {
        tabelaPagamentosPeriodo.getColumns().clear();
        ObservableList<Pagamento> lista = FXCollections.observableArrayList(pagamentos);

        colunaDataVP = new TableColumn<>("Vencimento");
        colunaDataVP.setMinWidth(100);
        colunaDataVP.setCellValueFactory(new PropertyValueFactory<>("dtVencimento"));
        
        colunaDataPP = new TableColumn<>("Pagamento");
        colunaDataPP.setMinWidth(100);
        colunaDataPP.setCellValueFactory(new PropertyValueFactory<>("dtPagamento"));
        
        colunaValorP = new TableColumn<>("Valor");
        colunaValorP.setMinWidth(80);
        colunaValorP.setCellValueFactory(new PropertyValueFactory<>("valor"));
        
        colunaSituacaoP = new TableColumn<>("Situação");
        colunaSituacaoP.setMinWidth(195);
        colunaSituacaoP.setCellValueFactory(new PropertyValueFactory<>("situacao"));

        tabelaPagamentosPeriodo.setItems(lista);
        tabelaPagamentosPeriodo.getColumns().addAll(colunaDataVP, colunaDataPP, colunaValorP, colunaSituacaoP);
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
            this.alunoCtrl = new AlunoCtrl();
        } catch (SQLException ex) {
            Logger.getLogger(PagamentoCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
