package main;

import controllers.AlunoCtrl;
import controllers.FrequenciaCtrl;
import controllers.FuncionarioCtrl;
import controllers.InicioAlunoCtrl;
import controllers.LoginCtrl;
import controllers.InicioCtrl;
import controllers.MatriculaCtrl;
import controllers.ModalidadeCtrl;
import controllers.PagamentoCtrl;
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
import models.Aluno;
import models.Funcionario;

/**
 * MAIN - GERENCIA LOGIN E NAVEGAÇÃO
 */
public class Main extends Application {

    private Stage stage;
    private String texto;
    private static Funcionario funcionarioLogado;
    private static Aluno alunoLogado;

    // private final double MINIMUM_WINDOW_WIDTH = 600.0;
    // private final double MINIMUM_WINDOW_HEIGHT = 400.0;
    public static void main(String[] args) {
//         System.out.println(args[0]);
        Application.launch(Main.class, (java.lang.String[]) null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Main.funcionarioLogado = new Funcionario();
            funcionarioLogado.setFuncao("");
            
            Main.alunoLogado = new Aluno();
            alunoLogado.setPlano("");

            this.stage = primaryStage;
            this.stage.setTitle("MAROMBA");

            exibirViewLogin();
            primaryStage.show();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ajustarDimensoes() {
        stage.setHeight(400);
    }

    // MÉTODOS DE AUTENTICAÇÃO
    public boolean loginDoFuncionario(String matricula, String senha) throws SQLException, NoSuchAlgorithmException {
        Main.setFuncionarioLogado(funcionarioLogado.verificarCredenciais(matricula, senha));
        if (funcionarioLogado.getMatricula() > 0) {
            exibirViewInicioFuncionario();
            return true;
        } else {
            return false;
        }

    }

    public boolean loginDoAluno(String matricula) throws SQLException, NoSuchAlgorithmException {
        Aluno aluno = alunoLogado.verificarCredenciais(matricula);
        Main.setAlunoLogado(aluno);
        if (alunoLogado.getMatricula() > 0) {
            exibirViewInicioAluno();
            return true;
        } else {
            return false;
        }
    }

    public void logoffDoFuncionario() throws SQLException {

        exibirViewLogin();
    }

    // MÉTODOS DE NAVEGAÇÃO DO USUÁRIO
    public void exibirViewInicioFuncionario() {
        try {
            ajustarDimensoes();
            stage.sizeToScene();
            InicioCtrl inicio = (InicioCtrl) alterarCena("inicio.fxml");
            inicio.setApp(this);
            inicio.setMenuApp(this);
            inicio.getMenuController().setPerfilFuncionario(Main.funcionarioLogado.getFuncao());
            inicio.getMenuController().setPerfilAluno(Main.alunoLogado.getPlano());
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exibirViewInicioAluno() {
        try {
            ajustarDimensoes();
            stage.sizeToScene();
            InicioAlunoCtrl inicio = (InicioAlunoCtrl) alterarCena("inicio_aluno.fxml");
            inicio.setApp(this);
            inicio.setMenuApp(this);
            inicio.getMenuController().setPerfilFuncionario(Main.funcionarioLogado.getFuncao());
            inicio.getMenuController().setPerfilAluno(Main.alunoLogado.getPlano());
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
            modalidade.getMenuController().setPerfilFuncionario(Main.funcionarioLogado.getFuncao());
            modalidade.getMenuController().setPerfilAluno(Main.alunoLogado.getPlano());
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
            alunoCtrl.getMenuController().setPerfilFuncionario(Main.funcionarioLogado.getFuncao());
            alunoCtrl.getMenuController().setPerfilAluno(Main.alunoLogado.getPlano());
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exibirViewPagamento() {
        try {
            ajustarDimensoes();
            stage.sizeToScene();
            PagamentoCtrl pagamentoCtrl = (PagamentoCtrl) alterarCena("pagamento.fxml");
            pagamentoCtrl.setApp(this);
            pagamentoCtrl.setMenuApp(this);
            pagamentoCtrl.getMenuController().setPerfilFuncionario(Main.funcionarioLogado.getFuncao());
            pagamentoCtrl.getMenuController().setPerfilAluno(Main.alunoLogado.getPlano());
            if(Main.alunoLogado.getPlano() != null){
                pagamentoCtrl.setPerfilAluno();
                pagamentoCtrl.desenharTabela(Main.alunoLogado);
                
            }
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
            funcionarioCtrl.getMenuController().setPerfilFuncionario(Main.funcionarioLogado.getFuncao());
            funcionarioCtrl.getMenuController().setPerfilAluno(Main.alunoLogado.getPlano());
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exibirViewFrequencia() {
        try {
            ajustarDimensoes();
            stage.sizeToScene();
            FrequenciaCtrl frequenciaCtrl = (FrequenciaCtrl) alterarCena("frequencia.fxml");
            frequenciaCtrl.setApp(this);
            frequenciaCtrl.setMenuApp(this);
            frequenciaCtrl.getMenuController().setPerfilFuncionario(Main.funcionarioLogado.getFuncao());
            frequenciaCtrl.getMenuController().setPerfilAluno(Main.alunoLogado.getPlano());
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
            matriculaCtrl.getMenuController().setPerfilFuncionario(Main.funcionarioLogado.getFuncao());
            matriculaCtrl.getMenuController().setPerfilAluno(Main.alunoLogado.getPlano());
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
    public static void setFuncionarioLogado(Funcionario funcionarioLogado) {
        Main.funcionarioLogado = funcionarioLogado;
    }

    public Funcionario getFuncionarioLogado() {
        return Main.funcionarioLogado;
    }

    public Aluno getAlunoLogado() {
        return Main.alunoLogado;
    }

    public static void setAlunoLogado(Aluno alunoLogado) {
        Main.alunoLogado = alunoLogado;
    }

}
