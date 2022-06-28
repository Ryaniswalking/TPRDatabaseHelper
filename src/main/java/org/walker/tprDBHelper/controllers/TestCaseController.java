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
import java.util.HashMap;

public class TestCaseController {

    private TestCaseView tcView;
    private TestCaseModel tcModel;
    private JSONObject couchDocumentObj;
    private int scrollPosition;
    private HashMap<String, Boolean> updatedTestCaseMap;

    public TestCaseController(TestCaseModel model, TestCaseView view){
        this.tcModel = model;
        this.tcView = view;
        setTestCaseFieldValues();

        this.tcView.setTitle(tcModel.getTestCaseKeyName());

        this.tcView.addSaveButtonListener(new SaveButtonListener());
        this.tcView.addBackButtonListener(new BackButtonListener());

        this.tcView.setVisible(true);

    }

    public TestCaseController(TestCaseModel model, TestCaseView view, JSONObject couchDocumentObj, int scrollPosition, HashMap<String, Boolean> updatedTestCaseMap){
        this(model, view);
        this.couchDocumentObj = couchDocumentObj;
        this.scrollPosition = scrollPosition;
        this.updatedTestCaseMap = updatedTestCaseMap;
    }

    private void setTestCaseFieldValues(){
        tcView.setTestCaseNameText(tcModel.getTestCaseName());
        tcView.setTestDescriptionText(tcModel.getDescription());
        tcView.setSourceAppCodeText(tcModel.getSourceAppCode());
        tcView.setDecodedRequestText(tcModel.getDecodedRequest());
        tcView.setDecodedResponseText(tcModel.getDecodedResponse());

        tcView.showChildDataPanel(false);

        //populate the child request and response section if applicable
        if(!tcModel.getDecodedChildRequest().isEmpty() && !tcModel.getDecodedChildResponse().isEmpty()){
            tcView.setDecodedRequestChildText(tcModel.getDecodedChildRequest());
            tcView.setDecodedResponseChildText(tcModel.getDecodedChildResponse());
            tcView.showChildDataPanel(true);
            tcView.setSize(1200, 900);
        }
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

                 if(tcView.isChildPanelVisible()){
                     testCaseObj.put("requestChild", tcView.getRequestChildTextEncoded());
                     testCaseObj.put("responseChild", tcView.getResponseChildTextEncoded());
                 }

                 allTestCasesObj.put(tcModel.getTestCaseKeyName(), testCaseObj);
             }catch(JSONException ex){}

                updatedTestCaseMap.put(tcModel.getTestCaseKeyName(), true);
                tcView.setVisible(false);

                try {
                    new ListTestCasesController(
                            new ListTestCasesModel(allTestCasesObj),
                            new ListTestCasesView(),
                            couchDocumentObj,
                            scrollPosition,
                            updatedTestCaseMap);
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
                    new ListTestCasesController(
                            new ListTestCasesModel(tcModel.getAllTestCasesObj()),
                            new ListTestCasesView(),
                            couchDocumentObj,
                            scrollPosition,
                            updatedTestCaseMap);

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                tcView.setVisible(false);
            }
        }
    }



}
