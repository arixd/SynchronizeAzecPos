/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.berkah.util.config;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shatria
 */
public class Config {
    public static Properties getConfig() {
        Properties config = new Properties();
        try {
            config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return config;
    }

   
}
