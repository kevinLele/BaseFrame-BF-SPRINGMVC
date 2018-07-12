package com.hq.cloudplatform.baseframe.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.StringWriter;

/**
 * Administrator
 * @author Administrator
 */
public class XmlUtil {

    private static XmlMapper xmlMapper = new XmlMapper();

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * xml字符串转成JSON格式字符串
     *
     * @param xml
     * @return
     */
    public static String convertXmlToJson(String xml) {
        StringWriter w = new StringWriter();

        try {
            JsonParser jp = xmlMapper.getFactory().createParser(xml);
            JsonGenerator jg = objectMapper.getFactory().createGenerator(w);

            while (jp.nextToken() != null) {
                jg.copyCurrentEvent(jp);
            }

            jp.close();
            jg.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return w.toString();
    }

    public static void main(String[] args) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><!DOCTYPE WMT_MS_Capabilities SYSTEM \"http://192.168.1.142:8080/globalserver/schemas/wms/1.1.1/WMS_MS_Capabilities.dtd\">"
                + "<user test=\"tset1\"  xmlns:pre=\"http://www.opengis.net/ows/1.1\">"
                + "<pre:address name1=\"name2\">jianXi</pre:address><age>2</age><name>xiaoMing</name><sex>yy</sex><userId>22</userId>"
                + "</user>";

        String json = convertXmlToJson(xml);
        System.out.println(json);
    }
}
