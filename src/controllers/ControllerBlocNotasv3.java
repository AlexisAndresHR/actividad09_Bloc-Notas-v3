
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.ModelBlocNotasv3;
import views.ViewBlocNotasv3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser; // Para utilizar el "selector de archivos".
import javax.swing.filechooser.FileNameExtensionFilter; // Para el filtro de extensiones de archivos.


public class ControllerBlocNotasv3 {
    
    ModelBlocNotasv3 modelblocnotas; // Crea un objeto para acceder al contenido del Model.
    ViewBlocNotasv3 viewblocnotas; // Crea un objeto para acceder al contenido de la View.
    
    JFileChooser selector_archivos = new JFileChooser(); // Objeto para utilizar el "selector de archivos".
    FileNameExtensionFilter filtro_extensiones = new FileNameExtensionFilter("Archivos de Texto", "txt"); // Objeto para aplicar un filtro de extensiones.
    
    ActionListener actionlistener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == viewblocnotas.jmi_leer) { // Comprueba si se selecciona el ítem "Leer".
                jmi_leer_action_performed();
            }
            else if (e.getSource() == viewblocnotas.jmi_guardar) { // Comprueba si se selecciona el ítem "Guardar".
                jmi_guardar_action_performed();
            }
            else if (e.getSource() == viewblocnotas.jmi_cifrar) { // Comprueba si se selecciona el ítem "Cifrar".
                jmi_cifrar_action_performed();
            }
            else if (e.getSource() == viewblocnotas.jmi_descifrar) { // Comprueba si se selecciona el ítem "Descifrar".
                jmi_descifrar_action_performed();
            }
            else if (e.getSource() == viewblocnotas.jmi_salir) { // Comprueba si se selecciona el ítem "Salir".
                System.exit(0); // Cierra la aplicación.
            }
        }
    };
    
    /**
     * Método para integrar los componentes del módelo MVC.
     * @param modelBloc
     * @param viewBloc 
     */
    public ControllerBlocNotasv3(ModelBlocNotasv3 modelBloc, ViewBlocNotasv3 viewBloc) {
        this.modelblocnotas = modelBloc;
        this.viewblocnotas = viewBloc;
        
        this.viewblocnotas.jmi_leer.addActionListener(actionlistener);
        this.viewblocnotas.jmi_guardar.addActionListener(actionlistener);
        this.viewblocnotas.jmi_cifrar.addActionListener(actionlistener);
        this.viewblocnotas.jmi_descifrar.addActionListener(actionlistener);
        this.viewblocnotas.jmi_salir.addActionListener(actionlistener);
        initComponents();
    }
    
    /**
     * Método para el ítem "Leer" del menú (ViewBlocNotasv3).
     */
    public void jmi_leer_action_performed() {
        selector_archivos.setFileFilter(filtro_extensiones); // Asigna el filtro de extensión .txt
        selector_archivos.showOpenDialog(viewblocnotas.jfc_cuadro_dialogo); // Abre el "selector de archivos" para abrir un archivo.
        File archivo = selector_archivos.getSelectedFile(); // Obtiene el archivo y lo guarda em la variable "archivo".
        String ruta = archivo.getPath(); // Guarda la ruta del archivo obtenido en la variable "ruta".
        
        modelblocnotas.setPath(ruta); // Asigna el valor de "ruta" (ubicación del archivo) a la variable "path".
        
        this.readFile(modelblocnotas.getPath()); // Invoca al método "readFile" y le da como parámetro el contenido de la variable "path" (Ubicada en el Model).
    }
    
    /**
     * Método para el ítem "Guardar" del menú (ViewBlocNotasv3).
     */
    public void jmi_guardar_action_performed() {
        selector_archivos.setFileFilter(filtro_extensiones); // Asigna el filtro de extensión .txt
        selector_archivos.showSaveDialog(viewblocnotas.jfc_cuadro_dialogo); // Abre el "selector de archivos" para guardar un archivo.
        File archivo = selector_archivos.getSelectedFile(); // Obtiene el archivo y lo guarda em la variable "archivo".
        String ruta = archivo.getPath(); // Guarda la ruta del archivo obtenido en la variable "ruta".
        
        modelblocnotas.setPath(ruta); // Asigna el valor de "ruta" (ubicación del archivo) a la variable "path".
        
        modelblocnotas.setMessage(viewblocnotas.jta_bloc_notas.getText()); // Asigna el contenido del área de texto (interfaz) a la variable "message".
        this.writeFile(modelblocnotas.getPath(), modelblocnotas.getMessage()); // Invoca al método "writeFile" y le da como parámetros el contenido de la variable "path" y de la variable "message" (ubicadas en el Model).
    }
    
    /**
     * Método para el ítem "Cifrar" del menú (ViewBlocNotasv3).
     */
    public void jmi_cifrar_action_performed() {
        String texto_area = viewblocnotas.jta_bloc_notas.getText();
        String texto_cifrado = "";
        for (int i = 0; i < texto_area.length(); i++) { // Ciclo para cifrar cada caracter del área de texto.
            char caracter = texto_area.charAt(i);
            int ascii_char = (int) caracter;
            ascii_char = ascii_char + 4; // Cambia el valor ascii del caracter.
            caracter = (char) ascii_char;
            texto_cifrado += caracter; // Acumula cada caracter cifrado.
        }
        viewblocnotas.jta_bloc_notas.setText(texto_cifrado); // Muestra el texto ya cifrado en la JTextAarea de la interfaz.
    }
    
    /**
     * Método para el ítem "Descifrar" del menú (ViewBlocNotasv3).
     */
    public void jmi_descifrar_action_performed() {
        String texto_area = viewblocnotas.jta_bloc_notas.getText();
        String texto_descifrado = "";
        for (int i = 0; i < texto_area.length(); i++) { // Ciclo para descifrar cada caracter del área de texto.
            char caracter = texto_area.charAt(i);
            int ascii_char = (int) caracter;
            ascii_char = ascii_char - 4; // Cambia el valor ascii del caracter.
            caracter = (char) ascii_char;
            texto_descifrado += caracter; // Acumula cada caracter descifrado.
        }
        viewblocnotas.jta_bloc_notas.setText(texto_descifrado); // Muestra el texto descifrado en la JTextAarea de la interfaz.
    }
    
    
// Métodos para la lectura y escritura del archivo de texto...
    
    /**
     * Método para mostrar en el área de texto, el contenido del archivo.
     * @param path: Indica la ruta de almacenamiento del archivo a manipular.
     * @return 
     */
    public String readFile (String path) {
        try {
            String row; // Variable que indica una "fila".
            String acumulador_texto = ""; // Variable para acumular todas las lineas de texto del archivo.
            try (FileReader archivo = new FileReader(path)) { // Permite leer el contenido del archivo.
                BufferedReader bufferedreader = new BufferedReader(archivo); // Permite almacenar el contenido del archivo de texto de forma temporal.
                while ((row = bufferedreader.readLine()) != null ) {
                    acumulador_texto += row + "\n"; // Acumulador de lineas.
                }
                viewblocnotas.jta_bloc_notas.setText(acumulador_texto);
            }
        }
        catch (FileNotFoundException err) { // Detecta un error en caso de no encontrar el archivo en el path indicado.
            System.err.println("Archivo no encontrado: " + err.getMessage());
        }
        catch (IOException err) { // Marca error en caso de no contar con los permisos para acceder al archivo indicado.
            System.err.println("Error en operación I/O: " + err.getMessage());
        }
        return null;
    };
    
    /**
     * Método para escribir (guardar) nuevo contenido en el archivo.
     * @param path: Indica la ruta de almacenamiento del archivo a manipular.
     * @param message: Variable que almacena el contenido del área de texto.
     */
    public void writeFile (String path, String message) {
        try {
            File archivo = new File(path); // Abre el archivo de la ruta especificada, si no existe, lo crea (según el path o ruta).
            FileWriter filewriter = new FileWriter(archivo, false); // Permite escribir en el archivo especificado.
            try (PrintWriter printwriter = new PrintWriter(filewriter) ) { // Permite guardar el nuevo contenido en del archivo especificado.
                printwriter.println(message);
                printwriter.close();
            }
        }
        catch (FileNotFoundException err) { // Detecta un error en caso de no encontrar el archivo en el path indicado.
            System.err.println("Archivo no encontrado: " + err.getMessage());
        }
        catch (IOException err) { // Marca error en caso de no contar con los permisos para acceder al archivo indicado.
            System.err.println("Error en operación I/O: " + err.getMessage());
        }
    }
    
    /**
     * Método para acceder a los componentes de la interfaz "ViewBlocNotasv3".
     */
    public void initComponents() {
        viewblocnotas.setVisible(true);
    }
    
}
