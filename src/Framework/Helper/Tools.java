package Framework.Helper;

import java.util.HashMap;

import org.dom4j.*;

import Framework.Data.JsonHelper;
import Framework.Xml.XmlDocModel;

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
