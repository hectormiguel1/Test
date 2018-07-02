import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Controller {

    private Location location;
    private Weather weather;
    private Irrigation irrigation;

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

    @FXML
    Button currentConditionsButton;

    @FXML
    Button irrigationButton;

    @FXML
    AnchorPane currentConditionPane;

    @FXML
    AnchorPane irrigationPane;

    @FXML
    StackPane parentPane;

    @FXML
    CheckBox fivePmCheckBox;

    @FXML
    CheckBox elevenAmCheckBox;

    @FXML
    CheckBox fiveAmCheckBox;

    @FXML
    CheckBox elevenPmCheckBox;

    @FXML
    Label elevenPmLabel;

    @FXML
    Label elevenAmLabel;

    @FXML
    Label fiveAmLabel;

    @FXML
    Label fivePmLabel;

    @FXML
    Label precipitationLabel;

    @FXML
    Label precipitationThresholdLabel;

    @FXML
    Label currentHumidityLabel;

    @FXML
    Label humidityThresholdLabel;

    @FXML
    Label commentLabel;



    //inititalize values and API connections.
    public void initialize(){
        currentConditionPane.toFront();
        currentConditionsButton.underlineProperty().setValue(true);
        irrigationPane.setOpacity(0);
        latitudeTextField.setDisable(true);
        longitudeTextField.setDisable(true);
        seasonTextField.setDisable(true);
        tiltTextField.setDisable(true);
        precipitationTextField.setDisable(true);
        humidityTExtField.setDisable(true);
        fiveAmCheckBox.setDisable(true);
        fivePmCheckBox.setDisable(true);
        elevenAmCheckBox.setDisable(true);
        elevenPmCheckBox.setDisable(true);

        try {
           location = new Location();
           weather = new Weather(location.getLatitude(), location.getLongitude());
            CalculateAngles calculateAngles = new CalculateAngles(location.getLatitude(), weather.getSeason());
            irrigation = new Irrigation(weather);

           //Initialize fields with initial values.
           latitudeTextField.setText(String.valueOf(location.getLatitude()));

           longitudeTextField.setText(String.valueOf(location.getLongitude()));

           seasonTextField.setText(String.valueOf(weather.getSeason()));

           tiltTextField.setText(String.valueOf(calculateAngles.getFinalAngle()));

           precipitationTextField.setText(String.valueOf(weather.getTodayPrecipitation()));

           humidityTExtField.setText(String.valueOf(weather.getCurrentHumidity()));

           lastUpdatedLabel.setText("Last Updated On: " + weather.getLastUpdated());

           precipitationThresholdLabel.setText(irrigation.getPrecipitationThreshold().toString() + " inch");
           humidityThresholdLabel.setText(irrigation.getHumidityThreshold().toString() + " %");
           currentHumidityLabel.setText(weather.getCurrentHumidity() + " %");
           precipitationLabel.setText(weather.getTodayPrecipitation() + " inch");
           fiveAmLabel.setText(irrigation.getTodayDate());
           fivePmLabel.setText(irrigation.getTodayDate());
           elevenAmLabel.setText(irrigation.getTodayDate());
           elevenPmLabel.setText(irrigation.getTodayDate());
           fiveAmCheckBox.setSelected(irrigation.getIrrigationCompleted()[0]);
           fivePmCheckBox.setSelected(irrigation.getIrrigationCompleted()[2]);
           elevenAmCheckBox.setSelected(irrigation.getIrrigationCompleted()[1]);
           elevenPmCheckBox.setSelected(irrigation.getIrrigationCompleted()[3]);
           switch(irrigation.getComment()){
               case 1:{
                   commentLabel.setText("Skipping irrigation for" + irrigation.getNextIrrigationScheduled().toString() + " due to high humidity!");
                   commentLabel.setTextFill(Color.RED);
                   break;
               }
               case 2: {
                   commentLabel.setText("Skipping irrigation for the rest of the day because precipitation already exceeds precipitation threshold!");
                   commentLabel.setTextFill(Color.YELLOW);
                   break;
               }
               case 3:{
                   commentLabel.setText("Irrigating for " + irrigation.getNextIrrigationScheduled().toString());
                   commentLabel.setTextFill(Color.GREEN);
                   break;
               }
               default:{
                   commentLabel.setText("Irrigation will continue as scheduled for " + irrigation.getNextIrrigationScheduled().toString());
                   commentLabel.setTextFill(Color.GREEN);
                   break;
               }
           }

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
            location = new Location();
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

    //changes which pane is bing displayed to irrigation pane

    @FXML
    public void setIrrigationButtonClicked(){
        irrigationPane.toFront();
        irrigationPane.setOpacity(1);
        irrigationButton.underlineProperty().setValue(true);
        currentConditionsButton.underlineProperty().setValue(false);
        fiveAmCheckBox.setSelected(irrigation.getIrrigationCompleted()[0]);
        fivePmCheckBox.setSelected(irrigation.getIrrigationCompleted()[2]);
        elevenAmCheckBox.setSelected(irrigation.getIrrigationCompleted()[1]);
        elevenPmCheckBox.setSelected(irrigation.getIrrigationCompleted()[3]);
        switch(irrigation.getComment()){
            case 1:{
                commentLabel.setText("Skipping irrigation for " + irrigation.getNextIrrigationScheduled().toString() + " due to high humidity!");
                commentLabel.setTextFill(Color.RED);
                break;
            }
            case 2: {
                commentLabel.setText("Skipping irrigation for the rest of the day because precipitation already exceeds precipitation threshold!");
                commentLabel.setTextFill(Color.YELLOW);
                break;
            }
            case 3:{
                commentLabel.setText("Irrigating for " + irrigation.getNextIrrigationScheduled().toString());
                commentLabel.setTextFill(Color.GREEN);
                break;
            }
            default:{
                commentLabel.setText("Irrigation will continue as scheduled for " + irrigation.getNextIrrigationScheduled().toString());
                commentLabel.setTextFill(Color.GREEN);
                break;
            }
        }

    }

    @FXML
    public void setCurrentConditionsButtonClicked(){
        irrigationPane.setOpacity(0);
        currentConditionPane.toFront();
        irrigationButton.underlineProperty().setValue(false);
        currentConditionsButton.underlineProperty().setValue(true);
    }

}
