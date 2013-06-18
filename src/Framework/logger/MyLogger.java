/*
 * copyright_1
 * copyright_2
 * Copyright (c) 2010 Sun , Inc. All Rights Reserved.
 */
package Framework.logger;

import Framework.Data.DbHelper;
import Framework.util.Tools;
import Framework.Xml.*;
import java.sql.*;

/**
 *
 * @author Administrator
 * @date 2010/4/6, 下午 02:04:20
 */
public class MyLogger {

    public static void Write(String argMsg, String argDetail, String argKind, String argObj) {
        int ret = 0;
        String id = Framework.CodeRobot.Coder.getCoder().getGUID();
        String sql = String.format("INSERT INTO PM_LOG(log_id,log_kind,log_obj,log_msg,log_content) VALUES('%1$s','%2$s','%3$s','%4$s','%5$s')",
                id,
                    argKind,
                    argObj,
                    Tools.replace(argMsg, "'", "''"),
                    Tools.replace(argDetail, "'", "''")
                );
        //"getdate()"});

        //Database db = Framework.Data.DALHelper.CreateDatabase("LOG");
        //DbCommand dbCommand = db.GetSqlStringCommand(sql);
        Statement st = null;
        ResultSet rs = null;
        Connection conn = null;
        //Database db = CreateDatabase();
        //DbCommand dbCommand = db.GetSqlStringCommand(argSql);
        try {

            conn = DbHelper.createConnection("LOG");

            st = conn.createStatement();
            Integer count = st.executeUpdate(sql);
            System.out.println("Message:" + argMsg + "\nDetail:"+argDetail + "\nKind:"+ argKind+"\nObject:"+argObj);
            //ret = db.ExecuteNonQuery(dbCommand);
        } catch (SQLException ex){//若写Log不成功，则直接中断，不抛出异常，因为若抛出异常会引起死循环
                           System.out.println(sql);

            String xMsg = String.format("<msg>%1$s</msg><sql>%2$s</sql><sys>%3$s</sys>",
                    ex.getMessage(), sql, ex.toString());
            ex.printStackTrace();
            XmlDocModel xErr = new XmlDocModel(xMsg);
            xErr.Save(xErr.getDocument(), "Logs\\"+id);
            
        }catch(Exception e) {
            System.out.println(e.getStackTrace().toString());
        }
        finally {
            if (conn != null) {
                //conn.close();
            }
        }

    }

    /// <summary>
    /// 寫Log檔。
    /// </summary>
    /// <param name="argMsg">簡單信息</param>
    /// <remarks>
    /// <para>[規格說明]
    ///
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Lucky      2005-02-11 Create
    /// </para>
    /// <example>
    ///	<code>
    ///	    String msg="SQL";
    ///	    String message = "SELECT project_id,proj_no,proj_name,proj_nm FROM PM_PROJECT WHERE (project_id LIKE '%')";
    ///	    String kind = "DB";
    ///	    String obj = "PM_PROJECT";
    ///		String mk_openlog = Framework.MyLogger.Write(msg,message,kind,obj);
    ///	</code>
    ///	</example>
    /// </remarks>
    public static void Write(String argMsg, String argMessage) {
        Write(argMsg, argMessage, "", "");
    }
    /// <summary>
    /// 寫Log檔。
    /// </summary>
    /// <param name="argMsg">簡單信息</param>
    /// <remarks>
    /// <para>[規格說明]
    ///
    /// </para>
    /// <para>[異動記錄]
    /// AUTHOR     DATE       NOTE
    /// ========== ========== ========================================
    /// Lucky      2005-02-11 Create
    /// </para>
    /// <example>
    ///	<code>
    ///	    String msg="SQL";
    ///	    String message = "SELECT project_id,proj_no,proj_name,proj_nm FROM PM_PROJECT WHERE (project_id LIKE '%')";
    ///	    String kind = "DB";
    ///	    String obj = "PM_PROJECT";
    ///		String mk_openlog = Framework.MyLogger.Write(msg,message,kind,obj);
    ///	</code>
    ///	</example>
    /// </remarks>

    public static void Write(String argMsg) {
        Write(argMsg, "");
    }
}
