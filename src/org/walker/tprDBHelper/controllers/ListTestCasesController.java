package org.walker.tprDBHelper.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.walker.tprDBHelper.models.CouchDBModel;
import org.walker.tprDBHelper.models.ListTestCasesModel;
import org.walker.tprDBHelper.models.TestCaseModel;
import org.walker.tprDBHelper.views.ListTestCasesView;
import org.walker.tprDBHelper.views.TestCaseView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class ListTestCasesController {

    private ListTestCasesModel ltcModel;
    private ListTestCasesView ltcView;

    public ListTestCasesController(ListTestCasesModel ltcModel, ListTestCasesView ltcView){
        this.ltcModel = ltcModel;
        this.ltcView = ltcView;

        ltcView.displayAllTestCases(ltcModel.getTestCaseNamesList());

        this.ltcView.addSelectedKeyNameListener(new SelectViewNameListener());
        this.ltcView.addSaveToCouchButtonListener(new SaveToCouchButtonLister());

        this.ltcView.setVisible(true);
    }

    class SelectViewNameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String keyName = e.getActionCommand();
            try {
                new TestCaseController(new TestCaseModel(ltcModel.getTestCaseObj(keyName), ltcModel.getAllTestCasesObj(), keyName), new TestCaseView());
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            ltcView.setVisible(false);
        }
    }

    class SaveToCouchButtonLister implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog(ltcView,"Are you sure you want to save to Couch?", "Save Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if(result == JOptionPane.YES_OPTION){
                String couchKey = null;
                try {
                    couchKey = ltcModel.getCouchDocumentId();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                JSONObject allTestCases = ltcModel.getAllTestCasesObj();

                CouchDBModel couchdb = new CouchDBModel();

                try {
                    int response = couchdb.saveUpdatedTestCasesToCouch(couchKey, allTestCases);

                    if(response == 201){
                        JOptionPane.showMessageDialog(ltcView, "Couch Document Successfully Save");
                    }else{
                        JOptionPane.showMessageDialog(ltcView,"Failed to Save Document to Couch");
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                ltcView.setVisible(false);
            }


        }
    }


}
