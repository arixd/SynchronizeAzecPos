/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.util.model;

import java.util.List;

/**
 * @author Ari
 */
public class TabelData
{
    String TableName, FieldName,DmlType;
    String[]KeyFieldsName;
    List<Records> DATA;

    public String getTableName()
    {
        return TableName;
    }

    public void setTableName(String TableName)
    {
        this.TableName = TableName;
    }

    public String getFieldName()
    {
        return FieldName;
    }

    public void setFieldName(String FieldName)
    {
        this.FieldName = FieldName;
    }

    public List<Records> getDATA()
    {
        return DATA;
    }

    public void setDATA(List<Records> DATA)
    {
        this.DATA = DATA;
    }

    public String[] getKeyFieldsName()
    {
        return KeyFieldsName;
    }

    public void setKeyFieldsName(String[] KeyFieldsName)
    {
        this.KeyFieldsName = KeyFieldsName;
    }

    public String getDmlType()
    {
        return DmlType;
    }

    public void setDmlType(String DmlType)
    {
        this.DmlType = DmlType;
    }
    
   
}
