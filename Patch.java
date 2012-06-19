
/**
 * The Patch is the fundemntal unit that the Garden is made up of. A patch can
 * absorb chemicals, spread chemicals, and decompose dead plants. It knows if
 * it has a plant on it, what neighbors it has, and contains information on how
 * much chemical it has. Running the patch runs whatever occupant it has.
 * 
 * @author Ravi Tadinada 
 * @version (a version number or a date)
 */
public class Patch {
    // Instance variables
    private Plant occupant; // the occupying plant of the patch (null if no plant)
    private int chemicalLevel; // the amount of secretion abosrbed by the plan
    private Patch[] neighbors; // the neighboring Patches of dirt
    private Garden garden;
    
    // Constructor
    public Patch() {
        // initialise instance variables
        occupant = null;
        garden = null;
        chemicalLevel = 0;
        neighbors = new Patch[8];
        for(int i = 0; i < 8; i++)
            neighbors[i] = null;
    }
    
    public Patch(Garden gardenx) {
        this();
        garden = gardenx;
    }
    
    // Setters
    public void setPlant(Plant occupantx) {occupant = occupantx;}
    public void setGarden(Garden gardenx) {garden = gardenx;}
    public void setchemicalLevel(int chemicalLevelx) {chemicalLevel = chemicalLevelx;}
    public void setNeighbors(Patch[] neighborsx) {neighbors = neighborsx;}
    public void setNieghborLoc(Patch neighbor, int location) {neighbors[location] = neighbor;}
    
    // Getters
    public Plant getPlant() {return occupant;}
    public Garden getGarden() {return garden;}
    public int getchemicalLevel() {return chemicalLevel;}
    public boolean hasPlant() {return occupant != null;}
    public Patch[] getNeighbors() {return neighbors;}
    public Patch getNeighborLoc(int location) {return neighbors[location];}

    // Methods
    public void addChemical(int addition) {chemicalLevel += addition;}
    public void subChemical(int subtraction) {chemicalLevel -= subtraction;}
    
    /**
     * Creates an occupant for the Patch
     */
    public void growPlant() {
        Plant temp = new Plant(this);
        occupant = temp;
    }
    
    public void growPlant(Plant parent) {
        Plant temp = new Plant(parent, this);
        occupant = temp;
    }
    
    /**
     * Orphans the Plant, removing it from the garden
     */
    public void removePlant() {
        this.removeChemical(occupant.getSecretion());
        occupant = null;
    }
    
    /**
     * Spreads the chemical to the neighboring Patches
     */
    public void spreadChemical(int amount) {
        for(int i = 0; i < 8; i++) {
            if(neighbors[i] != null)
                neighbors[i].addChemical(amount);
        }
    }
    
    /**
     * Removes the chemical from the neighboring Patches
     */
    public void removeChemical(int amount) {
        if(this.hasPlant()) {
            for(int i = 0; i < 8; i++) {
                if(neighbors[i] != null)
                    neighbors[i].subChemical(amount);
            }
        }
    }
    
    /**
     * Simulates the Patch and its occupant. If the occupant is dead, removes the Plant. Returns 0 if no plant is removed, returns  1, if a plant is removed.
     */
    public void Run() {
       if(this.hasPlant()) {
            occupant.Run();
            if(!occupant.isAlive()) {
                this.removePlant();
            }
       }
    }
}
