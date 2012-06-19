import java.util.Scanner;
import java.io.*;
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
            
            boolean showDay = true;
            boolean showGraphic = true;
            boolean showNum = true;
            boolean showBirths = true;
            boolean showDeaths = true;
            if(!getYN(userInput, "Would you like to see all of the step by step displays (y/n)? ")) {
                while(true) {
                    showDay = getYN(userInput, "Would you like to see the day step by step (y/n)? ");
                    showGraphic = getYN(userInput, "Would you like to see the garden step by step (y/n)? ");
                    showNum = getYN(userInput, "Would you like to see the number of plants step by step (y/n)? ");
                    showBirths = getYN(userInput, "Would you like to see the number of births step by step (y/n)? ");
                    showDeaths = getYN(userInput, "Would you like to see the number of deaths step by step (y/n)? ");
                    
                    if(!showGraphic && !showNum && !showBirths && !showDeaths)
                        System.out.println("You must select at least one.");
                    else
                        break;
                }
            }
            boolean showSimpGen = getYN(userInput, "Would you like to see the change in allele frequencies (y/n)? ");
            if(showSimpGen)
                System.out.println(
                "The plants with the positive allele (B+) have a 1 in 2 chance of reproducing\n" +
                "when in their prime. Plants with the negative allele (B-) have a 1 in 4 cha-\n" +
                "ce of reproducing in their prime. If you chose to view the garden each iter-\n" +
                "ation, then B+ plants will be represented by \"P\" and the B- plants by \"p\".");
            int frequency = getUserNum(userInput, "At what frequency would you like to see the display (every X days)? ");
            loop(xsize, ysize, plants, showDay, showGraphic, showNum, showBirths, showDeaths, showSimpGen, frequency);
            boolean done = !getYN(userInput, "Would you like to run the simulator again (y/n)? ");
            System.out.println();
            System.out.println();
            if(done)
                break;
            
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
    
    static void loop(int xsize, int ysize, int plants, boolean showDay, boolean showGraphic, boolean showNum,
    boolean showBirths, boolean showDeaths, boolean showSimpGen, int frequency) {
        Garden garden = new Garden(xsize, ysize, plants);
        
        while(true) {
            if(garden.getAge() % frequency == 0) {
                System.out.println();
                garden.PrintGarden(showDay, showGraphic, showNum, showBirths, showDeaths, showSimpGen);
                System.out.print("Press enter to continue of 'e' to exit: ");
                char temp = '\0';
                try {
                    temp = (char)System.in.read();
                }
                catch(IOException e) {
                    System.out.println("Error");
                }
                if(temp != 10)
                    break;
                try {
                    while (System.in.available() > 0)
                        System.in.read();
                }
                catch(IOException e) {
                    System.out.println("Error");
                }
            }
            garden.Run();
        }
    }
}
