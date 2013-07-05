package Framework.Helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.*;

import Framework.Data.JsonHelper;
import Framework.Xml.XmlDocModel;
import Framework.logger.MyLogger;

public class Tools {

	
    public static String SafeJSON(String sIn)
    {
        StringBuilder sbOut = new StringBuilder(sIn.length());
        
        char[] chars=sIn.toCharArray(); 
        for(char ch:chars){ 
            //if (char.IsControl(ch) || ch == '\'')
            if (ch == '\'')            	
            {
                int ich = (int)ch;
                //sbOut.append("\\u" + ich.toString("x4"));
                sbOut.append("\\u");
                continue;
            }
            else if (ch == '\"' || ch == '\\' || ch == '/')
            {
                sbOut.append('\\');
            }
            sbOut.append(ch);
        } 
 
        return sbOut.toString();
    }
    
    /**
     * 
     * 功能概述：  
     * @param hm_row
     * @return
     * @author lucky  
     * 创建时间：Jun 18, 2013 11:26:47 AM  
     * 修改人：lucky
     * 修改时间：Jun 18, 2013 11:26:47 AM  
     * 修改备注：  
     * @version  
     *
     */
    public static Node list2Node(HashMap<String, String> hm_row){
    	
    	Node ret = null;
    	
    	return ret;
    	
    }
    
    public static String XmlToJSON(XmlDocModel xmlDoc, String argInfo)
    {
    	String ret = "";
    	ret = JsonHelper.xmltoJson(xmlDoc.getDocument().asXML());
    	return ret;
    	
    }
    
    public static String XmlToJSON(String s_xml, String argInfo)
    {
    	String ret = "";
    	ret = JsonHelper.xmltoJson(s_xml);
    	return ret;
    	
    }
    
	/**
	 * 
	 * 功能概述：格式化日期
	 * 
	 * @param s_format
	 *            格式样式字串
	 * @return 格式化日期后的日期，若有异常返回错误
	 * @author lucky 
	 * @创建时间：Jun 7, 2013 2:54:10 PM
	 * @修改人：lucky 修改时间：Jun 7, 2013 2:54:10 PM 
	 * @note：
	 * @version
	 * 
	 */
	public static String formatDate(String s_format) {
		String ret = "";
		String defaultFormat = "yyMMdd";
		SimpleDateFormat sFormat = new SimpleDateFormat(s_format);
		try {
			ret = sFormat.format(System.currentTimeMillis());
		} catch (NullPointerException e) {
			sFormat = new SimpleDateFormat(defaultFormat);
			ret = sFormat.format(System.currentTimeMillis());
			e.printStackTrace();
		} catch (IllegalArgumentException e2) {
			sFormat = new SimpleDateFormat(defaultFormat);
			ret = sFormat.format(System.currentTimeMillis());
			e2.printStackTrace();
		}
		return ret;
	}

	// / <summary>
	// / 將Xml資料用指定的ＸＳＬＴ文件進行轉換。
	// / </summary>
	// / <param name="argXsltPath">Xslt文件名稱與路徑</param>
	// / <param name="argXmlData">Xml資料</param>
	// / <returns>轉換結果</returns>
	// / <remarks>
	// / <para>[規格說明]
	// / </para>
	// / <para>[異動記錄]
	// / AUTHOR DATE NOTE
	// / ========== ========== ========================================
	// / Rayd 2008-09-20 Create
	// / </para>
	// / <example>
	// / <code>
	// /
	// / </code>
	// / </example>
	// / </remarks>
	// public String Transform(String argXsltPath, XmlDocModel argXmlData)
	// {
	// int pos = argXsltPath.LastIndexOf("\\");
	// int pos2 = argXsltPath.LastIndexOf(".");
	// String HtmlFilePath = argXsltPath + ".xls";
	// XPathNavigator xpData = argXmlData.CreateNavigator(); ;
	// XmlTextWriter myWriter;
	// XslCompiledTransform xslTran = new XslCompiledTransform();
	// try
	// {
	// //XslTransform xslTran = new XslTransform();//for 2003
	// xslTran.Load(argXsltPath);
	//
	// }
	// catch (Exception e)
	// {
	//
	// }
	// // This is for generating the output in new HTML
	// myWriter = new XmlTextWriter(HtmlFilePath, System.Text.Encoding.Unicode);
	//
	// try
	// {
	// xslTran.Transform(xpData, null, myWriter);
	// }
	// catch (Exception ex)
	// {
	//
	// }
	// finally
	// {
	// myWriter.Close();
	//
	// }
	//
	// //return reader.ReadToEnd();
	// return HtmlFilePath;
	//
	// }

	/**
	 * This method applies the xsl file to in file,and writes the output to out file.
	 * 
	 * @paraminFilename infilepath
	 * @paramoutFilename outfilepath
	 * @paramxslFilename xslfilepath
	 */
	public static String Transform(String argXsltPath,XmlDocModel x_doc) {
		String HtmlFilePath = argXsltPath + ".xls";
		String outFilename = HtmlFilePath;
		String inFilename = argXsltPath + ".xml";
		
		try {
			x_doc.Save(x_doc.getDocument(), inFilename);
			// Create transformer factory
			TransformerFactory factory = TransformerFactory.newInstance();

			// Use the factory to create a template containing the xsl file
			Templates template = factory.newTemplates(new StreamSource(
					new FileInputStream(argXsltPath)));

			// Use the template to create a transformer
			Transformer xformer = template.newTransformer();

			// Prepare the input and output files
			Source source = new StreamSource(new FileInputStream(inFilename));
			//new FileInputStream()
			Result result = new StreamResult(new FileOutputStream(outFilename));

			// Apply the xsl file to the source file and write the result to the
			// output file
			xformer.transform(source, result);
			
		} catch (FileNotFoundException e) {
			MyLogger.Write(e.getMessage());
			// File not found
		} catch (TransformerConfigurationException e) {
			MyLogger.Write(e.getMessage());
			// An error occurred in the XSL file
		} catch (TransformerException e) {
			MyLogger.Write(e.getMessage());
			// An error occurred while applying the XSL file
			// Get location of error in input file
		}
		return HtmlFilePath;
	}



	// / <summary>
	// / 取得XSLT文件。
	// / </summary>
	// / <param name="argXsltPath">Xslt文件名稱與路徑</param>
	// / <returns>XSLT文件Path，如D:\Data\XsltFiles</returns>
	// / <remarks>
	// / <para>[規格說明]
	// / 1.若存在則直接返回。
	// / 2.若不存在，則依ReportRemplate.Xslt產生。
	// / </para>
	// / <para>[異動記錄]
	// / AUTHOR DATE NOTE
	// / ========== ========== ========================================
	// / Rayd 2008-09-23 Create
	// / </para>
	// / <example>
	// / <code>
	// / --ReportTemplate.xslt文件片段
	// / <div>
	// / <xsl:for-each select="/xconfig/records">
	// / <table cellpadding="0" cellspacing="0" border="0" frame="border"
	// width="100%" style="font-size: 10pt">
	// / <tr>
	// / <td>
	// / <table cellpadding="8" cellspacing="0" border="1" frame="border"
	// width="100%">
	// / <thead>
	// / <tr>
	// / <!--動態替換
	// / <td nowrap="nowrap" align="center" >代號</td>
	// / -->
	// / </tr>
	// / </thead>
	// / <tbody>
	// / <xsl:for-each select="record">
	// / <tr>
	// / <!--動態替換
	// / <td>
	// / <xsl:value-of select="proj_no"/>
	// / </td>
	// / -->
	// / </tr>
	// / </xsl:for-each>
	// / </tbody>
	// / </table>
	// / </td>
	// / </tr>
	// / </table>
	// / </xsl:for-each>
	// / </div>
	// / </code>
	// / </example>
	// / </remarks>
	public static String GetExportXsltFile(String argXsltPath, String argTemplatePath,
			XmlDocModel argXnConf) {
		String ret = null;
		// int p1 = argXsltPath.LastIndexOf('\\');
		// String xslFile = argXsltPath.Substring(p1+1);
		// String xslTpl = argXsltPath.Substring(0,p1+1) +
		// "ReportTemplate.xslt";
		Node xnTR, xnTD;
		boolean isShowFld = false;
		Element xnCol;

		Document doc = argXnConf.getDocument();
		Element root = doc.getRootElement();

		File xslFlile = new File(argXsltPath);
		// 文件存在直接返回
		if (xslFlile.exists()) {
			ret = argXsltPath;
		} else// 文件不存在
		{
			XmlDocModel xslTemplate = new XmlDocModel();
			xslTemplate.load(argTemplatePath);
			Element xnTheads = (Element)xslTemplate.getDocument().getRootElement().selectSingleNode("//xsl:template/html/body/div/xsl:for-each/table/tr/td/table/thead/tr");
			Element xnTBodys = (Element)xslTemplate.getDocument().getRootElement().selectSingleNode("//xsl:template/html/body/div/xsl:for-each/table/tr/td/table/tbody/xsl:for-each/tr");

			for (Iterator<?> it = root.elementIterator(); it.hasNext();) {

				xnCol = (Element) it.next();
				isShowFld = (xnCol.attribute("hidden") == null)
						|| (xnCol.attribute("hidden").getValue().equals("0"));
				if (isShowFld) {
					if (xnCol.attribute("iskey") != null
							&& xnCol.attribute("iskey").getValue().equals("1")) {
						isShowFld = false;
					}
				}

				//
				if (isShowFld) {
					// thead
					Element td = xnTheads.addElement("td");
					//xnTheads.
					if(xnCol.attribute("name")!=null){
						td.setText(xnCol.attribute("name").getValue());
					}else{
						td.setText(xnCol.getName());
					}
					
					// tbody
					td = xnTBodys.addElement("td");
					Element val = td.addElement("xsl:value-of");
					if(xnCol.attribute("id")!=null){
						val.addAttribute("select", xnCol.attribute("id").getValue());
					}else{
						val.addAttribute("select", xnCol.getName());
					}
					
				}
			}
			xslTemplate.Save(xslTemplate.getDocument(), argXsltPath);
		}
		return ret;
	}
    
    /// <summary>
    /// 將XMLDoc轉換成JSON
    /// </summary>
    /// <param name="xmlDoc">Xml數據對象</param>
    /// <param name="argInfo">加上信息段</param>
    /// <returns>JSON格式字串</returns>
    /// <remarks>
    /// <example>
    /// JSON格式字串:
    /// 多筆記錄
    /// { "newdataset": { "table": [ {"proj_id": "85309a1f-87bb-479a-87f0-8aa8a59bd6bc", "proj_name": "PM", "proj_nm": "PM", "proj_no": "P201008200004" }, 
    ///                             {"proj_id": "fc7a33aa-f7c6-4b83-ad25-6864075f6f47", "proj_name": "KM", "proj_nm": "km", "proj_no": "P201008200005" }, 
    ///                             {"proj_id": "6a0ecc3b-c342-46a5-bd1d-73c050ec6508", "proj_name": "WF", "proj_nm": "wf", "proj_no": "P201008200006" }, 
    ///                             {"proj_id": "91876592-2e3c-4fda-8e27-e18bef89b5b2", "proj_name": "ERP", "proj_nm": "ERP", "proj_no": "P201008200007" }, 
    ///                             {"proj_id": "748609ca-76ac-4902-bbc6-17cf1fcbff5f", "proj_name": "B2B", "proj_nm": "B2B", "proj_no": "P201008230001" } ] }}
    /// 單筆記錄
    /// { "newdataset": {"table": {"proj_id": "85309a1f-87bb-479a-87f0-8aa8a59bd6bc", "proj_name": "PM", "proj_nm": "PM", "proj_no": "P201008200004" } }}
    /// 
    /// </example>
    /// <para>
    /// 
    /// </para>
    /// </remarks>
//    public static String XmlToJSON(XmlDocModel xmlDoc, String argInfo)
//    {
//        StringBuilder sbJSON = new StringBuilder();
//        String[] arr1, arr2;
//        int i = 0;
//        sbJSON.append("{ ");
//        XmlToJSONnode(sbJSON, xmlDoc.getDocument().getRootElement(), true);
//        
//        if (!(argInfo.equals("")||argInfo!=null))
//        {
//            arr1 = argInfo.split("&");
//            sbJSON.append(",\"info\":{");
//            for (int m = 0; m < arr1.length; m++)
//            {
//                arr2 = arr1[m].split("=");
//
//                if (arr2.length == 2)
//                {
//                    if (i != 0)
//                    {
//                        sbJSON.append(",");
//
//                    }
//                    i++;
//                    sbJSON.append("\"")
//                        .append(arr2[0])
//                        .append("\":")
//                        .append("\"")
//                        .append(arr2[1])
//                        .append("\"");
//                }
//            }
//            sbJSON.append("}");
//
//        }
//        sbJSON.append("}");
//
//        return sbJSON.toString();
//    }
//
//    //  XmlToJSONnode:  Output an XmlElement, possibly as part of a higher array
//    private static void XmlToJSONnode(StringBuilder sbJSON, Element node, boolean showNodeName)
//    {
//        if (showNodeName)
//            //sbJSON.append("\"" + SafeJSON(node.Name) + "\": ");
//            sbJSON.append("\"" + node.getName().toLowerCase() + "\": ");
//        sbJSON.append("{");
//        // Build a sorted list of key-value pairs
//        //  where   key is case-sensitive nodeName
//        //          value is an ArrayList of String or XmlElement
//        //  so that we know whether the nodeName is an array or not.
//        SortedList childNodeNames = new SortedList();
//
//        //  Add in all node attributes
//        if (node.Attributes != null)
//            foreach (XmlAttribute attr in node.Attributes)
//                StoreChildNode(childNodeNames, attr.Name, attr.InnerText);
//
//        //  Add in all nodes
//        for (Node cnode : node.ChildNodes)
//        {
//            if (cnode is XmlText)
//                StoreChildNode(childNodeNames, "value", cnode.InnerText);
//            else if (cnode is XmlElement)
//                StoreChildNode(childNodeNames, cnode.Name, cnode);
//        }
//
//        // Now output all stored info
//        for (String childname : childNodeNames.Keys)
//        {
//            ArrayList alChild = (ArrayList)childNodeNames[childname];
//            if (alChild.Count == 1)
//                OutputNode(childname, alChild[0], sbJSON, true);
//            else
//            {
//                //sbJSON.append(" \"" + SafeJSON(childname) + "\": [ ");
//                sbJSON.append("\"" + childname.ToLower() + "\": [ ");
//                for (Object Child : alChild)
//                    OutputNode(childname, Child, sbJSON, false);
//                sbJSON.delete(sbJSON.length() - 2, 2);
//                sbJSON.append(" ], ");
//            }
//        }
//        sbJSON.delete(sbJSON.length() - 2, 2);
//        sbJSON.append(" }");
//    }
//
//    //  StoreChildNode: Store data associated with each nodeName
//    //                  so that we know whether the nodeName is an array or not.
//    private static void StoreChildNode(SortedList childNodeNames, String nodeName, object nodeValue)
//    {
//        // Pre-process contraction of XmlElement-s
//        if (nodeValue is XmlElement)
//        {
//            // Convert  <aa></aa> into "aa":null
//            //          <aa>xx</aa> into "aa":"xx"
//            XmlNode cnode = (XmlNode)nodeValue;
//            if (cnode.Attributes.Count == 0)
//            {
//                XmlNodeList children = cnode.ChildNodes;
//                if (children.Count == 0)
//                    nodeValue = null;
//                else if (children.Count == 1 && (children[0] is XmlText))
//                    nodeValue = ((XmlText)(children[0])).InnerText;
//            }
//        }
//        // Add nodeValue to ArrayList associated with each nodeName
//        // If nodeName doesn't exist then add it
//        object oValuesAL = childNodeNames[nodeName];
//        ArrayList ValuesAL;
//        if (oValuesAL == null)
//        {
//            ValuesAL = new ArrayList();
//            childNodeNames[nodeName] = ValuesAL;
//        }
//        else
//            ValuesAL = (ArrayList)oValuesAL;
//        ValuesAL.Add(nodeValue);
//    }
//
//    private static void OutputNode(String childname, object alChild, StringBuilder sbJSON, bool showNodeName)
//    {
//        if (alChild == null)
//        {
//            if (showNodeName)
//                sbJSON.append("\"" + SafeJSON(childname) + "\": ");
//                //sbJSON.append("\"" + childname.ToLower() + "\": ");
//            sbJSON.append("null");
//        }
//        else if (alChild is String)
//        {
//            if (showNodeName)
//               // sbJSON.append("\"" + SafeJSON(childname) + "\": ");
//                sbJSON.append("\"" + childname.ToLower() + "\": ");
//            String sChild = (String)alChild;
//            sChild = sChild.Trim();
//            sbJSON.append("\"" + SafeJSON(sChild) + "\"");
//            //sbJSON.append("\"" + sChild.ToLower() + "\"");
//        }
//        else
//            XmlToJSONnode(sbJSON, (XmlElement)alChild, showNodeName);
//        sbJSON.append(", ");
//    }



}
