/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helpers.Alerta;
import main.Main;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Menu Controller.
 */
public class MenuCtrl implements Initializable {

    private Main application;

    public void setApp(Main application) {
        this.application = application;
    }

    @FXML
    public void solicitarViewModalidades(ActionEvent event) {
        application.exibirViewModalidade();
    }

    @FXML
    public void solicitarViewMatricula(ActionEvent event) {
        application.exibirViewMatricula();
    }

    @FXML
    public void solicitarViewFrequencia(ActionEvent event) {
        application.exibirViewFrequencia();
    }

    @FXML
    public void solicitarViewPagamento(ActionEvent event) {
        application.exibirViewPagamento();
    }

    @FXML
    public void solicitarViewInicio(ActionEvent event) {
        application.exibirViewInicioFuncionario();
    }

    @FXML
    public void solicitarViewInicioAluno() {
        application.exibirViewAluno();
    }

    @FXML
    public void solicitarViewAluno(ActionEvent event) {
        application.exibirViewAluno();
    }

    @FXML
    public void solicitarViewFuncionario(ActionEvent event) {
        application.exibirViewFuncionario();
    }

    @FXML
    public void solicitarViewModalidade(ActionEvent event) {
        application.exibirViewModalidade();
    }

    @FXML
    public void solicitarLogoff(ActionEvent event) throws SQLException {
        application.logoffDoFuncionario();
    }

    @FXML
    MenuItem itemPagamento;

    @FXML
    MenuItem itemFrequencia;

    @FXML
    MenuItem itemFuncionario;

    @FXML
    MenuItem itemModalidade;

    @FXML
    MenuItem itemMatricula;

    @FXML
    MenuItem itemAluno;

    @FXML
    MenuItem menuCadastros;

    @FXML
    Menu menuRelatorios;

    public void setPerfilFuncionario(String funcao) {
        Alerta.log(funcao);
        
        if (funcao.equals("Gerente")) {
            menuCadastros.setVisible(true);
            menuRelatorios.setVisible(true);
            itemPagamento.setVisible(true);
            itemModalidade.setVisible(true);
        }
        
        
        if (funcao.equals("Professor")) {
            menuRelatorios.setVisible(true);
            itemModalidade.setVisible(false);
            menuCadastros.setVisible(false);
            itemPagamento.setVisible(false);
            itemFrequencia.setVisible(true);
        }

        if (funcao.equals("Recepcionista")) {
            itemModalidade.setVisible(false);
            itemFuncionario.setVisible(false);
            
             menuCadastros.setVisible(true);
            itemPagamento.setVisible(true);
        }
    }

    public void setPerfilAluno(String plano) {
        if (!plano.isEmpty()) {
            menuCadastros.setVisible(false);
            menuRelatorios.setVisible(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
