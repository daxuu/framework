/*
 * copyright_1
 * copyright_2
 * Copyright (c) 2010 Sun , Inc. All Rights Reserved.
 */

package Framework.Exception;
import java.lang.*;
import Framework.lang.*;
import Framework.logger.MyLogger;
/**
 *
 * @author Administrator
 * @date 2010/4/6, 上午 08:16:36
 */
public class PFException extends BaseException {
        String  USER_MSG = "系統平臺發生異常！。";
        String _sql;
        String _userMessage ;

        /// <summary>
        ///
        /// </summary>
        /// <param name="argCode"></param>
        /// <param name="argEx"></param>
        /// <param name="argSQL"></param>
        public PFException(String argCode, String argLogicMessage,Exception argEx)
        {
            this.setCode(argCode);
            String msg = String.format("%0$s([%1$s]--%2$s})",  argLogicMessage, argCode, USER_MSG );
            this.setUserMessage(msg) ;
            //this.Sql = argSQL;
            String message = String.format("\nCode = %1$s \nDetail Message:%2#s",  argCode ,argEx.toString());

            MyLogger.Write(this.getUserMessage(), message, "Exception", "PFException");
            //this.Sql = argSql;
        }
        /// <summary>
        ///
        /// </summary>
        /// <param name="argCode"></param>
        /// <param name="argEx"></param>
        /// <param name="argSQL"></param>
        public PFException(String argCode, String[] argMessages)
        {
            this.setCode(argCode);
           String msg = String.format(null,Translator.GetWord(argCode), argMessages);
            String UserMsg = String.format("%1$s([%2$s]--%3$s)", msg, argCode, USER_MSG);
            this.setUserMessage(UserMsg);
        }
}
