package Framework.Helper;

import java.util.HashMap;

import org.dom4j.Node;

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
                //sbOut.Append("\\u" + ich.toString("x4"));
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
}
