package org.walker.tprDBHelper.models;


import org.json.JSONException;
import org.json.JSONObject;
import org.walker.tprDBHelper.workers.PrettyPrint;

import java.util.Base64;


public class TestCaseModel {

    private JSONObject testCaseObj;
    private JSONObject allTestCasesObj;
    private String testCaseKeyName;

    private String testCaseName, description, sourceAppCode;
    private String encodedRequest, encodedResponse, encodedChildRequest, encodedChildResponse;
    private String decodedRequest, decodedResponse, decodedChildRequest, decodedChildResponse;


    public TestCaseModel(JSONObject testCaseObj, JSONObject allTestCasesObj, String testCaseKeyName){
        this.testCaseObj = testCaseObj;
        this.allTestCasesObj = allTestCasesObj;
        this.testCaseKeyName = testCaseKeyName;
        setTestCaseValues();
    }

    private void setTestCaseValues(){
        if(testCaseObj == null){
            return;
        }

        testCaseName = getStringValue("testCaseName");
        description = getStringValue("description");
        sourceAppCode = getStringValue("sourceAppCode");
        encodedRequest = getStringValue("request");
        encodedResponse = getStringValue("response");
        encodedChildRequest = getStringValue("requestChild");
        encodedChildResponse = getStringValue("responseChild");

        decodedRequest = getDecodedRequestOrResponse("request");
        decodedResponse = getDecodedRequestOrResponse("response");
        decodedChildRequest = getDecodedRequestOrResponse("requestChild");
        decodedChildResponse = getDecodedRequestOrResponse("responsChild");
    }

    private String getDecodedRequestOrResponse(String path){
        String value = "";
        try {
            value = testCaseObj.getString(path);
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            return "";
        }
        try {
            byte[] valueDecoded = Base64.getDecoder().decode(value);
            value = new String(valueDecoded);
        }catch(Exception e) {

        }
        return PrettyPrint.format(value);
    }

    private String getStringValue(String path) {
        try {
            return testCaseObj.getString(path);
        } catch (JSONException e) {
        }
        return "";
    }

    public JSONObject getTestCaseObj(){
        return this.testCaseObj;
    }

    public JSONObject getAllTestCasesObj(){ return this.allTestCasesObj; }

    public String getTestCaseKeyName(){
        return this.testCaseKeyName;
    }

    public void setTestCaseObj(JSONObject testCaseObj) {
        this.testCaseObj = testCaseObj;
    }

    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceAppCode() {
        return sourceAppCode;
    }

    public void setSourceAppCode(String sourceAppCode) {
        this.sourceAppCode = sourceAppCode;
    }

    public String getEncodedRequest() {
        return encodedRequest;
    }

    public void setEncodedRequest(String encodedRequest) {
        this.encodedRequest = encodedRequest;
    }

    public String getEncodedResponse() {
        return encodedResponse;
    }

    public void setEncodedResponse(String encodedResponse) {
        this.encodedResponse = encodedResponse;
    }

    public String getEncodedChildRequest() {
        return encodedChildRequest;
    }

    public void setEncodedChildRequest(String encodedChildRequest) {
        this.encodedChildRequest = encodedChildRequest;
    }

    public String getEncodedChildResponse() {
        return encodedChildResponse;
    }

    public void setEncodedChildResponse(String encodedChildResponse) {
        this.encodedChildResponse = encodedChildResponse;
    }

    public String getDecodedRequest() {
        return decodedRequest;
    }

    public void setDecodedRequest(String decodedRequest) {
        this.decodedRequest = decodedRequest;
    }

    public String getDecodedResponse() {
        return decodedResponse;
    }

    public void setDecodedResponse(String decodedResponse) {
        this.decodedResponse = decodedResponse;
    }

    public String getDecodedChildRequest() {
        return decodedChildRequest;
    }

    public void setDecodedChildRequest(String decodedChildRequest) {
        this.decodedChildRequest = decodedChildRequest;
    }

    public String getDecodedChildResponse() {
        return decodedChildResponse;
    }

    public void setDecodedChildResponse(String decodedChildResponse) {
        this.decodedChildResponse = decodedChildResponse;
    }
}
