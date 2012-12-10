package fr.pragmaticmemory.fileProcessing.tool;

import javax.swing.JFileChooser;
public class JFileChooserTester {
    public static void main(String[] args) throws InterruptedException {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.showSaveDialog(null);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
