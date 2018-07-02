import java.time.LocalDate;
import java.time.LocalTime;

public class Irrigation {

    private LocalTime time;
    private final LocalTime FIVEAM = LocalTime.of(5,0);
    private final LocalTime ELEVENAM = LocalTime.of(11,0);
    private final LocalTime FIVEPM = LocalTime.of(17,0);
    private final LocalTime ELEVENPM = LocalTime.of(23,0);
    private final Double humidityThreshold = 70.0;
    private final Double precipitationThreshold = 10.0;
    private Weather weather;
    private LocalDate todayDate = LocalDate.now();
    private Boolean[] irrigationCompleted = {false,false,false,false};
    private LocalTime nextIrrigationScheduled;



    public Irrigation(Weather currentWeather){
        weather = currentWeather;
    }

    public int getComment(){
        time = LocalTime.now();
        if(time.compareTo(FIVEAM) == -1)
            nextIrrigationScheduled = FIVEAM;
        else if(time.compareTo(ELEVENAM) == -1)
            nextIrrigationScheduled = ELEVENAM;
        else if (time.compareTo(FIVEPM) == -1)
            nextIrrigationScheduled = FIVEPM;
        else if(time.compareTo(ELEVENPM) == -1)
            nextIrrigationScheduled = ELEVENPM;
        else
            nextIrrigationScheduled = time;

        if(weather.getCurrentHumidity() > humidityThreshold || weather.getCurrentHumidity() == humidityThreshold )
            return 1;
        else if(weather.getTodayPrecipitation() > precipitationThreshold || weather.getTodayPrecipitation() == precipitationThreshold)
            return 2;
        else if(nextIrrigationScheduled.equals(FIVEAM) || nextIrrigationScheduled.equals(ELEVENAM) || nextIrrigationScheduled.equals(FIVEPM) || nextIrrigationScheduled.equals(ELEVENPM)){
            if(nextIrrigationScheduled.equals(FIVEAM) && (weather.getTodayPrecipitation() < precipitationThreshold && weather.getCurrentHumidity() < humidityThreshold))
                irrigationCompleted[0] = true;
            if(nextIrrigationScheduled.equals(FIVEPM) && (weather.getTodayPrecipitation() < precipitationThreshold && weather.getCurrentHumidity() < humidityThreshold))
                irrigationCompleted[2] = true;
            if(nextIrrigationScheduled.equals(ELEVENAM) && (weather.getTodayPrecipitation() < precipitationThreshold && weather.getCurrentHumidity() < humidityThreshold))
                irrigationCompleted[1] = true;
            if(nextIrrigationScheduled.equals(ELEVENPM) && (weather.getTodayPrecipitation() < precipitationThreshold && weather.getCurrentHumidity() < humidityThreshold))
                irrigationCompleted[3] = true;
            return 3;
        }
        else
            return 4;
    }

    public LocalTime getTime() {
        return time;
    }

    public Double getHumidityThreshold() {
        return humidityThreshold;
    }

    public Double getPrecipitationThreshold() {
        return precipitationThreshold;
    }

    public String getTodayDate() {
        return todayDate.getMonthValue() + "/" + todayDate.getDayOfMonth() + "/" + todayDate.getYear();
    }

    public Boolean[] getIrrigationCompleted() {
        return irrigationCompleted;
    }

    public LocalTime getNextIrrigationScheduled() {
        return nextIrrigationScheduled;
    }
}
