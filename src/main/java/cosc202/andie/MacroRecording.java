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

/**
 * Class that handles the UI elements
 * for the macro record feature
 * @author jamet966
 */
public class MacroRecording {
    
    private JButton b; // record button
    private ImagePanel target;
    
    public MacroRecording(JButton b, ImagePanel target) {
        
       this.target = target;
       this.b = b;
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
                           
                           JOptionPane.showMessageDialog(null,"Please select an image.");
                           return;
                       }
                       if(!target.getImage().isRecording()) {
                           
                           target.getImage().setRecording(true);
                           b.setText("Stop Recording");
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
                           b.setText("Record Macro");
                           b.setBackground(Color.GREEN);
                       }
                       
                   }
                   
               }
               );
    }
    
    
}
