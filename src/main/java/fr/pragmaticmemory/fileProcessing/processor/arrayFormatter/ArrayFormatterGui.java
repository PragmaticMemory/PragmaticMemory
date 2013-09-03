package fr.pragmaticmemory.fileProcessing.processor.arrayFormatter;
import fr.pragmaticmemory.fileProcessing.routeProvider.StringListRouteProvider;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
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

//                List<String> stringList = Arrays.asList(lines);
                List<String> stringList = new ArrayList<String>();
                stringList.add("+--+--+");
                stringList.add("|col1|col2|");
                ArrayFormatterProcessor processor = new ArrayFormatterProcessor();
                StringListRouteProvider routeProvider = new StringListRouteProvider(stringList);
                List<String> resultString;
                try {
                    processor.process(routeProvider);
                    resultString = routeProvider.getResultString();
                }
                catch (Exception e1) {
                    e1.printStackTrace();  // Todo
                }
                System.out.println();
            }
        });

        jFrame.add(textArea);
        jFrame.pack();
    }
}
