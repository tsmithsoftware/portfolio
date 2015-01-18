------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE:
PURPOSE OF PROJECT:
VERSION or DATE:
HOW TO START THIS PROJECT:
AUTHORS:
USER INSTRUCTIONS: Program will be started from Central

Central class holds array of Flights from Airports to Airports being flown by Captains 
operating under Restrictions(currently not included). Data class creates Airports and Flight times/dates.
GUI class displays timetable sent from Central class.

Each captain has a list of flights that they can do. The Central class would use a "first fit decreasing bin packing algorithm"-
"Basically you order all the sectors in order of size then give the biggest one to the first captain. 
The 2nd sector is given to the first captain, if it doesn't fit it is given to the second captain. 
This keeps going until a valid captain is found."

//Why have an Airport class at all? Why not have an array of place names in central, or even just in
Flight?..Have Strings detailing destination and departure in Flight, with an array of Strings in Central denoting all the 
airports.

//Two flights, one there, one back? One kinda totalFlight, covering all? I think the second, that would require design changes...
"2. The associations between captains and flights are called "pairings". We expect to use only simple pairings: captains go from A to B and then back. 
This may still incur a stopover if working both legs of the flight would lead to illegal working hours, but no transfers."
...
"5. We only consider pairings starting and ending in (and therefore only captains based in) London Gatwick (LGW). "
Second. Flights include both out and back.



GMT Offset will need to be included to do proper timetabling. Too much effort atm.

NB: FILE IS *NOT* AN EXCEL FILE. IS A *CSV* FILE. View in WordPad or similar, 23 Oct 07 was Flight Statistics Report, taken at
9:47, covering:
"23OCT07","09:47","Flight Statistics Report","Page -1 of 1","Legselect: LGW 738 FLIGHTS","01AUG2007 - 07AUG2007",
"Time mode: UTC","

Relevant information: "01AUG07","BA6852"," ","73H","GOXLA","C","LGW","HER","14:40","14:40","18:40","18:32","0:00","0:00"," ",
"0:00","0:00"," "," "," "," "," "," "," "," "," ",""," "," ","


Allocation of days off: holidays can be flights with either "" or "Vacation" as destination and origin

CURRENT TASKS: Captain.addFlight(), Captain.sortList(), Data.createFlights(),Flight.flightDutyEnd, 

