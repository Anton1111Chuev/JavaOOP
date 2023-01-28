package view;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.ParseException;

public class BaseView extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    private JLabel jLabel;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox comboBox1;

    private Controller controller;

    public void setTextPanel(String text){
        this.jLabel.setText( "<html>" + text.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
    }

    public void addComboBox(String text){
        this.comboBox1.addItem(text);
    }
    public BaseView(Controller controller) {
        this.controller = controller;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    private void onOK() throws SQLException, ParseException {
        this.controller.addTask(this.textField1.getText(),
                this.textField2.getText(),
                (String) this.comboBox1.getSelectedItem());
    }

    private void onCancel() {

        dispose();
    }


}
