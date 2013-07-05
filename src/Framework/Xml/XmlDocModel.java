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
import java.io.FileInputStream;
import java.io.FileWriter;
//工具包
import java.util.Iterator;
import java.util.List;
//dom4j包
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
//transform
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

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

	// Constructor

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
	 * 
	 * @param document
	 *            :待保存对象
	 * @param fileName
	 *            : 目的地路径
	 * @return 是否保存成功
	 * @author lucky 创建时间：Jun 13, 2013 10:57:25 AM 修改人：lucky 修改时间：Jun 13, 2013
	 *         10:57:25 AM 修改备注：
	 * @version
	 * 
	 */
	public boolean Save(Document document, String fileName) {
		boolean flag = true;
		try {
			/* 将document中的内容写入文件中 */
			// 默认为UTF-8格式"
			String path = fileName.substring(0, fileName.lastIndexOf('/'));
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			XMLWriter writer = new XMLWriter(
					new FileWriter(new File(fileName)), format);
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * load 载入一个xml文档
	 * 
	 * @return 成功返回Document对象，失败返回null
	 * @param filename
	 *            文件路径
	 */
	public Document load(String filename) {
		Document doc = null;
		try {
			SAXReader saxReader = new SAXReader();
			doc = saxReader.read(new File(filename));
		} catch (Exception ex) {
			// ex.printStackTrace();
		}
		this.setDocument(doc);
		return doc;
	}

	/**
	 * load 载入一个xml格式字串
	 * 
	 * @return 成功返回Document对象，失败返回null
	 * @param s_content
	 *            xml格式字串
	 */
	public Document loadXml(String s_content) {
		byte[] byteArrResponse = s_content.getBytes();
		Document doc = null;
		try {
			String strArrResponse = new String(byteArrResponse);
			doc = DocumentHelper.parseText(strArrResponse);
		} catch (Exception ex) {
			// ex.printStackTrace();
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

	// / <summary>
	// / 取得资料笔数，排除被删除行
	// / </summary>
	public int Count() {

		int ret = 0;
		String path = "record[@ischanged != '3']";
		if (!this.isEmpty()) {
			// DataSet转换
			List xnChangeds = this.getDocument().getRootElement()
					.selectNodes(path);
			if (xnChangeds != null) {
				ret = xnChangeds.size();
			}
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
			
			// File not found
		} catch (TransformerConfigurationException e) {
			// An error occurred in the XSL file
		} catch (TransformerException e) {
			// An error occurred while applying the XSL file
			// Get location of error in input file
		}
		return HtmlFilePath;
	}


	 public String Transform(String argXsltPath)
	 {
		 return Transform(argXsltPath, this);
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
	public String GetExportXsltFile(String argXsltPath, String argTemplatePath,
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
			// thead
			// XmlNode ndCol = xslTemplate.CreateNode(XmlNodeType.Element,
			// "xsl:value-of", "");
			Node xnTheads = xslTemplate.getDocument().getRootElement().selectSingleNode("xsl:template/html/body/div/xsl:for-each/table/tr/td/table/thead/tr");
			Node xnTBodys = xslTemplate.getDocument().getRootElement().selectSingleNode("xsl:template/html/body/div/xsl:for-each/table/tr/td/table/tbody/xsl:for-each/tr");

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
					// xnTR = xnTheads[0].ChildNodes[0];
					// xnTD = xslTemplate.CreateNode(XmlNodeType.Element, "td",
					// null);
					// xnTD.setText(xnCol.attribute("name").getValue());
					// xnTD = xnTR.AppendChild(xnTD);
					Element td = xnTheads.getDocument().addElement("td");
					td.setText(xnCol.attribute("name").getValue());
					// tbody
					// xnTR = xnTBodys[0].FirstChild.FirstChild;//有<xsl:for-each
					// select="record">
					// xnTD = xslTemplate.CreateNode(XmlNodeType.Element, "td",
					// null);
					// xnTD = xnTR.AppendChild(xnTD);
					td = xnTBodys.getDocument().addElement("td");
					Element val = td.addElement("xsl:value-of");
					val.addAttribute("select", xnCol.attribute("id").getValue());
					//
					// XmlNode ndValue =
					// xslTemplate.CreateNode(XmlNodeType.Element,
					// "xsl:value-of",
					// xslTemplate.DocumentElement.NamespaceURI);
					// XmlAttribute sel = xslTemplate.CreateAttribute(null,
					// "select", null);
					// sel.Value = xnCol.Attributes["id"].Value;
					// ndValue.Attributes.Append(sel);
					//
					// xnTD.AppendChild(ndValue);
				}
			}
			// XmlNode Table = Tables.Item(0);
			// Table.AppendChild(ndValue);
			xslTemplate.Save(xslTemplate.getDocument(), argXsltPath);
		}
		return ret;
	}

}
