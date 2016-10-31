package main;

import controllers.AlunoCtrl;
import controllers.FuncionarioCtrl;
import controllers.LoginCtrl;
import controllers.InicioCtrl;
import controllers.MatriculaCtrl;
import controllers.ModalidadeCtrl;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Funcionario;

/**
 * MAIN - GERENCIA LOGIN E NAVEGAÇÃO
 */
public class Main extends Application {

    private Stage stage;
    private String texto;
    private static Funcionario funcionarioLogado;

    // private final double MINIMUM_WINDOW_WIDTH = 600.0;
    // private final double MINIMUM_WINDOW_HEIGHT = 400.0;
    public static void main(String[] args) {
        // System.out.println(args[0]);
        Application.launch(Main.class, (java.lang.String[]) null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Main.funcionarioLogado = new Funcionario();

            // TESTES
//            if(Validador.validarCPF("04817039930")){
//                Alerta.log("CPF valido");
//            } else {
//                Alerta.log("CPF invalido");
//            }
//            Alerta.log(Validador.converterMD5("123"));
            this.stage = primaryStage;
            this.stage.setTitle("MAROMBA");

//            primaryStage.initStyle(StageStyle.UNDECORATED);
//            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
//            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            exibirViewLogin();
            primaryStage.show();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ajustarDimensoes(){
        stage.setHeight(400);
    }
    // MÉTODOS DE AUTENTICAÇÃO
    public boolean loginDoFuncionario(String matricula, String senha) throws SQLException, NoSuchAlgorithmException {
        Main.funcionarioLogado = funcionarioLogado.verificarCredenciais(matricula, senha);
        if (funcionarioLogado.getMatricula() > 0) {
            exibirViewInicio();
            return true;
        }
        return false;
    }

    public void logoffDoFuncionario() throws SQLException {

        exibirViewLogin();
    }

    // MÉTODOS DE NAVEGAÇÃO DO USUÁRIO
    public void exibirViewInicio() {
        try {
            ajustarDimensoes();
            stage.sizeToScene();
            InicioCtrl inicio = (InicioCtrl) alterarCena("inicio.fxml");
            inicio.setApp(this);
            inicio.setMenuApp(this);
            inicio.getMenuController().setPerfil(Main.funcionarioLogado.getFuncao());
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exibirViewModalidade() {
        try {
            ajustarDimensoes();
            stage.sizeToScene();
            ModalidadeCtrl modalidade = (ModalidadeCtrl) alterarCena("modalidade.fxml");
            modalidade.setApp(this);
            modalidade.setMenuApp(this);
            modalidade.getMenuController().setPerfil(Main.funcionarioLogado.getFuncao());
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exibirViewAluno() {
        try {
            ajustarDimensoes();
            stage.sizeToScene();
            AlunoCtrl alunoCtrl = (AlunoCtrl) alterarCena("aluno.fxml");
            alunoCtrl.setApp(this);
            alunoCtrl.setMenuApp(this);
            alunoCtrl.getMenuController().setPerfil(Main.funcionarioLogado.getFuncao());
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exibirViewLogin() {
        try {
            // stage.setWidth(400);
            // stage.setHeight(250);
            LoginCtrl login = (LoginCtrl) alterarCena("login.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exibirViewFuncionario() {
        try {
            ajustarDimensoes();
            stage.sizeToScene();
            FuncionarioCtrl funcionarioCtrl = (FuncionarioCtrl) alterarCena("funcionario.fxml");
            funcionarioCtrl.setApp(this);
            funcionarioCtrl.setMenuApp(this);
            funcionarioCtrl.getMenuController().setPerfil(Main.funcionarioLogado.getFuncao());
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void exibirViewMatricula() {
        try {
            stage.sizeToScene();
            stage.setHeight(600);
            MatriculaCtrl matriculaCtrl = (MatriculaCtrl) alterarCena("matricula.fxml");
            matriculaCtrl.setApp(this);
            matriculaCtrl.setMenuApp(this);
            matriculaCtrl.getMenuController().setPerfil(Main.funcionarioLogado.getFuncao());
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Initializable alterarCena(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream arquivoFxml = Main.class.getResourceAsStream("/views/" + fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane cena;
        try {
            cena = (AnchorPane) loader.load(arquivoFxml);
        } finally {
            arquivoFxml.close();
        }
        Scene scene = new Scene(cena);
        stage.setScene(scene);
        return (Initializable) loader.getController();
    }

    // GETTERS E SETTERS
    public Funcionario getFuncionarioLogado() {
        return Main.funcionarioLogado;
    }
}
