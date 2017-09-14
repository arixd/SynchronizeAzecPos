/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.util.webservice;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ari
 */
public class DataObject
{

    private int data1=100;
   private String data2="hello";

   private List<String> list=new ArrayList<String>(){
       {
           add("String1");
           add("String2");
           add("String3");
       }
   };

   public String toString()
   {    
       return"DataObject[data1=]"+data1+",data2="+data2+",list=";
   }
    public int getData1()
    {
        return data1;
    }

    public void setData1(int data1)
    {
        this.data1 = data1;
    }

    public String getData2()
    {
        return data2;
    }

    public void setData2(String data2)
    {
        this.data2 = data2;
    }

    public List<String> getList()
    {
        return list;
    }

    public void setList(List<String> list)
    {
        this.list = list;
    }
   
   
}
