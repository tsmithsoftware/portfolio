import java.util.LinkedList;
import java.io.*;

/**
 * Reads a text file containing a set of strings representing the start and end state of the game board.
 * Finds a path from one to the other and writes the path to a text file.
 * 
 * @author Timothy Smith
 * @version (a version number or a date)
 */
public class Main
{
  
    /**
     * Constructor for objects of class Main
     * Input: the location of the file containing the puzzles
     */
    public Main(String input)
    {
        //String file location
        //for each line in file:
        //solve problem
      try{
      BufferedReader puzzles = new BufferedReader(new FileReader(input));
        String puzzle = "";
        while((puzzle = puzzles.readLine())!=null){ 
              String[] startEnd = puzzle.split("2");
              LinkedList<String> route = iterativeDeep(startEnd[0],startEnd[1]);
              try{
                BufferedWriter output = new BufferedWriter(new FileWriter(puzzle + ".txt"));
                output.append("Solution for " + startEnd[0] + " to " + startEnd[1]);
                output.newLine();
                output.newLine();
                for(String str:route){
                      output.newLine();
                      output.append(str.substring(0,4));
                      output.newLine();
                      output.append(str.substring(4,8));
                      output.newLine();
                      output.append(str.substring(8,12));
                      output.newLine();
                    }
                output.newLine();
                output.close();
                System.out.print("\n Solution found for " + startEnd[0] + " to " + startEnd[1] + "\n");
            }
            catch(IOException e){
            }
        }
    puzzles.close();
    }
    catch(FileNotFoundException e){
        System.out.print("File not found");
    }
    catch(IOException e){
        System.out.println("A problem has been encountered in reading a puzzle off the puzzle file.");
    }
    }
    
    protected LinkedList<String> iterativeDeep(String config, String dest)
    {
        System.out.print("Depth: ");
        for(int depth = 1; true; depth ++){
            System.out.print(depth);//bugcheck
            LinkedList<String> route = depthFirst(config,dest,depth);
            if(route!=null) return route;
        }
    }
        
    protected LinkedList<String> depthFirst(String config, String dest, int depth)
    {
        if(depth == 0) return null;
        else if (config.equals(dest)){
            LinkedList<String> route = new LinkedList<String>();
            route.add(config);
            return route;
        }
        else{
            LinkedList<String> result = nextConfigs(config);
            for (String nextConfig:result){
                LinkedList<String> route = depthFirst(nextConfig,dest,depth - 1);
                if (route != null){
                    route.addFirst(config);
                    return route;
                }
            }
            return null;
        }
    }
    
    protected LinkedList<String> nextConfigs (String currentConfig) throws IndexOutOfBoundsException
    {
        LinkedList<String> nextConfigs = new LinkedList<String>();
        int pos = 0;
        int blank = 0;
        for(char charac:currentConfig.toCharArray()){
            if(new Character(charac).toString().equals("_")){
                blank=pos;
            }
            else{
                pos++;
            }
        }
                                char[] tempArray = currentConfig.toCharArray();
                                
                                if((blank == 3) || (blank == 7)){
                                }
                                else{
                                    try{
                                        Character blankPlusOne = tempArray[blank+1];
                                        tempArray[blank + 1] = tempArray[blank];
                                        tempArray[blank] = blankPlusOne;
                                        nextConfigs.add(new String(tempArray)); //moves tile one step right and adds resulting state to nextConfigs
                                                                          
                                        blankPlusOne = tempArray[blank+1];
                                        tempArray[blank + 1] = tempArray[blank];
                                        tempArray[blank] = blankPlusOne; //resets board
                                        }
                                    
                                    catch(IndexOutOfBoundsException e){
                                    }
                                }
                                
                                if((blank == 4) || (blank == 8)){
                                }
                                else{
                                try{
                                    Character blankPlusOne = tempArray[blank - 1];
                                    tempArray[blank - 1] = tempArray[blank];
                                    tempArray[blank] = blankPlusOne;
                                    nextConfigs.add(new String(tempArray)); //moves tile one step left and adds resulting state to nextConfigs
                                    
                                    blankPlusOne = tempArray[blank - 1];
                                    tempArray[blank - 1] = tempArray[blank];
                                    tempArray[blank] = blankPlusOne; //resets board
                                    }
                                    
                                    catch(IndexOutOfBoundsException e){
                                    }
                                }
                                
                                try{
                                Character blankPlusOne = tempArray[blank + 4];
                                tempArray[blank + 4] = tempArray[blank];
                                tempArray[blank] = blankPlusOne;
                                nextConfigs.add(new String(tempArray)); //moves tile one step down and adds resulting state to nextConfigs
                                
                                blankPlusOne = tempArray[blank + 4];
                                tempArray[blank + 4] = tempArray[blank];
                                tempArray[blank] = blankPlusOne; //resets board
                                }
                                
                                catch(IndexOutOfBoundsException e){
                                }
                                
                                try{
                                Character blankPlusOne = tempArray[blank - 4];
                                tempArray[blank - 4] = tempArray[blank];
                                tempArray[blank] = blankPlusOne;
                                nextConfigs.add(new String(tempArray)); //moves tile one step up and adds resulting state to nextConfigs
                                
                                blankPlusOne = tempArray[blank - 4];
                                tempArray[blank - 4] = tempArray[blank];
                                tempArray[blank] = blankPlusOne; //resets board
                                }
                                
                                catch(IndexOutOfBoundsException e){
                                }
        return nextConfigs;
    }

}
