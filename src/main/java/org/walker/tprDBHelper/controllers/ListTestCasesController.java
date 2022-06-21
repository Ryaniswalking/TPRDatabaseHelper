package org.walker.tprDBHelper.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.walker.tprDBHelper.models.CouchDBModel;
import org.walker.tprDBHelper.models.CouchDocumentsModel;
import org.walker.tprDBHelper.models.ListTestCasesModel;
import org.walker.tprDBHelper.models.TestCaseModel;
import org.walker.tprDBHelper.views.CouchDocumentsView;
import org.walker.tprDBHelper.views.ListTestCasesView;
import org.walker.tprDBHelper.views.TestCaseView;
import org.walker.tprDBHelper.workers.Prettify;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;


public class ListTestCasesController {

    private ListTestCasesModel ltcModel;
    private ListTestCasesView ltcView;
    private JSONObject couchDocumentsObj;
    private HashMap<String, Boolean> updateTestCaseMap;


    public ListTestCasesController(ListTestCasesModel ltcModel, ListTestCasesView ltcView){
        this.ltcModel = ltcModel;
        this.ltcView = ltcView;

        if(updateTestCaseMap == null || updateTestCaseMap.isEmpty()){
            updateTestCaseMap = ltcModel.getNonUpdateTestCaseMap();
        }

        ltcView.displayAllTestCases(ltcModel.getTestCaseNamesList(), updateTestCaseMap);


        this.ltcView.addSelectedKeyNameListener(new SelectTestCaseListener());
        this.ltcView.addSaveToCouchButtonListener(new SaveToCouchButtonLister());
        this.ltcView.addBackButtonListener(new BackButtonListener());

        this.ltcView.setVisible(true);
    }

    //Construct coming forward from couch document list contain couch document obj
    public ListTestCasesController(ListTestCasesModel ltcModel, ListTestCasesView ltcView, JSONObject couchDocumentsObj){
        this(ltcModel,ltcView);
        this.couchDocumentsObj = couchDocumentsObj;

    }

    //constructor when going back from TestCaseView, which holds the scroll bar location and the updated test case map
    public ListTestCasesController(ListTestCasesModel ltcModel, ListTestCasesView ltcView, JSONObject couchDocumentsObj, int scrollBarLocation, HashMap<String, Boolean> updateTestCaseMap){
        this.ltcModel = ltcModel;
        this.ltcView = ltcView;
        this.updateTestCaseMap = updateTestCaseMap;
        this.couchDocumentsObj = couchDocumentsObj;


        ltcView.displayAllTestCases(ltcModel.getTestCaseNamesList(), updateTestCaseMap);


        this.ltcView.addSelectedKeyNameListener(new SelectTestCaseListener());
        this.ltcView.addSaveToCouchButtonListener(new SaveToCouchButtonLister());
        this.ltcView.addBackButtonListener(new BackButtonListener());

        this.ltcView.setVisible(true);

        ltcView.setScrollBarLocation(scrollBarLocation);
    }

    class SelectTestCaseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String keyName = e.getActionCommand();

            int scrollBarLocation = ltcView.getScrollLocation();

            try {
                new TestCaseController(
                        new TestCaseModel(ltcModel.getTestCaseObj(keyName), ltcModel.getAllTestCasesObj(), keyName),
                        new TestCaseView(),
                        couchDocumentsObj,
                        scrollBarLocation,
                        updateTestCaseMap);

            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            ltcView.setVisible(false);
        }
    }

    class SaveToCouchButtonLister implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog(ltcView, "Are you sure you want to save to Couch?", "Save Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.NO_OPTION) {
                return;
            }

            //Get Couch Key from model
            String couchKey = null;
            try {
                couchKey = ltcModel.getCouchDocumentId();
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            JSONObject allTestCases = ltcModel.getAllTestCasesObj();

            CouchDBModel couchdb = new CouchDBModel();

            try {
                //prettify all the test cases and save to couch using key
                JSONObject prettyCouchDoc = Prettify.prettifyForCouch(allTestCases);
                int response = couchdb.saveUpdatedTestCasesToCouch(couchKey, prettyCouchDoc);

                if (response == 201) {
                    JOptionPane.showMessageDialog(ltcView, "Couch Document Successfully Save");
                    new CouchDocumentsController(new CouchDocumentsModel(couchDocumentsObj), new CouchDocumentsView());
                    ltcView.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(ltcView, "Failed to Save Document to Couch");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }

    }

    class BackButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new CouchDocumentsController(new CouchDocumentsModel(couchDocumentsObj), new CouchDocumentsView());
                ltcView.setVisible(false);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
}
