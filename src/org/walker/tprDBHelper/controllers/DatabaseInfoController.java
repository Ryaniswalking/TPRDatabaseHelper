package org.walker.tprDBHelper.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.walker.tprDBHelper.models.CouchDBModel;
import org.walker.tprDBHelper.models.CouchDocumentsModel;
import org.walker.tprDBHelper.views.CouchDocumentsView;
import org.walker.tprDBHelper.views.DatabaseInfoForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DatabaseInfoController {

    private DatabaseInfoForm dbForm;
    private CouchDBModel dbModel;

    public DatabaseInfoController(DatabaseInfoForm dbForm, CouchDBModel dbModel){
        this.dbForm = dbForm;
        this.dbModel = dbModel;

        this.dbForm.addEnterButtonListener(new EnterButtonlistener());
    }

    class EnterButtonlistener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JSONObject listOfTestCasesJson = new JSONObject();
            String designDoc, viewName;

            designDoc = dbForm.getDesignDocText();
            viewName = dbForm.getViewNameText();

            try {
                dbModel.setListOfCouchKeys(designDoc, viewName);
            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
            }

            try {
                new CouchDocumentsController(new CouchDocumentsModel(dbModel.getCouchKeysJson()), new CouchDocumentsView());
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

        }
    }
}
