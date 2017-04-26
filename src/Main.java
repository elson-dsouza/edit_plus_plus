import org.fife.rsta.ac.LanguageSupport;
import org.fife.rsta.ac.c.CLanguageSupport;
import org.fife.rsta.ac.css.CssLanguageSupport;
import org.fife.rsta.ac.groovy.GroovyLanguageSupport;
import org.fife.rsta.ac.html.HtmlLanguageSupport;
import org.fife.rsta.ac.java.JavaLanguageSupport;
import org.fife.rsta.ac.js.JavaScriptLanguageSupport;
import org.fife.rsta.ac.jsp.JspLanguageSupport;
import org.fife.rsta.ac.perl.PerlLanguageSupport;
import org.fife.rsta.ac.php.PhpLanguageSupport;
import org.fife.rsta.ac.sh.ShellLanguageSupport;
import org.fife.rsta.ac.xml.XmlLanguageSupport;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Elson on 18/4/17.
 */

public class Main extends JFrame implements ActionListener {

    private JMenuBar menu;
    private JTabbedPane tabbedPane;
    private ArrayList<TextFrame> tabInstances;
    private String language;
    private HashMap<String,LanguageSupport> langs;

    public static  void main(String[] args) {
        new Main("Edit++");
    }

    private Main(String s) {
        super(s);

        menu = new JMenuBar();
        setJMenuBar(menu);
        buildMenu();

        tabbedPane = new JTabbedPane();
        add(tabbedPane);

        tabInstances =new ArrayList<>();

        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                close();
                super.windowClosing(windowEvent);
            }
        });

        language = SyntaxConstants.SYNTAX_STYLE_NONE;
        langs = new HashMap<>();
        initHashMap();
    }

    private void initHashMap() {
        langs.put(SyntaxConstants.SYNTAX_STYLE_C,new CLanguageSupport());
        langs.put(SyntaxConstants.SYNTAX_STYLE_CSS,new CssLanguageSupport());
        langs.put(SyntaxConstants.SYNTAX_STYLE_HTML,new HtmlLanguageSupport());
        langs.put(SyntaxConstants.SYNTAX_STYLE_JAVA,new JavaLanguageSupport());
        langs.put(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT,new JavaScriptLanguageSupport());
        langs.put(SyntaxConstants.SYNTAX_STYLE_JSP,new JspLanguageSupport());
        langs.put(SyntaxConstants.SYNTAX_STYLE_PERL,new PerlLanguageSupport());
        langs.put(SyntaxConstants.SYNTAX_STYLE_PHP,new PhpLanguageSupport());
        langs.put(SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL,new ShellLanguageSupport());
        langs.put(SyntaxConstants.SYNTAX_STYLE_XML,new XmlLanguageSupport());
    }

    private void buildMenu() {
        buildFileMenu();
        buildEditMenu();
        buildLanguageMenu();
        buildHelpMenu();
    }

    private void buildLanguageMenu() {
        JMenu lang = new JMenu("Language");
        menu.add(lang);

        JMenuItem t0 = new JMenuItem("Plain Text");
        t0.addActionListener(this);
        lang.add(t0);

        JMenuItem t1 = new JMenuItem("Java");
        t1.addActionListener(this);
        lang.add(t1);

        JMenuItem t2 = new JMenuItem("C");
        t2.addActionListener(this);
        lang.add(t2);

        JMenuItem t3 = new JMenuItem("C++");
        t3.addActionListener(this);
        lang.add(t3);

        JMenuItem t4 = new JMenuItem("JSP");
        t4.addActionListener(this);
        lang.add(t4);

        JMenuItem t5 = new JMenuItem("CSS");
        t5.addActionListener(this);
        lang.add(t5);

        JMenuItem t6 = new JMenuItem("HTML");
        t6.addActionListener(this);
        lang.add(t6);

        JMenuItem t7 = new JMenuItem("Javascript");
        t7.addActionListener(this);
        lang.add(t7);

        JMenuItem t8 = new JMenuItem("Shell");
        t8.addActionListener(this);
        lang.add(t8);

        JMenuItem t9 = new JMenuItem("XML");
        t9.addActionListener(this);
        lang.add(t9);

        JMenuItem t10 = new JMenuItem("Groovy");
        t10.addActionListener(this);
        lang.add(t10);

        JMenuItem t11 = new JMenuItem("Perl");
        t11.addActionListener(this);
        lang.add(t11);

        JMenuItem t12 = new JMenuItem("PHP");
        t12.addActionListener(this);
        lang.add(t12);

//        JMenuItem t13 = new JMenuItem("CSS");
//        t13.addActionListener(this);
//        lang.add(t13);
//
//        JMenuItem t14 = new JMenuItem("HTML");
//        t14.addActionListener(this);
//        lang.add(t14);
//
//        JMenuItem t15 = new JMenuItem("JAVASCRIPT");
//        t15.addActionListener(this);
//        lang.add(t15);
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
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
    }

    private void buildEditMenu() {
        JMenu edit = new JMenu("Edit");
        menu.add(edit);

        JMenuItem cut = new JMenuItem("Cut");
        cut.addActionListener(this);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        edit.add(cut);

        JMenuItem copy = new JMenuItem("Copy");
        copy.addActionListener(this);
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        edit.add(copy);

        JMenuItem paste = new JMenuItem("Paste");
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

        JMenuItem background_color=new JMenuItem("Background");
        edit.add(background_color);
        background_color.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));
        background_color.addActionListener(e -> {
            Color c = JColorChooser.showDialog(Main.this,"Choose color",Color.white);
            int i=tabbedPane.getSelectedIndex();
            tabInstances.get(i).textArea.setBackground(c);
        });

        JMenuItem text_color=new JMenuItem("Text Colour");
        edit.add(text_color);
        text_color.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
        text_color.addActionListener(e -> {
            Color c = JColorChooser.showDialog(Main.this,"Choose color",Color.white);
            int i = tabbedPane.getSelectedIndex();
            tabInstances.get(i).textArea.setForeground(c);
        });
    }

    private void buildHelpMenu(){
        JMenu help=new JMenu("Help");
        menu.add(help);

        JMenuItem t1=new JMenuItem("About");
        help.add(t1);
        t1.addActionListener(e -> JOptionPane.showMessageDialog(Main.this,"A simple text editor tool to write various programs built by Elson and Aadithya of M. S. Ramaiah Institute Of Technology as part of a project in the subject Java & J2EE","About",JOptionPane.INFORMATION_MESSAGE));

        JMenuItem t2=new JMenuItem("Version");
        help.add(t2);
        t2.addActionListener(e -> JOptionPane.showMessageDialog(Main.this,"Version 1.0","Version", JOptionPane.INFORMATION_MESSAGE));

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();
        switch (action) {
            case "Quit":
                close();
                break;
            case "Open":
                loadFile();
                break;
            case "Save":
                tabInstances.get(tabbedPane.getSelectedIndex()).saveFile(this);
                break;
            case "New":
                newFile();
                break;
            case "Save as...":
                tabInstances.get(tabbedPane.getSelectedIndex()).saveAs("Save as...", this);
                break;
            case "Select All":
                tabInstances.get(tabbedPane.getSelectedIndex()).textArea.selectAll();
                break;
            case "Copy":
                tabInstances.get(tabbedPane.getSelectedIndex()).textArea.copy();
                break;
            case "Cut":
                tabInstances.get(tabbedPane.getSelectedIndex()).textArea.cut();
                break;
            case "Paste":
                tabInstances.get(tabbedPane.getSelectedIndex()).textArea.paste();
                break;
            case "Find":
                Find find = new Find(tabInstances.get(tabbedPane.getSelectedIndex()), true, this);
                find.showDialog();
                break;
            case "Plain Text":
                language = SyntaxConstants.SYNTAX_STYLE_NONE;
                tabInstances.get(tabbedPane.getSelectedIndex()).setLanguage(language,langs);
                break;
            case "Java":
                language = SyntaxConstants.SYNTAX_STYLE_JAVA;
                tabInstances.get(tabbedPane.getSelectedIndex()).setLanguage(language,langs);
                break;
            case "C":
                language = SyntaxConstants.SYNTAX_STYLE_C;
                tabInstances.get(tabbedPane.getSelectedIndex()).setLanguage(language,langs);
                break;
            case "C++":
                language = SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS;
                tabInstances.get(tabbedPane.getSelectedIndex()).setLanguage(language,langs);
                break;
            case "CSS":
                language = SyntaxConstants.SYNTAX_STYLE_CSS;
                tabInstances.get(tabbedPane.getSelectedIndex()).setLanguage(language,langs);
                break;
            case "HTML":
                language = SyntaxConstants.SYNTAX_STYLE_HTML;
                tabInstances.get(tabbedPane.getSelectedIndex()).setLanguage(language,langs);
                break;
            case "Javascript":
                language = SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT;
                tabInstances.get(tabbedPane.getSelectedIndex()).setLanguage(language,langs);
                break;
            case "JSP":
                language = SyntaxConstants.SYNTAX_STYLE_JSP;
                tabInstances.get(tabbedPane.getSelectedIndex()).setLanguage(language,langs);
                break;
            case "Perl":
                language = SyntaxConstants.SYNTAX_STYLE_PERL;
                tabInstances.get(tabbedPane.getSelectedIndex()).setLanguage(language,langs);
                break;
            case "PHP":
                language = SyntaxConstants.SYNTAX_STYLE_PHP;
                tabInstances.get(tabbedPane.getSelectedIndex()).setLanguage(language,langs);
                break;
            case "Shell":
                language = SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL;
                tabInstances.get(tabbedPane.getSelectedIndex()).setLanguage(language,langs);
                break;
            case "XML":
                language = SyntaxConstants.SYNTAX_STYLE_XML;
                tabInstances.get(tabbedPane.getSelectedIndex()).setLanguage(language,langs);
                break;
            case "Groovy":
                language = SyntaxConstants.SYNTAX_STYLE_GROOVY;
                tabInstances.get(tabbedPane.getSelectedIndex()).setLanguage(language,langs);
                break;
        }
    }

    private void newFile() {
        RSyntaxTextArea textArea = new RSyntaxTextArea();
        textArea.setCodeFoldingEnabled(true);

        RTextScrollPane scrollPane = new RTextScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        TextFrame txtFrm = new TextFrame(null, textArea, language,langs);
        tabInstances.add(txtFrm);
        tabbedPane.addTab("Untitled", scrollPane);
        int i = tabbedPane.getTabCount()-1;
        setTabClosable(i, tabbedPane.getTitleAt(i));
        tabbedPane.setSelectedIndex(i);
        textArea.requestFocusInWindow();

    }

    private void setTabClosable(int index, String title) {
        JPanel pnlTab = new JPanel(new BorderLayout());
        pnlTab.setOpaque(false);
        JLabel lblTitle = new JLabel(title);
        JButton btnClose = new JButton("x");
        btnClose.setOpaque(false);
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);

        pnlTab.add(lblTitle,BorderLayout.WEST);
        pnlTab.add(btnClose, BorderLayout.EAST);
        tabbedPane.setTabComponentAt(index, pnlTab);

        btnClose.addActionListener(actionEvent -> {
            int i = tabbedPane.indexOfTabComponent(pnlTab);
            if(!tabInstances.get(i).close(this))
                return;
            tabInstances.remove(i);
            tabbedPane.remove(i);
        });

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
                RSyntaxTextArea textArea = new RSyntaxTextArea();
                textArea.setCodeFoldingEnabled(true);

                RTextScrollPane scrollPane = new RTextScrollPane(textArea);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

                TextFrame txtFrm = new TextFrame(file, textArea, language,langs);
                tabInstances.add(txtFrm);
                tabbedPane.addTab(file.getName(), scrollPane);
                int i = tabbedPane.getTabCount()-1;
                setTabClosable(i, tabbedPane.getTitleAt(i));
                tabbedPane.setSelectedIndex(i);
                textArea.requestFocusInWindow();

            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void close() {
        for(int i=0;i<tabbedPane.getTabCount();i++)
            if(!tabInstances.get(i).close(this))
                return;
        System.exit(0);
    }
}