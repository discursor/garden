import java.util.Scanner;
/**
 * Write a description of class simulator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class simulator {
    public static void main(String[] args) {
        while(true) {
            Scanner userInput = new Scanner(System.in);
        
            int xsize = getUserNum(userInput, "Enter x-dimension: ");
            int ysize = getUserNum(userInput, "Enter y-dimension: ");
            int plants = getUserNum(userInput, "Enter number of plants: ");
        
            boolean showGraphic = false;
            boolean showNum = false;
            if(getYN(userInput, "Would you like to see the step by step of the run through (y/n)? ")) {
                showGraphic = getYN(userInput, "Would you like to see the garden step by step (y/n)? ");
                showNum = getYN(userInput, "Would you like to see the number of plants step by step (y/n)? ");
            }
        
            System.out.println();
            loop(xsize, ysize, plants, showGraphic, showNum);
            System.out.println();
            if(!getYN(userInput, "Would you like to run the simulator again (y/n)? "))
                break;
            System.out.println();
            System.out.println();
        }
    }
    
    static int getUserNum(Scanner userInput, String prompt) {
        int result = 0;
        while(true) {
            boolean done = true;
            System.out.print(prompt);
            try {
                result = Integer.parseInt(userInput.next());
            }
            catch(NumberFormatException e) {
                done = false;
            }
            if(done && result > 0)
                return result;
            System.out.println("Try again.");
        }
    }
    
    static boolean getYN(Scanner userInput, String prompt) {
        while(true) {
            System.out.print(prompt);
            String input = userInput.next();
            if(input.charAt(0) == 'y' || input.charAt(0) == 'Y')
                return true;
            if(input.charAt(0) == 'n' || input.charAt(0) == 'N')
                return false;
            System.out.println("Try again.");
        }
    }
    
    static void loop(int xsize, int ysize, int plants, boolean showGraphic, boolean showNum) {
        Garden garden = new Garden(xsize, ysize, plants);
        System.out.println("Start: ");
        garden.PrintGarden(true, true);
        System.out.println();
        
        while(true) {
            if(!garden.Run())
                break;
            garden.PrintGarden(showGraphic, showNum);
        }
        
        System.out.println();
        System.out.println("End: ");
        garden.PrintGarden(true, true);
    }
}
