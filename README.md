# BioFilm_Simulator_BacterialGrowth

## Project Overview

This project simulates the growth and interaction of two types of bacteria Red and Blue within a biofilm environment. The bacteria exhibit different behaviors such as motility and reproduction, and they interact with randomly placed nutrients that enhance their growth. The environment is modeled as a grid where the bacteria can spread, reproduce, and leave behind pheromone trails (Psl trails). The simulation is managed by the BioFilmSimulator class, which stages the behavior of bacteria and nutrient interactions.


## Table of Contents

- Project Overview
- How to Run
- Class Descriptions
    - BioFilmSimulator
    - Bacteria
    - BlueBacteria
    - RedBacteria
    - Nutrient      
- Features
- Future Improvements

## How to Start Simulation

Here are the instructions:
1. Clone or download the repository.
2. Compile the project using a Java development environment (e.g., JGrasp, Eclipse, or the command line).
3. Run the BioFilmSimulator class to start the simulation.
4. You can adjust parameters such as the growth rate, number of bacteria, temperature, PH, and nutrients within the simulation.

## Class Descriptions

# BioFilmSimulator
The BioFilmSimulator class functions as the primary controller of the simulation, responsible for initializing the grid, placing bacteria and nutrients, and updating the simulation's state over time.

*Code Description*:
This code initializes the grid environment and places bacteria and nutrients. It also updates bacteria positions, manages bacterial reproduction, and handles interactions between bacteria and nutrients, promoting growth when bacteria encounter nutrients. Additionally, it manages the main simulation loop and user-defined parameters such as the number of bacteria, growth rates, temperature, pH, and nutrient count.

# Bacteria
The Bacteria class is an abstract class that defines bacteria's core functionality and behavior. Both the BlueBacteria and RedBacteria classes extend this abstract class.

# Attributes:
- x, y: The horizontal and vertical coordinates of the bacterium in the grid.
- color: The color used to differentiate bacteria types.
- pslTrail: A list of coordinates representing the pheromone trail left by the bacteria.

# Methods:
- update(): Abstract method implemented by subclasses to define their specific growth behavior.
- trySpread(): Attempts to spread the bacteria to a nearby space.
- AsexualReproduction(): Abstract method for bacteria reproduction.
- addTrail(): Adds the current position to the bacterium’s pheromone trail.
