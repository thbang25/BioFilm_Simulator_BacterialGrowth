/**
Team 167
Thabang, Kenneth, Omolemo
Biofilm simulator prototy moc 12
blue bacteria class
**/
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class BlueBacteria extends Bacteria {
	//constructor for the blue varienat
    public BlueBacteria(int x, int y) {
        super(x, y, Color.BLUE); //set the colour
    }

    @Override
    public void update(int[][] Grid, List<Bacteria> newBacteria, double redGrowthRate, double blueGrowthRate, Random random) {
		//check bacterial growth rate set
        if (random.nextDouble() < blueGrowthRate) {
			//try to spread the bacteria around the enviroment randomly
            trySpread(Grid, newBacteria, random);}
    }

    @Override
    protected Bacteria AsexualReproduction(int x, int y) {
		//make sure that bacteria multiplies
        return new BlueBacteria(x, y);}
}

