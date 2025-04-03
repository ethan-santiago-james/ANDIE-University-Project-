/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cosc202.andie;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author jamet966
 */
public class KeyboardShortcuts implements KeyListener {
    
    public static boolean isCtrlPressed = false;
    public static boolean isShiftPressed = true;
    
    @Override
    public void keyPressed(KeyEvent e) {

        switch(e.getKeyCode()) {
                
            case 17: // CTRL
                    
                isCtrlPressed = true;
                break;
                
            case 16:
                    
                isShiftPressed = true;
                break;
                    
            

        }
        
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        
        switch (e.getKeyCode()) {
            
            case 17:
                
                isCtrlPressed = false;
                break;
                
            case 16:
                
                isShiftPressed = false;
                break;
            
        }
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
        switch(e.getKeyCode()) {
            
            case 83: // S
                
                if(isCtrlPressed && isShiftPressed) {
                    
                    
                } else if(isCtrlPressed) {
                    
                    
                    
                }
                break;
                
            case 69: // E
                
                if(isCtrlPressed) {
                    
                    
                }
                break;
                
            case 521: // PLUS SIGN
                
                if(isCtrlPressed) {
                    
                    
                    
                }
                break;
                
            case 45: // MINUS SIGN
                
                if(isCtrlPressed) {
                    
                    
                }
                break;
                
            case 89: // Y
                
                if(isCtrlPressed) {
                    
                    
                }
                break;
                
            case 90: // Z
                
                if(isCtrlPressed) {
                    
                    
                }
                break;
            
            case 227: // RIGHT ARROW
                
                break;
                
            case 226: // LEFT ARROW
                
                break;
        }
        
    }
    
}
