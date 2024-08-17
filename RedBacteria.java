/**
Team 167
Thabang, Kenneth, Omolemo
Biofilm simulator prototy moc 12
red bacteria class
**/
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class RedBacteria extends Bacteria {
	//constructor for the red variant
    public RedBacteria(int x, int y) {
        super(x, y, Color.RED); //set colour of the variant to red
    }

    @Override
    public void update(int[][] Grid, List<Bacteria> newBacteria, double redGrowthRate, double blueGrowthRate, Random random) {
        if (random.nextDouble() < redGrowthRate) {//check growth rate
            trySpread(Grid, newBacteria, random);} //spread variant around randomly in the enviroment

        // Movement for red bacteria
        if (random.nextDouble() < 0.05) {
            int[] HorizontalPlane = {-1, 0, 1, 0}; //the possible motions in the horizontal axis
            int[] VerticalPlane = {0, 1, 0, -1}; //the possible motions in the Vertical axis
            int direction = random.nextInt(4); //do a random selection of possible motions
            int updateHorizontal = (x + HorizontalPlane[direction] + Grid.length) % Grid.length; //determine the position on x
            int updateVertical = (y + VerticalPlane[direction] + Grid[0].length) % Grid[0].length; //determine the position on y
            //spawn into Grid cell if the slot is empty
            if (Grid[updateHorizontal][updateVertical] == 0) {
                x = updateHorizontal;
                y = updateVertical;}//end if
        }
    }

    @Override
    protected Bacteria AsexualReproduction(int x, int y) {
		//make sure that bacteria multiplies
        return new RedBacteria(x, y);}
}
