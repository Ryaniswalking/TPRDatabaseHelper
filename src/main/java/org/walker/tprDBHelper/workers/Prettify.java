package org.walker.tprDBHelper.workers;

import enums.AppCodes;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Prettify {

    /**
     *
     * @param unformattedXml - XML String
     * @return Properly formatted XML String
     */
    public static String format(String unformattedXml) {
        try {
            Document document = parseXmlFile(unformattedXml);

            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);

            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * This function converts String XML to Document object
     * @param in - XML String
     * @return Document object
     */
    private static Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Takes an XML Document object and makes an XML String. Just a utility
     * function.
     *
     * @param doc - The DOM document
     * @return the XML String
     */
    public String makeXMLString(Document doc) {
        String xmlString = "";
        if (doc != null) {
            try {
                TransformerFactory transfac = TransformerFactory.newInstance();
                Transformer trans = transfac.newTransformer();
                trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                trans.setOutputProperty(OutputKeys.INDENT, "yes");
                StringWriter sw = new StringWriter();
                StreamResult result = new StreamResult(sw);
                DOMSource source = new DOMSource(doc);
                trans.transform(source, result);
                xmlString = sw.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return xmlString;
    }

    public static JSONObject prettifyForCouch(JSONObject uglyCouchObject) throws JSONException {

        JSONObject prettyObj = setObjectAsLinkedHashMap();
        ArrayList<String> allTestCaseNames = new ArrayList<>();

        prettyObj.put("_id", uglyCouchObject.getString("_id"));
        prettyObj.put("_rev",uglyCouchObject.getString("_rev"));
        prettyObj.put("test_service", uglyCouchObject.getString("test_service"));
        prettyObj.put("testCaseName", uglyCouchObject.getString("testCaseName"));

        for(AppCodes appCode : AppCodes.values()){
            prettyObj.put(appCode.toString(), uglyCouchObject.getJSONArray(appCode.toString()));
        }


        for(AppCodes appCode : AppCodes.values()){
            JSONArray testNames = uglyCouchObject.getJSONArray(appCode.toString());
            for(int i=0;i< testNames.length();i++){
                allTestCaseNames.add(testNames.getString(i));
            }
        }

        for(String testCaseName : allTestCaseNames){
            JSONObject prettyTestCase = setObjectAsLinkedHashMap();
            JSONObject uglyTestCase = uglyCouchObject.getJSONObject(testCaseName);

            prettyTestCase.put("testCaseName",uglyTestCase.getString("testCaseName"));
            prettyTestCase.put("description", uglyTestCase.getString("description"));
            prettyTestCase.put("sourceAppCode", uglyTestCase.getString("sourceAppCode"));
            prettyTestCase.put("request", uglyTestCase.getString("request"));
            prettyTestCase.put("response", uglyTestCase.getString("response"));
            prettyTestCase.put("testCaseSteps", uglyTestCase.getJSONObject("testCaseSteps"));

            prettyObj.put(testCaseName, prettyTestCase);

//            JSONObject prettyTm4jData = setObjectAsLinkedHashMap();
//            JSONObject uglyTm4jData = uglyTestCase.getJSONObject("testCaseSteps");

//            prettyTm4jData.put("testCaseKey", uglyTm4jData.getString("testCaseKey"));
//            prettyTm4jData.put("requirementTraceability", uglyTm4jData.getString("requirementTraceability"));
//            prettyTm4jData.put("delta", uglyTm4jData.getString("delta"));
//            prettyTm4jData.put("precondition", uglyTm4jData.getString("precondition"));
//
//            for()

        }
        return prettyObj;
    }

    private static JSONObject setObjectAsLinkedHashMap(){
        JSONObject object = new JSONObject();
        try {
            Field changeMap = object.getClass().getDeclaredField("nameValuePairs");
            changeMap.setAccessible(true);
            changeMap.set(object, new LinkedHashMap<>());
            changeMap.setAccessible(false);
        }catch(IllegalAccessException | NoSuchFieldException e){
            System.out.println(e.getMessage());
        }
        return object;
    }
}