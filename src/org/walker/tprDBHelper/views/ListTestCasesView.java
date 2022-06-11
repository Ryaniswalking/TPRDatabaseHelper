package org.walker.tprDBHelper.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ListTestCasesView extends JFrame {

    private JPanel panel;
    private ArrayList<JButton> testCaseButtonList = new ArrayList<>();
    private JButton btn_SaveToCouch;
    private JPanel btnPanel;

    public ListTestCasesView(){

        panel = new JPanel(new GridBagLayout());

        this.setSize(600, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

    }

    public void displayAllTestCases(ArrayList<String> keyNameArray){
        if(keyNameArray == null){
            return;
        }
        GridBagConstraints c = new GridBagConstraints();

        btnPanel = new JPanel();
        btn_SaveToCouch = new JButton("Save to Couch");
        btnPanel.add(btn_SaveToCouch);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.LAST_LINE_END; //bottom of space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 2;   //2 columns wide
        c.gridy = 2;       //third row

        panel.add(btnPanel, c);

        JPanel testCasePanel = new JPanel();
        testCasePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 5));
        testCasePanel.setLayout(new GridLayout(0, 1));

        JScrollPane scrollPane = new JScrollPane(testCasePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        GridBagConstraints c2 = new GridBagConstraints();

        c2.anchor = GridBagConstraints.FIRST_LINE_START;
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.ipady = 475;//make this component tall
        c2.ipadx = 550;
        c2.weightx = 0.0;
        c2.gridwidth = 50;
        c2.gridx = 0;
        c2.gridy = 0;


        for(int i=0;i<keyNameArray.size();i++){
            JButton testCaseBTN = new JButton(keyNameArray.get(i));
            testCaseButtonList.add(testCaseBTN);
            testCasePanel.add(testCaseBTN);
        }

        panel.add(scrollPane, c2);

        this.add(panel);
    }

    public void addSelectedKeyNameListener(ActionListener listenForBeingSelected){
        for(JButton button : testCaseButtonList){
            button.addActionListener(listenForBeingSelected);
        }
    }

    public void addSaveToCouchButtonListener(ActionListener listenForSaveButtonClick){
        btn_SaveToCouch.addActionListener((listenForSaveButtonClick));
    }
}
