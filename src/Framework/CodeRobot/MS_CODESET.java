/*
 * copyright_1
 * copyright_2
 * Copyright (c) 2010 Sun , Inc. All Rights Reserved.
 */
package Framework.CodeRobot;

import Framework.Data.DbObject;

/**
 *
 * @author Administrator
 * @date 2010/4/6, 下午 02:05:33
 */
public class MS_CODESET extends DbObject {

    String _TableName = "MS_CODESET";
    String _DatabaseName = "EQ";
    String _DataSourceName = "MS_CODESET";

    public String getTableName() {
        return _TableName;
    }

    public void setTableName(String _TableName) {
        this._TableName = _TableName;
    }

    public String getDataSourceName() {
        return _DataSourceName;
    }

    public void setDataSourceName(String _DataSourceName) {
        this._DataSourceName = _DataSourceName;
    }

    public String getDatabaseName() {
        return _DatabaseName;
    }

    public void setDatabaseName(String _DatabaseName) {
        this._DatabaseName = _DatabaseName;
    }
}
