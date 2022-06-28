package org.walker.tprDataConverter.controllers;

//import org.json.JSONException;
import org.json.JSONException;
import org.walker.tprDataConverter.models.FileInputModel;
import org.walker.tprDataConverter.views.FileInputView;
import org.walker.tprDataConverter.workers.ConvertTestData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FileInputController {

    private FileInputView inputView;
    private FileInputModel inputModel;
    private JFileChooser fileChooser;

    public FileInputController(FileInputModel inputModel, FileInputView inputView){
        this.inputModel = inputModel;
        this.inputView = inputView;

        this.inputView.setVisible(true);

        inputView.addBrowseButtonActionListener(new BrowseButtonListener());
        inputView.addConvertButtonActionListener(new ConvertButtonListener());

    }

    class BrowseButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            String actionEvent = e.getActionCommand();

           fileChooser = new JFileChooser();
           fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

           int result = fileChooser.showOpenDialog(inputView);

           String filePath = "";

           if(result == JFileChooser.APPROVE_OPTION) {
                filePath = fileChooser.getSelectedFile().getPath().replaceAll("OneDrive - Mercury Insurance/", "");
           }

           if(actionEvent.equalsIgnoreCase("Requests")){
               inputView.setRequestFilePathTextField(filePath);
           }else if(actionEvent.equalsIgnoreCase("Output")){
               inputView.setOutputPathTextFields(filePath);
           }else{
               inputView.setResponseFilePathTextField(filePath);
           }
        }
    }

    class ConvertButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String requestPath = inputView.getRequestFilePathText();
            String responsePath = inputView.getResponseFilePathText();
            String outputPath = inputView.getOutputFilePathText();

            String reqTrace = JOptionPane.showInputDialog(inputView, "Enter Requirement Traceability");

            boolean correctInput = false;
            String delta = "";
            while(!correctInput){
               String input = JOptionPane.showInputDialog(inputView, "Delta? (y/n)");
               if(input.length()==1 && (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n"))){
                   delta = (input.equalsIgnoreCase("y")) ? "DELTA" : "NON_DELTA";
                   correctInput = true;
               }else{
                   JOptionPane.showMessageDialog(inputView, "Incorrect Input. Try Again.");
               }
            }

            try {
                new ConvertTestData(requestPath, responsePath, outputPath, delta, reqTrace);
                JOptionPane.showMessageDialog(null, "Conversion Complete");
                inputView.setVisible(false);
            } catch (IOException | JSONException ex) {
                JOptionPane.showMessageDialog(null, "Conversion Failed");
                ex.printStackTrace();
            }
        }
    }
}
