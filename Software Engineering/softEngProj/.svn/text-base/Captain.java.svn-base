import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

public class Captain {

    private String id;
    protected ArrayList<Pair> pairList;
    protected ArrayList<Flight> flightList;

    /**
     * Constructor for objects of class Captain
     */
    public Captain() {
        flightList = new ArrayList<Flight>();
        pairList = new ArrayList<Pair>();
        id = "New - " + GregorianCalendar.getInstance().toString();
    }

    public Captain(String id) {
        flightList = new ArrayList<Flight>();
        pairList = new ArrayList<Pair>();
        this.id = id;
    }

    /**
     * Adds flights to captain, for standby
     */
    public boolean addFlight(Flight flight)
    {
        getFlightList().add(flight);
        sortLists();
        if (doAllChecks()) {
            return true;
        } else {
            getFlightList().remove(flight);
            sortLists();
            return false;
        }
    }
    
    /**
     * Adds a flight to this captains timetable and then sorts the list
     * checks if flight falls within any flights already there
     * @return boolean true if successful, false otherwise
     */
    public boolean addPair(Pair chkFlight) {
        chkFlight.setCaptain(this);
        getPairList().add(chkFlight);
        getFlightList().add(chkFlight.flightOut);
        getFlightList().add(chkFlight.flightBack);
        sortLists();
        if (doAllChecks()) {
            return true;
        } else {
            getPairList().remove(chkFlight);
            chkFlight.setCaptain(null);
            getFlightList().remove(chkFlight.flightBack);
            getFlightList().remove(chkFlight.flightOut);
            sortLists();
            return false;
        }
    }

    /**
     * Sorts flightlist by date and time
     */
    public void sortLists() {
        Collections.sort(getPairList());
        Collections.sort(getFlightList());
    }

    /**
     * Removes a specific flight from this captains timetable
     */
    public void removeFlight(Pair flight) {
        Iterator it = getPairList().iterator();
        while(it.hasNext()){
            Pair chkFlight = (Pair)it.next();
            if(chkFlight.equals(flight)){
                it.remove();
                getFlightList().remove(flight.flightBack);
                getFlightList().remove(flight.flightOut);
            }
        }
            
//         for (Pair chkFlight : getPairList()) {
//             if (chkFlight.equals(flight)) {
//                 getPairList().remove(chkFlight);
//             }
//         }
        sortLists();
    
    }
    
    /**
     * Removes a specific single flight from this captains timetable
     * @returns false if there is a problem or the flight is half of a pair
     */
    public boolean removeSingleFlight(Flight flight)
    {
        if(pairList.contains(flight)){
            return false;
        }
        else{
            getFlightList().remove(flight);
            }
        sortLists();
        return true;
   }
        
    public String getId() {
        return id;
    }

    public void printTable() {
        System.out.print("Pilot Name: " + getId() + " \nNumber of pairs: " + pairList.size() + "\nFlights: \n");
        for (Pair flight : getPairList()) {
            flight.print();
            System.out.print("\n\n");
        }
        System.out.print("\n");
    }
    
    public String returnOutTable()
    {
        String out = "";
        for (Pair flight : getPairList()) 
        {
            out = out+flight.outPrint();
        }
        return out;
    }
    
    public String returnBackTable()
    {
        String back = "";
        for (Pair flight : getPairList()) 
        {
            back = flight.backPrint();
        }
        return back;
    }
    
    public String returnFlightTable()
    {
        String flights = "";
        for (Flight flight:flightList)
        {
            flights = flight.toString();
        }
        return flights;
    }
    /**
     * Check that the captain is not doing >3 consecutive early/late flights
     */
    public boolean checkConsecutiveEarlyLates() {
        //This is assuming that the flights are ordered by date.
        for (int i = 0; i < (getFlightList().size() - 3); i++) {
            Flight f1 = getFlightList().get(i);
            Flight f2 = getFlightList().get(i + 1);
            Flight f3 = getFlightList().get(i + 2);
            Flight f4 = getFlightList().get(i + 3);
            if (f1.isEarlyFlight() && f2.isEarlyFlight() && f3.isEarlyFlight() && f4.isEarlyFlight()) {
                return false;
            }
            if (f1.isLateFlight() && f2.isLateFlight() && f3.isLateFlight() && f4.isLateFlight()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check the captain is not doing >4 early/late duties in a 7 day period
     */
    public boolean checkEarlyLateDuties() {
        for (Flight flight1 : getFlightList()) {
            ArrayList<Flight> earlyFlights = new ArrayList<Flight>();
            ArrayList<Flight> lateFlights = new ArrayList<Flight>();
            GregorianCalendar aWeekAfter = (GregorianCalendar) flight1.getDutyStart().clone();
            aWeekAfter.add(GregorianCalendar.DAY_OF_YEAR, 7);
            if (flight1.isEarlyFlight()) {
                earlyFlights.add(flight1);
            }
            if (flight1.isLateFlight()) {
                lateFlights.add(flight1);
            }
            for (Flight flight2 : getFlightList()) {
                if (!flight1.equals(flight2)) {
                    if ((flight2.getDutyStart().after(flight1.getDutyStart())) && (flight2.getDutyStart().before(aWeekAfter))) {
                        if (flight2.isEarlyFlight()) {
                            earlyFlights.add(flight2);
                        }
                        if (flight2.isLateFlight()) {
                            lateFlights.add(flight2);
                        }
                    }
                }
            }
            if (earlyFlights.size() > 4) {
                return false;
            }
            if (lateFlights.size() > 4) {
                return false;
            }
        }
        return true;
    }

    private boolean existsDayOffBetweenFlights(Flight first, Flight second) {
        int time = Math.abs((int) (second.getDutyStart().getTimeInMillis() - first.getDutyEnd().getTimeInMillis()));
        int hours = time / (1000 * 60 * 60);
        //checks that the previous flight was 34 hours before minimum
        if (hours < 34) {
            return false;
        }

        //Get the time 23:59:59, 2 days before the date of duty start
        //Then check that the last duty ends before that
        GregorianCalendar auxTime = (GregorianCalendar) second.getLocalDutyStart();
        auxTime.add(GregorianCalendar.DAY_OF_YEAR, -2);
        auxTime.set(GregorianCalendar.HOUR_OF_DAY, 23);
        auxTime.set(GregorianCalendar.MINUTE, 59);
        auxTime.set(GregorianCalendar.SECOND, 59);

        if (auxTime.before(first.getLocalDutyEnd())) {
            return false;
        }
        auxTime = (GregorianCalendar) first.getLocalDutyEnd();
        auxTime.add(GregorianCalendar.DAY_OF_YEAR, 2);
        auxTime.set(GregorianCalendar.HOUR_OF_DAY, 6);
        auxTime.set(GregorianCalendar.MINUTE, 0);
        auxTime.set(GregorianCalendar.SECOND, 0);

        //After a day off, captain can't start before 6 a.m
        if (second.getLocalDutyStart().before(auxTime)) {
            return false;
        }

        //If no condition is violated, the method returns true
        return true;
    }

    private boolean existsXDaysOffBetweenFlights(Flight first, Flight second, int x) {
        int time = Math.abs((int) (second.getDutyStart().getTimeInMillis() - first.getDutyEnd().getTimeInMillis()));
        int hours = time / (1000 * 60 * 60);

        int neededFreeTime = x*8;
        neededFreeTime+=34;
        //checks that the previous flight was 34 hours before minimum
        if (hours < neededFreeTime) {
            return false;
        } 

        //Get the time 23:59:59, 2 days before the date of duty start
        //Then check that the last duty ends before that
        GregorianCalendar auxTime = (GregorianCalendar) second.getLocalDutyStart();
        auxTime.add(GregorianCalendar.DAY_OF_YEAR, -(x+1));
        auxTime.set(GregorianCalendar.HOUR_OF_DAY, 23);
        auxTime.set(GregorianCalendar.MINUTE, 59);
        auxTime.set(GregorianCalendar.SECOND, 59);

        if (auxTime.before(first.getLocalDutyEnd())) {
            return false;
        }

        auxTime = (GregorianCalendar) first.getLocalDutyEnd();
        auxTime.add(GregorianCalendar.DAY_OF_YEAR, 2);
        auxTime.set(GregorianCalendar.HOUR_OF_DAY, 6);
        auxTime.set(GregorianCalendar.MINUTE, 0);
        auxTime.set(GregorianCalendar.SECOND, 0);

        //After a day off, captain can't start before 6 a.m
        if (second.getLocalDutyStart().before(auxTime)) {
            return false;
        }
        //If no condition is violated, the method returns true
        return true;
    }

    /**
     * Check the captain has a holiday before 2 earlies
     */
    public boolean checkHolidayBeforeEarlies() {
        for (int i = 1; i < (getFlightList().size() - 1); i++) {
            //Gets 2 consecutive flights
            Flight f1 = getFlightList().get(i);
            Flight f2 = getFlightList().get(i + 1);
            //Check if both of them are early flights
            if (f1.isEarlyFlight() && f2.isEarlyFlight()) {
                //In case they are both early, check that there is no day off between them
                //In the case there is a day off between them, there is no need for an extra day off before
                if (!existsDayOffBetweenFlights(f1, f2)) {
                    //Gets the previous flight (the one before the two consecutive earlies)
                    Flight previous = getFlightList().get(i - 1);
                    if (!existsDayOffBetweenFlights(previous, f1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Check the captain is not flying over the maximum allowed flight time
     */
    public boolean belowMaxHours() {
        int counter = 0;
//         for (Pair pair : getPairList()) {
//             counter += (pair.flightOut.getDuration() + pair.flightBack.getDuration());
//         }
//         int hours = counter / 60;
//         return (hours < 100);
        for (Flight flight:getFlightList()){
            counter+= (flight.getDuration());
        }
        int hours = counter/60;
        return (hours < 100);
    }

    /**
     * Checks that you have one day off every 8 days
     */
    public boolean oneInEight(){
        for(int i = 0; i<flightList.size() -1; i++){
            //Get one flight
            Flight f1 = flightList.get(i);
            //Calculate the date 8 days later (no ".clone" is needed, it is done in "getScheduledTimeDeparture")
            GregorianCalendar eightDaysLater = f1.getScheduledTimeDeparture();
            eightDaysLater.add(GregorianCalendar.DAY_OF_YEAR, 8);

            boolean ok = false;            
            Flight f2 = null;
            int indexOfLastFlight = -1;
            
            for(int j = i; (j<flightList.size() - 1) && (!ok); j++){
                f1 = flightList.get(j);
                f2 = flightList.get(j+1);
                if(f2.getScheduledTimeDeparture().before(eightDaysLater)){
                    indexOfLastFlight = j+1;
                    if(existsDayOffBetweenFlights(f1, f2)){
                        //If there is a day off between f1 and f2, the condition is satisfied for these flights
                        //This means we can go and check with an 8-day-period starting at the departure of another flight
                        ok = true;
                        break;
                    }
                }else{
                    indexOfLastFlight = j;
                    //Flights are ordered by departure time
                    //This means that if f2 is more than 8 days later, all other flights will be as well
                    //This means that we don't need to keep looking, so we stop the second "for"
                    break;
                }
            }
            //If there is no day off between all flights in the 8-day-period, we have to see if there is a day off
            //between the last flight and the end of the 8-day-period
            if(!ok){
                //If the first flight after the 8-day-period starts before 6 a.m,
                //this period doesn't count as a day-off

                Flight lastFlight = flightList.get(indexOfLastFlight);
                //For it, there needs to be another flight afterwards
                if(indexOfLastFlight< flightList.size() -1){                    
                    Flight nextFlight = flightList.get(indexOfLastFlight + 1);
                    GregorianCalendar auxTime = (GregorianCalendar) lastFlight.getLocalDutyEnd();

                    auxTime.add(GregorianCalendar.DAY_OF_YEAR, 2);
                    auxTime.set(GregorianCalendar.HOUR_OF_DAY, 6);
                    auxTime.set(GregorianCalendar.MINUTE, 0);
                    auxTime.set(GregorianCalendar.SECOND, 0);

                    //After a day off, captain can't start before 6 a.m
                    if (nextFlight.getLocalDutyStart().before(auxTime)) {
                        return false;
                    }
                }
                
                int time = Math.abs((int) (eightDaysLater.getTimeInMillis() - lastFlight.getDutyEnd().getTimeInMillis()));
                int hours = time / (1000 * 60 * 60);
                //checks that the last flight of the 8-day-period is at least
                //34 hours before end of the period
                if (hours < 34) {
                    return false;
                }

                //Get the time 23:59:59, 2 days before the end of the 8-day-period
                //Then checks that the last duty ends before that
                //These checks are done in local time because the exact time matters
                GregorianCalendar local8DaysLater = flightList.get(i).getLocalDutyStart();
                local8DaysLater.add(GregorianCalendar.DAY_OF_YEAR, 8);
                GregorianCalendar auxTime = (GregorianCalendar) local8DaysLater.clone();
                auxTime.add(GregorianCalendar.DAY_OF_YEAR, -2);
                auxTime.set(GregorianCalendar.HOUR_OF_DAY, 23);
                auxTime.set(GregorianCalendar.MINUTE, 59);
                auxTime.set(GregorianCalendar.SECOND, 59);

                if (auxTime.before(lastFlight.getLocalDutyEnd())) {
                    return false;
                }
            }
        }
        return true;
    }    

    /*
     * Checks that there are 2 days off in fourteen
     * These days can be: 2 separate days (4 nights) or 2 consecutive days (3 nights)
     */
    public boolean twoInFourteen()
    {        
        for(int i = 0; i<flightList.size() -1; i++){
            int daysOff = 0;
            //Get one flight
            Flight f1 = flightList.get(i);
            //Calculate the date 14 days later (no ".clone" is needed, it is done in "getScheduledTimeDeparture")
            GregorianCalendar fourteenDaysLater = f1.getScheduledTimeDeparture();
            fourteenDaysLater.add(GregorianCalendar.DAY_OF_YEAR, 14);
            
            Flight f2 = null;
            int indexOfLastFlight = -1;

            for(int j = i; (j<flightList.size() - 1) && (daysOff<2); j++){
                f1 = flightList.get(j);
                f2 = flightList.get(j+1);
                if(f2.getScheduledTimeDeparture().before(fourteenDaysLater)){
                    indexOfLastFlight = j+1;
                    if(existsXDaysOffBetweenFlights(f1, f2, 2)){
                        //If there are 2 days off, the condition is satisfied for flight in position i
                        //We can continue checking with the 14-day-period starting with another flight
                        daysOff+=2;
                    }else if(existsDayOffBetweenFlights(f1, f2)){
                        //If there is a day off between f1 and f2, we add one dayOff and continue checkin for more
                        daysOff++;                        
                    }
                }else{
                    indexOfLastFlight = j;
                    //Flights are ordered by departure time
                    //This means that if f2 is more than 14 days later, all other flights will be as well
                    //This means that we don't need to keep looking, so we stop the second "for"
                    break;
                }
            }
            //If there is no 2 days off between all flights in the 14-day-period, we have to see if there is 2 days off
            //between the last flight and the end of the 14-day-period
            if(daysOff<2){
                //If the first flight after the 14-day-period starts before 6 a.m,
                //this period doesn't count as a day-off

                //For it, there needs to be another flight afterwards
                if(indexOfLastFlight< flightList.size() -1){
                    Flight lastFlight = flightList.get(indexOfLastFlight);
                    Flight nextFlight = flightList.get(indexOfLastFlight + 1);

                    GregorianCalendar auxTime = (GregorianCalendar) lastFlight.getLocalDutyEnd();
                    auxTime.add(GregorianCalendar.DAY_OF_YEAR, 2);
                    auxTime.set(GregorianCalendar.HOUR_OF_DAY, 6);
                    auxTime.set(GregorianCalendar.MINUTE, 0);
                    auxTime.set(GregorianCalendar.SECOND, 0);

                    //After a day off, captain can't start before 6 a.m
                    if (nextFlight.getLocalDutyStart().before(auxTime)) {
                        return false;
                    }
                }

                //If we already found 1 day off, we check that there is a second one
                if (daysOff == 1) {

                    Flight lastFlight = flightList.get(indexOfLastFlight);
                    int time = Math.abs((int) (fourteenDaysLater.getTimeInMillis() - lastFlight.getDutyEnd().getTimeInMillis()));
                    int hours = time / (1000 * 60 * 60);
                    //checks that the last flight of the 14-day-period is at least
                    //34 hours before end of the period
                    if (hours < 34) {
                        return false;
                    }
                    //Get the time 23:59:59, 2 days before the end of the 14-day-period
                    //Then checks that the last duty ends before that
                    //These checks are done in local time because the exact time matters
                    GregorianCalendar local14DaysLater = flightList.get(i).getLocalDutyStart();
                    local14DaysLater.add(GregorianCalendar.DAY_OF_YEAR, 14);
                    GregorianCalendar auxTime = (GregorianCalendar) local14DaysLater.clone();
                    auxTime.add(GregorianCalendar.DAY_OF_YEAR, -2);
                    auxTime.set(GregorianCalendar.HOUR_OF_DAY, 23);
                    auxTime.set(GregorianCalendar.MINUTE, 59);
                    auxTime.set(GregorianCalendar.SECOND, 59);

                    if (auxTime.before(lastFlight.getLocalDutyEnd())) {
                        return false;
                    }
                //If there is no daysOff found yet, we look for two days off
                } else {
                    Flight lastFlight = flightList.get(indexOfLastFlight);
                    int time = Math.abs((int) (fourteenDaysLater.getTimeInMillis() - lastFlight.getDutyEnd().getTimeInMillis()));
                    int hours = time / (1000 * 60 * 60);
                    //checks that the last flight of the 14-day-period is at least
                    //34 + 8 hours before end of the period
                    if (hours < 42) {
                        return false;
                    }
                    //Get the time 23:59:59, 3 days before the end of the 14-day-period
                    //(It is 3 days before because its 2 local nights for 1 day off and 1 extra night for every other day off))
                    //Then checks that the last duty ends before that
                    //These checks are done in local time because the exact time matters
                    GregorianCalendar local14DaysLater = flightList.get(i).getLocalDutyStart();
                    local14DaysLater.add(GregorianCalendar.DAY_OF_YEAR, 14);
                    GregorianCalendar auxTime = (GregorianCalendar) local14DaysLater.clone();
                    auxTime.add(GregorianCalendar.DAY_OF_YEAR, -3);
                    auxTime.set(GregorianCalendar.HOUR_OF_DAY, 23);
                    auxTime.set(GregorianCalendar.MINUTE, 59);
                    auxTime.set(GregorianCalendar.SECOND, 59);

                    if (auxTime.before(lastFlight.getLocalDutyEnd())) {
                        return false;
                    }
                }
            }
        }
        //throw new UnsupportedOperationException("Not yet implemented");
        return true;
    }

    public boolean eightIn28()
    {
        for(int i = 0; i<flightList.size() -1; i++){
            int daysOff = 0;
            //Get one flight
            Flight f1 = flightList.get(i);
            //Calculate the date 28 days later (no ".clone" is needed, it is done in "getScheduledTimeDeparture")
            GregorianCalendar twentyEightDaysLater = f1.getScheduledTimeDeparture();
            twentyEightDaysLater.add(GregorianCalendar.DAY_OF_YEAR, 28);

            Flight f2 = null;
            int indexOfLastFlight = -1;

            for(int j = i; (j<flightList.size() - 1) && (daysOff<8); j++){
                f1 = flightList.get(j);
                f2 = flightList.get(j+1);
                if(f2.getScheduledTimeDeparture().before(twentyEightDaysLater)){
                    indexOfLastFlight = j+1;
                    boolean vacation = false;
                    for(int d = 8; d>1; d--){
                        if(existsXDaysOffBetweenFlights(f1, f2, d)){
                            daysOff += d;
                            vacation = true;
                            break;
                        }
                    }
                    if(!vacation && existsDayOffBetweenFlights(f1, f2)){
                        //If there is a day off between f1 and f2, we add one dayOff and continue checkin for more
                        daysOff++;
                    }
                }else{
                    indexOfLastFlight = j;
                    //Flights are ordered by departure time
                    //This means that if f2 is more than 28 days later, all other flights will be as well
                    //This means that we don't need to keep looking, so we stop the second "for"
                    break;
                }
            }
            //If there is no 8 days off between all flights in the 28-day-period, we have to see if there is 2 days off
            //between the last flight and the end of the 28-day-period
            if(daysOff<8){
                //If the first flight after the 28-day-period starts before 6 a.m,
                //this period doesn't count as a day-off

                //For it, there needs to be another flight afterwards
                if(indexOfLastFlight< flightList.size() -1){
                    Flight lastFlight = flightList.get(indexOfLastFlight);
                    Flight nextFlight = flightList.get(indexOfLastFlight + 1);

                    GregorianCalendar auxTime = (GregorianCalendar) lastFlight.getLocalDutyEnd();
                    auxTime.add(GregorianCalendar.DAY_OF_YEAR, 2);
                    auxTime.set(GregorianCalendar.HOUR_OF_DAY, 6);
                    auxTime.set(GregorianCalendar.MINUTE, 0);
                    auxTime.set(GregorianCalendar.SECOND, 0);

                    //After a day off, captain can't start before 6 a.m
                    if (nextFlight.getLocalDutyStart().before(auxTime)) {
                        return false;
                    }
                }

                //If we already 7 days, off, we check that there is anotherone
                if (daysOff == 7) {

                    Flight lastFlight = flightList.get(indexOfLastFlight);
                    int time = Math.abs((int) (twentyEightDaysLater.getTimeInMillis() - lastFlight.getDutyEnd().getTimeInMillis()));
                    int hours = time / (1000 * 60 * 60);
                    //checks that the last flight of the 28-day-period is at least
                    //34 hours before end of the period
                    if (hours < 34) {
                        return false;
                    }
                    //Get the time 23:59:59, 2 days before the end of the 28-day-period
                    //Then checks that the last duty ends before that
                    //These checks are done in local time because the exact time matters
                    GregorianCalendar local28DaysLater = flightList.get(i).getLocalDutyStart();
                    local28DaysLater.add(GregorianCalendar.DAY_OF_YEAR, 28);
                    GregorianCalendar auxTime = (GregorianCalendar) local28DaysLater.clone();
                    auxTime.add(GregorianCalendar.DAY_OF_YEAR, -2);
                    auxTime.set(GregorianCalendar.HOUR_OF_DAY, 23);
                    auxTime.set(GregorianCalendar.MINUTE, 59);
                    auxTime.set(GregorianCalendar.SECOND, 59);

                    if (auxTime.before(lastFlight.getLocalDutyEnd())) {
                        return false;
                    }
                //If there are more daysOff left, we look for that amount
                } else {
                    Flight lastFlight = flightList.get(indexOfLastFlight);
                    int time = Math.abs((int) (twentyEightDaysLater.getTimeInMillis() - lastFlight.getDutyEnd().getTimeInMillis()));
                    int hours = time / (1000 * 60 * 60);
                    //checks that the last flight of the 14-day-period is at least
                    //34 + 8 hours before end of the period
                    int daysMissing = 8-daysOff;
                    int extraTime = 8 * daysMissing;
                    if (hours < 34 + extraTime) {
                        return false;
                    }
                    //Get the time 23:59:59, ("daysMissing" + 1) days before the end of the 28-day-period
                    //(It is +1 days before because its 2 local nights for 1st day off and 1 extra night for every other day off))
                    //Then checks that the last duty ends before that
                    //These checks are done in local time because the exact time matters
                    GregorianCalendar local28DaysLater = flightList.get(i).getLocalDutyStart();
                    local28DaysLater.add(GregorianCalendar.DAY_OF_YEAR, 28);
                    GregorianCalendar auxTime = (GregorianCalendar) local28DaysLater.clone();
                    auxTime.add(GregorianCalendar.DAY_OF_YEAR, -(daysMissing+1));
                    auxTime.set(GregorianCalendar.HOUR_OF_DAY, 23);
                    auxTime.set(GregorianCalendar.MINUTE, 59);
                    auxTime.set(GregorianCalendar.SECOND, 59);

                    if (auxTime.before(lastFlight.getLocalDutyEnd())) {
                        return false;
                    }
                }
            }
        }
        //throw new UnsupportedOperationException("Not yet implemented");
        return true;
    }

    /**
     * Checks captain has minimum of 12 hours or length of last duty between duties
     */
    public boolean hasTimeBetween() {
        Pair previous = null;
        if(pairList.size() > 1){//check if this is first flight added, if not:
           Iterator it = pairList.iterator();
           while(it.hasNext()){
               Pair current = (Pair)it.next();
               if(previous != null){
                   int time = Math.abs((int) (previous.getDutyStart().getTimeInMillis() -current.getDutyEnd().getTimeInMillis()));//wrong!so very, very wrong!
                   int hours = time / (1000 * 60 * 60);  
                   if(!(hours > 12 || hours > previous.returnSize())){
                      return false;
                    }
                }
                previous = current;
            }            
        }        
        return true;//if flightlist contains only one flight
    }//endfunction

    /**
     * Checks the pilot has no more than 55 hours in 7 days
     */
    public boolean fiftyFiveInSeven() {
        int hours = 0;
        //Get every flight
        for (Flight flight1 : getFlightList()) {
            //Consider a week starting when the flight starts
            GregorianCalendar aWeek = (GregorianCalendar) flight1.getDutyStart().clone();
            //Calculate the date exactly 1 week afterwards
            GregorianCalendar aWeek1 = aWeek;
            aWeek1.add(GregorianCalendar.DAY_OF_MONTH, 7);
            //Start the timer with the first flight's duration
            int counter = flight1.getDuration();
            for (Flight flight2 : getFlightList()) {
                if (!flight1.equals(flight2)) {
                    //Check if the second flight is in the first flight's week
                    if ((flight2.getDutyStart().after(flight1.getDutyStart())) && (flight2.getDutyStart().before(aWeek1))) {
                        counter += flight2.getDuration();
                    }
                }
            }
            hours = counter / 60;
            if (hours > 55) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks the pilot has no more than 95 hours in 14 days - recopy of 55in7
     */
    public boolean ninetyFiveInFourteen() {
//         GregorianCalendar finalTimeDate = pairList.get(0).getFlightOut().getDutyStart();
//         finalTimeDate.add(GregorianCalendar.HOUR, (14 * 24));
//         ArrayList<Pair> flights = new ArrayList<Pair>();
//         for (Pair flight : pairList) {
//             if (flight.getFlightBack().getDutyEnd().before(finalTimeDate)) {
//                 flights.add(flight);
//             }
//         }
// //         for(Flight flight:flightList){
// //             if(flight.getDutyEnd().before(finalTimeDate)){
// //                 flights.add(flight);
// //             }
// //         }
//         int totalDuration = 0;
//         for (Pair flight : flights) {
//             totalDuration += flight.returnSize();
//         }
// 
//         if ((totalDuration / 60) > 95) {
//             return false;
//         } else {
//             return true;
//         }

     int hours = 0;
        //Get every flight
        for (Flight flight1 : getFlightList()) {
            //Consider a week starting when the flight starts
            GregorianCalendar aWeek = (GregorianCalendar) flight1.getDutyStart().clone();
            //Calculate the date exactly 1 week afterwards
            GregorianCalendar aWeek1 = aWeek;
            aWeek1.add(GregorianCalendar.DAY_OF_MONTH, 14);
            //Start the timer with the first flight's duration
            int counter = flight1.getDuration();
            for (Flight flight2 : getFlightList()) {
                if (!flight1.equals(flight2)) {
                    //Check if the second flight is in the first flight's week
                    if ((flight2.getDutyStart().after(flight1.getDutyStart())) && (flight2.getDutyStart().before(aWeek1))) {
                        counter += flight2.getDuration();
                    }
                }
            }
            hours = counter / 60;
            if (hours > 95) {
                return false;
            }
        }
        return true;

    }

    /*
     * Each pair is compared with all the rest.
     * In case one pair is during another one, the method returns false.
     * False means 2 pairs clash.
     * True means that no pairs clash.
     */
    public boolean doesNotClash() {
        for (int i = 0; i < getPairList().size() - 1; i++) {
            for (int j = (i + 1); j < getPairList().size(); j++) {
                Pair pair1 = getPairList().get(i);
                Pair pair2 = getPairList().get(j);
                if (!pair1.isNotDuringPair(pair2)) {
                    return false;
                }
            }
        }
       
//         for(int y = 0; y < flightList.size()-1;y++){
//             if(flightList.get(y).isDuringFlight(flightList.get(y+1))){
//                 return false;
//             }
//         }
        return true;
    }

    /**
     * Checks the pilot has no more than 190 hours in 28 days -- recopy
     */
    public boolean oneNinetyInTwentyEight() {
    GregorianCalendar finalTimeDate = flightList.get(0).getDutyStart();
           //GregorianCalendar finalTimeDate = pairList.get(0).getFlightOut().getDutyStart(); 
            finalTimeDate.add(GregorianCalendar.HOUR, (28 * 24));
            ArrayList<Pair> flights = new ArrayList<Pair>();
            for (Pair flight : pairList) {
                if (flight.getFlightBack().getDutyEnd().before(finalTimeDate)) {
                    flights.add(flight);
                }
    
            }
    //         for(Flight flight:flightList){
    //             if(flight.getDutyEnd().before(finalTimeDate)){
    //                 flights.add(flight);
    //             }
    //         }
            int totalDuration = 0;
            for (Pair flight : flights) {
                totalDuration += flight.returnSize();
            }
    
            if ((totalDuration / 60) > 190) {
                return false;
            } else {
                return true;
            }
//         int hours = 0;
//         //Get every flight
//         for (Flight flight1 : getFlightList()) {
//             //Consider a week starting when the flight starts
//             GregorianCalendar aWeek = (GregorianCalendar) flight1.getDutyStart().clone();
//             //Calculate the date exactly 1 week afterwards
//             GregorianCalendar aWeek1 = aWeek;
//             aWeek1.add(GregorianCalendar.DAY_OF_MONTH, 28);
//             //Start the timer with the first flight's duration
//             int counter = flight1.getDuration();
//             for (Flight flight2 : getFlightList()) {
//                 if (!flight1.equals(flight2)) {
//                     //Check if the second flight is in the first flight's week
//                     if ((flight2.getDutyStart().after(flight1.getDutyStart())) && (flight2.getDutyStart().before(aWeek1))) {
//                         counter += flight2.getDuration();
//                     }
//                 }
//             }
//             hours = counter / 60;
//             if (hours > 190) {
//                 return false;
//             }
//         }
//         return true;
    }

    private boolean doAllChecks() {
        if (!doesNotClash()) {
            return false;
        }
        if (!oneNinetyInTwentyEight()) {
            return false;
        }
        if (!ninetyFiveInFourteen()) {
            return false;
        }
        if (!fiftyFiveInSeven()) {
            return false;
        }
        if (!hasTimeBetween()) {
            return false;
        }
        if (!eightIn28()) {
            return false;
        }
        if (!twoInFourteen()) {
            return false;
        }
        if (!oneInEight()) {
            return false;
        }
        if (!belowMaxHours()) {
            return false;
        }
        if (!checkHolidayBeforeEarlies()) {
            return false;
        }
        if (!checkEarlyLateDuties()) {
            return false;
        }
        if (!checkConsecutiveEarlyLates()) {
            return false;
        }
        return true;
    }

    /**
     * Checks if a captain can be standby for a given pairing. Includes rest period.
     */
    public boolean checkStandbyFlight(Pair chkFlight) {
        getPairList().add(chkFlight);
        getFlightList().add(chkFlight.flightOut);
        getFlightList().add(chkFlight.flightBack);
        sortLists();
        if (doAllChecks()) {
            chkFlight.setStandbyCaptain(this);
            return true;
        } else {
            getPairList().remove(chkFlight);
            getFlightList().remove(chkFlight.flightBack);
            getFlightList().remove(chkFlight.flightOut);
            sortLists();
            return false;
        }
    }

    /**
     * @return the pairList
     */
    public ArrayList<Pair> getPairList() {
        return pairList;
    }

    /**
     * @return the flightList
     */
    public ArrayList<Flight> getFlightList() {
        return flightList;
    }

    /**
     * Checks if the captain is flying a given flight
     */
    public boolean isFlying(Pair flight) {
        for (Pair pair : pairList) {
            if (pair.equals(flight)) {
                return true;
            }
        }
        return false;
    }
}