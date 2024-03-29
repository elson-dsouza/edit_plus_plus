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

public class Find extends JDialog implements ActionListener, KeyListener {

    private TextFrame parent;
    private JTextField textField;
    private JCheckBox caseSensitive;
    private boolean finishedFinding = true;
    private Matcher matcher;

    Find(TextFrame parent, boolean modal, Main main) {
        super(main, modal);
        this.parent = parent;
        getContentPane().addKeyListener(this);
        getContentPane().setFocusable(true);
        initComponents();
        setTitle("Find");
        setLocationRelativeTo(main);
        pack();
        textField.requestFocusInWindow();
    }

    void showDialog() {
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new GridLayout(3, 1));
        JPanel panel1 = new JPanel();
        JLabel label = new JLabel("Find : ");
        panel1.add(label);

        textField = new JTextField(15);
        panel1.add(textField);
        label.setLabelFor(textField);
        add(panel1);

        JPanel panel2 = new JPanel();
        caseSensitive = new JCheckBox("Case sensitive");
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

        textField.addKeyListener(this);
        find.addKeyListener(this);
        close.addKeyListener(this);
        caseSensitive.addKeyListener(this);

    }

    private void find(String pattern) {
        if (!finishedFinding) {
            if (matcher.find()) {
                int selectionStart = matcher.start();
                int selectionEnd = matcher.end();
                parent.textArea.moveCaretPosition(matcher.start());
                parent.textArea.select(selectionStart, selectionEnd);
            }
            else {
                finishedFinding = true;
                JOptionPane.showMessageDialog(this, "You have reached the end of the file", "End of file",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            matcher = Pattern.compile(pattern).matcher(parent.textArea.getText());
            finishedFinding = false;
            find(pattern);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("Find")) {
            String input = textField.getText();
            StringBuilder pattern = new StringBuilder();
            if (!caseSensitive.isSelected()) {
                pattern.append("(?i)");
            }
            pattern.append(input);
            find(pattern.toString());
        } else if (cmd.equals("Close")) {
            closeDialog();
        }
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

}