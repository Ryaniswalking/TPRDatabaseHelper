package org.walker.tprDBHelper.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.walker.tprDBHelper.models.ListTestCasesModel;
import org.walker.tprDBHelper.models.TestCaseModel;
import org.walker.tprDBHelper.views.ListTestCasesView;
import org.walker.tprDBHelper.views.TestCaseView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestCaseController {

    private TestCaseView tcView;
    private TestCaseModel tcModel;

    public TestCaseController(TestCaseModel model, TestCaseView view){
        this.tcModel = model;
        this.tcView = view;
        setTestCaseFieldValues();

        this.tcView.setTitle(tcModel.getTestCaseKeyName());

        this.tcView.addSaveButtonListener(new SaveButtonListener());
        this.tcView.addBackButtonListener(new BackButtonListener());

        this.tcView.setVisible(true);

    }

    private void setTestCaseFieldValues(){
        tcView.setTestCaseNameText(tcModel.getTestCaseName());
        tcView.setTestDescriptionText(tcModel.getDescription());
        tcView.setSourceAppCodeText(tcModel.getSourceAppCode());
        tcView.setDecodedRequestText(tcModel.getDecodedRequest());
        tcView.setDecodedResponseText(tcModel.getDecodedResponse());
    }

    class SaveButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog(tcView,"Are you want to save?", "Save Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if(result == JOptionPane.YES_OPTION) {
                JSONObject allTestCasesObj = tcModel.getAllTestCasesObj();
                JSONObject testCaseObj = tcModel.getTestCaseObj();

             try{
                 testCaseObj.put("testCaseName", tcView.getTestCaseNameText());
                 testCaseObj.put("description", tcView.getTestDescriptionText());
                 testCaseObj.put("sourceAppCode", tcView.getSourceAppCodeText());
                 testCaseObj.put("request", tcView.getRequestTextEncoded());
                 testCaseObj.put("response", tcView.getResponseTextEncoded());

                 allTestCasesObj.put(tcModel.getTestCaseKeyName(), testCaseObj);
             }catch(JSONException ex){}


                tcView.setVisible(false);

                try {
                    new ListTestCasesController(new ListTestCasesModel(allTestCasesObj), new ListTestCasesView());
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class BackButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            int result = JOptionPane.showConfirmDialog(tcView,"Sure? You want to exit?", "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if(result == JOptionPane.YES_OPTION){
                try {
                    new ListTestCasesController(new ListTestCasesModel(tcModel.getAllTestCasesObj()), new ListTestCasesView());
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                tcView.setVisible(false);
            }
        }
    }



}