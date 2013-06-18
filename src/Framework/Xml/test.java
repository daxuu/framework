/*
 * copyright_1
 * copyright_2
 * Copyright (c) 2010 Sun , Inc. All Rights Reserved.
 */
package Framework.Xml;

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
 * @author Administrator
 * @date 2010/4/2, 上午 10:04:57
 */
public class test {

    static Document document;
    private boolean validating;

    public test() {
    }

    /**
    * doc2String
    * 将xml文档内容转为String
    * @return 字符串
    * @param document
    */
   public static String doc2String(Document document)
   {
      String s = "";
      try
      {
           //使用输出流来进行转化
           ByteArrayOutputStream out = new ByteArrayOutputStream();
           //使用GB2312编码
           OutputFormat format = new OutputFormat("  ", true, "utf-8");
           XMLWriter writer = new XMLWriter(out, format);
           writer.write(document);
           s = out.toString("utf-8");
      }catch(Exception ex)
      {
           ex.printStackTrace();
      }
      return s;
   }

   /**
    * string2Document
    * 将字符串转为Document
    * @return
    * @param s xml格式的字符串
    */
   public static Document string2Document(String s)
   {
      Document doc = null;
      try
      {
           doc = DocumentHelper.parseText(s);
      }catch(Exception ex)
      {
           ex.printStackTrace();
      }
      return doc;
   }

    /**
    * doc2XmlFile
    * 将Document对象保存为一个xml文件到本地
    * @return true:保存成功  flase:失败
    * @param filename 保存的文件名
    * @param document 需要保存的document对象
    */
   public static boolean doc2XmlFile(Document document,String filename)
   {
      boolean flag = true;
      try
      {
            /* 将document中的内容写入文件中 */
            //默认为UTF-8格式，指定为"GB2312"
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("utf-8");
            XMLWriter writer = new XMLWriter(new FileWriter(new File(filename)),format);
            writer.write(document);
            writer.close();
        }catch(Exception ex)
        {
            flag = false;
            ex.printStackTrace();
        }
        return flag;
   }

   /**
    * string2XmlFile
    * 将xml格式的字符串保存为本地文件，如果字符串格式不符合xml规则，则返回失败
    * @return true:保存成功  flase:失败
    * @param filename 保存的文件名
    * @param str 需要保存的字符串
    */
   public static boolean string2XmlFile(String str,String filename)
   {
      boolean flag = true;
      try
      {
         Document doc =  DocumentHelper.parseText(str);
         flag = doc2XmlFile(doc,filename);
      }catch (Exception ex)
      {
         flag = false;
         ex.printStackTrace();
      }
      return flag;
   }

    /**
    * load
    * 载入一个xml文档
    * @return 成功返回Document对象，失败返回null
    * @param uri 文件路径
    */
   public static Document load(String filename)
   {
      Document document = null;
      try
      {
          SAXReader saxReader = new SAXReader();
          document = saxReader.read(new File(filename));
      }
      catch (Exception ex){
          ex.printStackTrace();
      }
      return document;
   }

   /**
    * xmlWriteDemoByString
    * 演示String保存为xml文件
    */
   public void xmlWriteDemoByString()
   {
      String s = "";
      /** xml格式标题 "<?xml version='1.0' encoding='GB2312'?>" 可以不用写*/
      s = "<config>\r\n"
         +"   <ftp name='DongDian'>\r\n"
         +"     <ftp-host>127.0.0.1</ftp-host>\r\n"
         +"     <ftp-port>21</ftp-port>\r\n"
         +"     <ftp-user>cxl</ftp-user>\r\n"
         +"     <ftp-pwd>longshine</ftp-pwd>\r\n"
         +"     <!-- ftp最多尝试连接次数 -->\r\n"
         +"     <ftp-try>50</ftp-try>\r\n"
         +"     <!-- ftp尝试连接延迟时间 -->\r\n"
         +"     <ftp-delay>10</ftp-delay>\r\n"
         +"  </ftp>\r\n"
         +"</config>\r\n";
      //将文件生成到classes文件夹所在的目录里
      string2XmlFile(s,"xmlWriteDemoByString.xml");
      //将文件生成到classes文件夹里
      string2XmlFile(s,"classes/xmlWriteDemoByString.xml");
   }

     /**
    * 演示手动创建一个Document，并保存为XML文件
    */
   public void xmlWriteDemoByDocument()
   {
        /** 建立document对象 */
        Document document = DocumentHelper.createDocument();
        /** 建立config根节点 */
        Element configElement = document.addElement("config");
        /** 建立ftp节点 */
        configElement.addComment("东电ftp配置");
        Element ftpElement = configElement.addElement("ftp");
        ftpElement.addAttribute("name","DongDian");
        /** ftp 属性配置 */
        Element hostElement = ftpElement.addElement("ftp-host");
        hostElement.setText("127.0.0.1");
        (ftpElement.addElement("ftp-port")).setText("21");
        (ftpElement.addElement("ftp-user")).setText("cxl");
        (ftpElement.addElement("ftp-pwd")).setText("longshine");
        ftpElement.addComment("ftp最多尝试连接次数");
        (ftpElement.addElement("ftp-try")).setText("50");
        ftpElement.addComment("ftp尝试连接延迟时间");
        (ftpElement.addElement("ftp-delay")).setText("10");
        /** 保存Document */
        doc2XmlFile(document,"classes/xmlWriteDemoByDocument.xml");
   }

    /**
    *  演示读取文件的具体某个节点的值
    */
   public static void xmlReadDemo()
   {
      Document doc = load("classes/xmlWriteDemoByDocument.xml");
      //Element root = doc.getRootElement();
      /** 先用xpath查找所有ftp节点 并输出它的name属性值*/
      List list = doc.selectNodes("/config/ftp" );
      Iterator it = list.iterator();
      while(it.hasNext())
      {
          Element ftpElement = (Element)it.next();
          System.out.println("ftp_name="+ftpElement.attribute("name").getValue());
      }
      /** 直接用属性path取得name值 */
      list = doc.selectNodes("/config/ftp/@name" );
      it = list.iterator();
      while(it.hasNext())
      {
          Attribute attribute = (Attribute)it.next();
          System.out.println("@name="+attribute.getValue());
      }
      /** 直接取得DongDian ftp的 ftp-host 的值 */
      list = doc.selectNodes("/config/ftp/ftp-host" );
      it = list.iterator();
      Element hostElement=(Element)it.next();
      System.out.println("DongDian's ftp_host="+hostElement.getText());
   }


//   /** ftp节点删除ftp-host节点 */
//ftpElement.remove(hostElement);
///** ftp节点删除name属性 */
//ftpElement.remove(nameAttribute);
///** 修改ftp-host的值 */
//hostElement.setText("192.168.0.1");
///** 修改ftp节点name属性的值 */
//nameAttribute.setValue("ChiFeng");
}
