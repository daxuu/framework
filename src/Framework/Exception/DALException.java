/*
 * copyright_1
 * copyright_2
 * Copyright (c) 2010 Sun , Inc. All Rights Reserved.
 */

package Framework.Exception;

import Framework.logger.MyLogger;

/**
 *
 * @author Administrator
 * @date 2010/4/7, 上午 07:35:50
 */
public class DALException  extends BaseException{

         final String  USER_MSG = "在資料存取時發生異常！。";
        String _sql,_DSN;
        String _userMessage ;
        String _dbName;

        public DALException(String argCode,String argDsn, Exception argEx,String argSQL,String argDbName)
        {

            this.setCode(argCode);
            String userMsg = String.format("%1$s([%2$s]--%3$s)", argEx.getMessage() ,this.getCode(), USER_MSG  );
            this.setUserMessage(userMsg);
            this.setSql(argSQL)  ;
            this.setDSN(argDsn)  ;
            String msg = String.format("\ndb Name = %1$s \nSQL = %2$s \nError Message:%3$s", argDbName,argSQL, argEx.toString() );


            MyLogger.Write(this.getUserMessage(), msg, "Exception", "DALException");
        }
        public DALException(String argCode,String argDsn, Exception argEx,String argSQL)
        {
            //DALException(String argCode,String argDsn, Exception argEx,String argSQL,"default");

        }
    public String getDSN() {
        return _DSN;
    }

    public void setDSN(String _DSN) {
        this._DSN = _DSN;
    }

    public String getSql() {
        return _sql;
    }

    public void setSql(String _sql) {
        this._sql = _sql;
    }

    public String getUserMessage() {
        return _userMessage;
    }

    public void setUserMessage(String _userMessage) {
        this._userMessage = _userMessage;
    }

    public String getDbName() {
        return _dbName;
    }

    public void setDbName(String _dbName) {
        this._dbName = _dbName;
    }


}
