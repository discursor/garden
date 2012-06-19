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
    private int births;
    private int deaths;
    private int age;
    
    // Initialization
    private void InitGarden() {
        garden = new Patch[ysize][xsize];
        for(int i = 0; i < ysize; i++) {
            for(int j = 0; j < xsize; j++) {
                garden[i][j] = new Patch(this);
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
        births = 0;
        deaths = 0;
        age = 0;
        
        InitGarden();
    }
    
    
    // Getters
    public int getAge() {return age;}
    
    
    // Methods
    public void addBirth() {births++;}
    public void addDeath() {deaths++;}
    
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
    
    public int countPlants() {
        int result = 0;
        for(int i = 0; i < ysize; i++) {
            for(int j = 0; j < xsize; j++) {
                if(garden[i][j].hasPlant())
                    result++;
            }
        }
        return result;
    }
    
    public int countPlantsSimpGenP() {
        int result = 0;
        for(int i = 0; i < ysize; i++) {
            for(int j = 0; j < xsize; j++) {
                if(garden[i][j].hasPlant() && garden[i][j].getPlant().getGeneNum(0))
                    result++;
            }
        }
        return result;
    }
    
    public int countPlantsSimpGenN() {
        return plants - countPlantsSimpGenP();
    }
    
    public boolean Run() {
        for(int i = 0; i < ysize; i++) {
            for(int j = 0; j < xsize; j++) {
                garden[i][j].Run();
            }
        }
        int plantstemp = countPlants();
        boolean changed = plants != plantstemp;
        plants = plantstemp;
        age++;
        return changed;
    }
    
    public String toString() {
        return toString(false, true, false, false, false);
    }
    
    public String toString(boolean showDay, boolean showGraphic, boolean showNum,
    boolean showBirths, boolean showDeaths) {
        return toString(showDay, showGraphic, showNum, showBirths, showDeaths,
        false);
    }
    
    public String toString(boolean showDay, boolean showGraphic, boolean showNum,
    boolean showBirths, boolean showDeaths, boolean showSimpGen) {
        String result = "";
        if(showDay) {
            result += "Day ";
            result += age;
            result += "\n";
        }
        if(showGraphic) {
            for(int i = 0; i < xsize-1; i++) {
                result += "---";
            }
            result += "-\n";
        
            for(int i = 0; i < ysize; i++) {
                for(int j = 0; j < xsize; j++) {
                    if(garden[i][j].hasPlant()) {
                        if(showSimpGen){
                            if(garden[i][j].getPlant().getGeneNum(0))
                                result += "P";
                            else
                                result += "p";
                        }
                        else
                            result += "P";
                    }
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
            result += "\n";
        }
        if(showNum) {
            result += plants;
            result += " plants.";
            result += "\n";
        }
        if(showBirths) {
            result += births;
            result += " births.";
            result += "\n";
        }
        if(showDeaths) {
            result += deaths;
            result += " deaths.";
            result += "\n";
        }
        if(showSimpGen) {
            result += countPlantsSimpGenP();
            result += " B+ plants.";
            result += "\n";
            result += countPlantsSimpGenN();
            result += " B- plants.";
            result += "\n";
        }
        if(result.length() > 0)
            result = result.substring(0,result.length()-1);
        return result;
    }
    
    public void PrintGarden() {
        System.out.println(toString());
    }
    
    public void PrintGarden(boolean showDay, boolean showGraphic, boolean showNum,
    boolean showBirths, boolean showDeaths, boolean showSimpGen) {
        System.out.println(toString(showDay, showGraphic, showNum, showBirths,
        showDeaths, showSimpGen));
    }
}
