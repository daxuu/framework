package Framework.Data;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map.Entry;
import java.sql.*;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class JsonHelper {

	/**
	 * 
	 * 功能概述：
	 * 
	 * @param rs_data
	 *            ResultSet資料集合
	 * @return 数据转换成JSON格式后的字符，以records开头
	 * @author lucky 创建时间：Jun 11, 2013 2:53:12 PM 修改人：lucky 修改时间：Jun 11, 2013
	 *         2:53:12 PM 修改备注：字段名称全部转换成小写，
	 * @version
	 * 
	 */
	public static String DataSetToJson(ResultSet rs_data) {
		String ret = "";
		try {
			// DataTable需要有资料
			if (rs_data != null && rs_data.getRow() > 0) {
				ResultSetMetaData rsmd = rs_data.getMetaData();
				int cols = rsmd.getColumnCount(); // 得到数据集的列数

				StringBuilder jsonBuilder = new StringBuilder();
				jsonBuilder.append("{\"records");
				// jsonBuilder.append(dt.TableName.ToString());
				jsonBuilder.append("\":[");
				for (int i = 0; i < rs_data.getRow(); i++) {
					jsonBuilder.append("{");
					for (int j = 0; j < cols; j++) {
						jsonBuilder.append("\"");
						// 暂时取消
						// jsonBuilder.append(Helper.Tools.SafeJSON(rsmd.getColumnName(i).toLowerCase()));
						jsonBuilder.append(rsmd.getColumnName(i).toLowerCase());
						jsonBuilder.append("\":\"");
						jsonBuilder.append(stringToJson(rs_data.getString(i)));
						jsonBuilder.append("\",");
					}
					jsonBuilder.delete(jsonBuilder.length() - 1, 1);
					jsonBuilder.append("},");
				}
				jsonBuilder.delete(jsonBuilder.length() - 1, 1);
				jsonBuilder.append("]");
				jsonBuilder.append("}");
				ret = jsonBuilder.toString();
			}// end if

		} catch (SQLException ex) {
			ret = "\r\n" + ex.getMessage();
		}

		return ret;
	}

	/**
	 * map to xml xml <node><key label="key1">value1</key><key
	 * label="key2">value2</key>......</node>
	 * 
	 * @param map
	 * @return
	 */
	public static String maptoXml(Map<?, ?> map) {
		Document document = DocumentHelper.createDocument();
		Element nodeElement = document.addElement("node");
		for (Object obj : map.keySet()) {
			Element keyElement = nodeElement.addElement("key");
			keyElement.addAttribute("label", String.valueOf(obj));
			keyElement.setText(String.valueOf(map.get(obj)));
		}
		return doc2String(document);
	}

	/**
	 * list to xml xml <nodes><node><key label="key1">value1</key><key
	 * label="key2">value2</key>......</node><node><key
	 * label="key1">value1</key><key
	 * label="key2">value2</key>......</node></nodes>
	 * 
	 * @param list
	 * @return
	 */
	public static String listtoXml(List<?> list) throws Exception {
		Document document = DocumentHelper.createDocument();
		Element nodesElement = document.addElement("nodes");
		int i = 0;
		for (Object o : list) {
			Element nodeElement = nodesElement.addElement("node");
			if (o instanceof Map) {
				for (Object obj : ((Map<?, ?>) o).keySet()) {
					Element keyElement = nodeElement.addElement("key");
					keyElement.addAttribute("label", String.valueOf(obj));
					keyElement
							.setText(String.valueOf(((Map<?, ?>) o).get(obj)));
				}
			} else {
				Element keyElement = nodeElement.addElement("key");
				keyElement.addAttribute("label", String.valueOf(i));
				keyElement.setText(String.valueOf(o));
			}
			i++;
		}
		return doc2String(document);
	}

	/**
	 * json to xml {"node":{"key":{"@label":"key1","#text":"value1"}}} conver
	 * <o><node class="object"><key class="object"
	 * label="key1">value1</key></node></o>
	 * 
	 * @param json
	 * @return
	 */
	public static String jsontoXml(String json) {
		try {
			XMLSerializer serializer = new XMLSerializer();
			JSON jsonObject = JSONSerializer.toJSON(json);
			return serializer.write(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * xml to map xml <node><key label="key1">value1</key><key
	 * label="key2">value2</key>......</node>
	 * 
	 * @param xml
	 * @return
	 */
	public static Map<String, String> xmltoMap(String xml) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			Document document = DocumentHelper.parseText(xml);
			Element nodeElement = document.getRootElement();
			List<?> node = nodeElement.elements();
			for (Iterator<?> it = node.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				map.put(elm.attributeValue("label"), elm.getText());
				elm = null;
			}
			node = null;
			nodeElement = null;
			document = null;
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * xml to list xml <nodes><node><key label="key1">value1</key><key
	 * label="key2">value2</key>......</node><node><key
	 * label="key1">value1</key><key
	 * label="key2">value2</key>......</node></nodes>
	 * 
	 * @param xml
	 * @return
	 */
	public static List xmltoList(String xml) {
		try {
			@SuppressWarnings("rawtypes")
			List<Map> list = new ArrayList<Map>();
			Document document = DocumentHelper.parseText(xml);
			Element nodesElement = document.getRootElement();
			List<?> nodes = nodesElement.elements();
			for (Iterator<?> its = nodes.iterator(); its.hasNext();) {
				Element nodeElement = (Element) its.next();
				Map<?, ?> map = xmltoMap(nodeElement.asXML());
				list.add(map);
				map = null;
			}
			nodes = null;
			nodesElement = null;
			document = null;
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * xml to json <node><key label="key1">value1</key></node> 转化为
	 * {"key":{"@label":"key1","#text":"value1"}}
	 * 
	 * @param xml
	 * @return
	 */
	public static String xmltoJson(String s_xml) {
		XMLSerializer x = new XMLSerializer();
		JSON json = x.read(s_xml);
		return json.toString();
	}

	/**
	 * 
	 * @param document
	 * @return
	 */
	public static String doc2String(Document document) {
		String s = "";
		try {
			// 使用输出流来进行转化
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// 使用UTF-8编码
			OutputFormat format = new OutputFormat("   ", true, "UTF-8");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}

	/**
	 * 生成js数据
	 * 
	 * @param 元数据map
	 * @return js数据
	 */
	@SuppressWarnings("unchecked")
	public static String createJsData(Object obj) {
		if (null == obj) {
			return "\"\"";
		}
		StringBuffer resultStr = new StringBuffer();
		if (obj instanceof Collection) { // 集合List、Set等类型
			resultStr.append("[");
			Collection collection = (Collection) obj;
			if (collection.size() > 0) {
				Object[] collectionObj = collection.toArray();
				for (int i = 0; i < collectionObj.length; i++) {
					resultStr.append(createJsData(collectionObj[i]) + ",");
				}
				resultStr.deleteCharAt(resultStr.lastIndexOf(","));
			}
			resultStr.append("]");
		} else if (obj instanceof Map) { // Map类型
			resultStr.append("{");
			Map map = (Map) obj;
			if (map.size() > 0) {
				Iterator<Entry> iter = map.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = iter.next();
					String key = entry.getKey().toString();
					resultStr.append("\"" + key + "\":"); // jquery1.4以上要求双引号包裹
					Object tempObj = entry.getValue();
					resultStr.append(createJsData(tempObj) + ",");
				}
				resultStr.deleteCharAt(resultStr.lastIndexOf(","));
			}
			resultStr.append("}");
		} else { // 基本类型
			try {
				int arrLength = Array.getLength(obj);
				resultStr.append("[");
				if (arrLength > 0) {
					for (int i = 0; i < arrLength; i++) {
						resultStr.append(createJsData(Array.get(obj, i)) + ",");
					}
					resultStr.deleteCharAt(resultStr.lastIndexOf(","));
				}
				resultStr.append("]");
			} catch (IllegalArgumentException e) { // 不是数组，是最基本的数据
				resultStr.append("\"" + stringToJson(obj + "") + "\"");
			}
		}
		return resultStr.toString();
	}

	/**
	 * 处理特殊字符
	 * 
	 * @param json字符串
	 * @return 处理过的字符串
	 */
	public static String stringToJson(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
