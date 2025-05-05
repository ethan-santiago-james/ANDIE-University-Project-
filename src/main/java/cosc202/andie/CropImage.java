package cosc202.andie;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;


/**
 *Crops image to a custom amount
 * @author linad885
 */
public class CropImage {
    
    
    public BufferedImage CropImage(BufferedImage input){      
        //calculate values for crop
        int xOrigin = Math.min(MouseActions.startPoint.x, MouseActions.endPoint.x);
        int yOrigin = Math.min(MouseActions.startPoint.y, MouseActions.endPoint.y);
        int cropWidth = Math.max(MouseActions.startPoint.x, MouseActions.endPoint.x) - Math.min(MouseActions.startPoint.x, MouseActions.endPoint.x);
        int cropHeight = Math.max(MouseActions.startPoint.y, MouseActions.endPoint.y) - Math.min(MouseActions.startPoint.y, MouseActions.endPoint.y);
        
        AffineTransform at = new AffineTransform();
        
        
    }
    
}
