import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Created by elson on 17/4/17.
 */

public class TextFrame implements DocumentListener {

    private JScrollPane tabRootPane;
    private JTextArea textPane;
    private boolean changed = false;
    private File file;

    public TextFrame(File file, JTextArea textPane) {
        this.file = file;
        this.textPane = textPane;
        textPane.getDocument().addDocumentListener(this);
        //tabRootPane.add(textPane);
        textPane.setText(readFile(file));
        changed = false;
    }


    private void newFile() {
        if (changed)
            saveFile();
        file = null;
        textPane.setText("");
        changed = false;
        //setTitle("Edit++");
    }

    private void loadFile() {

    }

    private String readFile(File file) {
        StringBuilder result = new StringBuilder();
        try (	FileReader fr = new FileReader(file);
                 BufferedReader reader = new BufferedReader(fr);) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot read file !", "Error !", JOptionPane.ERROR_MESSAGE);
        }
        return result.toString();
    }

    private void saveFile() {
        if (changed) {
            int ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (ans == JOptionPane.NO_OPTION)
                return;
        }
        if (file == null) {
            saveAs("Save");
            return;
        }
        String text = textPane.getText();
        try (PrintWriter writer = new PrintWriter(file);){
            if (!file.canWrite())
                throw new Exception("Cannot write file!");
            writer.write(text);
            changed = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveAs(String dialogTitle) {
//        JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
//        dialog.setDialogTitle(dialogTitle);
//        int result = dialog.showSaveDialog(this);
//        if (result != JFileChooser.APPROVE_OPTION)
//            return;
//        file = dialog.getSelectedFile();
//        try (PrintWriter writer = new PrintWriter(file);){
//            writer.write(textPane.getText());
//            changed = false;
//            setTitle("Edit++ - " + file.getName());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public void insertUpdate(DocumentEvent e) {
        changed = true;
    }

    public void removeUpdate(DocumentEvent e) {
        changed = true;
    }

    public void changedUpdate(DocumentEvent e) {
        changed = true;
    }

}