package fr.pragmaticmemory.fileProcessing.processor.arrayFormatter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ArrayFormatterGui {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        final JTextArea textArea = new JTextArea();
        textArea.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {

            }


            public void keyPressed(KeyEvent e) {
            }


            public void keyReleased(KeyEvent e) {
                String text = textArea.getText();
                String[] lines = text.split("\n");

                System.out.println();
            }
        });

        jFrame.add(textArea);
        jFrame.pack();
    }
}
