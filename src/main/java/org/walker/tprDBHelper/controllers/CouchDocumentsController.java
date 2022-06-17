package org.walker.tprDBHelper.controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.walker.tprDBHelper.models.CouchDBModel;
import org.walker.tprDBHelper.models.CouchDocumentsModel;
import org.walker.tprDBHelper.models.ListTestCasesModel;
import org.walker.tprDBHelper.views.CouchDocumentsView;
import org.walker.tprDBHelper.views.DatabaseInfoForm;
import org.walker.tprDBHelper.views.ListTestCasesView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CouchDocumentsController {
    private CouchDocumentsView cdView;
    private CouchDocumentsModel cdModel;

    public CouchDocumentsController(CouchDocumentsModel cdModel, CouchDocumentsView cdView) throws JSONException {
        this.cdView = cdView;
        this.cdModel = cdModel;

        cdView.displayCouchKeyNames(cdModel.getCouchKeyNamesArray());

        this.cdView.addSelectedKeyNameListener(new SelectViewNameListener());
        this.cdView.addBackButtonListener(new BackButtonLister());

        cdView.setVisible(true);
    }

    class SelectViewNameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String keyName = e.getActionCommand();
            String keyNameID = null;
            try {
                keyNameID = cdModel.getCouchIDOfKeyName(keyName);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            CouchDBModel couchDB = new CouchDBModel();
            try {
                JSONObject testCases = couchDB.getTestCaseData(keyNameID);

                new ListTestCasesController(new ListTestCasesModel(testCases), new ListTestCasesView(), cdModel.getCouchDocumentsObj());

                cdView.setVisible(false);

            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
    class BackButtonLister implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new DatabaseInfoController(new DatabaseInfoForm(), new CouchDBModel());
            cdView.setVisible(false);
        }
    }
}
