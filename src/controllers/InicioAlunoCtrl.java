package controllers;

import main.Main;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import models.Aluno;

/**
 * Login Controller.
 */
public class InicioAlunoCtrl implements Initializable {

    private Main application;
    private AlunoCtrl alunoCtrl;

    @FXML
    private void teste() {
        System.out.println(this.application.getFuncionarioLogado().getNome());
        System.out.println(this.application.getAlunoLogado().getNome());
    }

    @FXML
    private MenuCtrl menuController;

    @FXML
    Button liberarAcesso;

    @FXML
    private void liberarAcessoAcademia() throws SQLException {
        this.alunoCtrl.liberarAcessoAcademia(this.application.getAlunoLogado());
    }

    @FXML
    public void solicitarViewFrequencia(ActionEvent event) {
        application.exibirViewFrequencia();
    }

    @FXML
    public void solicitarViewPagamento(ActionEvent event) {
        application.exibirViewPagamento();
    }

    public void setApp(Main application) {
        this.application = application;
    }

    public void setMenuApp(Main application) {
        this.menuController.setApp(application);
    }

    public MenuCtrl getMenuController() {
        return menuController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("");
        try {
            this.alunoCtrl = new AlunoCtrl();
        } catch (SQLException ex) {
            Logger.getLogger(InicioAlunoCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
