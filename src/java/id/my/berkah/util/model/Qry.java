/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.util.model;

/**
 * @author dadan
 */
public class Qry
{

    String tableName, sqlQry,keyFields,dmlType;

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getSqlQry()
    {
        return sqlQry;
    }

    public void setSqlQry(String sqlQry)
    {
        this.sqlQry = sqlQry;
    }

    public String getKeyFields()
    {
        return keyFields;
    }

    public void setKeyFields(String keyFields)
    {
        this.keyFields = keyFields;
    }

    public String getDmlType()
    {
        return dmlType;
    }

    public void setDmlType(String dmlType)
    {
        this.dmlType = dmlType;
    }

    
    
}
