import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientCalculator extends JFrame {

    public static final String EVALUATE = "Evaluate expression";
    private JTextField txtExpression;
    private JLabel lblExpression, lblResult;
    private JTextArea taVariables;
    private JPanel UIPanel;
    private GridBagLayout gridbag;
    private GridBagConstraints gbc;

    public ClientCalculator() {
        super("Interpreter Calculator");

        txtExpression = new JTextField();
        lblExpression = new JLabel("Expression: ");
        lblResult = new JLabel("Evaluated expression: ");
        taVariables = new JTextArea("Add variables here...");

        JButton btnEvaluate = new JButton(ClientCalculator.EVALUATE);
        btnEvaluate.setMnemonic(KeyEvent.VK_E);
        ButtonHandler objButtonHandler = new ButtonHandler(this);

        btnEvaluate.addActionListener(objButtonHandler);

        UIPanel = new JPanel();

        gridbag = new GridBagLayout();
        UIPanel.setLayout(gridbag);
        gbc = new GridBagConstraints();

        UIPanel.add(txtExpression);
        UIPanel.add(lblExpression);
        UIPanel.add(lblResult);
        UIPanel.add(taVariables);
        UIPanel.add(btnEvaluate);

        gbc.insets.top = 5;
        gbc.insets.bottom = 5;
        gbc.insets.left = 5;
        gbc.insets.right = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gridbag.setConstraints(lblExpression, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(txtExpression, gbc);
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;


        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 10;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(btnEvaluate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gridbag.setConstraints(lblResult, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gridbag.setConstraints(taVariables, gbc);

        Container contentPane = getContentPane();
        contentPane.add(UIPanel, BorderLayout.CENTER);

        try {
            SwingUtilities.updateComponentTreeUI(
                    ClientCalculator.this);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new ClientCalculator();
        frame.addWindowListener(new WindowAdapter() {
                                    public void windowClosing(WindowEvent e) {
                                        System.exit(0);
                                    }
                                }
        );
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public String getExpression() {
        return txtExpression.getText();
    }

    public String getVariables() {
        return taVariables.getText();
    }

    public void setResult(String r) {
        lblResult.setText("Evaluated Expression: " + r);
    }

}

class ButtonHandler implements ActionListener {

    ClientCalculator objCalculatorManager;

    public ButtonHandler(ClientCalculator inObjCalculatorManager) {
        objCalculatorManager = inObjCalculatorManager;
    }

    public void actionPerformed(ActionEvent e) {
        String validationResult = null;
        if (e.getActionCommand().equals(ClientCalculator.EVALUATE)) {
            Calculator calc = new Calculator();
            Context ctx = new Context(objCalculatorManager.getVariables());


            //configure the calculator with the context
            calc.setContext(ctx);

            //set the expression to evaluate
            String x = objCalculatorManager.getExpression().replace(" ", "");
            calc.setExpression(x);

            objCalculatorManager.setResult(String.valueOf(calc.evaluate()));
        }
    }

    public ButtonHandler() {
    }


}

