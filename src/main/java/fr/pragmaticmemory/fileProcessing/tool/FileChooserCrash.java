package fr.pragmaticmemory.fileProcessing.tool;/*
* Attempt to reproduce JFileChooser Windows Crash
*/
import java.awt.BorderLayout;
import java.awt.Robot;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * @author scott
 */
public class FileChooserCrash {

    static final String DISPLAYED_MESSAGE
          = "Press \"Start...\" and let go of the mouse. Quickly press ESC key TWICE to stop. Robot will be used to change FileChooser to details view and then quickly click column headings until the crash.";
    static CountDownLatch latch = new CountDownLatch(1);
    static JFrame frame;
    static JButton button;
    static JFileChooser chooser;
    static Robot robot;


    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    swingMain(args);
                }
                catch (Exception ex) {
                    Logger.getLogger(FileChooserCrash.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        latch.await();

        // do the clicks
        sleep(3000);
        Window window = JDialog.getWindows()[1]; // better be there!
        System.out.println("window=" + window);
        int detailsX = window.getX() + window.getWidth() - 36;
        int detailY = window.getY() + 50;
        System.out.println("details at " + detailsX + ", " + detailY);
        robot.mouseMove(detailsX, detailY);
        clickMouse();
        sleep(100);

        int headingY = window.getY() + 90;
        long now = System.currentTimeMillis();
        while (System.currentTimeMillis() - now < 30 * 60 * 100) {
            int headingX = window.getX() + 50 + (int)(Math.random() * 400);
            robot.mouseMove(headingX, headingY);
            clickMouse();
            sleep(100);
        }
    }


    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException ex) {
        }
    }


    private static void clickMouse() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        sleep(100);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }


    private static void swingMain(String[] args) throws Exception {
        robot = new Robot();
        frame = new JFrame("JFileChooser Crash Test");
        button = new JButton("Start...");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showFileChooser(frame);
            }
        });
        frame.getContentPane().add(button, BorderLayout.CENTER);
        frame.getContentPane().add(new JLabel(DISPLAYED_MESSAGE), BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    private static void listenToEscapeKey(JComponent theFrame) {
        //Creating an input map of the Root Pane
        JComponent frontComponent = theFrame.getRootPane();
        if (frontComponent == null) {
            frontComponent = theFrame;
        }
        InputMap map = frontComponent.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        //Adding the key that needs to be captured
        map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");
        ActionMap actionMap = frontComponent.getActionMap();
        //Creating the action that needs to be executed when the key is pressed
        Action close = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        };
        actionMap.put("cancel", close);
    }


    static void showFileChooser(JFrame parent) {
        chooser = new JFileChooser("C:\\");
        listenToEscapeKey(chooser);
        latch.countDown();
        chooser.showOpenDialog(parent);
    }
}


