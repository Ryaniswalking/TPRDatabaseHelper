package org.walker.tprDataConverter.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FileInputView extends JFrame {

    private JPanel mainPanel;
    private JTextField txt_ReqFilePath;
    private JTextField txt_ResFilePath;
    private JButton btn_BrowseRequestFiles;
    private JButton btn_BrowseResponseFiles;
    private JTextField txt_OutputPath;
    private JButton btn_OutputFilepath;
    private JButton btn_ConvertTestData;

    public FileInputView(){

        this.setSize(500, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        this.add(mainPanel);

        this.setVisible(true);
    }

    public void addBrowseButtonActionListener(ActionListener listener){
        btn_BrowseRequestFiles.addActionListener(listener);
        btn_BrowseResponseFiles.addActionListener(listener);
        btn_OutputFilepath.addActionListener(listener);
    }

    public void addConvertButtonActionListener(ActionListener listener){
        btn_ConvertTestData.addActionListener(listener);
    }

    public void setRequestFilePathTextField(String filePath){
        this.txt_ReqFilePath.setText(filePath);
    }

    public void setResponseFilePathTextField(String filePath){
        this.txt_ResFilePath.setText(filePath);
    }

    public void setOutputPathTextFields(String filePath){
        this.txt_OutputPath.setText(filePath);
    }

    public String getRequestFilePathText(){
        return this.txt_ReqFilePath.getText();
    }

    public String getResponseFilePathText(){
        return this.txt_ResFilePath.getText();
    }

    public String getOutputFilePathText(){
        return this.txt_OutputPath.getText();
    }
}
