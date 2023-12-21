import java.util.Scanner;
import java.util.Random;

class Main {
    static boolean firstTime = true;
    static boolean craps = false;
    static boolean playPointGame = false;
    static int credits = 10;    //example 10credits to start
						


    public static void main(String[] args) {
        //WELCOME
        System.out.println("Welcome");

        do {
            askIfLikeToPlay();
			// (1) Credits should be printed at the end of each round
            printCredits();
            askPlayerToRoll();
            int rolled = rollDice();
            System.out.println("You rolled: " + rolled);
            checkPlayPointGame(rolled);

			// (2) should add the "rolled" parameter
            if (playPointGame) {
                playPointGame();
            } else {
                playCraps();
            }						   

        } while (!craps);
    }

    static void playCraps() {
        System.out.println("do - play craps code");
        simulateWinOrLoose();
    }

    static void playPointGame() {
        System.out.println("do - play point game code");
        simulateWinOrLoose();
    }

    static void checkPlayPointGame(int rolled) {
		// (3) "First roll" and "non-first roll" should be judged differently.						
        playPointGame = rolled != 7 && rolled != 11 && rolled != 2
                && rolled != 3 && rolled != 12;	   
    }

    static int rollDice() {
        Random random = new Random();
        int die1 = random.nextInt(6) + 1;
        int die2 = random.nextInt(6) + 1;
        int rolled = die1 + die2;
        return rolled;
    }

    static void printCredits() {
        System.out.println("Credits: " + credits);
    }

    static void askPlayerToRoll() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter to Roll");
        String userInput = scanner.nextLine();

        if (userInput.equals("")) {

        } else {
            System.out.println("quitting...");
            System.exit(-1);
        }
    }

    static void askIfLikeToPlay()        //definition
    {
        if (firstTime) {
            System.out.println("play");
			// (4) The boolean should be changed after the "first roll".
            firstTime = false;
        } else {
            System.out.println("play again");
        }
    }

    static void simulateWinOrLoose() {
		// (5) Wrong way to judge "crap" or "pointgame".
        if (Math.random() > .5) {
						
											  
            System.out.println("Simulated Win");														
			 credits += 5;		 
	} else {									 
            System.out.println("Simulated Loose");													   
            System.out.println("Setting craps to true");
            craps = true; //set craps boolean to true

            System.out.println("game will now end...");
        }
    }
}