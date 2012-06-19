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
    // Properties    
    private Patch ground; // the Patch the plant is on
    private int age; // age of the plant in days, starts at 0
    private boolean alive; // true when alive, false otherwise
    private boolean[] genes; // the genetic information of the plants (see detailed information on what gene controls what in setTraits())
    private final int numGenes = 1; // number of genes in the plant
    
    //Traits
    private int secretion; // amount the plant secretes
    private int threshold; // threshold level of plant death, set to the level of secretion
    private int budAge; // the age at which the plant's fertile period starts
    private int budChance; // chance, during its fertile period, the plant will disperse a seed (1 out of X)
    private int dieAge; // the "death age" after which the plant has a chance of dying from old age
    private int dieChance; // chance, after a "death age", the plant will die (1 out of X)
    
    // Constructors    
    /**
     * The generic constructor of a Plant
     */
    public Plant() {
        ground = null;
        age = 0;
        alive = true;
        genes = new boolean[numGenes];
        Random randomGenerator = new Random();
        for(int i = 0; i < numGenes; i++)
            genes[i] = randomGenerator.nextInt(2) == 0;
        setTraits();
    }
    
    public Plant(boolean[] genex, Patch groundx) {
        this();
        genes = genex;
        setGround(groundx);
    }
    
    public Plant(boolean[] genex) {
        this();
        genes = genex;
    }
    
    /**
     * Same constructor as above, but sets ground to an inputted value and secretes its chemical.
     * @param groundx - Patch that ground is set to
     */
    public Plant(Patch groundx) {
        this();
        setGround(groundx);
    }
    
    public Plant(Plant parent, Patch groundx) {
        this();
        for(int i = 0; i < numGenes; i++)
            genes[i] = parent.genes[i];
        setTraits();
        setGround(groundx);
    }
    
    
    // Private Methods
    /**
     * Sets the secretion. Sets the threshold level to the secretion.
     * @param secretionx - value the secretion is set to
     */
    private void setSecretion(int secretionx) {
        secretion = secretionx;
        threshold = secretion;
    }
    
    /**
     * Private method which sets the traits of the Plant
     * genes[0] affects the budChance, setting it to 2, if it's true and 4, if it's false;
     */
    private void setTraits() {
        setSecretion(2);
        budAge = 4;
        if(genes[0])
            budChance = 2;
        else
            budChance = 4;
        dieAge = 10;
        dieChance = 3;
    }
    
    
    // Setters
    public void setGround(Patch groundx) {
        if(ground != null)
            ground.removePlant();
        ground = groundx;
        secreteChemical();
    }
    
    
    // Getters
    public int getSecretion() {return secretion;}
    public boolean[] getGenes() {return genes;}
    public boolean getGeneNum(int num) {return genes[num];}
    public boolean isAlive() {return alive;}
    
    
    // Methods
    /**
     * Orphans the Plant, removing it from the garden.
     */
    public void Die() {
        alive = false;
        ground.getGarden().addDeath();
        ground = null;
    }
    
    public void Bud() {
        Random randomGenerator = new Random();
        while(true) {
            int p1 = randomGenerator.nextInt(8);
            int p2 = randomGenerator.nextInt(8);
            
            if(ground.getNeighborLoc(p1) != null && ground.getNeighborLoc(p1).getNeighborLoc(p2) != null) {
                Patch temp = ground.getNeighborLoc(p1).getNeighborLoc(p2);
                if(!(ground.getNeighborLoc(p1).getNeighborLoc(p2).hasPlant())) {
                    ground.getNeighborLoc(p1).getNeighborLoc(p2).growPlant(this);
                    ground.getGarden().addBirth();
                    break;
                }
            }
        }
    }
    
    /**
     * Simulates the plant secreting the chemical
     */
    public void secreteChemical() {
        if(ground != null){
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
        if(detectChemical() >= threshold && randomGenerator.nextInt(2) == 0) {
            Die();
            return;
        }
        if(age > 4 && randomGenerator.nextInt(budChance) == 0) {
            Bud();
        }
        if(age > 10 && randomGenerator.nextInt(dieChance) == 0) {
            Die();
        }
        age++;
    }
}
