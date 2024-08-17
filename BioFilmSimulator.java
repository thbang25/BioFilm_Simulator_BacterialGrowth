/**
Team 167
Thabang, Kenneth, Omolemo
Biofilm simulator prototy moc 12
biofim to execute the bacteria, UI and threads
**/

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioFilmSimulator extends JFrame {
//created the constants for the simulator
   private static final int WIDTH = 700; //the width of our screen
   private static final int HEIGHT = 700;//the height of our screen
   private static final int GRID_SIZE = 100;//created the size of the Grid
   private static final int CELL_SIZE = 6; //the magnitude of the bacteria
   private static final int NUM_THREADS = 4; //setting threads
//instances
   private int[][] Grid;//the Grid for the bacteria to spread in 2d so far
   private List<Bacteria> bacteria; //bacteria
   private Random random; //randomness of the spread
   private ExecutorService executorService;
   private Timer timer;//to refresh simulator
   private boolean isSimulating = false;//look if its running
   private boolean isSpreadingOnGrid = true; //try to spread
//my user interface componets for the user to interact with
   private JButton startButton;//start
   private JButton pauseButton;//pause
   private JButton resumeButton;//resume simulator
   private JButton resetButton;//go back to default or start
   private JButton stopButton;//end but ask first
   private JTextField redBacteriaCountField;
   private JTextField blueBacteriaCountField;
   private JTextField redGrowthRateField;
   private JTextField blueGrowthRateField;
   private JPanel simulationPanel;//the area
   private JLabel redBacteriaCounterLabel;//set their labels for us to dispkau them on screen
   private JLabel blueBacteriaCounterLabel;
//set the default values just incase the wrong values or the user opts to start
   private int RedBacteriaCount = 35;
   private int BlueBacteriaCount = 45;
   private double redGrowthRate = 0.1;
   private double blueGrowthRate = 0.1;
  //setting up my bacteria counters
   private int blueCounter = 0;
   private int redCounter = 0;

   public BioFilmSimulator() {
      
     //setup the screen for the biofilm
      setTitle("Biofilm Simulation"); //simulator name
      setSize(WIDTH, HEIGHT);//parameters of the screen
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//end if its closed
      setLocationRelativeTo(null);//put it in the center
   
      Grid = new int[GRID_SIZE][GRID_SIZE];//the enviroment parameters
      bacteria = new ArrayList<>();//bacteria
      random = new Random();//randomizer
      executorService = Executors.newFixedThreadPool(NUM_THREADS);//start the threads
   
    //start Interface and the enviroment
      setupUI();
      initializeGrid();
   }

   private void setupUI() {
      setLayout(new BorderLayout());//set up the view for the enviroment
   
   //show how the interactions in the simulation
      simulationPanel = 
         new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
               super.paintComponent(g);
               drawGrid(g); //produce the enviroment
            }
         };
   //creating the desired size for the panel
      simulationPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT - 100));
   //the buttons for the controls
      JPanel controlPanel = new JPanel(new FlowLayout());
   //created the panel for the acepted input
      JPanel inputPanel = new JPanel(new GridLayout(4, 2));
    
    // Initialize counter labels
      redBacteriaCounterLabel = new JLabel("Red Bacteria Count: 0");
      blueBacteriaCounterLabel = new JLabel("Blue Bacteria Count: 0");
   
    // Create a panel for the counters
      JPanel counterPanel = new JPanel(new FlowLayout());
      //add red to the panel
      counterPanel.add(redBacteriaCounterLabel);
      //add bleu to the panel
      counterPanel.add(blueBacteriaCounterLabel);
   
    // Add the counter panel to the top of the frame
      add(counterPanel, BorderLayout.NORTH);
   
   //created and initialize the control buttons
      startButton = new JButton("Start");
      pauseButton = new JButton("Pause");
      resumeButton = new JButton("Resume");
      resetButton = new JButton("Reset");
      stopButton = new JButton("Stop");
      
   //initialize the input spaces
      redBacteriaCountField = new JTextField(5);
      blueBacteriaCountField = new JTextField(5);
      redGrowthRateField = new JTextField(5);
      blueGrowthRateField = new JTextField(5);
   
   //add the action listeners for each buttons
      startButton.addActionListener(e -> startSimulation());
      pauseButton.addActionListener(e -> pauseSimulation());
      resumeButton.addActionListener(e -> resumeSimulation());
      resetButton.addActionListener(e -> resetSimulation());
      stopButton.addActionListener(e -> stopSimulation());
   
   //add the buttons to the designed control panel
      controlPanel.add(startButton);
      controlPanel.add(pauseButton);
      controlPanel.add(resumeButton);
      controlPanel.add(resetButton);
      controlPanel.add(stopButton);
      
   //add the input and the name of the fields to the inout space
      inputPanel.add(new JLabel("Red Bacteria Count:"));
      inputPanel.add(redBacteriaCountField);
      inputPanel.add(new JLabel("Red Bacteria Growth Rate:"));
      inputPanel.add(redGrowthRateField);
      inputPanel.add(new JLabel("Blue Bacteria Count:"));
      inputPanel.add(blueBacteriaCountField);
      inputPanel.add(new JLabel("Blue Bacteria Growth Rate:"));
      inputPanel.add(blueGrowthRateField);
   
   //added the bitton for the section at the end of the screen
      JPanel bottomPanel = new JPanel(new BorderLayout());
   //added the input panel at the centre
      bottomPanel.add(inputPanel, BorderLayout.CENTER);
    //control panel
      bottomPanel.add(controlPanel, BorderLayout.SOUTH);
   //simulation oanel to the centre of the screen
      add(simulationPanel, BorderLayout.CENTER);
    //the screen panel to the end of the screen
      add(bottomPanel, BorderLayout.SOUTH);
   
   //refresh for new instances of simulation data
      timer = new Timer(100, 
         e -> {
            updateGrid(); //refresh enviroment
            simulationPanel.repaint();
         });
   //uodate the button states based on how the simulation is running at the moment
      updateButtonStates();
   }

   private void initializeGrid() {
      // Initialize the Grid with zeros
      for (int i = 0; i < GRID_SIZE; i++) {
         for (int j = 0; j < GRID_SIZE; j++) {
            Grid[i][j] = 0;
         }
      }
   
      bacteria.clear();
     // Place red bacteria
      placeBacteria(RedBacteriaCount, 2);
      // Place blue bacteria	  
      placeBacteria(BlueBacteriaCount, 1); 
      isSpreadingOnGrid = true;}

   private void placeBacteria(int count, int type) {
      int placed = 0;
      while (placed < count) {
         int x = random.nextInt(GRID_SIZE);
         int y = random.nextInt(GRID_SIZE);
         if (Grid[x][y] == 0) {//the enviroment has space
         //create type of bacteria
            Bacteria b = type == 1 ? new BlueBacteria(x, y) : new RedBacteria(x, y);
            bacteria.add(b);
         //deploy time
            Grid[x][y] = type;
            placed++;
            //blueCounter++; //attempt a counter
         }
      }
   }

   private void updateGrid() {
      //update the grid enviroment
      int[][] newGrid = new int[GRID_SIZE][GRID_SIZE];
      List<Bacteria> newBacteria = new ArrayList<>(bacteria);
   //check if we are spreading the bacteria
      if (isSpreadingOnGrid) {
         for (Bacteria b : bacteria) {
            b.trySpread(Grid, newBacteria, random);
         }
         isSpreadingOnGrid = false;
      } else {
         for (Bacteria b : bacteria) {
            b.update(Grid, newBacteria, redGrowthRate, blueGrowthRate, random);
         }
      }
   //create the red bacteria type  
      bacteria = newBacteria;
      for (Bacteria b : bacteria) {
         newGrid[b.getX()][b.getY()] = b instanceof RedBacteria ? 2 : 1;
         //redCounter++; //count red
      }
      //created a loop that checks how many times the enviroment spawns bacteria
      for (Bacteria b : bacteria) {
         if (b instanceof RedBacteria) {//count if its red
            redCounter++;
         } else if (b instanceof BlueBacteria) {//count if its blue
            blueCounter++;
         }
      }
      //shpw the text for the counters, we want it to update the counter as the grid updates as well
      redBacteriaCounterLabel.setText("Red Bacteria Count: " + redCounter);
      blueBacteriaCounterLabel.setText("Blue Bacteria Count: " + blueCounter);
      
      Grid = newGrid;}

   private void drawGrid(Graphics g) {
     //create the templates for the grid enviroment
      g.setColor(Color.WHITE); //colour white for unocuppied
      g.fillRect(0, 0, getWidth(), getHeight());
   
      for (Bacteria b : bacteria) {
         g.setColor(b.getColor());
         g.fillRect(b.getX() * CELL_SIZE, b.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);}
   }

   private void startSimulation() {
      if (!isSimulating) {
         try {
            // Read user inputs
            RedBacteriaCount = Integer.parseInt(redBacteriaCountField.getText());
            BlueBacteriaCount = Integer.parseInt(blueBacteriaCountField.getText());
            redGrowthRate = Double.parseDouble(redGrowthRateField.getText());
            blueGrowthRate = Double.parseDouble(blueGrowthRateField.getText());
         
            // Validate inputs
            if (RedBacteriaCount < 0 || BlueBacteriaCount < 0 || redGrowthRate < 0 || blueGrowthRate < 0) {
               throw new NumberFormatException("Values must be non-negative.");
            }
         } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Starting with default values.");
            // Use default values if input is invalid
            RedBacteriaCount = 100;
            BlueBacteriaCount = 100;
            redGrowthRate = 0.1;
            blueGrowthRate = 0.1;
         }
      
         initializeGrid(); //start enviroment with the updated values
         isSimulating = true; //create running simulation flag
         timer.start(); //beigin the timer
         updateButtonStates(); //refresh the states of each button
      }
   }

   private void pauseSimulation() {
      if (isSimulating) {
        //simulation to temporary stop
         isSimulating = false;
       //stop the timer
         timer.stop();
       //refresh the state of each button
         updateButtonStates();}
   }

   private void resumeSimulation() {
      if (!isSimulating) {
        //simulation now is going to continue
         isSimulating = true;
       //continue timer
         timer.start();
       //refresh the state of each button
         updateButtonStates();}
   }

   private void resetSimulation() {
     //pause the simulation
      pauseSimulation();
     //restart enviroment
      initializeGrid();
     //paint again
      simulationPanel.repaint();
     //refresh the state of each button
      updateButtonStates();
      //reset the counters to 0 if reset is pressed
      redCounter = 0;
      blueCounter = 0;
   }
   
   private void stopSimulation() {
   // Stop the timer and exit the application
      pauseSimulation();
      int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
      if (response == JOptionPane.YES_OPTION) {
         System.exit(0); // Exit the application
      } else {
         updateButtonStates();
      }
   }

   private void updateButtonStates() {
     //refresh or update the state of each of the buttons based on the state of the simulation
      startButton.setEnabled(!isSimulating);
      pauseButton.setEnabled(isSimulating);
      resumeButton.setEnabled(!isSimulating);
      resetButton.setEnabled(true);
      stopButton.setEnabled(true);
      redBacteriaCountField.setEnabled(!isSimulating);
      blueBacteriaCountField.setEnabled(!isSimulating);
      redGrowthRateField.setEnabled(!isSimulating);
      blueGrowthRateField.setEnabled(!isSimulating);}

   public static void main(String[] args) {
      SwingUtilities.invokeLater(
         () -> {
          //create the instance of the simulator
            BioFilmSimulator simulation = new BioFilmSimulator();
         //create the sreen
            simulation.setVisible(true);
         });
   }
}
