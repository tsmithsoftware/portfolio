
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Hashtable;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

/**
 * Contains algorithm for distributing flights among captains. Calls Data class 
 * with a file. An object of the Data class would then hold all the Captain/Flight/
 * Airport data. The Data class would then be called to access specific information used to
 * create timetables.
 * 
 * subversive type 69
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Central {

    Data data = new Data();
    ArrayList<String> airports;
    ArrayList<Captain> captains;
    LinkedList<Pair> flights;
    LinkedList<Flight> standbyFlights;

    public Central() {
        flights = new LinkedList<Pair>();
        captains = new ArrayList<Captain>();
        standbyFlights = new LinkedList<Flight>();
    }

      
    private void sort(LinkedList toSort)
    {
        
    }
    /**
     * main program. Will be called with the file
     */
    public void Start() //ts308
    {
        JFrame frame = new JFrame();//for user feedback
        LinkedList<Flight> singleFlights = data.createFlights();
        try {
            flights = createPairings(singleFlights);
       
        ListIterator<Captain> captainIter = captains.listIterator();        
        //Half the main algorithm. While there are still flights to be assigned, for each captain,
        //check if the biggest flight can be assigned. Then, check if the
        //smallest flight can be assigned
        while(flights.size() != 0){
            captainIter.add(new Captain("" + captains.size()));
            Captain currentCap = captainIter.previous();
            int point = flights.size()/2;
            LinkedList<Pair> flightsTBA = new LinkedList<Pair>();
            for(int i = 0; i < point; i++){
                Pair big = flights.poll();
                Pair small = flights.pollLast();
                boolean temp = currentCap.addPair(big);
                if(!temp) flightsTBA.addFirst(big);
                boolean temp2 = currentCap.addPair(small);
                if(!temp2) flightsTBA.addLast(small);
            }
            
            if(flights.size() == 1){
                Pair f = flights.get(0);
                flights.remove(0);
                boolean temp =currentCap.addPair(f);
                if(!temp) flightsTBA.add(f);
                sort(flightsTBA);
                flights = flightsTBA;
            }
            sort(flightsTBA);
            flights = flightsTBA;            
        }

//         //Second half of the main algorithm. Assigns standby captains to flights. Same algorithm as main, but using standbyFlights
        //Second half of the main algorithm. Assigns captains to standby flights, which has already been created. Same algorithm as main, but using standbyFlight. If standby flight cannot be assigned, create a new captain. Flights have flags stating standby 
        //System.out.print("Initial assignment completed. Number of captains: " + captains.size() + "Assigning standby captains, please wait...");
        JOptionPane.showMessageDialog(frame, "Initial assignment completed. Number of captains: " + captains.size() + ". Now assigning standby captains - press OK to continue.");
//         ListIterator<Captain> iter2 = captains.listIterator();//to reset pointer
//      while(standbyFlights.size() != 0){//while there are still standby flights to assign
//        for(int x = 0; x < captains.size(); x++){//for each captain
//            Captain currentCap = null;
//            if(! iter2.hasNext()){
//                currentCap = iter2.previous();//accessing captain after captain addition. There's probably a better way to do it.
//             }
//             else{
//              currentCap = iter2.next();//grab the captain
//         }
//             int point = standbyFlights.size()/2;
//             LinkedList<Flight> flightsTBA2 = new LinkedList<Flight>();
//             for(int i = 0; i < point; i++){//try and assign all standby flights to captain
//                 Flight big = standbyFlights.poll();
//                 Flight small = standbyFlights.pollLast();
//                 boolean temp = currentCap.addFlight(big);
//                 if(!temp) flightsTBA2.addFirst(big);
//                 boolean temp2 = currentCap.addFlight(small);
//                 if(!temp2) flightsTBA2.addLast(small);
//             }
//             
//             if(standbyFlights.size() == 1){
//                 Flight f = standbyFlights.get(0);
//                 standbyFlights.remove(0);
//                 boolean temp = currentCap.addFlight(f);
//                 if(!temp) flightsTBA2.add(f);
//                 sort(flightsTBA2);
//                 standbyFlights = flightsTBA2;
//             }
//             sort(flightsTBA2);
//             standbyFlights = flightsTBA2;            
//         }
//         
//         if(standbyFlights.size() != 0){//if all captains have been tried with standby flights and standby flights still exist, add a captain
//             iter2.add(new Captain("" + captains.size()));
//         }
//     }
//     
//    ListIterator<Captain> iter3 = captains.listIterator();
//    while(iter3.hasNext()){
//        Captain capt = (Captain)iter3.next();
//        if(capt.getPairList().size() == 0){
//            if(capt.getFlightList().size() == 0){
//                iter3.remove();
//             }
//         }
//     }
    for(Captain currentCap:captains){
            int point = standbyFlights.size()/2;
            LinkedList<Flight> flightsTBA2 = new LinkedList<Flight>();
            for(int i = 0; i < point; i++){//try and assign all standby flights to captain
                Flight big = standbyFlights.poll();
                Flight small = standbyFlights.pollLast();
                boolean temp = currentCap.addFlight(big);
                if(!temp) flightsTBA2.addFirst(big);
                boolean temp2 = currentCap.addFlight(small);
                if(!temp2) flightsTBA2.addLast(small);
            }
            
            if(standbyFlights.size() == 1){
                Flight f = standbyFlights.get(0);
                standbyFlights.remove(0);
                boolean temp = currentCap.addFlight(f);
                if(!temp) flightsTBA2.add(f);
                sort(flightsTBA2);
                standbyFlights = flightsTBA2;
            }
            sort(flightsTBA2);
            standbyFlights = flightsTBA2;            
        }
        
        while(standbyFlights.size() != 0){
            captains.add(new Captain("" + captains.size()));
            Captain currentCap = captains.get(captains.size() - 1);//get the last added captain
            int point = standbyFlights.size()/2;
            LinkedList<Flight> flightsTBA2 = new LinkedList<Flight>();
            for(int i = 0; i < point; i++){//try and assign all standby flights to captain
                Flight big = standbyFlights.poll();
                Flight small = standbyFlights.pollLast();
                boolean temp = currentCap.addFlight(big);
                if(!temp) flightsTBA2.addFirst(big);
                boolean temp2 = currentCap.addFlight(small);
                if(!temp2) flightsTBA2.addLast(small);
            }
            
            if(standbyFlights.size() == 1){
                Flight f = standbyFlights.get(0);
                standbyFlights.remove(0);
                boolean temp = currentCap.addFlight(f);
                if(!temp) flightsTBA2.add(f);
                sort(flightsTBA2);
                standbyFlights = flightsTBA2;
            }
            sort(flightsTBA2);
            standbyFlights = flightsTBA2;            
        }

        //for (Captain cap : captains) {
        //    cap.printTable();
        //}
        //System.out.println("\nTotal amount of captains: " + captains.size());
        JOptionPane.showMessageDialog(frame, "\nTotal amount of captains: " + captains.size());
        
         } catch (Exception ex) {
           JOptionPane.showMessageDialog(frame,"There was an error creating flights out of the given file. Please provide another file.");
        }

    }

    private void testFlightComparison() {
        // Flight fl1 = new Flight(
    }

    private LinkedList<Pair> createPairings(LinkedList<Flight> singleFlights) throws Exception {
        //flights are sorted by departing airport
        LinkedList<Pair> returnedPairs = new LinkedList<Pair>();
        Hashtable<String, ArrayList<Flight>> sortedFlights = orderFlights(singleFlights);
        ArrayList<Flight> flightsFromLondon = sortedFlights.get("LGW");
        createStandbyFlights(flightsFromLondon);
        for (Flight flight1 : flightsFromLondon) {
            //In case an exception is thrown, check "orderFlights" method for errors
            //If no errors are found, it means that there is no flight leaving from the destination airport of the current flight.
            //Hence that flight can't be paired up (not likely to happen)
            ArrayList<Flight> possibleReturnFlights = sortedFlights.get(flight1.getDestinationAirport().getCode());
            Flight second = null;
            if (possibleReturnFlights == null) {
                throw new Exception("An error has ocurred or there is a flight with no possible return trip");
            } else {
                long minInterval = Long.MAX_VALUE;
                for (Flight flight2 : possibleReturnFlights) {
                    if (flight2.isAfter(flight1)) {
                        long secondTime = flight2.getScheduledTimeDeparture().getTimeInMillis();
                        long firstTime = flight1.getScheduledTimeDeparture().getTimeInMillis();
                        long interval = secondTime - firstTime;
                        if (interval < minInterval) {
                            minInterval = interval;
                            second = flight2;
                        }
                    }
                }
            }
            Pair newPair = new Pair(flight1, second);
            possibleReturnFlights.remove(second);
            returnedPairs.add(newPair);
        }
        return returnedPairs;
    }

    private Hashtable<String, ArrayList<Flight>> orderFlights(LinkedList<Flight> singleFlights) {
        Hashtable<String, ArrayList<Flight>> sortedFlights = new Hashtable<String, ArrayList<Flight>>();
        for (Flight flight : singleFlights) {
            ArrayList<Flight> oldList = sortedFlights.get(flight.getDepartureAirport().getCode().trim().toUpperCase());
            if (oldList != null) {
                oldList.add(flight);
            } else {
                ArrayList<Flight> newList = new ArrayList<Flight>();
                newList.add(flight);
                sortedFlights.put(flight.getDepartureAirport().getCode().trim().toUpperCase(), newList);
            }
        }
        return sortedFlights;
    }

    private void createStandbyFlights(ArrayList<Flight> flightsFromLondon) {
        for(Flight f : flightsFromLondon){
            GregorianCalendar starts = f.getScheduledTimeDeparture();
            starts.add(GregorianCalendar.HOUR, -3);
            GregorianCalendar ends = f.getScheduledTimeDeparture();
            ends.add(GregorianCalendar.HOUR, 3);

            Flight standby = new Flight(starts, ends, f.getDepartureAirport(), f.getDepartureAirport(), f.getFlightNumber() + " - Standby", true);
            standbyFlights.add(standby);
        }
    }
}
        


