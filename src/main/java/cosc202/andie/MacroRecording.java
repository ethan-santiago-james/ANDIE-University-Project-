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

/**
 *
 * @author GGPC
 */
public class MacroRecording {
    
    private JButton b;
    private ImagePanel target;
    
    public MacroRecording(JButton b, ImagePanel target) {
        
       this.target = target;
       this.b = b;
       addActionListener();
        
    }
    
    public void addActionListener() {
        
       this.b.addActionListener(
               new ActionListener() {
                   
                   public void actionPerformed(ActionEvent ae) {
                       
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
