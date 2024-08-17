/**
Team 167
Thabang, Kenneth, Omolemo
Biofilm simulator prototy moc 12
bacteria class to allow the varients to exte
**/
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//abstract class to make the functions and behavior of bacteria
abstract class Bacteria {
    protected int x;
    protected int y;
    protected Color color;
//create constructor
    public Bacteria(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;}
//created method to be used by the children or extensions of the variants
    public abstract void update(int[][] Grid, List<Bacteria> newBacteria, double redGrowthRate, double blueGrowthRate, Random random);
//return red or blue
    public Color getColor() {
        return color;}
//return horizontal cordinate
    public int getX() {
        return x;}
//return vertical cordinate
    public int getY() {
        return y;}
//spread the growth of the bacteria around the gris
    protected boolean trySpread(int[][] Grid, List<Bacteria> newBacteria, Random random) {
        int[] HorizontalPlane = {-1, 0, 1, 0}; //the possible motions in the horizontal axis
        int[] VerticalPlane = {0, 1, 0, -1}; //the possible motions in the Vertical axis
        List<Integer> availableDirections = new ArrayList<>();
//possible directions
        for (int i = 0; i < 4; i++) {
			//determine the position on x
            int updateHorizontal = (x + HorizontalPlane[i] + Grid.length) % Grid.length;
			//determine the position on y
            int updateVertical = (y + VerticalPlane[i] + Grid[0].length) % Grid[0].length;
            if (Grid[updateHorizontal][updateVertical] == 0) {
                availableDirections.add(i);}
        }
//if the space is not occupied move into it
        if (!availableDirections.isEmpty()) {
			//choose direction
            int direction = availableDirections.get(random.nextInt(availableDirections.size()));
			//the new the position on x
            int updateHorizontal = (x + HorizontalPlane[direction] + Grid.length) % Grid.length;
			//the new the position on y
            int updateVertical = (y + VerticalPlane[direction] + Grid[0].length) % Grid[0].length;
            newBacteria.add(AsexualReproduction(updateHorizontal, updateVertical));
            return true;}
        return false;
    }
//make sure that bacteria multiplies
    protected abstract Bacteria AsexualReproduction(int x, int y);
}
