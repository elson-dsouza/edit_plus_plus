import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Elson on 17/4/17.
 */

<<<<<<< HEAD
public class FindAndReplace extends JDialog implements ActionListener, KeyListener {

    private TextFrame parent;
    private JTextField textFieldFind;
    private JCheckBox caseSensitive;
    private boolean finishedFinding = true;
    private Matcher matcher;
    private JTextField textFieldReplace;
    private JCheckBox regularExpression;

    FindAndReplace(TextFrame parent, boolean modal, Main main, boolean replace) {
        super(main, modal);
        this.parent = parent;
        getContentPane().addKeyListener(this);
        getContentPane().setFocusable(true);
        if(!replace) {
            initFindComponents();
            setTitle("Find");
        }
        else {
            initFindAndReplaceComponents();
            setTitle("Find And Replace");
        }
        setLocationRelativeTo(main);
        pack();
        textFieldFind.requestFocusInWindow();
    }

    private void initFindAndReplaceComponents() {
        setLayout(new GridLayout(5, 1));
        JPanel panel1 = new JPanel();
        JLabel label = new JLabel("Find : ");
        panel1.add(label);

        textFieldFind = new JTextField(15);
        panel1.add(textFieldFind);
        label.setLabelFor(textFieldFind);
        add(panel1);

        JPanel panel2 = new JPanel();
        JLabel label2 = new JLabel("Replace : ");
        panel2.add(label2);

        textFieldReplace = new JTextField(15);
        panel2.add(textFieldReplace);
        label.setLabelFor(textFieldReplace);
        add(panel2);

        JPanel panel3 = new JPanel();
        caseSensitive = new JCheckBox("Case sensitive");
        regularExpression = new JCheckBox("Regular Expression");
        panel3.add(regularExpression);
        panel3.add(caseSensitive);
        add(panel3);

        JPanel panel4 = new JPanel();
        JButton replace = new JButton("Replace");
        JButton replaceAll = new JButton("Replace All");
        JButton close = new JButton("Close");
        replace.addActionListener(this);
        replaceAll.addActionListener(this);
        close.addActionListener(this);
        panel4.add(replace);
        panel4.add(replaceAll);
        panel4.add(close);
        add(panel4);

        textFieldFind.addKeyListener(this);
        textFieldReplace.addKeyListener(this);
        replace.addKeyListener(this);
        replaceAll.addKeyListener(this);
        close.addKeyListener(this);
        caseSensitive.addKeyListener(this);
        regularExpression.addKeyListener(this);

    }

    void showDialog() {
        setVisible(true);
    }

    private void initFindComponents() {
        setLayout(new GridLayout(3, 1));
        JPanel panel1 = new JPanel();
        JLabel label = new JLabel("Find : ");
        panel1.add(label);

        textFieldFind = new JTextField(15);
        panel1.add(textFieldFind);
        label.setLabelFor(textFieldFind);
        add(panel1);

        JPanel panel2 = new JPanel();
        caseSensitive = new JCheckBox("Case sensitive");
        regularExpression = new JCheckBox("Regular Expression");
        panel2.add(regularExpression);
        panel2.add(caseSensitive);
        add(panel2);

        JPanel panel3 = new JPanel();
        JButton find = new JButton("Find");
        JButton close = new JButton("Close");
        find.addActionListener(this);
        close.addActionListener(this);
        panel3.add(find);
        panel3.add(close);
        add(panel3);

        textFieldFind.addKeyListener(this);
        find.addKeyListener(this);
        close.addKeyListener(this);
        caseSensitive.addKeyListener(this);
        regularExpression.addKeyListener(this);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
//        String cmd = e.getActionCommand();
//        if (cmd.equals("Find")) {
//            String input = textField.getText();
//            StringBuilder pattern = new StringBuilder();
//            if (!caseSensitive.isSelected()) {
//                pattern.append("(?i)");
//            }
//            pattern.append(input);
//            find(pattern.toString());
//        } else if (cmd.equals("Close")) {
//            closeDialog();
//        }
    }

    private void closeDialog() {
        setVisible(false);
        dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            closeDialog();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent){}

=======
public class FindAndReplace extends JFrame {

    private static final long serialVersionUID = 1L;

    private RSyntaxTextArea textArea;
    private JTextField searchField;
    private JCheckBox regexCB;
    private JCheckBox matchCaseCB;

    public FindAndReplace(RSyntaxTextArea textArea) {

        this.textArea = textArea;


        //setContentPane(cp);
        setTitle("Find and Replace Demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

    }

    public void actionPerformed(ActionEvent e) {

        // "FindNext" => search forward, "FindPrev" => search backward
        String command = e.getActionCommand();
        boolean forward = "FindNext".equals(command);

        // Create an object defining our search parameters.
        SearchContext context = new SearchContext();
        String text = searchField.getText();
        if (text.length() == 0) {
            return;
        }
        context.setSearchFor(text);
        context.setMatchCase(matchCaseCB.isSelected());
        context.setRegularExpression(regexCB.isSelected());
        context.setSearchForward(forward);
        context.setWholeWord(false);

        boolean found = SearchEngine.find(textArea, context).wasFound();
        if (!found) {
            JOptionPane.showMessageDialog(this, "Text not found");
        }


    }
>>>>>>> b5c1fd2... Working on find and replace
}