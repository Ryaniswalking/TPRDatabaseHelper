package org.walker.tprDBHelper.controllers;

import org.json.JSONException;
import org.walker.tprDBHelper.models.CouchDBModel;
import org.walker.tprDBHelper.models.CouchDocumentsModel;
import org.walker.tprDBHelper.views.CouchDocumentsView;
import org.walker.tprDBHelper.views.DatabaseInfoForm;
import org.walker.tprDBHelper.views.TPRDatabaseHelperView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DatabaseInfoController {

    private DatabaseInfoForm dbForm;
    private CouchDBModel dbModel;

    public DatabaseInfoController(DatabaseInfoForm dbForm, CouchDBModel dbModel){
        this.dbForm = dbForm;
        this.dbModel = dbModel;

        this.dbForm.addEnterButtonListener(new EnterButtonListener());
        this.dbForm.addBackButtonListener(new BackButtonLister());

        this.dbForm.setVisible(true);
    }

    class EnterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String designDoc, viewName;

            designDoc = dbForm.getDesignDocText();
            viewName = dbForm.getViewNameText();

            if(designDoc.isEmpty() || viewName.isEmpty()){
                JOptionPane.showMessageDialog(null, "Design Doc and View Name required.");
                return;
            }

            try {
                dbModel.setListOfCouchKeys(designDoc, viewName);
            } catch (IOException | JSONException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Design Doc and View Name");
                return;
            }

            try {
                new CouchDocumentsController(new CouchDocumentsModel(dbModel.getCouchKeysJson()), new CouchDocumentsView());
                dbForm.setVisible(false);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    class BackButtonLister implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new TPRDatabaseHelperView();
            dbForm.setVisible(false);
        }
    }
}
