
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Locale;

/**
 * Reads in timetable and creates objects of other classes. Creates flights 
 * (airports and planes) and holds Captain data.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Data {

    ArrayList<Captain> allCaptains;
    LinkedList<Flight> flights;
    GregorianCalendar earliestFlight;

    /**
     * Constructor for objects of class Data. Should take a File object
     * and read off, but atm, data will be fed by hand. Will *definately* be
     * reworked.
     */
    public Data() {
        flights = new LinkedList<Flight>();
        allCaptains = new ArrayList<Captain>();
    }

    /**
     * Main program of class. Creates flights from data. Again, data is currently 
     * being fed in by hand.Extremely bad.
     */
    public LinkedList<Flight> createFlights() {
        //We have to think of a predefined path or ask the user to enter its location
        String input = readFile(GUI.getFilePath());
        String[] separatedInput = input.split("\n");
        try {
            for (int i = 1; i < separatedInput.length; i += 2) {
                String[] data = separatedInput[i].split(",");

                //Date
                SimpleDateFormat fullDateFormat = new SimpleDateFormat("ddMMMyy HH:mm", Locale.ENGLISH);
                String rawDate = data[0].trim().substring(1, 8);

                String rawSheduledTimeDeparture = data[8].trim().substring(1, 6);
                String rawSheduledTimeArrival = data[10].trim().substring(1, 6);

                //Join the date with the different times
                String scheduledDateTimeDeparture = rawDate + " " + rawSheduledTimeDeparture;
                String scheduledDateTimeArrival = rawDate + " " + rawSheduledTimeArrival;

                //Create the GregorianCalenders that the flight saves
                GregorianCalendar scheduledDeparture = new GregorianCalendar();
                GregorianCalendar scheduledArrival = new GregorianCalendar();

                //Initiate those GregorianCalendars with the date and time
                scheduledDeparture.setTime(fullDateFormat.parse(scheduledDateTimeDeparture));
                scheduledArrival.setTime(fullDateFormat.parse(scheduledDateTimeArrival));

                if (scheduledDeparture.before(earliestFlight)) {
                    earliestFlight = scheduledDeparture;
                }

                //To take out the ""
                String depAirport = data[6].trim().substring(1, data[6].length()-1);
                String arrAirport = data[7].trim().substring(1, data[7].length()-1);                
                String code = data[1].trim().substring(1, data[1].length()-1);

                Flight flight = new Flight(scheduledDeparture, scheduledArrival, new Airport(depAirport), new Airport(arrAirport), code, false);
                flights.add(flight);
            }
            ArrayList<Flight> extraFlights = new ArrayList<Flight>();
            for (Flight f1 : flights) {
                Flight f2 = f1.cloneLater(1);
                Flight f3 = f1.cloneLater(2);
                Flight f4 = f1.cloneLater(3);
                extraFlights.add(f2);
                extraFlights.add(f3);
                extraFlights.add(f4);
            }
            for (Flight flight : extraFlights) {
                flights.add(flight);
            }
            //Collections.sort(flights);
            return flights;
            
        } catch (Exception ex) {
            //If an exception occured the method will return null
            return null;
        }
    }

    public String readFile(String path) {
        File file = new File(path);
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            // repeat until all lines is read
            while ((text = reader.readLine()) != null) {
                contents.append(text).append(System.getProperty("line.separator"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return contents.toString();
        }
    }
}
