import java.util.Scanner;
import java.util.Random;

class Main {
    static boolean firstTime = true;
    static boolean craps = false;
    static boolean playPointGame = false;
    static int credits = 10;    //example 10credits to start
    static int lastRoll;


    public static void main(String[] args) {
        //WELCOME
        System.out.println("Welcome");

        do {
            askIfLikeToPlay();						   
            askPlayerToRoll();
            int rolled = rollDice();
            System.out.println("You rolled: " + rolled);
            checkPlayPointGame(rolled);

			// (2) add the "rolled" parameter
            if (playPointGame) {
                playPointGame(rolled);
            } else {
                playCraps(rolled);
            }
			// (1) print credits
            printCredits();

        } while (!craps);
    }

    static void playCraps(int rolled) {
        System.out.println("do - play craps code");
        simulateWinOrLoose(rolled);
    }

    static void playPointGame(int rolled) {
        System.out.println("do - play point game code");
        simulateWinOrLoose(rolled);
    }

    static void checkPlayPointGame(int rolled) {
		// (3) Depending on whether it is the "first roll" or not, the judgement is different.
        if (firstTime) {
            playPointGame = rolled != 7 && rolled != 11 && rolled != 2
                    && rolled != 3 && rolled != 12;
        }else {
            playPointGame = rolled != 7 && rolled != lastRoll;
        }
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
        } else {
            System.out.println("play again");
        }
    }

    static void simulateWinOrLoose(int rolled) {
        // (5) Different rules for "first time" and "non-first time"
        if (firstTime) {
            if (rolled == 7 || rolled == 11) {
                System.out.println("Simulated Win!");
                System.out.println("Setting craps to true");
                craps = true; //set craps boolean to true
                credits += 5;
                System.out.println("game will now end...");
            } else if (rolled == 2 || rolled == 3 || rolled == 12) {
                System.out.println("Simulated Loose!");
                System.out.println("Setting craps to true");
                craps = true; //set craps boolean to true
                credits -= 5;
                System.out.println("game will now end...");
            } else {
                System.out.println("Simulated Point Game");
                System.out.println("Setting playPointGame to true");
                System.out.println("game will go on...");
                // (6) If the game needs to continue, record the number of points for this time.
                lastRoll = rolled;
            }
			// (4) The boolean should be changed after the "first roll".
            firstTime = false;
        } else {
            if (rolled == lastRoll) {
                System.out.println("Simulated Win!");
                System.out.println("Setting craps to true");
                craps = true; //set craps boolean to true
                credits += 5;
                System.out.println("game will now end...");
            } else if (rolled == 7) {
                System.out.println("Simulated Loose!");
                System.out.println("Setting craps to true");
                craps = true; //set craps boolean to true
                credits -= 5;
                System.out.println("game will now end...");
            } else {
                System.out.println("Simulated Point Game");
                System.out.println("Setting playPointGame to true");
                playPointGame = true;
                System.out.println("game will go on...");
                lastRoll = rolled;
            }
        }
    }
}