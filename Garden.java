import java.util.Random;
/**
 * The Garden class is a 2D array of Patches. It sets up all of the Patches with their
 * neighbors, runs each patch, and can print out an image of the the 2D Patch array,
 * showing where there are Plants and where not.
 * 
 * @author Ravi Tadinada 
 * @version 6/15/2012
 */
public class Garden
{
    // Instance variables
    private Patch[][] garden;
    private int xsize;
    private int ysize;
    private int plants;
    
    // Initialization
    private void InitGarden() {
        garden = new Patch[ysize][xsize];
        for(int i = 0; i < ysize; i++) {
            for(int j = 0; j < xsize; j++) {
                garden[i][j] = new Patch();
            }
        }
        for(int i = 0; i < ysize; i++) {
            for(int j = 0; j < xsize; j++) {
                createNeighbors(j,i);
            }
        }
        
        if(plants < xsize*ysize) {
            Random randomGenerator = new Random();
            for(int i = 0; i < plants; i++) {
                int y, x;
                while(true) {    
                    y = randomGenerator.nextInt(ysize);
                    x = randomGenerator.nextInt(xsize);
                    if(!garden[y][x].hasPlant()) {
                        garden[y][x].growPlant();
                        break;
                    }
                }
            }
        }
        else {
            for(int i = 0; i < ysize; i++) {
                for(int j = 0; j < xsize; j++){
                    garden[i][j].growPlant();
                }
            }
            plants = xsize*ysize;
        }
    }

    
    // Constructor
    public Garden(int xsizex, int ysizex, int plantsx) {
        xsize = xsizex;
        ysize = ysizex;
        plants = plantsx;
        
        InitGarden();
    }
    
    
    // Methods
    public void createNeighbors(int x, int y) {
        int iter = 0;
        for(int i = y-1; i < y+2; i++) {
            for(int j = x-1; j < x+2; j++) {
                if(!(i == y && j == x)) {
                    if(i >= 0 && i < ysize && j >= 0 && j < xsize) {
                        garden[y][x].setNieghborLoc(garden[i][j], iter);
                    }
                    iter++;
                }
            }
        }
    }
    
    public boolean Run() {
        boolean change = false;
        for(int i = 0; i < ysize; i++) {
            for(int j = 0; j < xsize; j++) {
                if(garden[i][j].Run()) {
                    plants--;
                    change = true;
                }
            }
        }
        return change;
    }
    
    public String toString() {
        return this.toString(true, false);
    }
    
    public String toString(boolean showGraphic, boolean showNum) {
        String result = "";
        if(showGraphic) {
            for(int i = 0; i < xsize-1; i++) {
                result += "---";
            }
            result += "-\n";
        
            for(int i = 0; i < ysize; i++) {
                for(int j = 0; j < xsize; j++) {
                    if(garden[i][j].hasPlant())
                        result += "P";
                    else
                        result += ".";
                    result += "  ";
                }
                result += "\n";
            }
        
            for(int i = 0; i < xsize-1; i++) {
                result += "---";
            }
            result += "-";
            if(showNum)
                result += "\n";
        }
        if(showNum) {
            result += plants;
            result += " plants.";
        }
        return result;
    }
    
    public void PrintGarden() {
        System.out.println(this.toString());
    }
    
    public void PrintGarden(boolean showGraphic, boolean showNum) {
        System.out.println(this.toString(showGraphic, showNum));
    }
}
