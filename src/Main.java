import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by elson on 18/4/17.
 */

public class Main extends JFrame implements ActionListener {

    private JMenuBar menu;
    private JMenuItem cut;
    private JMenuItem copy;
    private JMenuItem paste;
    private JTabbedPane tabbedPane;
    private ArrayList<TextFrame> tabInstances;

    public static  void main(String[] args) {
        new Main("Edit++");
    }

    public Main(String s) {
        super(s);

        menu = new JMenuBar();
        setJMenuBar(menu);
        buildMenu();

        tabbedPane = new JTabbedPane();
        add(tabbedPane);

        tabInstances =new ArrayList<>();

        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void buildMenu() {
        buildFileMenu();
        buildEditMenu();
    }

    private void buildFileMenu() {
        JMenu file = new JMenu("File");
        menu.add(file);

        JMenuItem n = new JMenuItem("New");
        n.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        n.addActionListener(this);
        file.add(n);

        JMenuItem open = new JMenuItem("Open");
        file.add(open);
        open.addActionListener(this);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));

        JMenuItem save = new JMenuItem("Save");
        file.add(save);
        save.addActionListener(this);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

        JMenuItem saveas = new JMenuItem("Save as...");
        saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
        file.add(saveas);
        saveas.addActionListener(this);

        JMenuItem quit = new JMenuItem("Quit");
        file.add(quit);
        quit.addActionListener(this);
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
    }

    private void buildEditMenu() {
        JMenu edit = new JMenu("Edit");
        menu.add(edit);

        cut = new JMenuItem("Cut");
        cut.addActionListener(this);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        edit.add(cut);

        copy = new JMenuItem("Copy");
        copy.addActionListener(this);
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        edit.add(copy);

        paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        edit.add(paste);
        paste.addActionListener(this);

        JMenuItem find = new JMenuItem("Find");
        find.addActionListener(this);
        edit.add(find);
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));

        JMenuItem sall = new JMenuItem("Select All");
        sall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        sall.addActionListener(this);
        edit.add(sall);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();
        if (action.equals("Quit")) {
            System.exit(0);
        } else if (action.equals("Open")) {
            loadFile();
        } else if (action.equals("Save")) {
            tabInstances.get(tabbedPane.getSelectedIndex()).saveFile(this);
        } else if (action.equals("New")) {
            newFile();
        } else if (action.equals("Save as...")) {
            tabInstances.get(tabbedPane.getSelectedIndex()).saveAs("Save as...",this);
        } else if (action.equals("Select All")) {
            tabInstances.get(tabbedPane.getSelectedIndex()).textArea.selectAll();
        } else if (action.equals("Copy")) {
            tabInstances.get(tabbedPane.getSelectedIndex()).textArea.copy();
        } else if (action.equals("Cut")) {
            tabInstances.get(tabbedPane.getSelectedIndex()).textArea.cut();
        } else if (action.equals("Paste")) {
            tabInstances.get(tabbedPane.getSelectedIndex()).textArea.paste();
        } else if (action.equals("Find")) {
            Find find = new Find(tabInstances.get(tabbedPane.getSelectedIndex()), true,this);
            find.showDialog();
        }
    }

    private void newFile() {
        JTextArea textArea = new JTextArea(100, 100);
        JScrollPane scrollPane = new JScrollPane(textArea);
        TextFrame txtFrm = new TextFrame(null, textArea);
        tabInstances.add(txtFrm);
        tabbedPane.addTab("Untitled", scrollPane);
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
        textArea.requestFocusInWindow();
    }

    private void loadFile() {
        JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
        dialog.setMultiSelectionEnabled(false);
        try {
            int result = dialog.showOpenDialog(this);
            if (result == JFileChooser.CANCEL_OPTION)
                return;
            if (result == JFileChooser.APPROVE_OPTION) {

                File file = dialog.getSelectedFile();
                JTextArea textArea = new JTextArea(100, 100);
                JScrollPane scrollPane = new JScrollPane(textArea);
                TextFrame txtFrm = new TextFrame(file, textArea);
                tabInstances.add(txtFrm);
                tabbedPane.addTab(file.getName(), scrollPane);
                tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
                textArea.requestFocusInWindow();

            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
