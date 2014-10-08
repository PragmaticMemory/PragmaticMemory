package fr.pragmaticmemory.fileProcessing.processor.arrayFormatter;
import fr.pragmaticmemory.fileProcessing.routeProvider.StringListRouteProvider;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import static java.awt.event.KeyEvent.VK_DELETE;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_UP;

public class ArrayFormatterGui {


    static final int[] IGNORED_KEYS = {VK_UP, VK_DOWN, VK_LEFT, VK_DOWN, VK_DELETE};
    static final List<Integer> ignoredKeyList;

    static
    {
            ignoredKeyList = null;

    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        final JTextArea textArea = new JTextArea();
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {

            }


            public void keyPressed(KeyEvent e) {
//                processAction(textArea);
            }


            public void keyReleased(KeyEvent e) {

                if (ignoreKey(e.getKeyCode())) {
                    return;
                }
                processAction(textArea);
            }


            private boolean ignoreKey(int keyCode) {
                if (keyCode == VK_UP || keyCode == VK_DOWN || keyCode == VK_LEFT || keyCode == VK_DOWN
                    || keyCode == VK_DELETE) {
                    return true;
                }
                return false;
            }
        });

        jFrame.add(textArea);
        jFrame.pack();
    }



    private static void processAction(JTextArea textArea) {
        String text = textArea.getText();
        String separator = "\n";
        String[] lines = text.split(separator);

        List<String> stringList = Arrays.asList(lines);
//                List<String> stringList = new ArrayList<String>();
//                stringList.add("+--+--+");
//                stringList.add("|col1|col2");
        ArrayFormatterProcessor processor = new ArrayFormatterProcessor();
        StringListRouteProvider routeProvider = new StringListRouteProvider(stringList);
        List<String> resultLines;
        try {
            processor.process(routeProvider);
            resultLines = routeProvider.getResultString();
            int resultLinesSize = resultLines.size();
            if (resultLinesSize == 1) {
                int caretPosition = textArea.getCaretPosition();
                textArea.setText(resultLines.get(0));
                textArea.setCaretPosition(caretPosition);
                return;
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < resultLinesSize - 1; i++) {
                String line = resultLines.get(i);
                builder.append(line);
                builder.append(separator);
            }
            builder.append(resultLines.get(resultLinesSize - 1));
            int caretPosition = textArea.getCaretPosition();
            textArea.setText(builder.toString());
            textArea.setCaretPosition(caretPosition + 1);
        }
        catch (Throwable e1) {
            e1.printStackTrace();  // Todo
        }
    }
}
