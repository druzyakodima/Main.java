import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ApplicationForm extends JFrame {

    private JTextField inputField;


    public ApplicationForm() throws Exception, HeadlessException {
        super.setTitle("Calculator v 1.0");
        setBounds(700, 250, 250, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setJMenuBar(createMenu());

        setLayout(new BorderLayout());

        add(createTop(), BorderLayout.NORTH);
        add(createBottom(), BorderLayout.CENTER);

        setVisible(true);
    }

    private JMenuBar createMenu() {

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        menu.add(new JMenuItem("Clear"));
        JMenuItem exit = menu.add(new JMenuItem("Exit"));
        exit.addActionListener(new ExitButtonListener());

        return menuBar;
    }

    private JPanel createBottom() throws Exception {

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("Nashorn");

        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());


        //DIGITS
        JPanel digitsPanel = new JPanel();

        digitsPanel.setLayout(new GridLayout(4, 3));
        ActionListener buttonLister = new ButtonListener(inputField);

        for (int i = 1; i <= 10; i++) {
            String buttonTitle = (i == 10) ? "0" : String.valueOf(i);
            JButton btn = new JButton(buttonTitle);
            btn.addActionListener(buttonLister);
            digitsPanel.add(btn);

        }

        bottom.add(digitsPanel, BorderLayout.CENTER);
        //OPERATORS
        JPanel advPanel = new JPanel();

        advPanel.setLayout(new GridLayout(4, 1));

        JButton clear = new JButton("C");
        clear.addActionListener(e -> inputField.setText(""));
        digitsPanel.add(clear);


        JButton minus = new JButton("-");
        minus.addActionListener(buttonLister);
        advPanel.add(minus);

        JButton plus = new JButton("+");
        plus.addActionListener(buttonLister);
        advPanel.add(plus);

        JButton multiply = new JButton("*");
        multiply.addActionListener(buttonLister);
        advPanel.add(multiply);

        JButton division = new JButton("/");
        division.addActionListener(buttonLister);
        advPanel.add(division);

        JButton calc = new JButton("=");
        calc.addActionListener(e -> {

            try {

                Object result = scriptEngine.eval(inputField.getText());
                inputField.setText(String.valueOf(result));

            } catch (ScriptException ex) {
                ex.printStackTrace();
            }
        });
        digitsPanel.add(calc);
        bottom.add(advPanel, BorderLayout.WEST);

        return bottom;
    }

    private JPanel createTop() {
        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        inputField = new JTextField();
        inputField.setEditable(false);
        top.add(inputField);

        return top;
    }

}
