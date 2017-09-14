/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.util.controller;

import id.my.berkah.util.config.GlobalTools;
import id.my.berkah.util.implement.UtilImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Shatria
 */
public class Model
{

    UtilImpl posImpl = new UtilImpl();
    private static int seqNumber = 0;

    public void updatePOSversion(Map map)
    {
//        posImpl.updatePOSversion(map);
        showLog(map, "updatePOSversion");
    }
    public String downloadMainDaily(Map map)
    {
//        posImpl.updatePOSversion(map);
        showLog(map, "downloadMainDaily");
        return posImpl.downloadMainDaily(map);
    }

    public void showLog(Map<String, Object> map, String title)
    {
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            String string = entry.getKey();
            Object object = entry.getValue();
            GlobalTools.printLog(title, string + " : " + object);
        }
        seqNumber++;
        GlobalTools.printLog(title, "---------------------------------");
    }

}
