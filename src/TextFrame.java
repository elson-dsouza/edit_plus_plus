import java.io.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Created by elson on 17/4/17.
 */

public class TextFrame implements DocumentListener {

    private JScrollPane tabRootPane;
    private JTextArea textArea;
    private boolean changed = false;
    private File file;

    public TextFrame(File file, JTextArea textArea) {
        this.file = file;
        this.textArea = textArea;
        textArea.getDocument().addDocumentListener(this);
        textArea.setText(readFile(file));
        changed = false;
    }


    private void newFile() {
        if (changed)
            //saveFile();
        file = null;
        textArea.setText("");
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

    public void saveFile(Main main) {
        if (file == null) {
            saveAs("Save",main);
            return;
        }
        String text = textArea.getText();
        try (PrintWriter writer = new PrintWriter(file);){
            if (!file.canWrite())
                throw new Exception("Cannot write file!");
            writer.write(text);
            changed = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAs(String dialogTitle, Main main) {
        JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
        dialog.setDialogTitle(dialogTitle);
        int result = dialog.showSaveDialog(main);
        if (result != JFileChooser.APPROVE_OPTION)
            return;
        file = dialog.getSelectedFile();
        try (PrintWriter writer = new PrintWriter(file);){
            writer.write(textArea.getText());
            changed = false;
            //setTitle("Edit++ - " + file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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