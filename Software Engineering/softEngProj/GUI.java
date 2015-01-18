import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.io.*;

/**
 * Class describing the GUI to the project
 * 
 * @author ts308
 */
public class GUI
{
    private JFrame frame;
    private JFrame loadFrame;
    private JFrame detailFrame;
    final JFileChooser fc = new JFileChooser();
    public static String filePath;
    Central c = new Central();
    JPanel center = new JPanel();
    Container loadPane;
    Container detailPane;
    public GUI()
    {
       makeLoadFrame();
    }
    
    private void makeLoadFrame()
    {
        loadFrame = new JFrame("Airline Scheduler");
        loadFrame.setLayout(new BorderLayout(20, 20));
        loadFrame.setSize(400, 400);
        loadFrame.setResizable(false);
        loadPane = loadFrame.getContentPane();
        final JPanel loadPanel = new JPanel();
        {
            //JLabels
            final JLabel welcomeLabel = new JLabel("<html><center>Please click on the button below to select the file you wish to load. <br> Supported file types are : .csv</center></html>");
            loadPanel.add(welcomeLabel, BorderLayout.NORTH);                                           
            //JButton for loading a file
            final JButton b = new JButton("open");
            b.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) {
                                 welcomeLabel.setText("Opening file...");
                                 b.setEnabled(false);
                                 open();    
                                 b.setEnabled(true);
                               }
                           });
            loadPanel.add(b, BorderLayout.SOUTH);
        }
        loadPane.add(loadPanel, BorderLayout.CENTER);
//         loadFrame.pack();
        loadFrame.setVisible(true);
    }
    
    private void makeFrame()
    {
        frame = new JFrame("Airline Scheduler");  
        frame.setLayout(new BorderLayout(8,8));
        frame.setSize(400,600);
        Container contentPane = frame.getContentPane();

        //Create North Panel
        JPanel north = new JPanel();
        {
            north.setLayout(new BorderLayout());
            JPanel toolbar = new JPanel();
            {
                 toolbar.setLayout(new GridLayout(1, 0));
  
//                  JButton button = new JButton("Refresh");
//                  button.addActionListener(new ActionListener() {
//                                        public void actionPerformed(ActionEvent e) { refresh(); }
//                                 });
//                  toolbar.add(button);
                        
                 JButton button = new JButton("Quit");
                 button.addActionListener(new ActionListener() {
                                   public void actionPerformed(ActionEvent e) { quit(); }
                               });
                 toolbar.add(button);
            }
               
            JPanel title = new JPanel();
            {
                JLabel label = new JLabel("Timetable");
                title.add(label);
                
            }
            north.add(title, BorderLayout.NORTH);
            north.add(toolbar, BorderLayout.SOUTH);
     
        }
        contentPane.add(north, BorderLayout.NORTH);
        // center pane
        {
            center.setLayout(new GridLayout(c.captains.size(), 1));
            int count = 0;
            for(final Captain cap : c.captains)
            {
                JLabel gridLabel = new JLabel("Captain " + cap.getId());
                JButton gridButton = new JButton("View pairing details");
                gridButton.addActionListener(new ActionListener() {
                                       public void actionPerformed(ActionEvent e) { viewDetails(cap.getId(),true); }
                                });
                JButton flightButton = new JButton("View flight details");
                flightButton.addActionListener(new ActionListener(){
                                        public void actionPerformed(ActionEvent e) { viewDetails(cap.getId(),false);}
                    
                                });
                center.add(gridLabel);
                center.add(gridButton);
                center.add(flightButton);
            }        
        }
        JScrollPane sPane = new JScrollPane(center);
        //sPane.setFillsViewportHeight(true);
        contentPane.add(sPane);
       
        JPanel south = new JPanel();
        {
            south.setLayout(new BorderLayout());
            
            JPanel capTot = new JPanel();
            {
                JLabel label = new JLabel("Total captains needed at LGW : " + c.captains.size());
                capTot.add(label);
            }
            south.add(capTot, BorderLayout.WEST);
        }
        contentPane.add(south, BorderLayout.SOUTH);
        
        //Makes menu bar
        {
            JMenuBar menubar = new JMenuBar();
            frame.setJMenuBar(menubar);
        
            // create the File menu
            JMenu fileMenu = new JMenu("File");
            menubar.add(fileMenu);
            
            JMenu helpMenu = new JMenu("Help");
            menubar.add(helpMenu);
            
            JMenuItem instructItem = new JMenuItem("Instructions");
            instructItem.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { instructItem(); }
                           });
            helpMenu.add(instructItem);
            
            JMenuItem openItem = new JMenuItem("Open");
            openItem.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) {
                                   JOptionPane.showMessageDialog(frame,"The project will appear to freeze while the program is loading. This is to be expected - press OK to continue.");
                                   open(); 
                                  }
                           });
            fileMenu.add(openItem);
            
            JMenuItem quitItem = new JMenuItem("Quit");
            quitItem.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { quit(); }
                           });
            fileMenu.add(quitItem);          
        }
        
            frame.pack();
            frame.setVisible(true);       
    }
    
    //captain details
    private void detailFrameSub(String id, boolean isPair)
    {
        detailFrame = new JFrame();  
        detailFrame.setLayout(new BorderLayout(8,8));
        detailFrame.setSize(400,600);
        detailPane = frame.getContentPane();
        final String idCopy = id; //for use in repainting frames
        final boolean isPairCopy = isPair;
        
        JPanel north = new JPanel();
        {
            JLabel captainTitle = new JLabel("Captain " + id);
            Font font = captainTitle.getFont();
            captainTitle.setFont(new Font(font.getFontName(), font.getStyle(), 25));
            north.add(captainTitle);
        }
        detailFrame.add(north, BorderLayout.NORTH);
        
        JPanel center = new JPanel();
        if(isPair){//to display a captains pairings
            center.setLayout(new GridLayout(c.flights.size(), 3));
                for(final Captain cap : c.captains)
                {
                     if(cap.pairList.size() == 0 && cap.getId().equals(id)){
                        JPanel panel = new JPanel();
                        center.add(panel.add(new JLabel("This captain has no pairs assigned, but he may still have flights.")));
                    }
                    for(final Pair pair : cap.pairList)
                    {
                        if(cap.getId().equals(id))
                        {
                            final JPanel pairPanel = new JPanel(new BorderLayout());
                            {
                                int one = pair.getFlightOut().getDutyStart().get(pair.getFlightOut().getDutyStart().DAY_OF_MONTH); //test
                                int two = pair.getFlightOut().getDutyStart().get(pair.getFlightOut().getDutyStart().MONTH); //test
                                int three = pair.getFlightBack().getDutyEnd().get(pair.getFlightBack().getDutyEnd().DAY_OF_MONTH); //test
                                int four = pair.getFlightBack().getDutyEnd().get(pair.getFlightBack().getDutyEnd().MONTH); //test
                                JButton details = new JButton("Flight from the " + one + "/" + two + " until the " + three + "/" + four); 
                                {
                                    details.addActionListener(new ActionListener(){
                                        public void actionPerformed(ActionEvent e){
                                            final JFrame detail = new JFrame("Pair details");
                                            JPanel panel = new JPanel();
                                            panel.add(new JLabel(pair.outPrint()), BorderLayout.NORTH);
                                            panel.add(new JLabel(pair.backPrint()),BorderLayout.SOUTH);
                                            detail.add(panel);
                                            detail.pack();
                                            detail.setVisible(true);
                                        }
                                    });
                                }
                                JLabel outLabel = new JLabel();
                                //outLabel.setText(cap.returnOutTable());
                                outLabel.setText("Outward flight of the pair: " + pair.outPrint() + "<br> Return flight of the flight:" +  pair.backPrint());
                                //pairPanel.add(outLabel, BorderLayout.NORTH);
                                pairPanel.add(details, BorderLayout.NORTH);
                            }
                            JButton removePair = new JButton("Remove pair");
                                removePair.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) { 
                                    removePair(cap, pair); 
                                    detailFrame.setVisible(false);
                                    detailFrame.dispose();
                                    //pairPanel.repaint();
                                    detailFrameSub(idCopy,isPairCopy);
                                }
                                });
                            
                            JButton reassignPair = new JButton("Reassign pair");
                            {
                                reassignPair.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) { 
                                    reassignPair(cap,pair,isPairCopy); 
                                    detailFrame.setVisible(false);
                                    detailFrame.dispose();
                                    //pairPanel.repaint();
                                }
                                });
                            }
                            center.add(pairPanel);
                            center.add(removePair);
                            center.add(reassignPair);
                        }
                    }
                }
            
            JScrollPane scroll = new JScrollPane(center);
            detailFrame.add(scroll, BorderLayout.CENTER);
            detailFrame.pack();
            detailFrame.setVisible(true);
        }
        else{//displaying a captains flights
            center.setLayout(new GridLayout(c.flights.size(), 3)); //change
                for(final Captain cap : c.captains)
                {
                    for(final Flight flight : cap.flightList)
                    {
                        if(cap.getId().equals(id))
                        {
                            final JPanel flightPanel = new JPanel(new BorderLayout());
                            {
                               JButton details = new JButton("Flight " + flight.returnName()); 
                                {
                                    details.addActionListener(new ActionListener(){
                                        public void actionPerformed(ActionEvent e){
                                            final JFrame detail = new JFrame("Flight details");
                                            JPanel panel = new JPanel();
                                            panel.add(new JLabel(flight.toString()), BorderLayout.NORTH);
                                            detail.add(panel);
                                            detail.pack();
                                            detail.setVisible(true);
                                        }
                                    });
                                }
                                flightPanel.add(details);
                            }
                            JButton removePair = new JButton("Remove flight");
                                removePair.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                        if(removeFlight(cap,flight)){
                                               flightPanel.repaint();
                                               JOptionPane.showMessageDialog(frame, "Flight removal successful. Reloading captain flight list...");
                                               detailFrame.setVisible(false);
                                               detailFrame.dispose();
                                               detailFrameSub(idCopy,isPairCopy);
                                        }
                                        else{
                                            JOptionPane.showMessageDialog(frame, "Flight removal unsuccessful.");
                                    }
                                }
                                });
                            
                            JButton reassignPair = new JButton("Reassign flight");
                            {
                                reassignPair.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) { 
                                    reassignFlight(cap,flight,isPairCopy); 
                                        detailFrame.setVisible(false);
                                        detailFrame.dispose();
                                    }
                                });
                            }
                            center.add(flightPanel);
                            center.add(removePair);
                            center.add(reassignPair);
                        }
                    }
                }
            JScrollPane scroll = new JScrollPane(center);
            detailFrame.add(scroll, BorderLayout.CENTER);
            detailFrame.pack();
            detailFrame.setVisible(true);
        }
        frame.pack();
    }
    
    public void open()
    {
        final JProgressBar progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true); 
        progressBar.setIndeterminate(true);
        fc.showOpenDialog(frame);
        JPanel loadPanel = (JPanel)loadFrame.getContentPane().getComponents()[0];
        Component[] panelComps = loadPanel.getComponents();
        int count = 0;
        for(Component comp:panelComps){
            if(comp instanceof JLabel){//remove and add component
                JLabel label = (JLabel)comp;
                label.setText("Loading file...");
                //(JLabel)panelComps[count].setText("Loading file...");
                loadFrame.repaint();
            }
            count++;
        }
        try{
             File file = fc.getSelectedFile();
                    if((file.canRead()) && (file != null)){
                    loadPanel.add(progressBar,BorderLayout.SOUTH);
                    loadFrame.repaint();
                    filePath = file.getPath();
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Beginning captain assignation...");
                    for(Component comp:panelComps){
                        if(comp instanceof JLabel){
                            JLabel label = (JLabel)comp;
                            label.setText("Assigning captains...");
                            //panelComps[count] = new JLabel("Assigning captains...");
                            loadFrame.repaint();
                        }
                    }
                    c.Start();
                    if(c.captains.size() > 0){
                    makeFrame();
                    loadFrame.dispose();
                    }
                    else{//set the progress bar hidden
                        for(Component comp:loadPanel.getComponents()){
                            if(comp instanceof JProgressBar){
                                comp.setVisible(false);    
                                loadPanel.repaint();
                        }
                    }
                        for(Component comp:loadPanel.getComponents()){
                            if(comp instanceof JLabel){
                                JLabel label = (JLabel) comp;
                                label.setText("Please select another file to load.");
                            }
                        }
                 }
                }
                 else{
                     for(Component comp:panelComps){
                        if(comp instanceof JLabel){
                            JLabel label = (JLabel)comp;
                            label.setText("Please select a file to load.");
                            //panelComps[count] = new JLabel("Assigning captains...");
                            loadFrame.repaint();
                        }
                    }
                }
        }
        catch(Exception e){
            for(Component comp:panelComps){
                if(comp instanceof JLabel){
                    JLabel newL = (JLabel)comp;
                    newL.setText("File could not be loaded. Please try a different file.");
                    loadFrame.repaint();
                }
            }
        }
    }
    
    public void removePair(Captain cap, Pair pair)
    {
        cap.removeFlight(pair);
        detailFrame.setVisible(true);
        detailFrame.repaint();
    }
        
    public void reassignPair(final Captain currentCap, final Pair pair, final boolean isPairCopy) 
    {
      final JFrame resFrame = new JFrame("Choose captain to reassign pair to:");
      final JPanel res = new JPanel(new GridLayout(2, c.captains.size()/2));
      
      for(final Captain cap:c.captains){
          JButton captain = new JButton("Captain " + cap.getId());
          captain.addActionListener(new ActionListener(){
                        Captain curr = currentCap;
                        Captain cap2 = cap;
                        Pair toChange = pair;
                    public void actionPerformed(ActionEvent e){
                        if(cap2.addPair(toChange)){//if the receiving captain can take it
                            curr.removeFlight(toChange);//remove it from the giving captain
                            toChange.setCaptain(cap2);
                            JOptionPane.showMessageDialog(resFrame, "Pair reassignment successful.");
                            resFrame.dispose();
                            detailFrameSub(currentCap.getId(),isPairCopy);
                        }
                        else{
                             JOptionPane.showMessageDialog(resFrame, "This captain cannot take this pair.");
                        }
                    }});
          res.add(captain); 
        }
        resFrame.add(res);
        resFrame.pack();
        resFrame.setVisible(true);
    }
    
    public boolean removeFlight(Captain cap, Flight flight)
    {
        return cap.removeSingleFlight(flight);
    }
    
    public void reassignFlight(final Captain currentCap, final Flight flight, final boolean isPairCopy)
    {
      final JFrame resFrame = new JFrame("Choose captain to reassign flight to:");
      JPanel res = new JPanel(new GridLayout(2, c.captains.size()/2));
      
      for(final Captain cap:c.captains){
          JButton captain = new JButton("Captain " + cap.getId());
          captain.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if(cap.addFlight(flight)){//if the receiving captain can receive it
                            if(! currentCap.removeSingleFlight(flight)){ //but the giving captain cannot afford to lose it because it is one of a pair
                                cap.removeSingleFlight(flight); //take it back off the receiving captain
                                JOptionPane.showMessageDialog(frame,"This flight cannot be reassigned - it is one of a pair. Please reassign the pair.");
                            }
                            else{
                                JOptionPane.showMessageDialog(resFrame, "Flight successfully reassigned.");
                                resFrame.dispose();
                                detailFrameSub(currentCap.getId(),isPairCopy);
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(resFrame,"The receiving captain cannot take this flight. Please try another captain.");
                        }
                    }});
          res.add(captain);
          resFrame.add(res);          
          resFrame.pack();
        }
        resFrame.pack();
        resFrame.setVisible(true);
    }
    
    private void refresh()
    {
       
    } 
    
    public void viewDetails(String id,boolean isPair)
    {
        detailFrameSub(id, isPair);
    }
  
    /**
     * Quit function: quit the application.
     */
    private void quit()
    {
        System.exit(0);
    }
    
    /**
     * Instructions
     */
    private void instructItem()
    {
        JFrame instruct = new JFrame("Instructions");
        JPanel ins = new JPanel();
        JTextArea textArea = new JTextArea("Welcome to the CO531 Software Engineering Project. To begin, load a .csv file containing the flights you wish to assign.\n After completion, it is possible to load a new .csv file by selecting File -> Open. The screen will appear to freeze while the program is loading. \nAfter the program has run to completion, it will present a list of captains. Each captain will have a list of pairs - where the captain is flying both legs of the flight - and a list of flights, which will include the flights the captain is standby for.\nIt will be possible to reallocate or delete flights or pairings as required.");
        textArea.setEditable(false);
        ins.add(textArea);
        instruct.add(ins);
        instruct.pack();
        instruct.setVisible(true);
    }
   
    public static String getFilePath()
    {
        return filePath;
    }
}