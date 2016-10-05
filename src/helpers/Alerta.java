/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author orlando
 */
public class Alerta {
    
    public static boolean confirmar(String pergunta){        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação.");
        alert.setHeaderText(pergunta);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
    
    public static void informar(String texto){        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("MAROMBA");
        alert.setHeaderText(texto);
        alert.showAndWait();
    }
    
    public static void log(String string){
        System.out.println(string);
    }
}
