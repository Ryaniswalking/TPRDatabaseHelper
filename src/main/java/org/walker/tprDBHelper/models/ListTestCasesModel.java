package org.walker.tprDBHelper.models;

import enums.AppCodes;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListTestCasesModel {
    private JSONObject testCases;
    private ArrayList<String> testCaseNames = new ArrayList<>();

    public ListTestCasesModel(JSONObject testCases) throws JSONException {
        this.testCases = testCases;
        setTestCaseNamesList();
    }

    private void setTestCaseNamesList() throws JSONException {
        if(testCases == null){
            return;
        }

        for(AppCodes appCode: AppCodes.values()){
            try {
                 JSONArray testCaseArray = testCases.getJSONArray(appCode.toString());
                    for (int j = 0; j < testCaseArray.length(); j++) {
                        testCaseNames.add(testCaseArray.getString(j));
                    }
            }catch(JSONException e){
                System.out.println("No Info for: " + appCode);
            }
        }
    }

    public ArrayList<String> getTestCaseNamesList(){
        return this.testCaseNames;
    }

    public JSONObject getTestCaseObj(String keyName) throws JSONException {
        return testCases.getJSONObject(keyName);
    }

    public JSONObject getAllTestCasesObj(){
        return this.testCases;
    }

    public String getCouchDocumentId() throws JSONException {
        return this.getAllTestCasesObj().getString("_id");
    }

    public String getTestCaseString(String keyName) throws JSONException {
        return testCases.getJSONObject(keyName).toString();
    }
}
