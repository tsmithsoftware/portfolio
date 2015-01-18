import java.util.GregorianCalendar;
public class Pair implements Comparable<Pair> {

    Flight flightOut = new Flight();
    Flight flightBack = new Flight();
    private Captain captain;
    private Captain standByCaptain = new Captain();

    private GregorianCalendar dutyStart;
    private GregorianCalendar dutyEnd;
    private GregorianCalendar localDutyStart;
    private GregorianCalendar localDutyEnd;
    private GregorianCalendar flightDutyStart;
    private GregorianCalendar flightDutyEnd;

    public Pair(Flight out, Flight back) {
        flightOut = out;
        flightBack = back;
        out.setPair(this);
        back.setPair(this);

        dutyStart = (GregorianCalendar) flightOut.getScheduledTimeDeparture().clone();
        dutyStart.add(GregorianCalendar.HOUR, -1);
        flightDutyStart = dutyStart;
        dutyEnd = (GregorianCalendar) flightBack.getScheduledTimeArrival().clone();
        flightDutyEnd = (GregorianCalendar) flightBack.getScheduledTimeArrival().clone();
        dutyEnd.add(GregorianCalendar.MINUTE, 30);        

        localDutyStart = (GregorianCalendar) dutyStart.clone();
        localDutyStart.add(GregorianCalendar.HOUR_OF_DAY, flightOut.getDepartureAirport().getLocalOffset());
        localDutyEnd = (GregorianCalendar) dutyEnd.clone();
        localDutyEnd.add(GregorianCalendar.HOUR_OF_DAY, flightBack.getDestinationAirport().getLocalOffset());
    }

    public Captain getCaptain(){
        return captain;
    }
    
    public void setCaptain(Captain c){
        captain = c;
    }

    public Flight getFlightOut() {
        return flightOut;
    }

    public Flight getFlightBack() {
        return flightBack;
    }

    /**
     * Cheks if this pair is not during given pair
     * This means that neither the flight out nor the flight back is during the given pair
     */
    public boolean isNotDuringPair(Pair chkPair) {
        //return (chkPair.getFlightOut().isNotDuring(flightOut)) && (chkPair.getFlightBack().isNotDuring(getFlightBack()));
        //boolean true1 = ((flightOut.getDutyStart().after(chkPair.flightOut.getDutyStart()))&&(flightOut.getDutyStart().before(chkPair.flightBack.getDutyEnd())));
        //boolean true2 = ((flightBack.getDutyStart().after(chkPair.flightOut.getDutyStart()))&&(flightOut.getDutyStart().before(chkPair.flightBack.getDutyEnd())));
        
        boolean true1 = this.flightOut.isNotDuring(chkPair);
        boolean true2 = this.flightBack.isNotDuring(chkPair);        
        return (true1 && true2);
    }

    public long returnSize() {
        return flightOut.getDuration() + flightBack.getDuration();
    }

    public void print() {
        System.out.print(" Outward Flight Details: " + flightOut.toString());
        System.out.print("\n Return Flight Details: " + flightBack.toString());
    }
    public String outPrint()
    {
        return flightOut.toString();
    }
    public String backPrint()
    {
        return flightBack.toString();
    }
    
    @Override
    public boolean equals(Object obj){
        Pair check = (Pair)obj;
        return (this.flightOut.equals(check.flightOut) && this.flightBack.equals(flightBack));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.flightOut != null ? this.flightOut.hashCode() : 0);
        hash = 31 * hash + (this.flightBack != null ? this.flightBack.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(Pair pair){
        return flightOut.compareTo(pair.flightOut);
    }
      public void setStandByCaptain(Captain captain)
    {
        flightOut.setStandByCaptain(captain);
    }
    
    /**
     * Returns the standby Captain ts308
     */
    public void setStandbyCaptain(Captain standby)
    {
       standByCaptain = standby;
    }

    /**
     * @return the standByCaptain
     */
    public Captain getStandByCaptain() {
        return standByCaptain;
    }

    /**
     * @return the dutyStart
     */
    public GregorianCalendar getDutyStart() {
        return dutyStart;
    }

    /**
     * @return the dutyEnd
     */
    public GregorianCalendar getDutyEnd() {
        return dutyEnd;
    }

    /**
     * @return the localDutyStart
     */
    public GregorianCalendar getLocalDutyStart() {
        return localDutyStart;
    }

    /**
     * @return the localDutyEnd
     */
    public GregorianCalendar getLocalDutyEnd() {
        return localDutyEnd;
    }

    /**
     * @return the flightDutyStart
     */
    public GregorianCalendar getFlightDutyStart() {
        return flightDutyStart;
    }

    /**
     * @return the flightDutyEnd
     */
    public GregorianCalendar getFlightDutyEnd() {
        return flightDutyEnd;
    }
}
