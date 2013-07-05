/*
 * copyright_1
 * copyright_2
 * Copyright (c) 2010 Sun , Inc. All Rights Reserved.
 */
package Framework.lang;

import java.util.*;

import org.dom4j.Element;

import Framework.Helper.Tools;
import Framework.Xml.XmlDocModel;

/**
 *
 * @author Administrator
 * @date 2010/4/6, 上午 11:19:30
 */
public class Translator {
	private static String currLang = "zh_tw";

    ///code:结果,0--成功；其他为不成功；
    ///msg:简易信息提示，对使用者的；
    ///message:详细信息，处理方式或进一步的解释
    private static String retMessage = "\"code\":\"{0}\",\"msg\":\"{1}\",\"message\":\"{2}\"";
    
    public static String getCurrLang() {
		return currLang;
	}

	public static void setCurrLang(String currLang) {
		Translator.currLang = currLang;
	}

	public static String GetWord(String argCode) {
        String ret = argCode;
        String lang_c = "zh";
        String lang_a = "TW";
        //Locale locale = Locale.getDefault();
        Locale locale = new Locale(lang_c,lang_a);

        ResourceBundle localResource = ResourceBundle.getBundle("Message", locale);

        ret = localResource.getString(argCode);
        //System.out.println("ResourceBundle: " + ret);
        return ret;

    }
    
    /// <summary>
    /// 取得用户提示信息
    /// </summary>
    /// <param name="s_code">信息代码</param>
    /// <param name="sa_messages">信息参数</param>
    /// <param name="s_techmsg">技术参考消息</param>
    /// <param name="b_isreturn">是否需要封装成返回信息</param>
    /// <returns>用户信息</returns>
    public static String GetUserMessage(String s_code,String[] sa_messages,String s_techmsg,boolean b_isreturn)
    {
        String msg = s_code;
        String sql = "SELECT lang_name FROM ms_lang WHERE lang_kind='%1$s' AND lang_no='%2$s'";
        MS_LANG dal = new MS_LANG();

        sql = String.format(sql, new String[] { getCurrLang(),s_code});
        XmlDocModel doc = dal.QueryXml(sql);
        Element xLang = doc.getDocument().getRootElement();
        
        if (xLang.selectSingleNode("xconfig/records/record/LANG_NAME") != null)
        {
            msg = xLang.selectSingleNode("xconfig/records/record/LANG_NAME").getText();
        }
        //有动态值数组传入，信息提示中有{0}
        if (sa_messages.length>0 && msg.indexOf("%"+Integer.toString((sa_messages.length - 1))+"$s") > -1)
        {
            msg = String.format(msg, sa_messages);
        }
        //封装成返回信息
        if (b_isreturn)
        {
            //格式化信息
            msg = String.format(retMessage, new String[] { 
                s_code,
                Tools.SafeJSON(msg),
                s_techmsg==null?"":Tools.SafeJSON(s_techmsg)
            });
            //String.format 不支持"{""}"
            msg = "{" + msg + "}";
        }

        return msg;
    }
    /// <summary>
    /// 取得用户提示信息
    /// </summary>
    /// <param name="s_code">信息代码</param>
    /// <param name="sa_messages">信息参数</param>
    /// <param name="b_isreturn">是否需要封装成返回信息</param>
    /// <returns>用户信息</returns>
    public static String GetUserMessage(String s_code, String[] sa_messages, boolean b_isreturn)
    {
        return GetUserMessage(s_code,sa_messages,"",b_isreturn);
    }
    /// <summary>
    /// 取得用户提示信息
    /// </summary>
    /// <param name="s_code">信息代码</param>
    /// <param name="sa_messages">信息参数</param>
    /// <returns>用户信息</returns>
    public static String GetUserMessage(String s_code, String[] sa_messages)
    {
        return GetUserMessage(s_code, sa_messages, "", false);
    }
}
