package org.walker.tprDataConverter.workers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.LinkedHashMap;

import io.restassured.path.xml.XmlPath;

public class ConvertTestData {

    private static File requestDir;
    private static File responseDir;
    private static String outputDirPath;

    private static String sourceAppCode, encodedRequestBody, encodedResponseBody;

    private static JSONObject allTestCases = setToLinkedHashMapJson();

    private static final String[] appCodes = new String[] {"GW","BB","QB","M1","AS","MR","NG"};

    public ConvertTestData(String requestFilePath, String responseFilePath, String outputFilePath) throws IOException, JSONException {
        requestDir = new File(requestFilePath);
        responseDir = new File(responseFilePath);
        outputDirPath = outputFilePath;

        runConverter();
    }

    private void runConverter() throws IOException, JSONException {
        File[] listOfRequestFiles = requestDir.listFiles();

        for(String appCode: appCodes) {
            allTestCases.put(appCode, new JSONArray());
        }

        if (listOfRequestFiles != null) {
            for (File child : listOfRequestFiles) {
                BufferedReader br = new BufferedReader(new FileReader(child));

                String line, xmlString = "";

                //Store contents of file into String
                while ((line = br.readLine()) != null) {
                    xmlString += line.trim().replaceAll("\r", "");
                }

                //Grab the name of the file
                String fileName = child.getName().replace("_req.xml", "");

                //set the request as a xmlPath
                XmlPath requestBody = new XmlPath(xmlString);

                //grab the sourceAppCode from the XML
                sourceAppCode = requestBody.getString("CreditRequest.requestHeader.sourceAppCode");

                //Place the test case name under the appropriate
                ((JSONArray) allTestCases.get(sourceAppCode)).put(fileName);

                //encoded the request
                encodedRequestBody =  Base64.getEncoder().encodeToString(requestBody.prettify().getBytes());

                //encode the response
                encodedResponseBody = Base64.getEncoder().encodeToString(new XmlPath(getResponse(fileName)).prettify().getBytes()); //create as xml to ensure proper format

                setTestCaseInfo(fileName);

                FileWriter file = new FileWriter(outputDirPath+"/TestCases.txt");
                file.write(prettifyJSON(allTestCases.toString()));
                file.close();
            }
        }

    }

    private static void setTestCaseInfo(String testCaseName) throws JSONException {
        JSONObject tm4jData = setToLinkedHashMapJson();
        JSONArray tm4jArraySteps = new JSONArray();
        JSONObject tm4jSteps1 = setToLinkedHashMapJson();
        JSONObject tm4jSteps2 = setToLinkedHashMapJson();
        JSONObject testDataInfo = setToLinkedHashMapJson();

        //TM4J Information
        tm4jData.put("testCaseKey", "");
        tm4jData.put("requirementTraceability", "FR 2.3");
        tm4jData.put("delta", "DELTA");
        tm4jData.put("precondition", "");

        tm4jSteps1.put("step", "");
        tm4jSteps1.put("testData", "");
        tm4jSteps1.put("expectedResult", "System processes the request successfully. Status Code = 200.");
        //Step 2 for TM4J
        tm4jSteps2.put("step", "");
        tm4jSteps2.put("testData", "");
        tm4jSteps2.put("expectedResult", "");

        tm4jArraySteps.put(tm4jSteps1);
        tm4jArraySteps.put(tm4jSteps2);
        tm4jData.put("steps",tm4jArraySteps);

        testDataInfo.put("testCaseName", testCaseName);
        testDataInfo.put("description", "");
        testDataInfo.put("sourceAppCode", sourceAppCode);
        testDataInfo.put("request", encodedRequestBody);
        testDataInfo.put("response", encodedResponseBody);
        testDataInfo.put("testCaseSteps", tm4jData);


        allTestCases.put(testCaseName, testDataInfo);
    }

    private static JSONObject setToLinkedHashMapJson() {
        JSONObject object = new JSONObject();
        try {
            Field changeMap = object.getClass().getDeclaredField("nameValuePairs");
            changeMap.setAccessible(true);
            changeMap.set(object, new LinkedHashMap<>());
            changeMap.setAccessible(false);
        }catch(IllegalAccessException | NoSuchFieldException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return object;
    }

    private static String prettifyJSON(String uglyJsonString) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(uglyJsonString);
        return gson.toJson(je);
    }

    private static String getResponse(String fileName) throws IOException {

        //Pass the file name as the filter
        Filter filter = new Filter(fileName);

        File[] responseDirectory = responseDir.listFiles(filter);
        if(responseDirectory != null && responseDirectory.length >0 && !(responseDirectory.length > 1)) {
            BufferedReader br = new BufferedReader(new FileReader(responseDirectory[0]));
            String line, xmlString = "";

            //Store contents of file into String
            while((line = br.readLine()) != null) {
                xmlString += line.trim().replaceAll("\r","");
            }

            System.out.println(xmlString);

            return xmlString;
        }

        return "";
    }
}
