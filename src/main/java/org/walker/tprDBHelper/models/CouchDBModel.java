package org.walker.tprDBHelper.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CouchDBModel {

    private static final String baseUrl = "http://brdvwapp001:5984";


    private static String dbName;
    private String designDoc;
    private String viewName;
    private String keyName;
    private String url;

    //Test Case Data
    private JSONObject couchKeysJson;
    private JSONObject testCasesObject;

    //View Componets


    public CouchDBModel(){
        dbName = "tpr";
    }

    public CouchDBModel(String dbName){
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }
    public void setDbName(String db) {
        dbName = db;
    }
    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
    public void setDesignDoc(String designDoc) {
        this.designDoc = designDoc;
    }
    public void setKeyName(String keyName) {this.keyName = keyName;}

    public JSONObject getCouchKeysJson(){
        return this.couchKeysJson;
    }

    public JSONObject getTestCaseObject(){
        return this.testCasesObject;
    }

    public void setListOfCouchKeys(String designDoc, String viewName) throws IOException, JSONException{
        String urlStr = baseUrl + "/" + dbName + "/_design/" + designDoc + "/_view/" + viewName;
        couchKeysJson = getDBRequest(urlStr);
    }

    public JSONObject getTestCaseData(String id) throws IOException, JSONException {
        String urlStr = baseUrl +"/"+dbName+"/"+id;
        return getDBRequest(urlStr);
    }

    public int saveUpdatedTestCasesToCouch(String documentID, JSONObject updateTestCases) throws IOException {
        String urlStr = baseUrl + "/" + dbName + "/" +documentID;

        URL url = new URL(urlStr);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("PUT");
        OutputStreamWriter out = new OutputStreamWriter(
                httpCon.getOutputStream());
        out.write(updateTestCases.toString());
        out.close();
        httpCon.getInputStream();

        int statusCode  = httpCon.getResponseCode();
        return statusCode;
    }

    private JSONObject getDBRequest(String urlStr) throws IOException, JSONException {

        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();


        con.setRequestProperty("Content-Type", "application/json");

        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        return new JSONObject(content.toString());
    }
}
