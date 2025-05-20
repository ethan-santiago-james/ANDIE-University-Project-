/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package cosc202.andie;

import java.util.Queue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author jamet966
 */
public class AndieTest {
    
    public AndieTest() {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testGetZoomAfterSetZoom() {
        
        ImagePanel testPanel = new ImagePanel();
        
        testPanel.setZoom(120.0);
        
        assertEquals(testPanel.getZoom(), 120.0);
    }
    
    @Test
    public void testIsRecordingAfterStartedRecording() {
        
        EditableImage e = new EditableImage();
        
        e.setRecording(true);
        
        assertTrue(e.isRecording());
        
    }
    
    @Test
    public void testIsRecordingAfterStoppedRecording() {
        
        EditableImage e = new EditableImage();
        
        e.setRecording(false);
        
        assertFalse(e.isRecording());
        
    }
    
    @Test
    public void randomMacrosGenerated() {
        
        EditableImage e = new EditableImage();
        
        int size = 10;
        e.generateRandomMacro(size);
        
        Queue<ImageOperation> randomMacros = e.getRandomMacroOps();
                
        assertEquals(randomMacros.size(),size);
    }
    
    
    
}
