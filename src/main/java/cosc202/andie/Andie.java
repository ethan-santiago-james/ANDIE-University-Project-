package cosc202.andie;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.imageio.*;
import java.util.ResourceBundle;

/**
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * </p>
 *
 * <p>
 * This class is the entry point for the program. It creates a Graphical User
 * Interface (GUI) that provides access to various image editing and processing
 * operations.
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
public class Andie {

    private static JFrame frame;
    private static ImagePanel imagePanel;
    private static JMenuBar menuBar;
    private static JToolBar toolBar;
    private static JButton recordButton;
    
    private static FileActions fileActions;
    private static EditActions editActions;
    private static ViewActions viewActions;
    private static TransformActions transformActions;
    private static FilterActions filterActions;
    private static ColourActions colourActions;
    private static LanguageActions languageActions;
    private static ResourceBundle bundle = LanguageUtil.getBundle();

    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     *
     * <p>
     * This method sets up an interface consisting of an active image (an
     * {@code ImagePanel}) and various menus which can be used to trigger
     * operations to load, save, edit, etc. These operations are implemented
     * {@link ImageOperation}s and triggered via {@code ImageAction}s grouped by
     * their general purpose into menus.
     * </p>
     *
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     * @see LanguageActions
     *
     * @throws Exception if something goes wrong.
     */
    private static void createAndShowGUI() throws Exception {
        // Set up the main GUI frame
        frame = new JFrame("ANDIE");

        Image image = ImageIO.read(Andie.class.getClassLoader().getResource("icon.png"));
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // The main content area is an ImagePanel
        imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add in menus for various types of action the user may perform.
        recordButton = new JButton("Record Macro");
        recordButton.setBackground(Color.GREEN);
        
        menuBar = new JMenuBar();
        
        // Initialize action classes with the bundle
        fileActions = new FileActions(bundle);
        menuBar.add(fileActions.createMenu());

        editActions = new EditActions(bundle);
        menuBar.add(editActions.createMenu());

        viewActions = new ViewActions(bundle);
        menuBar.add(viewActions.createMenu());
        
        // Transform actions control the image can be transformed, being either rotated or flipped
        transformActions = new TransformActions(bundle);
        menuBar.add(transformActions.createMenu());

        filterActions = new FilterActions(bundle);
        menuBar.add(filterActions.createMenu());

        colourActions = new ColourActions(bundle);
        menuBar.add(colourActions.createMenu());

        languageActions = new LanguageActions(bundle);
        menuBar.add(languageActions.createMenu());
        menuBar.add(recordButton);
        
        MacroRecording mR = new MacroRecording(recordButton,imagePanel);
        
        KeyboardShortcuts k = new KeyboardShortcuts(imagePanel);
        frame.addKeyListener(k);

        frame.setJMenuBar(menuBar);
        
        toolBar = new JToolBar();
        
        //file actions to toolbar
        toolBar.add(fileActions.getFileOpenAction());
        toolBar.add(fileActions.getFileSaveAction());
        toolBar.add(fileActions.getFileSaveAsAction());
        
        //edit actions to toolbar
        toolBar.add(editActions.getUndoAction());
        toolBar.add(editActions.getRedoAction());
        
        //view actions to toolbar
        toolBar.add(viewActions.getZoomInAction());
        toolBar.add(viewActions.getZoomOutAction());
        
        //transform actions to toolbar
        toolBar.add(transformActions.getFlipHorizontal());
        toolBar.add(transformActions.getFlipVertical());
        toolBar.add(transformActions.getRotateAntiClockwise());
        toolBar.add(transformActions.getRotateClockwise());
        
        frame.add(toolBar, BorderLayout.NORTH);
        
        frame.pack();
        frame.setVisible(true);

    }
    
    /***
     * <p>
     * Refreshes GUI upon user switching languages
     * </p>
     * 
     * <p>
     * This method uses {@see LanguageUtil} to initialize
     * new instances of the "Actions" classes, then rebuilds
     * the JFrame with the new translated Strings
     * </p>
     * 
     * @see LanguageActions
     */
    public static void refreshGUI() {
        bundle = LanguageUtil.getBundle();

        // Re-initialize action classes with the new language bundle
        fileActions = new FileActions(bundle);
        menuBar.removeAll();
        menuBar.add(fileActions.createMenu());

        editActions = new EditActions(bundle);
        menuBar.add(editActions.createMenu());

        viewActions = new ViewActions(bundle);
        menuBar.add(viewActions.createMenu());
        
        transformActions = new TransformActions(bundle);
        menuBar.add(transformActions.createMenu());

        filterActions = new FilterActions(bundle);
        menuBar.add(filterActions.createMenu());

        colourActions = new ColourActions(bundle);
        menuBar.add(colourActions.createMenu());

        languageActions = new LanguageActions(bundle);
        menuBar.add(languageActions.createMenu());

        frame.setJMenuBar(menuBar);
        frame.setTitle("ANDIE");
        frame.revalidate();
        frame.repaint();
    }

    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     *
     * <p>
     * Creates and launches the main GUI in a separate thread. As a result, this
     * is essentially a wrapper around {@code createAndShowGUI()}.
     * </p>
     *
     * @param args Command line arguments, not currently used
     * @throws Exception If something goes awry
     * @see #createAndShowGUI()
     */
    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        });
    }
}
