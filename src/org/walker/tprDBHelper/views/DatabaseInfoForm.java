package org.walker.tprDBHelper.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class DatabaseInfoForm extends JFrame {

    private JTextField text_DesignDoc;
    private JTextField text_ViewName;
    private JButton btn_Enter;
    private JPanel panel;
    private JLabel label_DesignDoc;
    private JLabel label_ViewName;

    public DatabaseInfoForm() {

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(300, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        this.add(panel);
    }

    public String getDesignDocText(){
        return text_DesignDoc.getText();
    }

    public String getViewNameText(){
        return text_ViewName.getText();
    }

    public void addEnterButtonListener(ActionListener listenForEnter){
        btn_Enter.addActionListener(listenForEnter);
    }

    public void displayErrorMessage(String errorMessage){
        JOptionPane.showMessageDialog(this, errorMessage);
    }

}
