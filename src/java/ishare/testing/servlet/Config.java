/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ishare.testing.servlet;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author dadan
 */
public class Config {
    public static Properties getConfig() throws IOException {
        Properties config = new Properties();
        config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
        return config;
    }
}
