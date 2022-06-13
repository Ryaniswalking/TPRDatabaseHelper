package org.walker.tprDBHelper.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CouchDocumentsModel {

    //all couch documents from specified view name and design doc
    private JSONObject couchDocuments;
    private JSONArray couchKeyNamesArray;

    public CouchDocumentsModel(JSONObject couchDocuments) throws JSONException {
        this.couchDocuments = couchDocuments;
        setKeyNameJsonArray();
    }

    public JSONObject getTestCasesJson(){
        return this.couchDocuments;
    }

    public JSONArray getCouchKeyNamesArray() { return this.couchKeyNamesArray;}

    public void setKeyNameJsonArray() throws JSONException{

        //Json array of all key names in specified view
        JSONArray couchDocKeyArray = new JSONArray();

        if(couchDocuments != null) {
            try{
                couchDocKeyArray   = couchDocuments.getJSONArray("rows");
            }catch(JSONException e){
                throw new JSONException("Error Finding Couch Document Key Names");
            }
        }

        this.couchKeyNamesArray = couchDocKeyArray;
    }

    public String getCouchIDOfKeyName(String keyName) throws JSONException {
        if(couchKeyNamesArray == null) {
            return "";
        }
        for(int i=0; i<couchKeyNamesArray.length();i++){
            JSONObject obj = couchKeyNamesArray.getJSONObject(i);
            if(obj.getString("key").equalsIgnoreCase(keyName)){
                return obj.getString("id");
            }
        }

        return "";
    }
}
