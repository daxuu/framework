package Framework.Data;

import java.io.File;  
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;  
import java.io.FileInputStream;  
import java.util.*;

import javax.xml.soap.Node;

import org.dom4j.Document;
import org.dom4j.Element;

import Framework.Xml.XmlDocModel;


/**
 * 
 * 项目名称：Framework  
 * 类名称：Global  
 * 类描述：  
 * @author lucky  
 * 创建时间：Jun 11, 2013 8:19:40 AM  
 * 修改人：lucky
 * 修改时间：Jun 11, 2013 8:19:40 AM  
 * 修改备注：  
 * @version  
 *
 */
public class Global {

	private String appDir="";
	private String accountId="";
	private String accountName="";
	
	/**
	 * 
	 * 功能概述：  
	 * @param argKey config查找的Key值
	 * @return
	 * @author lucky  
	 * 创建时间：Jun 11, 2013 8:20:15 AM  
	 * 修改人：lucky
	 * 修改时间：Jun 11, 2013 8:20:15 AM  
	 * 修改备注：  
	 * @version  
	 *
	 */
    public static String AppConfig(String argKey)
    {
        String ret = null;
    	XmlDocModel xConfig = new XmlDocModel();
		//xConfig.load("/media/lucky/DATA/Apps/ebridge/Workspace-Man/Framework/src/Framework/Data/app.cfg.xml");
		xConfig.load(getAppDir()+"/data/config/app.cfg.xml");
		Document doc = xConfig.getDocument();
		//Element root = doc.getRootElement();
		
		org.dom4j.Node nd = doc.selectSingleNode("/appconfig/"+argKey);
		if(nd!=null){
			ret = nd.getText();
		}
    		
         return ret;
    }
    
    /*
     * 此方法用properties类，不过没有发现比直接用XML文件来用更好，暂时不用
    public static String AppConfig(String argKey)
    {
        String ret = null;
    	//Properties properties = new Properties();
    	XmlDocModel xConfigDocModel = new XmlDocModel();
    	try{
            File f = new File("/media/lucky/DATA/Apps/ebridge/Workspace-Man/Framework/src/Framework/Data/app.cfg.xml");  
            InputStream in = new FileInputStream(f);  
            byte b[]=new byte[(int)f.length()];     //创建合适文件大小的数组  
            in.read(b);    //读取文件中的内容到b[]数组  
        	properties.loadFromXML(in);
        	in.close();
        	
        	ret = properties.getProperty(argKey);
        	
    	}catch(FileNotFoundException e1){
    		e1.printStackTrace();
    		
    	}catch (InvalidPropertiesFormatException e2) {
    		e2.printStackTrace();
			// TODO: handle exception
		}catch (IOException e3) {
			// TODO: handle exception
			e3.printStackTrace();
		}
         return ret;
    }
	*/    
    
    /**
	 * @return 系统运行路径
	 */
	public static String getAppDir() {
		return System.getProperty("user.dir");
	}

	/**
	 * 
	 * 功能概述：  
	 * @return 返回系统运行路径
	 * @author lucky  
	 * 创建时间：Jun 11, 2013 11:18:00 AM  
	 * 修改人：lucky
	 * 修改时间：Jun 11, 2013 11:18:00 AM  
	 * 修改备注：  
	 * @version  
	 *
	 */
	public static String AppDir()
    {
    	//this.getClass().getResource("")
        return getAppDir();
    }

	/**
	 * @return 当前帐号ID
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @return 当前帐号名称
	 */
	public String getAccountName() {
		return accountName;
	}

 
	
}
