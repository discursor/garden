import java.util.Random;
/**
 * The Plant class contains all the vitals of a plants and what it does.Namely, secrete
 * a chemical into the ground to tell other plants that it's there, detect chemicals
 * that other plants secrete, and, if the detected chemical is higher than the
 * threshold, possibly die.
 * 
 * @author Ravi Tadinada 
 * @version 6/15/2012
 */
public class Plant {
    // Instance variables
    private int secretion; // amount the plant secretes
    private int threshold; // threshold level of plant death, set to the level of secretion
    private boolean alive; // true when alive, false otherwise
    private Patch ground; // the Patch the plant is on

    // Constructors
    /**
     * Constructor which allows setting of the secretion value. Sets alive to true and ground to null.
     * @param secrtionx - value the secretion is set to
     */
    
    /**
     * Sets alive to true and randomly sets the secretion to between 1 and 3
     */
    public Plant() {
        Random randomGenerator = new Random();
        int secretionx = randomGenerator.nextInt(3) + 1;
        setSecretion(secretionx);
        alive = true;
        ground = null;
    }
    
    /**
     * Same constructor as above, but sets ground to an inputted value and secretes its chemical.
     * @param groundx - Patch that ground is set to
     */
    public Plant(Patch groundx) {
        this();
        
        ground = groundx;
        this.secreteChemical();
    }
    
    
    // Setters
    /**
     * Sets the secretion. Sets the threshold level to the secretion.
     * @param secretionx - value the secretion is set to
     */
    public void setSecretion(int secretionx) {
        secretion = secretionx;
        threshold = secretion;
    }
    public void setGround(Patch groundx) {
        if(ground != null)
            ground.removePlant();
        ground = groundx;
        this.secreteChemical();
    }
    
    
    // Getters
    public int getSecretion() {return secretion;}
    public int getThreshold() {return threshold;};
    public boolean isAlive() {return alive;}
    
    
    // Methods
    /**
     * Orphans the Plant, removing it from the garden.
     */
    public void Die() {
        alive = false;
        ground = null;
    }
    
    /**
     * Simulates the plant secreting the chemical
     */
    public void secreteChemical() {
        if(this.ground != null){
            ground.spreadChemical(secretion);
        }
    }
    
    public int detectChemical() {
        return ground.getchemicalLevel();
    }
    
    /**
     * Simulates the plant. The plant has a 1/2 chance of killing itself if chemical levels go above a treshold.
     */
    public void Run() {
        Random randomGenerator = new Random();
        if(this.detectChemical() >= threshold && randomGenerator.nextInt(2) == 0) {
            this.Die();
        }
    }
}
