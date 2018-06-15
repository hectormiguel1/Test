import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Weather {

    private final String URL1 = "http://api.apixu.com/v1/current.xml?key=c41f1e1e46454de99cb232712181306&q=";
    private final String URL2 = ",";
    private double latitude;
    private double longitude;
    private double todayPrecipitation;
    private double currentHumidity;
    private String lastUpdated;
    private String currentTime;
    private Season season;

    public Weather(double latitude, double longitude){
        this.latitude = Double.parseDouble(new DecimalFormat("##.####").format(latitude));
        this.longitude = Double.parseDouble(new DecimalFormat("##.####").format(longitude));
        currentWeather();
        calculateSeason();
    }


    public double getTodayPrecipitation() {
        return todayPrecipitation;
    }

    public void currentWeather() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document document = dbBuilder.parse(new URL(URL1 + latitude + URL2 + longitude).openStream());

            document.getDocumentElement().normalize();

            todayPrecipitation = Double.parseDouble(document.getElementsByTagName("precip_in").item(0)
                    .getChildNodes().item(0).getNodeValue());
            currentHumidity = Double.parseDouble(document.getElementsByTagName("humidity").item(0)
                    .getChildNodes().item(0).getNodeValue());
            lastUpdated = document.getElementsByTagName("current").item(0).
                    getChildNodes().item(1).getChildNodes().item(0).getNodeValue();
            currentTime = document.getElementsByTagName("location").item(0).getChildNodes().
                    item(7).getChildNodes().item(0).getNodeValue();

        } catch (ParserConfigurationException | SAXException | IOException e) {
        }

    }

    public double getCurrentHumidity() {
        return currentHumidity;
    }

    private void calculateSeason(){
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int month = localDate.getMonthValue() - 1;

        if (month == Months.DECEMBER.ordinal() || month == Months.JANUARY.ordinal() || month == Months.FEBRUARY.ordinal()) {
            season = Season.WINTER;
        } else if (month == Months.MARCH.ordinal() || month == Months.APRIL.ordinal() || month == Months.MAY.ordinal()) {
            season = Season.SPRING;
        } else if (month == Months.JUNE.ordinal() || month == Months.JULY.ordinal() || month == Months.AUGUST.ordinal()) {
            season = Season.SUMMER;
        }
        else{
            season = Season.FALL;
        }
    }

    public Season getSeason() {
        return season;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getCurrentTime() {
        return currentTime;
    }
}
