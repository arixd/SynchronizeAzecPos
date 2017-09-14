package ishare.testing.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import oracle.jdbc.OracleConnection;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GetConfig {

    String strUserName = null;
    String strPwd = null;
    String strHost = null;
    String strSID = null;
    String strPort = null;
    String strCStr = null;
    String strTempDir = null;

    public void getAccess(){
        try {
            Properties properties = Config.getConfig();
            this.strUserName = properties.getProperty("strUserName");
            this.strHost = properties.getProperty("strHost");
            this.strSID = properties.getProperty("strSID");
            this.strPort = properties.getProperty("strPort");
            this.strPwd = properties.getProperty("strPwd");
            this.strTempDir = properties.getProperty("strTempDir");
            this.strCStr = properties.getProperty("strCStr");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

//    public void GetAccess() {
//        int startIndex = 0;
//        int pageSize = 0;
//        try {
//            String strPath = new File(".").getCanonicalPath();
//
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document document = builder.parse(new File(new StringBuilder().append(strPath).append("/erafone.xml").toString()));
//            NodeList nodeList = document.getElementsByTagName("ConnectString");
//            int totalRecs = nodeList.getLength();
//
//            if (totalRecs < 1) {
//                System.out.println("no record on XML file.");
//            }
//
//            if (startIndex < 0) {
//                startIndex = 0;
//            }
//
//            int stopIndex = startIndex + pageSize;
//
//            if (stopIndex > totalRecs - 1) {
//                stopIndex = totalRecs - 1;
//            }
//
//            for (int i = 0; i < nodeList.item(0).getChildNodes().getLength(); i++) {
//                Node node = nodeList.item(0).getChildNodes().item(i);
//                if (node.getNodeType() != 1) {
//                    continue;
//                }
//                if (node.getNodeName().equals("USER")) {
//                    this.strUserName = getNodeValue(node);
//                } else if (node.getNodeName().equals("HOST")) {
//                    this.strHost = getNodeValue(node);
//                } else if (node.getNodeName().equals("SID")) {
//                    this.strSID = getNodeValue(node);
//                } else if (node.getNodeName().equals("PORT")) {
//                    this.strPort = getNodeValue(node);
//                } else if (node.getNodeName().equals("PWD")) {
//                    this.strPwd = getNodeValue(node);
//                } else if (node.getNodeName().equals("TEMPDIR")) {
//                    this.strTempDir = getNodeValue(node);
//                } else {
//                    if (!node.getNodeName().equals("CSTR")) {
//                        continue;
//                    }
//                    this.strCStr = getNodeValue(node);
//                }
//            }
//
//        } catch (Exception ex) {
//            System.out.print("...\t  [ERROR] : ERAFONEDownloadDml\t");
//            ex.printStackTrace();
//        }
//        this.strUserName = "192.168.3.33";
//        this.strHost = "APPS";
//        this.strSID = "11GAPPS";
//        this.strPort = "2016";
//        this.strPwd = "11GAPPS";
//        this.strTempDir = "";
//        this.strCStr="8";
//    }
//    private String getNodeValue(Node node) {
//        StringBuilder buf = new StringBuilder();
//        NodeList children = node.getChildNodes();
//        for (int i = 0; i < children.getLength(); i++) {
//            Node textChild = children.item(i);
//            if (textChild.getNodeType() != 3) {
//                System.err.println(new StringBuilder().append("Mixed content! Skipping child element ").append(textChild.getNodeName()).toString());
//            } else {
//                buf.append(textChild.getNodeValue());
//            }
//        }
//        return buf.toString();
//    }
    public String getUSER() {
        return this.strUserName;
    }

    public String getPASSWORD() {
        return this.strPwd;
    }

    public String getHOST() {
        return this.strHost;
    }

    public String getSID() {
        return this.strSID;
    }

    public String getPORT() {
        return this.strPort;
    }

    public String getCStr() {
        return this.strCStr;
    }

    public String getTempDir() {
        return this.strTempDir;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    public void setStrPwd(String strPwd) {
        this.strPwd = strPwd;
    }

    public void setStrHost(String strHost) {
        this.strHost = strHost;
    }

    public void setStrSID(String strSID) {
        this.strSID = strSID;
    }

    public void setStrPort(String strPort) {
        this.strPort = strPort;
    }

    public void setStrCStr(String strCStr) {
        this.strCStr = strCStr;
    }

    public void setStrTempDir(String strTempDir) {
        this.strTempDir = strTempDir;
    }
    
}
