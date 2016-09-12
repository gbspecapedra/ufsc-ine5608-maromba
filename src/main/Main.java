package main;

import controllers.FuncionarioCtrl;
import controllers.LoginCtrl;
import controllers.InicioCtrl;
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
import javafx.stage.StageStyle;
import models.Funcionario;
import models.Log;

/**
 * MAIN - GERENCIA LOGIN E NAVEGAÇÃO
 */
public class Main extends Application {

    private Stage stage;
    private String texto;
    private Log log;
    
//    private final double MINIMUM_WINDOW_WIDTH = 600.0;
//    private final double MINIMUM_WINDOW_HEIGHT = 400.0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
              
    // System.out.println(args[0]);
        
        Application.launch(Main.class, (java.lang.String[]) null);
    }
    private Funcionario loggedUser;

    @Override
    public void start(Stage primaryStage) {
        try {            
            
            // TESTES
//            if(Validador.validarCPF("04817039930")){
//                Alerta.log("CPF valido");
//            } else {
//                Alerta.log("CPF invalido");
//            }
            
//            Alerta.log(Validador.converterMD5("123"));
            
            this.loggedUser = new Funcionario();
            this.log = new Log();
            stage = primaryStage;
            stage.setTitle("MAROMBA");
//            primaryStage.initStyle(StageStyle.UNDECORATED);
//            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
//            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            exibirViewLogin();
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // MÉTODOS DE AUTENTICAÇÃO
    public boolean userLogging(String userId, String password) throws SQLException, NoSuchAlgorithmException {
        
        loggedUser = loggedUser.verificarCredenciais(userId, password);
        if (loggedUser.getMatricula() > 0) {
            exibirViewInicio();
            return true;
        }
        return false;
    }

    public void userLogout() throws SQLException {
        this.loggedUser = new Funcionario();
        exibirViewLogin();
    }

    // MÉTODOS DE NAVEGAÇÃO DO USUÁRIO
    public void exibirViewInicio() {
        try {
            stage.sizeToScene();
            InicioCtrl inicio = (InicioCtrl) alterarCena("inicio.fxml");
            inicio.setApp(this);
            inicio.setMenuApp(this); 
            inicio.getMenuController().setPerfil(this.loggedUser.getFuncao());
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
            stage.sizeToScene();
            FuncionarioCtrl funcionarioCtrl = (FuncionarioCtrl) alterarCena("funcionario.fxml");
            funcionarioCtrl.setApp(this);
            funcionarioCtrl.setMenuApp(this);
            funcionarioCtrl.getMenuController().setPerfil(this.loggedUser.getFuncao());
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    // LOG
     public void criarLog(String descricao) throws SQLException {
       this.log = new Log();
       this.log.setDescricao(descricao);
       this.log.setIdusuario(loggedUser.getMatricula());
       this.log.inserirLog(this.log);
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
    public Funcionario getLoggedUser() {
        return loggedUser;
    }
}
