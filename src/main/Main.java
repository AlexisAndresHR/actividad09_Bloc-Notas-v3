
package main;

import controllers.ControllerBlocNotasv3;
import models.ModelBlocNotasv3;
import views.ViewBlocNotasv3;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ModelBlocNotasv3 modelblocnotas = new ModelBlocNotasv3();
        ViewBlocNotasv3 viewblocnotas = new ViewBlocNotasv3();
        ControllerBlocNotasv3 controllerblocnotas = new ControllerBlocNotasv3(modelblocnotas, viewblocnotas); // Integra los componentes del modelo MVC.
        
    }
    
}
