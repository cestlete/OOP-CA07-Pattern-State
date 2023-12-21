import java.util.*;
class Runner{
    public static void main(String[] args){
        GameController game = new GameController();
        game.start();
    }
}
class GameController{
    static Map<String, CrapsGameState> possibleStates = new HashMap<String, CrapsGameState>();
    static CrapsGameState gameState;
    static int credits;


    GameController(){
        possibleStates.put( "CRAPS", new CrapsState() );
        possibleStates.put( "POINT", new PointState() );
        possibleStates.put( "WIN", new WinState() );
        possibleStates.put( "LOOSE", new LooseState() );
        gameState = possibleStates.get("CRAPS");
        credits = 30;
    }
    void start(){
        while(true){
            UIHandler.askPlayerToRoll();
            play( UIHandler.userInputTestRollValue() );
        }
    }
	
    void play(int rolled){
        gameState.play(rolled); //"delegation" to this.gameState.play() (i.e. INITIAL 1st time)
    }
    static void setState(String gameStateName){
        System.out.println("setState( :"+gameStateName+")");
        gameState = getState(gameStateName);
        gameState.doEnterStateActivity();
    }
    static void setState(String gameStateName, int rollValue){
        CrapsGameState passedIn = getState(gameStateName);

        // ***********************************************
        if(passedIn instanceof WinState || passedIn instanceof LooseState){
            passedIn.play(rollValue);
			// (1) "Lose" or "win", exit.
            gameState.doExitStateActivity();
        }

        if(passedIn instanceof PointState){
            ((PointState)passedIn).storePointValueAs(rollValue);
            // （5）Adjust the position of this method
            setState(gameStateName);
        }
    }
    static CrapsGameState getState(String gameStateLookUp){
        return possibleStates.get(gameStateLookUp);
    }
}

abstract class CrapsGameState{
    void play(int rolled){
        System.out.println( "CrapsGameState.play()" );
        if( rolled == 7 || rolled == 11){
            // now has 2 params
            GameController.setState("WIN", rolled);
  
        }else if ( rolled == 2 || rolled == 3 || rolled == 12 ){
            GameController.setState("LOOSE", rolled);
        }else {
            GameController.setState("POINT", rolled );
        }
    }
 
							 
							  

    void doEnterStateActivity(){
        ; //default: entry/doNothing


    }
    void doExitStateActivity(){
        ; //default: entry/doNothing
		// (2) exit game
        System.out.println("quitting...");
        System.exit(-1);
    }

    public String toString(){
        return this.getClass().getSimpleName();
    }
}

class CrapsState extends CrapsGameState{
    void play(int rolled){
        System.out.println( "CrapsState.play() - calling on..." );
        super.play(rolled);
    }
}

class WinState extends CrapsGameState{

    void play(int rolled){
        System.out.println( "WinState.play()- rolled: "+rolled );
        System.out.println( "increase credits");
        GameController.credits+= 10;
        System.out.println("Credits remaining: "+ GameController.credits);
        GameController.setState("CRAPS");
    }
}

class LooseState extends CrapsGameState{

    void play(int rolled){
        System.out.println( "LooseState.play() - rolled: "+rolled );
        System.out.println( "decrease credits");
        if (GameController.credits == 10)
        {
            System.out.println( "0 credits remaining. Game Over.");
            System.exit(0);
        }
        else{
            GameController.credits-= 10;
            System.out.println("Credits remaining: "+ GameController.credits);
            GameController.setState("CRAPS");
        }
    }
}
class PointState extends CrapsGameState{

    int pointValue;

    void play(int rolled){
        System.out.println( "PointState.play()");
		// (3) The setState() method should be called with two arguments.
        if( rolled == pointValue ){
            resetPointValue();
            GameController.setState("WIN", rolled);
        }else if( rolled == 7 ){
            resetPointValue();
            GameController.setState("LOOSE", rolled);
        }else{
            ; //do nothing
			// (4) Setting the "POINT" state
            GameController.setState("POINT", rolled );
        }
    }

    void storePointValueAs(int rolled){
        pointValue = rolled;
    }
    void resetPointValue(){
        pointValue = 0;
    }
}
class Dice{
    static int rollDice()
    {
        Random random = new Random();
        int die1 = random.nextInt(6) + 1;
        int die2 = random.nextInt(6) + 1;
        int rolled = die1 + die2;
        System.out.println("...rolled: "+rolled);
        return rolled;
    }
}
class UIHandler{
    static void askPlayerToRoll()
    {
        String userInput = getUserInput("Press Enter to Roll");

        if(userInput.equals(""))
        {

        }else{
            System.out.println("quitting...");
            System.exit(-1);
        }
    }
    static String getUserInput(String msg){
        Scanner scanner = new Scanner(System.in);
        System.out.println(msg);
        return scanner.nextLine();
    }
    static int userInputTestRollValue(){
        String userInput = UIHandler.getUserInput("Enter a number between 2 and 12 to test");
        return Integer.parseInt(userInput);
    }
}