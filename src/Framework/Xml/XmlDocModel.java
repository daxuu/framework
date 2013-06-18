/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Framework.Xml;

import Framework.logger.MyLogger;
import java.sql.*;
//文件包
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
//工具包
import java.util.Iterator;
import java.util.List;
//dom4j包
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author 
 * @date 2010/3/29, 下午 02:41:58
 */
public class XmlDocModel {

    private final String ROOT_NAME = "xconfig";
    boolean _empty = true;
    boolean _changed = false;
    Document _document = null;
    //Constructor

    public XmlDocModel() {
        Document doc = null;
        String root = "<?xml version=\"1.0\" encoding=\"utf-8\"?><%1$s></%1$s>";
        String s = String.format(root, ROOT_NAME);

        try {
            doc = DocumentHelper.parseText(s);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.setDocument(doc);
    }

    public XmlDocModel(String argContent) {
        Document doc = null;
        String root = "<?xml version=\"1.0\" encoding=\"utf-8\"?><%1$s>%2$s</%1$s>";
        String s = String.format(root, ROOT_NAME, argContent);

        try {
            doc = DocumentHelper.parseText(s);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.setDocument(doc);
    }

    /**
     * 
     * 功能概述：保存XmlDocument到指定的路径  
     * @param document :待保存对象
     * @param fileName: 目的地路径
     * @return 是否保存成功
     * @author lucky  
     * 创建时间：Jun 13, 2013 10:57:25 AM  
     * 修改人：lucky
     * 修改时间：Jun 13, 2013 10:57:25 AM  
     * 修改备注：  
     * @version  
     *
     */
    public boolean Save(Document document, String fileName) {
        boolean flag = true;
        try {
            /* 将document中的内容写入文件中 */
            //默认为UTF-8格式"
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("utf-8");
            XMLWriter writer = new XMLWriter(new FileWriter(new File(fileName)), format);
            writer.write(document);
            writer.close();
        } catch (Exception ex) {
            flag = false;
            ex.printStackTrace();
        }
        return flag;
    }


    /**
     * load
     * 载入一个xml文档
     * @return 成功返回Document对象，失败返回null
     * @param filename  文件路径
     */
    public Document load(String filename) {
        Document doc = null;
        try {
            SAXReader saxReader = new SAXReader();
            doc = saxReader.read(new File(filename));
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        this.setDocument(doc);
        return doc;
    }
    
    
    /**
     * load
     * 载入一个xml格式字串
     * @return 成功返回Document对象，失败返回null
     * @param s_content  xml格式字串
     */
    public Document loadXml(String s_content) {
    	byte[]  byteArrResponse = s_content.getBytes();
        Document doc = null;
        try {
            String strArrResponse = new String(byteArrResponse);  
            doc = DocumentHelper.parseText(strArrResponse); 
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        this.setDocument(doc);
        return doc;
    }
    
    /**
     * 將xmldocmodel對象轉換爲字串
     */
    public String toString() {
        
        return this.getDocument().asXML();
    }

    public boolean isEmpty() {
        return _empty;
    }

    public boolean isChanged() {
        return _changed;
    }

    public Document getDocument() {
        return _document;
    }

    public void setDocument(Document _document) {
        this._document = _document;
    }
}
