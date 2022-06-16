package org.walker.tprDBHelper.views;

import org.walker.tprDBHelper.controllers.DatabaseInfoController;
import org.walker.tprDBHelper.models.CouchDBModel;
import org.walker.tprDataConverter.controllers.FileInputController;
import org.walker.tprDataConverter.models.FileInputModel;
import org.walker.tprDataConverter.views.FileInputView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TPRDatabaseHelperView extends JFrame {

    public TPRDatabaseHelperView(){
        JPanel panel = new JPanel();

        JButton dbHelperButton = new JButton("Database Helper");
        JButton dataConverter = new JButton("Data Converter");

        dbHelperButton.addActionListener(this::buttonPressed);
        dataConverter.addActionListener(this::buttonPressed);

        panel.add(dbHelperButton);
        panel.add(dataConverter);

        this.add(panel);

        this.setSize(300, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        this.setVisible(true);
    }

    private void buttonPressed(ActionEvent event) {
        String actionEvent = event.getActionCommand();

        if(actionEvent.equalsIgnoreCase("Database Helper")){
            DatabaseInfoForm dbForm = new DatabaseInfoForm();
            CouchDBModel dbModel = new CouchDBModel();

            new DatabaseInfoController(dbForm, dbModel);

            this.setVisible(false);
        }

        if(actionEvent.equalsIgnoreCase("Data Converter")){
            new FileInputController(new FileInputModel(), new FileInputView());
        }
    }
}
