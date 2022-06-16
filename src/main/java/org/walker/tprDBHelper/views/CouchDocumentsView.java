package org.walker.tprDBHelper.views;

import org.json.JSONArray;
import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CouchDocumentsView extends JFrame {

    private JPanel mainPanel;
    private JPanel keysPanel;
    private JPanel backButtonPanel;
    private JButton backButton;
    private ArrayList<JButton> viewNameButtonList = new ArrayList<>();

    public CouchDocumentsView(){

        mainPanel = new JPanel(new GridLayout(0, 1));
        keysPanel = new JPanel();

        keysPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        keysPanel.setLayout(new GridLayout(0, 1));

        this.setSize(300, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        mainPanel.add(keysPanel);

        //Also Display Back Button
        backButtonPanel = new JPanel(new BorderLayout());
        backButton = new JButton("Back");
        backButtonPanel.add(backButton, BorderLayout.PAGE_END);

        mainPanel.add(backButtonPanel);

        this.add(mainPanel);

    }

    public void displayCouchKeyNames(JSONArray keyNameArray) throws JSONException {
        if(keyNameArray == null){
            return;
        }

        for(int i=0;i<keyNameArray.length();i++){
            JSONObject a = keyNameArray.getJSONObject(i);
            String keyName = a.getString("key");
            JButton keyNameBTN = new JButton(keyName);
            viewNameButtonList.add(keyNameBTN);
            panel.add(keyNameBTN);
        }
    public void addBackButtonListener(ActionListener listener){
        backButton.addActionListener(listener);
    }

    public void addSelectedKeyNameListener(ActionListener listenForBeingSelected){
        for(JButton button : viewNameButtonList){
             button.addActionListener(listenForBeingSelected);
        }
    }

    public void displayErrorMessage(String errorMessage){
        JOptionPane.showMessageDialog(this, errorMessage);
    }

    public void setKeysPanel(JPanel keysPanel){
        this.keysPanel = keysPanel;
    }
}
