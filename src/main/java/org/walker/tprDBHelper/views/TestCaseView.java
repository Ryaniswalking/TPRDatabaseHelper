package org.walker.tprDBHelper.views;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;

public class TestCaseView extends JFrame {

    private JPanel mainPanel;

    private JLabel lbl_TestCaseName;
    private JTextField txt_TestCaseName;
    private JLabel lbl_TestDescription;
    private JScrollPane sp_Description;
    private JTextArea txtArea_TestDescription;
    private JLabel lbl_SourceAppCode;
    private JTextField txt_SourceAppCode;
    private JLabel lbl_RequestDec;

    private JLabel lbl_ResponseDec;
    private JTextArea txtArea_ResponseDec;
    private JTextArea txtArea_RequestDec;
    private JScrollPane sp_Response;
    private JScrollPane sp_Request;
    private JButton btn_Save;
    private JButton btn_Back;

    public TestCaseView(){

        this.setSize(850, 900);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);



        this.add(mainPanel);
    }

    public void addSaveButtonListener(ActionListener listenForSave){
        btn_Save.addActionListener(listenForSave);
    }

    public void addBackButtonListener(ActionListener listenForBack){
        btn_Back.addActionListener(listenForBack);
    }

    public void setTestCaseNameText(String value) {
        this.txt_TestCaseName.setText(value);
    }

    public void setTestDescriptionText(String value) {
        this.txtArea_TestDescription.setText(value);
    }

    public void setSourceAppCodeText(String value) {
        this.txt_SourceAppCode.setText(value);
    }

    public void setDecodedRequestText(String value) {
        this.txtArea_RequestDec.setText(value);
        this.txtArea_RequestDec.setCaretPosition(0);
    }

    public void setDecodedResponseText(String value) {
        this.txtArea_ResponseDec.setText(value);
        this.txtArea_ResponseDec.setCaretPosition(0);
    }

    public String getTestCaseNameText(){
        return this.txt_TestCaseName.getText();
    }

    public String getTestDescriptionText() {
        return this.txtArea_TestDescription.getText();
    }

    public String getSourceAppCodeText() {
        return this.txt_SourceAppCode.getText();
    }

    public String getRequestTextEncoded(){
        String encodedRequest = Base64.getEncoder().encodeToString(this.txtArea_RequestDec.getText().getBytes());
        return encodedRequest;
    }

    public String getResponseTextEncoded() {
        AtomicReference<String> encodedResponse = new AtomicReference<>(Base64.getEncoder().encodeToString(this.txtArea_ResponseDec.getText().getBytes()));
        return encodedResponse.get();
    }

    private void createUIComponents() {

        txtArea_RequestDec = new JTextArea();
        txtArea_ResponseDec = new JTextArea();

        //Set default caret to never update so the scroll bar default to top location after textfield is added.
        DefaultCaret caret = (DefaultCaret) txtArea_ResponseDec.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        caret = (DefaultCaret) txtArea_RequestDec.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        sp_Request = new JScrollPane(txtArea_RequestDec, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        sp_Response = new JScrollPane(txtArea_ResponseDec,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
}
