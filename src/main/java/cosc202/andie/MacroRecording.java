/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cosc202.andie;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.util.ResourceBundle;

/**
 * Class that handles the UI elements
 * for the macro record feature
 * @author jamet966
 */
public class MacroRecording {
    
    private JButton b; // record button
    private ImagePanel target;
    private static ResourceBundle bundle = ResourceBundle.getBundle("bundle");
    
    /*
    * @param b the JButton that will listen for macro record options
    * @param target the current image in the panel being edited
    * @param bundle the bundle object that allows for internationalisation
    */
    public MacroRecording(JButton b, ImagePanel target, ResourceBundle bundle) {
        
       this.target = target;
       this.b = b;
       this.bundle = bundle;
       addActionListener();
        
    }
    
    /*
    * Method that adds an action listener to the button
    * so that it knows when to start, and stop recording.
    * This method also handles the UI for saving a macro
    * file to be used later on.
    */
    public void addActionListener() {
        
       this.b.addActionListener(
               new ActionListener() {
                   
                   public void actionPerformed(ActionEvent ae) {
                       
                       if(!target.getImage().hasImage()) {
                           
                           JOptionPane.showMessageDialog(null,bundle.getString("PLEASE SELECT AN IMAGE."));
                           return;
                       }
                       if(!target.getImage().isRecording()) {
                           
                           target.getImage().setRecording(true);
                           b.setText(bundle.getString("STOP RECORDING"));
                           b.setBackground(Color.RED);

                       } else {
                           
                           JFileChooser fileChooser = new JFileChooser();
                           int result = fileChooser.showSaveDialog(target);
                           
                           if(result == JFileChooser.APPROVE_OPTION) {
                               
                              try {
                                  
                                  String imageFilePath = fileChooser.getSelectedFile().getCanonicalPath();
                                  target.getImage().saveAs(imageFilePath);
                              } catch(Exception ex) {}
                               
                           }
                           target.getImage().stopRecording();
                           target.getImage().setRecording(false);
                           b.setText(bundle.getString("RECORD MACRO"));
                           b.setBackground(Color.GREEN);
                       }
                       
                   }
                   
               }
               );
    }
    
    
}
