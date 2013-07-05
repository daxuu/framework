package Framework.Data;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Element;
import org.jaxen.dom4j.Dom4jXPath;

import Framework.Helper.Tools;
import Framework.Xml.XmlDocModel;
import Framework.lang.Translator;
import Framework.logger.MyLogger;
import Framework.Exception.*;

/**
 * 
 * 
 * 项目名称：Framework 类名称：BLLInterface 类描述：逻辑层共用类，提供給BLL層作父類，實現通用的增、刪、改、查等基本功能
 * 
 * @author rayd 创建时间：Jun 13, 2013 9:11:39 AM 修改人：rayd 修改时间：Jun 13, 2013 9:11:39
 *         AM 修改备注：
 * @version
 * 
 */
public class BLLInterface {
	// Manuan.eBridge.Framework.Cache.PageCache cacher = new
	// Manuan.eBridge.Framework.Cache.PageCache();
	private String dataID;

	// / <summary>
	// / 页面对象的数据ID
	// / </summary>
	public String getDataID() {
		return dataID;
	}

	public void setDataID(String dataID) {
		this.dataID = dataID;
	}

	// / <summary>
	// / 分页xsl文件路径
	// / </summary>
	public String getPosXslPath() {
		return Global.AppDir() + "/data/xslt/position.xsl";
	}

	// / <summary>
	// / 分页xsl文件路径
	// / </summary>
	public String getSortXslPath() {
		return Global.AppDir() + "/data/xslt/sort.xsl";
	}

	// / <summary>
	// / 分页xsl文件路径
	// / </summary>
	public String getReportXslPath() {
		return Global.AppDir() + "/data/xslt/ReportTemplate.xslt";
	}

	// / <summary>
	// / 直接根据条件取资料
	// / </summary>
	// / <returns>JSON資料</returns>
	// / <remarks>
	// / <para>[規格說明]
	// /
	// / </para>
	// / <para>
	// / Change Log:
	// / Date Athor Remark
	// / ----------- ---------- ----------
	// / 2012-12-04 Rayd create
	// /
	// / </para>
	// / <example>
	// / <code>
	// /
	// / </code>
	// / </example>
	// / </remarks>
	public String GetValue(String s_conds, String s_fields, String s_options,
			DBTable o_dal) {
		String ret = "";
		String fields = "*";
		// XmlDocModel xOptions = new XmlDocModel(s_options);
		// Framework.MyLogger.Log(xOptions.OuterXml);
		// fields
		/*
		 * if (xOptions.selectSingleNode("xconfig/fields") != null) { fields =
		 * xOptions.selectSingleNode("xconfig/fields").InnerXml; }
		 */
		// remark temp
		ret = o_dal.QueryJson(s_fields, s_conds);

		return ret;

	}

	// / <summary>
	// / 处理按條件查詢資料，或翻页请求,以s_options.type來區分
	// / </summary>
	// / <returns>JSON資料</returns>
	// / <remarks>
	// / <para>[規格說明]
	// /
	// / </para>
	// / <para>
	// / Change Log:
	// / Date Athor Remark
	// / ----------- ---------- ----------
	// / 2012-11-12 Rayd create
	// /
	// / </para>
	// / <example>
	// / <code>
	// /
	// / </code>
	// / </example>
	// / </remarks>
	public String Query(String s_conds, String s_fields, String s_options,
			DBTable o_dal) {
		String ret = "";
		String fields = "*";
		String obj_id = "";
		String request_type = "";
		String size = "10", curr_pages = "1";
		String sortType = "asc";
		String dataType = "String";
		String fldname = "";
		String grid_id = "";

		XmlDocModel doc = new XmlDocModel(s_options);
		Element xOptions = doc.getDocument().getRootElement();

		// Framework.MyLogger.Log(xOptions.OuterXml);
		// fields
		// if (xOptions.selectSingleNode("xconfig/fields") != null)
		// {
		// fields = xOptions.selectSingleNode("xconfig/fields").InnerXml;
		// }

		// id
		if (xOptions.selectSingleNode("xconfig/id") != null) {
			obj_id = xOptions.selectSingleNode("xconfig/id").getText();
		}
		// request_type
		if (xOptions.selectSingleNode("xconfig/type") != null) {
			request_type = xOptions.selectSingleNode("xconfig/type").getText();
		}
		// size option
		if (xOptions.selectSingleNode("xconfig/size") != null) {
			size = xOptions.selectSingleNode("xconfig/size").getText();
		}
		// curr option
		if (xOptions.selectSingleNode("xconfig/curr") != null) {
			curr_pages = xOptions.selectSingleNode("xconfig/curr").getText();
		}
		this.setDataID(obj_id + Global.getAccountId());

		// 取消Cache,一次只要查詢一頁數據
		if (request_type.trim().toLowerCase().equals("query")) {
			// 直接将数据用JSON格式返回；
			// XmlDocModel xData = o_dal.QueryXml(s_fields, s_conds);
			// cache data
			// SetData(this.getDataID(), xData);

			// ret = TurnPage("1", size, xOptions);
			ret = o_dal.QueryJson(s_fields, s_conds, 1, Integer.parseInt(size));

		} else if (request_type.trim().toLowerCase().equals("page")) {
			// 分页是条件不变，取得不同页次的数据
			// ret = TurnPage(curr_pages, size, xOptions);
			ret = o_dal.QueryJson(s_fields, s_conds,
					Integer.parseInt(curr_pages), Integer.parseInt(size));

		} else if (request_type.trim().toLowerCase().equals("sort")) {
			// 排序需仅将本页数据进行排序，并记忆排序字段与类型
			// sortType option
			if (xOptions.selectSingleNode("xconfig/sortType") != null) {
				sortType = xOptions.selectSingleNode("xconfig/sortType")
						.getText();
			}
			// dataType option
			if (xOptions.selectSingleNode("xconfig/dataType") != null) {
				dataType = xOptions.selectSingleNode("xconfig/dataType")
						.getText();
			}
			// fldname option
			if (xOptions.selectSingleNode("xconfig/fldname") != null) {
				fldname = xOptions.selectSingleNode("xconfig/fldname")
						.getText();
			}
			// ret = Sort(curr_pages, size, fldname, dataType, sortType,
			// xOptions);
		} else if (request_type.trim().toLowerCase().equals("export")) {
			// 导出数据不分页，需将符合条件的数据全部导出；
			// fldname option
			if (xOptions.selectSingleNode("xconfig/id") != null) {
				grid_id = xOptions.selectSingleNode("xconfig/id").getText();
			}
			XmlDocModel xDataDocModel = o_dal.QueryXml(s_fields, s_conds);
			ret = Export(xDataDocModel, grid_id, fields);

		}

		return ret;
	}

	// / <summary>
	// / 翻頁:分割数据
	// / </summary>
	// / <param name="s_currpages">當前頁次</param>
	// / <param name="s_size">每頁筆數</param>
	// / <returns>JSON資料</returns>
	// / <remarks>
	// / <para>[規格說明]
	// / nothing
	// / </para>
	// / <para>
	// / Change Log:
	// / Date Athor Remark
	// / ----------- ---------- ----------
	// / 2012-11-12 Rayd create
	// /
	// / </para>
	// / <example>
	// / <code>
	// /
	// / </code>
	// / </example>
	// / </remarks>

	// protected String TurnPage(String s_currpages, String s_size,
	// Element x_options) {
	// String ret = "";
	// int size = 10, curr = 0;
	//
	// size = Integer.parseInt(s_size);
	// curr = Integer.parseInt(s_currpages);
	//
	// XmlDocModel xData = GetData(this.getDataID());
	// int start = (curr - 1) * size + 1;
	// int end = start + size;
	//
	// String info = "count=" + Integer.toString(xData.Count());
	// // 分割數據
	// ret = Helper.Tools.XmlToJSON(
	// xData.GetData(start, end, this.getPosXslPath()), info);
	// // 若Client端设定了解密，返回的资料需加密
	// if (x_options.selectSingleNode("xconfig/decrypt") != null
	// && x_options.selectSingleNode("xconfig/decrypt").getText() == "true") {
	// ret = eBridge.Framework.Security.DES.encrypt(ret,
	// eBridge.Framework.Helper.NumberConvert.getPrivateKey());
	// }
	// return ret;
	// }

	// / <summary>
	// / 排序
	// / </summary>
	// / <param name="argInData"></param>
	// / <returns>json String</returns>
	// / <remarks>
	// / <para>[規格說明]
	// / 1.size:
	// / 2.curr:
	// / 3.fldname:
	// / 4.sorttype:
	// / </para>
	// / <para>
	// / Change Log:
	// / Date Athor Remark
	// / ----------- ---------- ----------
	// / 2012-11-12 Rayd create
	// /
	// / </para>
	// / <example>
	// / <code>
	// /
	// / </code>
	// / </example>
	// / </remarks>
	// private String Sort(String s_currpages, String s_size, String s_fldname,
	// String s_datatype, String s_sorttype,Element x_options)
	// {
	// String ret = "";
	// int size = Integer.parseInt(s_size);
	// int curr = Integer.parseInt(s_currpages);
	// String dataType = "text";//or number
	// //data type
	// if(s_datatype.trim().toLowerCase().equals("number")){
	// dataType = "number";
	// }
	//
	// //fldname:DataSet.WriteXml转出来的Node名称全部为大写
	// //XmlDocModel xData =
	// this.GetData(this.getDataID()).Sort(s_fldname.ToUpper(), dataType,
	// s_sorttype, this.SortXslPath);
	// XmlDocModel xData = this.GetData(this.getDataID()).Sort(s_fldname,
	// dataType, s_sorttype, this.SortXslPath);
	// int start = (curr - 1) * size + 1;
	// int end = start + size;
	// this.SetData(this.getDataID(), xData);
	// String info = "count=" + xData.Count.ToString();
	// //分割數據
	// ret = Helper.Tools.XmlToJSON(xData.GetData(start, end, this.PosXslPath),
	// info);
	// //若Client端设定了解密，返回的资料需加密
	// if (x_options.selectSingleNode("xconfig/decrypt") != null &&
	// x_options.selectSingleNode("xconfig/decrypt").getText() == "true")
	// {
	// ret = eBridge.Framework.Security.DES.encrypt(ret,
	// eBridge.Framework.Helper.NumberConvert.getPrivateKey());
	// }
	// return ret;
	// }
	//

	// / <summary>
	// / 轉出成Excel格式資料
	// / </summary>
	// / <returns>產生的文件路徑</returns>
	// / <remarks>
	// / <para>[規格說明]
	// / 1.size:
	// / 2.curr:
	// / 3.fldname:
	// / 4.sorttype:
	// / </para>
	// / <para>
	// / Change Log:
	// / Date Athor Remark
	// / ----------- ---------- ----------
	// / 2012-11-17 Rayd create
	// /
	// / </para>
	// / <example>
	// / <code>
	// /
	// / </code>
	// / </example>
	// / </remarks>
	public String Export(XmlDocModel x_data, String s_grid_id, String s_fields) {
		String ret = "";
		String elementId = s_grid_id;
		String path = "/data/xslt/report/";

		String xslPath = elementId + ".xslt";
		String htmlPath = path + xslPath + ".xls";

		xslPath = Global.getAppDir() + path + xslPath;

		XmlDocModel xCol = new XmlDocModel(s_fields);

		ret = Tools.GetExportXsltFile(xslPath, this.getReportXslPath(), xCol);

		ret = Tools.Transform(xslPath, xCol);
		return htmlPath;
	}

	// / <summary>
	// / 保存数据,分别调用增删改对应方法
	// / </summary>
	// / <param name="s_records">待处理数据，用XML格式封装</param>
	// / <param name="s_options">资料处理选项，用XML格式封装</param>
	// / <returns>JSON資料</returns>
	// / <remarks>
	// / <para>[規格說明]
	// /
	// / </para>
	// / <para>
	// / Change Log:
	// / Date Athor Remark
	// / ----------- ---------- ----------
	// / 2012-11-12 Rayd create
	// /
	// / </para>
	// / <example>
	// / <code>
	// /
	// / </code>
	// / </example>
	// / </remarks>
	public String Save(String s_records, String s_fields, String s_options,
			DBTable o_dal) {
		String ret = Translator.GetUserMessage("000000", new String[] {}, true);
		StringBuilder sbMsg = new StringBuilder();
		String msg = "";
		String fields = "*";
		String obj_id = "";
		String request_type = "";
		String size = "10", curr_pages = "1";
		String sortType = "asc";
		String dataType = "String";
		String fldname = "";

		XmlDocModel docOptions = new XmlDocModel(s_options);
		XmlDocModel docRecords = new XmlDocModel(s_records);
		XmlDocModel docFieldInfo = new XmlDocModel(s_fields);

		Element xOptions = docOptions.getDocument().getRootElement();
		Element xRecords = docRecords.getDocument().getRootElement();
		Element xFieldInfo = docFieldInfo.getDocument().getRootElement();

		// Framework.MyLogger.Log(xOptions.OuterXml);
		// fields
		// if (xOptions.selectSingleNode("xconfig/fields") != null)
		// {
		// fields = xOptions.selectSingleNode("xconfig/fields").InnerXml;
		// xFieldInfo = new XmlDocModel(fields);
		// }

		// id
		if (xOptions.selectSingleNode("xconfig/id") != null) {
			obj_id = xOptions.selectSingleNode("xconfig/id").getText();
		}
		// request_type
		if (xOptions.selectSingleNode("xconfig/type") != null) {
			request_type = xOptions.selectSingleNode("xconfig/type").getText();
		}
		// size option
		if (xOptions.selectSingleNode("xconfig/size") != null) {
			size = xOptions.selectSingleNode("xconfig/size").getText();
		}
		// curr option
		if (xOptions.selectSingleNode("xconfig/curr") != null) {
			curr_pages = xOptions.selectSingleNode("xconfig/curr").getText();
		}
		this.setDataID(obj_id + Global.getAccountId());

		// 处理每一笔资料
		for (Iterator<?> iter = xRecords.elementIterator(); iter.hasNext();) {
			Element e = (Element) iter.next();
			for (Iterator<?> it = e.elementIterator(); it.hasNext();) {

				Element record = (Element) it.next();

				// for (XmlNode record in
				// xRecords.selectNodes("xconfig/record"))
				// {
				int kind = Integer.parseInt(record.selectSingleNode("myui_chg")
						.getText());
				switch (kind) {
				case 1:// new
					msg = AddRow(record, xFieldInfo, o_dal);
					if (msg != "") {
						sbMsg.append("\r\n").append(msg);
						msg = "";
					}

					break;
				case 2:// update

					msg = UpdateRow(record, xFieldInfo, o_dal);

					if (msg != "") {
						sbMsg.append("\r\n").append(msg);
						msg = "";
					}

					break;
				case 3:// delete
					msg = RemoveRow(record, xFieldInfo, o_dal);
					if (msg != "") {
						sbMsg.append("\r\n").append(msg);
						msg = "";
					}

					break;
				default:
					break;
				}
			}
		}
		// 若有错误信息
		if (sbMsg.length() > 0) {
			ret = Translator.GetUserMessage("savefail",
					new String[] { sbMsg.toString() }, true);
		}
		return ret;
	}

	// / <summary>
	// / 新增行
	// / </summary>
	// / <param name="xdData">用XmlNode封裝的一行資料</param>
	// / <returns>受影響的資料行數</returns>
	// / <remarks>
	// / <para>[規格說明]
	// / nothing
	// / </para>
	// / <para>
	// / Change Log:
	// / Date Athor Remark
	// / ----------- ---------- ----------
	// / 2012-11-12 Rayd create
	// /
	// / </para>
	// / <example>
	// / <code>
	// / nothing
	// / </code>
	// / </example>
	// / </remarks>
	public String AddRow(Element xdData, XmlDocModel x_fieldinfo, DBTable o_dal) {
		String msg = "";
		int ret = 0;

		try {
			msg = validate(xdData, x_fieldinfo, o_dal);

			// 是否验证有错误信息返回
			if (msg == "") {
				ret = o_dal.AddRow(xdData);
			}
		} catch (DALException exdb) {
			MyLogger.Log(String.format("調用此方法失敗：%1%s，Table=%3%s,錯誤信息：%2%s",
					"AddRow", exdb.getUserMessage(), o_dal.getDatabaseName()
							+ "." + o_dal.getTableName()));
			msg = exdb.getUserMessage();
			// throw exdb;
		} catch (PFException exbll) {
			MyLogger.Log(String.format("調用此方法失敗：%1%s，Table=%3%s,錯誤信息：%2%s",
					"AddRow", exbll.getUserMessage(), o_dal.getDatabaseName()
							+ "." + o_dal.getTableName()));
			msg = exbll.getUserMessage();
			// throw exbll;
		} catch (Exception ex) {
			MyLogger.Log(String.format("調用此方法失敗：%1%s，Table=%3%s,錯誤信息：%2%s",
					"AddRow", ex.getMessage(), o_dal.getDatabaseName() + "."
							+ o_dal.getTableName()));
			msg = ex.getMessage();
			// throw ex;
		}

		return msg;
	}

	 /// <summary>
	 /// 修改行
	 /// </summary>
	 /// <param name="xdData">用XmlNode封裝的一行資料</param>
	 /// <param name="xdConf">配置信息，來著PCM</param>
	 /// <returns>受影響的資料行數</returns>
	 /// <remarks>
	 /// <para>[規格說明]
	 /// nothing
	 /// </para>
	 /// <para>
	 /// Change Log:
	 /// Date Athor Remark
	 /// ----------- ---------- ----------
	 /// 2012-11-12 Rayd create
	 ///
	 /// </para>
	 /// <example>
	 /// <code>
	 /// nothing
	 /// </code>
	 /// </example>
	 /// </remarks>
	 public String UpdateRow(Element xdData, XmlDocModel x_fieldinfo,
	 DBTable o_dal)
	 {
	 String msg = "";
	 int ret = 0;
	 try
	 {
	 msg = validate(xdData, x_fieldinfo, o_dal);
	
	 //是否验证有错误信息返回
	 if (msg == "")
	 {
	 ret = o_dal.UpdateRow(xdData);
	 }
	
	 }
	 catch (DALException exdb)
	 {
	 MyLogger.Log(String.format("調用此方法失敗：%1$s，Table=%3$s,錯誤信息：%2$s",
	 "AddRow", exdb.getUserMessage(), o_dal.getDatabaseName() + "." +
	 o_dal.getTableName()));
	 msg = exdb.getUserMessage();
	 //throw exdb;
	 }
	 catch (PFException exbll)
	 {
		 
	 MyLogger.Log(String.format("調用此方法失敗：%1%s，Table=%3%s,錯誤信息：%2%s",
	 "AddRow", exbll.getUserMessage(), o_dal.getDatabaseName() + "." +
	 o_dal.getTableName()));
	 msg = exbll.getUserMessage();
	 //throw exbll;
	 }
	 catch (Exception ex)
	 {
	 MyLogger.Log(String.format("調用此方法失敗：%1%s，Table=%3%s,錯誤信息：%2%s",
	 "AddRow", ex.getMessage(), o_dal.getDatabaseName() + "." +
	 o_dal.getTableName()));
	 msg = ex.getMessage();
	 //throw ex;
	 }
	
	 return msg;
	
	 }
	
	
	
	 /// <summary>
	 /// 刪除行
	 /// </summary>
	 /// <param name="xdData">用XmlNode封裝的一行資料</param>
	 /// <param name="xdConf">配置信息，來著PCM</param>
	 /// <returns>受影響的資料行數</returns>
	 /// <remarks>
	 /// <para>[規格說明]
	 /// nothing
	 /// </para>
	 /// <para>
	 /// Change Log:
	 /// Date Athor Remark
	 /// ----------- ---------- ----------
	 /// 2012-11-12 Rayd create
	 ///
	 /// </para>
	 /// <example>
	 /// <code>
	 /// nothing
	 /// </code>
	 /// </example>
	 /// </remarks>
	 public String RemoveRow(Element xdData, XmlDocModel x_fieldinfo,
	 DBTable o_dal)
	 {
	 String msg = "";
	 int ret = 0;
	 try
	 {
	 msg = validate(xdData, x_fieldinfo, o_dal);
	
	 //是否验证有错误信息返回
	 if (msg == "")
	 {
	 ret = o_dal.DeleteRow(xdData);
	 }
	 }
	 catch (DbAccessException exdb)
	 {
	 MyLogger.Log(String.format("調用此方法失敗：%1%s，Table=%3%s,錯誤信息：%2%s",
	 "AddRow", exdb.getUserMessage(), o_dal.getDatabaseName() + "." +
	 o_dal.getTableName()));
	 msg = exdb.getUserMessage();
	 //throw exdb;
	 }
	 catch (BLLException exbll)
	 {
	 MyLogger.Log(String.format("調用此方法失敗：%1%s，Table=%3%s,錯誤信息：%2%s",
	 "AddRow", exbll.getUserMessage(), o_dal.getDatabaseName() + "." +
	 o_dal.getTableName()));
	 msg = exbll.getUserMessage();
	 //throw exbll;
	 }
	 catch (Exception ex)
	 {
	 MyLogger.Log(String.format("調用此方法失敗：%1%s，Table=%3%s,錯誤信息：%2%s",
	 "AddRow", ex.getMessage(), o_dal.getDatabaseName() + "." +
	 o_dal.getTableName()));
	 msg = ex.getMessage();
	 //throw ex;
	 }
	
	 return msg;
	 }
	
	
	 /// <summary>
	 /// 资料提交到DB前检验合法性
	 /// </summary>
	 /// <param name="xdData">资料记录</param>
	 /// <param name="x_fieldinfo">栏位信息</param>
	 /// <param name="o_dal">DAL 对象</param>
	 /// <returns>错误信息，若通过检查，返回空值</returns>
	 private String validate(Element xdData, XmlDocModel x_fieldinfo, DBTable	 o_dal)
	 {
	 String msg = "";
	 int i = 0;
	 String unique_fld = "";
	 String unique_val = "";
	 String key_fld = "";
	 String key_val = "";
	
	 HashMap<String, String> sdUnique = new HashMap<String, String>();
	 
	 XmlDocModel xUnique = new XmlDocModel();
	
	 //資料提交前判断
//	 if (xdData != null 
//			 && x_fieldinfo != null 
//			 && xdData.HasChildNodes 
//			 && x_fieldinfo.selectSingleNode("/xconfig").HasChildNodes)
	 if(xdData != null && x_fieldinfo != null)
	 {
	 //每栏
			for (Iterator<?> iter = xdData.elementIterator(); iter.hasNext();) {
				Element xdRecord = (Element) iter.next();

	 //for (Node xdRecord : xdData.ChildNodes)
	 //{
	 Element fld = (Element)x_fieldinfo.selectSingleNode("/xconfig/" + xdRecord.getName());
	
	 //找到
	 if (fld != null)
	 {
	 //KEY值
	 if (fld.attribute("iskey") != null)
	 {
	 key_fld = fld.getName();
	 key_val = xdRecord.getText();
	 }
	
	 //不能为空值判断
	 if (fld.attribute("min") != null && fld.attribute("min").getValue().equals("1"))
	 {
	 if (xdRecord.getText().equals(""))
	 {
	 msg += "\r\n" +
	 Translator.GetUserMessage("cant_empty", new
	 String[] { fld.attribute("name").getValue() });
	 }
	 }
	
	 //指定栏值是否重复判断
	 if (fld.attribute("unique") != null)
	 {
	 //group 实现多栏重复判断
	 if (sdUnique.containsKey(fld.attribute("unique").getValue()))
	 {
		 sdUnique.get(fld.attribute("unique").getValue().toString()) += "<" + fld.Name + " val=\"" + xdRecord.getText() + "\"/>";
	 }
	 else
	 {
	 sdUnique.put(fld.attribute("unique").getValue().toString(), "<" + fld.getName() +
	 " val=\"" + xdRecord.getText() + "\"/>");
	 }
	 }
	 //指定栏值是否需加密保存
	 if (fld.attribute("encrypt") != null &&
	 fld.attribute("encrypt").getValue().toString().toLowerCase()=="true")
	 {
	 xdRecord.setText(DES.encrypt(xdRecord.getText(),xdRecord.getName()));
	 }
	 }
	 }//every field
	
	 if (key_val.trim() == "")
	 {
	 msg += "\r\n" +
	 Translator.GetUserMessage("notkeyvalue", new String[] { key_fld });
	 }
	
	 //每组判断，指定栏值是否重复
	 foreach (DictionaryEntry de in sdUnique)
	 {
	 xUnique = new XmlDocModel(de.Value.ToString());
	
	 foreach (XmlNode xdFld in xUnique.selectSingleNode("xconfig").ChildNodes)
	 {
	
	 if (i == 0)
	 {
	 unique_fld = xdFld.getName();
	 unique_val = xdFld.attribute("val").getValue();
	 }
	 else
	 {
	 unique_fld += "+" + xdFld.getName();
	 unique_val += "+" + xdFld.attribute("val").getValue();
	
	 }
	 i++;
	 }
	 if (o_dal.IsExist(unique_fld, unique_val))
	 {
	 msg += "\r\n" +
	 Translator.GetUserMessage("exist", new
	 String[] { unique_val });
	 }
	 //}//group
	 //}
	 return msg;
	 }
	
	// /// <summary>
	// /// 將資料保存到Cache
	// /// </summary>
	// /// <param name="x_data">XmlDocModel 形態資料</param>
	// /// <remarks>
	// /// <para>[規格說明]
	// /// nothing
	// /// </para>
	// /// <para>
	// /// Change Log:
	// /// Date Athor Remark
	// /// ----------- ---------- ----------
	// /// 2012-11-12 Rayd create
	// ///
	// /// </para>
	// /// <example>
	// /// <code>
	// ///<?xml version="1.0" encoding="utf-8"?>
	// ///<xconfig>
	// /// <records>
	// /// <record ischanged="0">
	// /// <item_id>1</item_id>
	// /// <item_pid>1</item_pid>
	// /// <status>1</status>
	// /// <proj_id>1</proj_id>
	// /// <file_id>1</file_id>
	// /// <item_kind>1.00000</item_kind>
	// /// <item_title>點“停用通知”會引起OS報錯</item_title>
	// /// <item_content>1234567890</item_content>
	// /// <item_puter>Rayd</item_puter>
	// /// <item_put_time>2009-04-10</item_put_time>
	// /// <item_plan_time>2009-04-10</item_plan_time>
	// /// <item_handler>xza</item_handler>
	// /// <item_real_time>2009-04-10</item_real_time>
	// /// <dataer>1</dataer>
	// /// <datatime>2009/4/2 上午 09:53:22</datatime>
	// /// </record>
	// /// </records>
	// /// </xconfig>
	// /// </code>
	// /// </example>
	// /// </remarks>
	// protected void SetData(String s_dataId, XmlDocModel x_data)
	// {
	// //add to cache
	// cacher.Add(s_dataId, x_data);
	// }
	//
	// /// <summary>
	// /// 從Cache取得資料
	// /// </summary>
	// /// <param name="argCondId">Element ID</param>
	// /// <returns>條件</returns>
	// /// <remarks>
	// /// <para>[規格說明]
	// /// nothing
	// /// </para>
	// /// <para>
	// /// Change Log:
	// /// Date Athor Remark
	// /// ----------- ---------- ----------
	// /// 2012-11-12 Rayd create
	// ///
	// /// </para>
	// /// <example>
	// /// <code>
	// /// nothing
	// /// </code>
	// /// </example>
	// /// </remarks>
	// protected XmlDocModel GetData(String s_dataId)
	// {
	// XmlDocModel ret = null;
	// ret = cacher.GetData(s_dataId);
	// if (ret == null)
	// {
	// ret = new XmlDocModel("<records></records>");
	// }
	// return ret;
	// }
}
