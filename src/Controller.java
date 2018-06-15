import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Controller {

    private Location location;
    private Weather weather;
    private CalculateAngles calculateAngles;

    @FXML
    TextField latitudeTextField;

    @FXML
    TextField longitudeTextField;

    @FXML
    TextField seasonTextField;

    @FXML
    TextField tiltTextField;

    @FXML
    TextField precipitationTextField;

    @FXML
    TextField humidityTExtField;

    @FXML
    Button updateButton;


    @FXML
    Button closeButton;

    @FXML
    Label lastUpdatedLabel;

    //inititalize values and API connections.
    public void initialize(){
        latitudeTextField.setDisable(true);
        longitudeTextField.setDisable(true);
        seasonTextField.setDisable(true);
        tiltTextField.setDisable(true);
        precipitationTextField.setDisable(true);
        humidityTExtField.setDisable(true);

        try {
           location = new Location();
           weather = new Weather(location.getLatitude(), location.getLongitude());
           calculateAngles = new CalculateAngles(location.getLatitude(), weather.getSeason());

           //Initialize fields with initial values.
           latitudeTextField.setText(String.valueOf(location.getLatitude()));

           longitudeTextField.setText(String.valueOf(location.getLongitude()));

           seasonTextField.setText(String.valueOf(weather.getSeason()));

           tiltTextField.setText(String.valueOf(calculateAngles.getFinalAngle()));

           precipitationTextField.setText(String.valueOf(weather.getTodayPrecipitation()));

           humidityTExtField.setText(String.valueOf(weather.getCurrentHumidity()));

           lastUpdatedLabel.setText("Last Updated On: " + weather.getLastUpdated());
       }catch (Exception e){
          if(e instanceof IOException){
              JOptionPane.showMessageDialog(null,"ERROR CONNECTING TO INTERNET", "ERROR CONNECTING",0);
          }
          else if(e instanceof ParserConfigurationException)
              JOptionPane.showMessageDialog(null,"ERROR READING INFORMATION FROM SERVER. \n" +
                      "PARSING ERROR", "ERROR PARSING",0);
          else if (e instanceof SAXException)
              JOptionPane.showMessageDialog(null,((SAXException)e).getMessage(), "ERROR PARSING",0);
          else if (e instanceof NullPointerException)
              JOptionPane.showMessageDialog(null,"ERROR INITIALIZING VARIABLES", "ERROR INITIALIZING",0);
       }

    }
    //updates precipitation and humidity values when update button is pressed.
    @FXML
    public void setUpdateButtonClicked() {
        try {
            weather = new Weather(location.getLatitude(), location.getLongitude());

            precipitationTextField.setText(String.valueOf(weather.getTodayPrecipitation()));

            humidityTExtField.setText(String.valueOf(weather.getCurrentHumidity()));

            lastUpdatedLabel.setText("Last Updated On : " + weather.getCurrentTime());

        }catch (Exception e){
            if(e instanceof IOException){
                JOptionPane.showMessageDialog(null,"ERROR CONNECTING TO INTERNET", "ERROR CONNECTING",0);
            }
            else if(e instanceof ParserConfigurationException)
                JOptionPane.showMessageDialog(null,"ERROR READING INFORMATION FROM SERVER. \n" +
                        "PARSING ERROR", "ERROR PARSING",0);
            else if (e instanceof SAXException)
                JOptionPane.showMessageDialog(null,((SAXException)e).getMessage(), "ERROR PARSING",0);
            else if (e instanceof NullPointerException)
                JOptionPane.showMessageDialog(null,"ERROR INITIALIZING VARIABLES \n" +
                        "CHECK INTERNET CONNECTION.", "ERROR INITIALIZING",0);
        }
    }

    //closes the window
    @FXML
    public void setCloseButtonClicked(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }




}
