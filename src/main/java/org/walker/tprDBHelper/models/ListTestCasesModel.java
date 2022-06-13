package org.walker.tprDBHelper.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListTestCasesModel {

    private static final String appCodes = "GW/M1/QB/BB/AS/MR/NG";

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
        String[] appCodeArr = appCodes.split("/");

        for(int i=0;i<appCodeArr.length;i++) {
            JSONArray testCaseArray = testCases.getJSONArray(appCodeArr[i]);
            for(int j=0;j<testCaseArray.length();j++){
                testCaseNames.add(testCaseArray.getString(j));
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
