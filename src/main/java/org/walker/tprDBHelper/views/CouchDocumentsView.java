package org.walker.tprDBHelper.views;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CouchDocumentsView extends JFrame {

    private JPanel panel;
    private ArrayList<JButton> viewNameButtonList = new ArrayList<>();

    public CouchDocumentsView(){

        panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        panel.setLayout(new GridLayout(0, 1));

        this.setSize(300, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        this.add(panel);
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
    }

    public void addSelectedKeyNameListener(ActionListener listenForBeingSelected){
        for(JButton button : viewNameButtonList){
             button.addActionListener(listenForBeingSelected);
        }
    }

    public void displayErrorMessage(String errorMessage){
        JOptionPane.showMessageDialog(this, errorMessage);
    }

    public void setPanel(JPanel panel){
        this.panel = panel;
    }
}
