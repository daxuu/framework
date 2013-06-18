package Framework.CodeRobot;

import java.util.UUID;

public class NORobot {
    private static NORobot noRobot = new NORobot();
    /// <summary>
    /// 暫存Hash
    /// </summary>
    //public Hashtable htTemp = new Hashtable();
    private NOInfo code = new NOInfo();
    //private static object lockObj = new object();

    /// <summary>
    /// 構造式
    /// </summary>
    private NORobot()
    {
        //該類生成時就將NOInfo類實例化
        //NOInfo code = new NOInfo();
    }
    /// <summary>
    /// 實例化自已,單例
    /// </summary>
    /// <returns>NORobot本身</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// 所有的Client都只能通過此方法來Instance此類
    /// 目的是為了控制整個系統只有一份NoRobot實例。
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Rayd      2005-10-11 Create
    /// 
    /// </para>
    /// </remarks>
    /// <example>
    ///	
    ///	<code>
    ///		NORobot robot = Instance();
    ///	</code>
    /// </example>
    public static NORobot Instance()
    {
        return noRobot;
    }
    /// <summary>
    /// 產生GUID編碼
    /// </summary>
    /// <returns>GUID碼</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Rayd      2008-07-21 Create
    /// 
    /// </para>
    /// </remarks>
    /// <example>
    ///	
    ///	<code>
    ///		String ID = Framework.CodeRobot.NORobot.Guid();
    ///	</code>
    /// </example>
    public static String Guid()
    {
        //return System.Guid.NewGuid().ToString();
        UUID uuid = UUID.randomUUID();

        return uuid.toString();
    }
    
    /// <summary>
    /// 產生GUID編碼
    /// </summary>
    /// <returns>GUID碼</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Rayd      2008-07-21 Create
    /// 
    /// </para>
    /// </remarks>
    /// <example>
    ///	
    ///	<code>
    ///		String ID = Framework.CodeRobot.NORobot.Guid();
    ///	</code>
    /// </example>
    public static String GetErrorCode(Object obj)
    {
        //String code = obj.GetType().FullName.ToLower() + obj.GetType().Name.ToLower();
        String code = "ErrorCode";

        return code;
    }
    



    /// <summary>
    /// 取得編碼
    /// </summary>
    /// <param name="argRuleName">編碼規則識別名稱，如PROJ_NO</param>
    /// <returns>最新編碼</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// 1.調用同名方法實現
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Rayd      2005-10-11 Create
    /// Rayd      2009-09-07 
    /// </para>
    /// </remarks>
    /// <example>
    ///	
    ///	<code>
    ///		String id = GetCode("user_id");
    ///	</code>
    /// </example>
    public String GetCode(String argRuleName)
    {
        String ret = "";
        
        ret = GetCode(argRuleName, 1);
        return ret;
    }

    /// <summary>
    /// 取得編碼，批量編碼存放在Hashtable中
    /// </summary>
    /// <param name="argRuleName">編碼規則識別名稱，如PROJ_NO</param>
    /// <param name="argQty">批量編碼，一次編好幾個碼</param>
    /// <returns>編碼</returns>
    /// <remarks>
    /// <para>[規格說明]
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Rayd      2005-10-11 Create
    /// 
    /// </para>
    /// </remarks>
    /// <example>
    ///	
    ///	<code>
    ///		String id = GetCode("user_id",10);
    ///	</code>
    /// </example>
    public String GetCode(String argRuleName, int argQty)
    {
        String ret = "";
        ret = code.MakeCode(argRuleName, argQty);

        return ret;
    }

}
