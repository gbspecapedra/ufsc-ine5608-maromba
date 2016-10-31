package controllers;

import main.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * Login Controller.
 */
public class InicioCtrl implements Initializable {

    private Main application;

    @FXML
    private void teste() {
        System.out.println(this.application.getFuncionarioLogado().getNome());
    }

    @FXML
    private MenuCtrl menuController;

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
    }
}
