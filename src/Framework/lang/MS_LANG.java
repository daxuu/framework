package Framework.lang;

import Framework.Data.DBTable;

public class MS_LANG extends DBTable 
{
    String _TableName = "MS_LANG";
    String _DatabaseName = "eq";
    String _DataSourceName = "VIEW_MS_LANG";

    public MS_LANG() {
		super();
		this.setFieldAdder("dataer");
		this.setKeyName("lang_id");
	}

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
