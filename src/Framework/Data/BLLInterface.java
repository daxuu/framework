package Framework.Data;

/**
 * 
 * 
 * 项目名称：Framework  
 * 类名称：BLLInterface  
 * 类描述：逻辑层共用类，提供給BLL層作父類，實現通用的增、刪、改、查等基本功能  
 * @author lucky  
 * 创建时间：Jun 13, 2013 9:11:39 AM  
 * 修改人：lucky
 * 修改时间：Jun 13, 2013 9:11:39 AM  
 * 修改备注：  
 * @version  
 *
 */
public class BLLInterface {
    //Manuan.eBridge.Framework.Cache.PageCache cacher = new Manuan.eBridge.Framework.Cache.PageCache();
    private String dataID;
    
    /// <summary>
    /// 页面对象的数据ID
    /// </summary>
    public String getDataID() {
		return dataID;
	}
	public void setDataID(String dataID) {
		this.dataID = dataID;
	}
	

    /// <summary>
    /// 分页xsl文件路径
    /// </summary>
    public String getPosXslPath()
    {
    	return Global.AppDir() + "data\\xslt\\position.xsl";
    }
    
    /// <summary>
    /// 分页xsl文件路径
    /// </summary>
    public String getSortXslPath()
    {
    	return Global.AppDir() + "data\\xslt\\sort.xsl";
    }
    /// <summary>
    /// 分页xsl文件路径
    /// </summary>
    public String getReportXslPath()
    {
         return Global.AppDir() + "data\\xslt\\ReportTemplate.xslt";
    }
    
    /// <summary>
    /// 直接根据条件取资料
    /// </summary>
    /// <returns>JSON資料</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// 
    /// </para>
    /// <para>
    /// Change Log:
    /// Date        Athor      Remark
    /// ----------- ---------- ----------
    /// 2012-12-04  Rayd        create
    /// 
    /// </para>
    /// <example>
    /// <code>
    /// 
    /// </code>
    /// </example>
    /// </remarks>
    public  String GetValue(String s_conds, String s_fields, String s_options, DBTable o_dal)
    {
        String ret = "";
        String fields = "*";
        //XmlDocModel xOptions = new XmlDocModel(s_options);
        //Framework.MyLogger.Log(xOptions.OuterXml);
        //fields
        /*
        if (xOptions.SelectSingleNode("xconfig/fields") != null)
        {
            fields = xOptions.SelectSingleNode("xconfig/fields").InnerXml;
        }
        */
        //remark temp
        //ret = o_dal.QueryJson(s_fields, s_conds);

        return ret;

    }
}
