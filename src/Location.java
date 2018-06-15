import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DecimalFormat;

public class Location {
    private double latitude;
    private double longitude;
    private final String ERROR = "ERROR";
    private final String  URL2 = "?access_key=2e0af9d296f610271d93c0e223467bec&format=1&fields=latitude,longitude&output=xml";
    private final String  URL1 = "http://api.ipstack.com/";
    private final String IPURL = "https://api.ipify.org";

    private String ipAddr;


    public Location() throws IOException, ParserConfigurationException, SAXException {
     ipAddr = getCurrentIP();
     if(!ipAddr.equals(ERROR)){
        calculateLatitude();
     }
    }

    private String getCurrentIP () throws IOException{
        java.util.Scanner s = new java.util.Scanner(new java.net.URL(IPURL).openStream(),
                "UTF-8").useDelimiter("\\A");
          return s.next();

    }

    private void calculateLatitude() throws IOException, SAXException, ParserConfigurationException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        Document document = dbBuilder.parse(new URL(URL1 + ipAddr + URL2).openStream());

        document.getDocumentElement().normalize();

        NodeList nodes = document.getElementsByTagName("result").item(0).getChildNodes();


        latitude = Double.parseDouble(nodes.item(0).getChildNodes().item(0).getNodeValue());
        longitude = Double.parseDouble(nodes.item(1).getChildNodes().item(0).getNodeValue());


    }

    public double getLatitude() {
        return Double.parseDouble(new DecimalFormat("##.####").format(latitude));
    }

    public double getLongitude() {
        return Double.parseDouble(new DecimalFormat("##.####").format(longitude));
    }
}
