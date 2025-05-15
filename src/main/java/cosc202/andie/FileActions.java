package cosc202.andie;

import java.awt.GridLayout;
import java.util.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * <p>
 * Actions provided by the File menu.
 * </p>
 *
 * <p>
 * The File menu is very common across applications, and there are several items
 * that the user will expect to find here. Opening and saving files is an
 * obvious one, but also exiting the program.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 * @author Steven Mills
 * @version 1.0
 */
public class FileActions {

    private static ResourceBundle bundle = ResourceBundle.getBundle("bundle");

    /**
     * A list of actions for the File menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     * @param bundle language bundle for switching languages
     */
    public FileActions(ResourceBundle bundle) {
        this.bundle = bundle;
        actions = new ArrayList<>();
        actions.add(new FileOpenAction(bundle.getString("OPEN"), null, bundle.getString("OPEN A FILE"), KeyEvent.VK_O));
        actions.add(new FileSaveAction(bundle.getString("SAVE"), null, bundle.getString("SAVE THE FILE"), KeyEvent.VK_S));
        actions.add(new FileSaveAsAction(bundle.getString("SAVE AS"), null, bundle.getString("SAVE A COPY"), KeyEvent.VK_A));
        actions.add(new FileExitAction(bundle.getString("EXIT"), null, bundle.getString("EXIT THE PROGRAM"), KeyEvent.VK_X));
        actions.add(new FileExportAction(bundle.getString("EXPORT"), null, bundle.getString("EXPORT THE IMAGE"), KeyEvent.VK_E));
        actions.add(new ApplyMacroOption(bundle.getString("APPLY MACRO"), null, bundle.getString("APPLY MACRO"), null));
        actions.add(new RandomMacroOption("Generate Random Macro", null, "Generate Random Macro",null));
    }

    /**
     * <p>
     * Create a menu containing the list of File actions.
     * </p>
     *
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("FILE"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }
    
    public Action getFileOpenAction() throws IOException{
        return new FileOpenAction(null, Andie.getIcon("icons/open.png"), bundle.getString("OPEN A FILE"), null);
    }
    
    public Action getFileSaveAction() throws IOException{
        return new FileSaveAction(null, Andie.getIcon("icons/save.png"), bundle.getString("SAVE THE FILE"), null);
    }
    
    public Action getFileSaveAsAction() throws IOException{
        return new FileSaveAsAction(null, Andie.getIcon("icons/save-as.png"), bundle.getString("SAVE A COPY"), null);
    }

    /**
     * <p>
     * Action to open an image from file.
     * </p>
     *
     * @see EditableImage#open(String)
     */
    public class FileOpenAction extends ImageAction {

        /**
         * <p>
         * Create a new file-open action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        FileOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-open action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the FileOpenAction is triggered. It
         * prompts the user to select a file and opens it as an image.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            
            open();
        }
        
        public static void open() {
            
            boolean saveFirst = false;
            
            if(target.getImage().hasImage()) {
                
                int response = JOptionPane.showConfirmDialog(null, bundle.getString("SAVE_WARNING"), "Confirmation", JOptionPane.YES_NO_OPTION);
                
                if(response == JOptionPane.YES_OPTION) {
                    
                    saveFirst = true;
                    
                }
            }
            
            if(saveFirst == true) {
                
                try {
                    target.getImage().save();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
                }
                
            }
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().open(imageFilepath);
                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
                  
                }
            }

            target.repaint();
            target.getParent().revalidate();
            
        }

    }

    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     *
     * @see EditableImage#save()
     */
    public class FileSaveAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the FileSaveAction is triggered. It
         * saves the image to its original filepath.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            save();
        }
        
        public static void save() {
            
            try {
                target.getImage().save();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
            }
            
        }

    }

    /**
     * <p>
     * Action to save an image to a new file location.
     * </p>
     *
     * @see EditableImage#saveAs(String)
     */
    public class FileSaveAsAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save-as action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        FileSaveAsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save-as action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the FileSaveAsAction is triggered. It
         * prompts the user to select a file and saves the image to it.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            
            saveAs();
            
        }
        
        public static void saveAs() {
            
            if(!target.getImage().hasImage()) {
                
                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
            } else {
                
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(target);

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                        target.getImage().saveAs(imageFilepath);
                    } catch (Exception ex) {


                        //System.exit(1);
                    }
                }
            }
            
        }

    }

    /**
     * <p>
     * Action to quit the ANDIE application.
     * </p>
     */
    public class FileExitAction extends AbstractAction {

        /**
         * <p>
         * Create a new file-exit action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        FileExitAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-exit action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the FileExitAction is triggered. It
         * quits the program.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(FileSaveAction.target.getImage().hasImage()) {
                
               int response = JOptionPane.showConfirmDialog(null, bundle.getString("EXIT WARNING"), "Confirmation", JOptionPane.YES_NO_OPTION);
            
                
                if(response == JOptionPane.YES_OPTION) {

                    FileSaveAction.save();
                }     
            }
            
            System.exit(0);
        }

    }
    
    /**
     * <p>
     * Action to export the file which represents the image that has been edited
     * </p>
     */
    public class FileExportAction extends ImageAction {
        
        /**
         * <p>
         * Create a new file-export action
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExportAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            
            super(name,icon,desc,mnemonic);
            
        }
        
        /**
         * <p>
         * Callback for when the export action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the FileExportAction is triggered. It
         * exports the image file representing the image that has been edited to a given destination.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
 
            export(); 
        }
        
        
        public static void export() {
            
            if(!target.getImage().hasImage()) {
                
                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
            } else {
                
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle(bundle.getString("EXPORT THE IMAGE"));

                // Add filters BEFORE showing the dialog
                FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG " + bundle.getString("IMAGE") + " (*.png)", "png");
                FileNameExtensionFilter gifFilter = new FileNameExtensionFilter("GIF " + bundle.getString("IMAGE") + " (*.gif)", "gif");

                fileChooser.addChoosableFileFilter(pngFilter);
                fileChooser.addChoosableFileFilter(gifFilter);

                fileChooser.setFileFilter(pngFilter); // Set default filter to PNG

                int result = fileChooser.showSaveDialog(target);

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        File selectedFile = fileChooser.getSelectedFile();
                        String imageFilepath = selectedFile.getCanonicalPath();

                        // Determine the correct file format
                        String format = "png";
                        if(fileChooser.getFileFilter() != pngFilter) {

                            format = "gif";
                        }

                        // Ensure correct file extension
                        if (!imageFilepath.toLowerCase().endsWith("." + format)) {
                            imageFilepath += "." + format;
                        }

                        // Save the image in the selected format
                        BufferedImage image = target.getImage().getCurrentImage();
                        ImageIO.write(image, format, new File(imageFilepath));

                        JOptionPane.showMessageDialog(null, bundle.getString("EXPORT_SUCCESS"));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, bundle.getString("EXPORT FAILURE"));
                    }
                }    
                
            }
            
            
        }
    }
    
    
    public class ApplyMacroOption extends ImageAction {
        
        /**
         * <p>
         * Create a new apply-macro action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        ApplyMacroOption(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        
        public void actionPerformed(ActionEvent ae) {
            
            if(!target.getImage().hasImage()) {
                
                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
            } else {
                
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(target);

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                        target.getImage().readOpsFile(imageFilepath);
                    } catch (Exception ex) {

                        JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));

                    }
                }

                target.repaint();
                target.getParent().revalidate();
                
            }
            
            
            
        }
        
    }
    
     public class RandomMacroOption extends ImageAction {
        
        /**
         * <p>
         * Create a new apply-macro action.
         * </p>
         *
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if
         * null).
         */
        RandomMacroOption(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        
        public void actionPerformed(ActionEvent ae) {
            
            if(!target.getImage().hasImage()) {
                
                JOptionPane.showMessageDialog(null, bundle.getString("PLEASE SELECT AN IMAGE."));
            } else {
                
                JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5)); //2 rows, 2 columns, 5 spacing

                //contrast input
                JLabel macroSizeLabel = new JLabel("Macro Size");
                SpinnerNumberModel macroSizeModel = new SpinnerNumberModel(0, 0, 100, 1);
                JSpinner macroSizeSpinner = new JSpinner(macroSizeModel);

                panel.add(macroSizeLabel);
                panel.add(macroSizeSpinner);

                int option = JOptionPane.showOptionDialog(
                        null,
                        panel,
                        "Select A Macro Size",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        null
                );
                    
                int macroSize = 0;
                
                if(option == JOptionPane.OK_OPTION) {
                    
                   macroSize = macroSizeModel.getNumber().intValue();
                   target.getImage().generateRandomMacro(macroSize);
                   
                   JFileChooser fileChooser = new JFileChooser();
                   int result = fileChooser.showSaveDialog(target);
                           
                   if(result == JFileChooser.APPROVE_OPTION) {
                               
                      try {
                                  
                          String imageFilePath = fileChooser.getSelectedFile().getCanonicalPath();
                          target.getImage().saveAs(imageFilePath);
                      } catch(Exception ex) {}
                               
                   }
                    
                }

                
            }
            
            
            
        }
        
    }
    
}
