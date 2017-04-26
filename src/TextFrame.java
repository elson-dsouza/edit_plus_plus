import org.fife.rsta.ac.LanguageSupport;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.*;
import java.util.HashMap;

/**
 * Created by Elson on 17/4/17.
 */

public class TextFrame implements DocumentListener {

    RSyntaxTextArea textArea;
    private boolean changed = false;
    private File file;
    private String language;

    TextFrame(File file, RSyntaxTextArea textArea, String language,HashMap<String,LanguageSupport> langs) {
        this.file = file;
        this.textArea = textArea;
        textArea.getDocument().addDocumentListener(this);
        if(file!=null)
            textArea.setText(readFile(file));
        changed = false;
        this.language = language;
        setLanguage(language,langs);
    }

    void setLanguage(String language,HashMap<String,LanguageSupport> langs) {
        textArea.setSyntaxEditingStyle(language);
        if (langs.containsKey(this.language))
            langs.get(this.language).uninstall(textArea);
        if(langs.containsKey(language))
            langs.get(language).install(textArea);
        this.language = language;
    }

    private String readFile(File file) {
        StringBuilder result = new StringBuilder();
        try (	FileReader fr = new FileReader(file);
                 BufferedReader reader = new BufferedReader(fr)) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot read file !", "Error !", JOptionPane.ERROR_MESSAGE);
        }
        return result.toString();
    }

    void saveFile(Main main) {
        if (file == null) {
            saveAs("Save",main);
            return;
        }
        String text = textArea.getText();
        try (PrintWriter writer = new PrintWriter(file)){
            if (!file.canWrite())
                throw new Exception("Cannot write file!");
            writer.write(text);
            changed = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void saveAs(String dialogTitle, Main main) {
        JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
        dialog.setDialogTitle(dialogTitle);
        int result = dialog.showSaveDialog(main);
        if (result != JFileChooser.APPROVE_OPTION)
            return;
        file = dialog.getSelectedFile();
        try (PrintWriter writer = new PrintWriter(file)){
            writer.write(textArea.getText());
            changed = false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    boolean close(Main main){
        if (changed) {
            int ans = JOptionPane.showConfirmDialog(null, "The file has been changed. You want to save it?", "Save file",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (ans == JOptionPane.NO_OPTION)
                return true;
            else if (ans == JOptionPane.CLOSED_OPTION || ans == JOptionPane.CANCEL_OPTION)
                return false;
            saveFile(main);
        }
        return true;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        changed = true;
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        changed = true;
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        changed = true;
    }

}