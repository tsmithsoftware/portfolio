import java.util.GregorianCalendar;

public class Flight implements Comparable<Flight>
{
    private String flightNumber;
    private Captain standByCaptain;

    private GregorianCalendar scheduledTimeDeparture;    
    private GregorianCalendar scheduledTimeArrival;    

    private GregorianCalendar dutyStart;
    private GregorianCalendar dutyEnd;
    private GregorianCalendar localDutyStart;
    private GregorianCalendar localDutyEnd;
    private GregorianCalendar flightDutyStart;
    private GregorianCalendar flightDutyEnd;

    private Airport destinationAirport;
    private Airport departureAirport;

    private int duration;
    private int flightDutyDuration;
    private int dutyDuration;

    boolean standby;

    private Pair pair;
    
    private boolean earlyFlight = false;
    private boolean lateFlight = false;

    public Flight(){}

    public Flight(GregorianCalendar departure, GregorianCalendar arrival, Airport dept, Airport dest, String id, Boolean isStandby)
    {
        flightNumber = id;
        scheduledTimeArrival = arrival;
        scheduledTimeDeparture = departure;
        
        destinationAirport = dest;
        departureAirport = dept;

        dutyStart = (GregorianCalendar) scheduledTimeDeparture.clone();
        dutyStart.add(GregorianCalendar.HOUR, -1);
        flightDutyStart = (GregorianCalendar) dutyStart.clone();
        dutyEnd = (GregorianCalendar) scheduledTimeArrival.clone();
        dutyEnd.add(GregorianCalendar.MINUTE, 30);
        flightDutyEnd = (GregorianCalendar) scheduledTimeArrival.clone();


        localDutyStart = (GregorianCalendar) dutyStart.clone();
        localDutyStart.add(GregorianCalendar.HOUR_OF_DAY, departureAirport.getLocalOffset());
        localDutyEnd = (GregorianCalendar) dutyEnd.clone();
        localDutyEnd.add(GregorianCalendar.HOUR_OF_DAY, destinationAirport.getLocalOffset());

        GregorianCalendar earlyStart = new GregorianCalendar(getDutyStart().get(GregorianCalendar.YEAR), getDutyStart().get(GregorianCalendar.MONTH), getDutyStart().get(GregorianCalendar.DAY_OF_MONTH), 4, 59);
        GregorianCalendar earlyEnd = new GregorianCalendar(getDutyStart().get(GregorianCalendar.YEAR), getDutyStart().get(GregorianCalendar.MONTH), getDutyStart().get(GregorianCalendar.DAY_OF_MONTH), 7, 00);
        GregorianCalendar lateStart = new GregorianCalendar(getDutyStart().get(GregorianCalendar.YEAR), getDutyStart().get(GregorianCalendar.MONTH), getDutyStart().get(GregorianCalendar.DAY_OF_MONTH), 00, 59);
        GregorianCalendar lateEnd = new GregorianCalendar(getDutyStart().get(GregorianCalendar.YEAR), getDutyStart().get(GregorianCalendar.MONTH), getDutyStart().get(GregorianCalendar.DAY_OF_MONTH), 02, 00);

        if(localDutyStart.after(earlyStart) && localDutyStart.before(earlyEnd))
            earlyFlight = true;

        if(localDutyEnd.after(lateStart) && localDutyEnd.before(lateEnd))
            lateFlight = true;

        standby = isStandby;
        
        calculateTotalTimes();
    }

    /**
     * Compares current flight against another flight
     * @return true if same flight
     */
    public boolean compareFlights(Flight secondFlight)
    {
        return getFlightNumber().equals(secondFlight.getFlightNumber());
    }

    public GregorianCalendar getScheduledTimeDeparture() {
        return (GregorianCalendar) scheduledTimeDeparture.clone();
    }

    public GregorianCalendar getScheduledTimeArrival() {
        return (GregorianCalendar) scheduledTimeArrival.clone();
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }    

    public int getDuration() {
        return duration;
    }

    public Pair getPair() {
        return pair;
    }

    public void setPair(Pair newPair)
    {
        pair = newPair;
    }

    public GregorianCalendar getDutyStart() {
        return (GregorianCalendar) dutyStart.clone();
    }

    public GregorianCalendar getDutyEnd() {
        return (GregorianCalendar) dutyEnd.clone();
    }

    public GregorianCalendar getLocalDutyStart() {
        return (GregorianCalendar) localDutyStart.clone();
    }

    public GregorianCalendar getLocalDutyEnd() {
        return (GregorianCalendar) localDutyEnd.clone();
    }

    public boolean isEarlyFlight() {
        return earlyFlight;
    }

    public boolean isLateFlight() {
        return lateFlight;
    }

    /*
     * A flight is during a given pair is it starts after the flightback or if it finishes before the flight out
     */
    public boolean isNotDuring(Pair givenPair)
    {
        //return ( (isAfterDuty(givenFlight)) || (isBeforeDuty(givenFlight)) );
        return ((this.isAfterDuty(givenPair.flightBack)) || (this.isBeforeDuty(givenPair.flightOut)));
    }

    /*
     * returns true if this dutyStart is after the parameter's dutyEnd
     */
    public boolean isAfterDuty(Flight chkFlight)
    {
        int ds = (int) dutyStart.getTimeInMillis();
        int other = (int) chkFlight.dutyEnd.getTimeInMillis();
        return dutyStart.after(chkFlight.dutyEnd);
    }
    
    /*
     * returns true if this dutyEnd is before the parameter's dutyStart
     */
    public boolean isBeforeDuty(Flight chkFlight)
    {
        return dutyEnd.before(chkFlight.dutyStart);
    }

    public void calculateTotalTimes(){
        int time = (int)(scheduledTimeArrival.getTimeInMillis() - scheduledTimeDeparture.getTimeInMillis());
        int minutes = time/(1000 * 60);
        duration = minutes;

        time = (int)(flightDutyEnd.getTimeInMillis() - flightDutyStart.getTimeInMillis());
        minutes = time/(1000 * 60);
        flightDutyDuration = minutes;

        time = (int)(dutyEnd.getTimeInMillis() - dutyStart.getTimeInMillis());
        minutes = time/(1000 * 60);
        dutyDuration = minutes;
    }

    @Override
    public String toString(){
        String output =  this.flightNumber + ": " + departureAirport.getCode() + " - " + destinationAirport.getCode();
        output += " \nDeparting: " + scheduledTimeDeparture.getTime() + ". Arriving: " + scheduledTimeArrival.getTime() + " ";
        return output;
    }
    
    @Override
    public boolean equals(Object obj){
        return this.flightNumber.equalsIgnoreCase(((Flight)obj).flightNumber);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.flightNumber != null ? this.flightNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(Flight flight){
        return scheduledTimeDeparture.compareTo(flight.scheduledTimeDeparture);
    }

    public Flight cloneLater(int i){
        GregorianCalendar departure = (GregorianCalendar) scheduledTimeDeparture.clone();
        departure.add(GregorianCalendar.WEEK_OF_YEAR, i);
        GregorianCalendar arrival = (GregorianCalendar) scheduledTimeArrival.clone();
        arrival.add(GregorianCalendar.WEEK_OF_YEAR, i);
        Flight flight = new Flight(departure, arrival, departureAirport, destinationAirport, flightNumber + " - " + i, standby);
        return flight;
    }
     /**
     * Returns the standby Captain ts308
     */
    public Captain getStandbyCaptain()
    {
        return standByCaptain;
    }
    
    /**
     * Sets the standby Captain ts308
     */
    public void setStandByCaptain(Captain captain)
    {
        standByCaptain = captain;
    }
    
    /**
     * Change the flight duty start (used for standby captain checks)
     */
    public void changeFlightDutyStart(GregorianCalendar newStart)
    {
        flightDutyStart = newStart;
    }
    
    /**
     * Change the flight duty end (used for standby captain checks)
     */
    public void changeFlightDutyEnd(GregorianCalendar newEnd)
    {
        flightDutyEnd = newEnd;
    }

    boolean isAfter(Flight flight1) {
        return this.scheduledTimeDeparture.after(flight1.scheduledTimeArrival);
    }
    
    public String returnName()
    {
        return this.flightNumber;
    }
}
