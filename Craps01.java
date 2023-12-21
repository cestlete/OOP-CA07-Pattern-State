/*
 *This work by Rob Cleary is licensed under a Creative Commons AttributionShareAlike
 *3.0 Unported License. Based on a work at www.usethetools.net. Permissions beyond
 *the scope of this license may be available via usethetoolsinstructor1@gmail.com.
 */
import java.util.*;
class Main{
    public static void main(String[] args){
        GameController game = new GameController();
        game.start();
    }
}
class GameController{
    static Map<String, CrapsGameState> possibleStates = new HashMap<String, CrapsGameState>();
    static CrapsGameState gameState;

    GameController(){
        possibleStates.put( "CRAPS", new CrapsState() );
        possibleStates.put( "POINT", new PointState() );
        possibleStates.put( "WIN", new WinState() );
        possibleStates.put( "LOSE", new LoseState() );
        gameState = possibleStates.get("CRAPS");
    }
    void start(){
        while (true) {
            UIHandler.askPlayerToRoll();
            gameState.play(Dice.rollDice());
        }
    }

    void play(int rolled){
        gameState.play(rolled); //"delegation" to this.gameState.play() (i.e. INITIAL 1st time)
    }
    static void setState(String gameStateName){
        System.out.println("setState( :"+gameStateName+")");
        gameState = getState(gameStateName);
		// (1) If it is not "PointState", exit the game.
        gameState.doExitStateActivity();
    }
    static void setState(String gameStateName, int rollValue){
        CrapsGameState passedIn = getState(gameStateName);
        if(passedIn instanceof PointState){
            ((PointState)passedIn).storePointValueAs(rollValue);
        }
		
		// (2) Assign "PointState" value to "gameState".
        System.out.println("setState( :"+gameStateName+")");
        gameState = getState(gameStateName);
    }
    static CrapsGameState getState(String gameStateLookUp){
        return possibleStates.get(gameStateLookUp);
    }
}

abstract class CrapsGameState{
    void play(int rolled){
        System.out.println( "CrapsGameState.play()" );
		// (3) Create different objects and call different methods depending on the number of points.
        if( rolled == 7 || rolled == 11){
            WinState winState = new WinState();
            winState.play(rolled);
        }else if ( rolled == 2 || rolled == 3 || rolled == 12 ){
            LoseState loseState = new LoseState();
            loseState.play(rolled);
        }else {
            GameController.setState("POINT", rolled );
        }
    }

    void doEnterStateActivity(){
        ; //default: entry/doNothing
    }
    void doExitStateActivity(){
		// (4) Perform an "exit" operation
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
        System.out.println( "WinState.play() - rolled: "+rolled );
        System.out.println( "increase credits");
        GameController.setState("CRAPS");
    }
}

class LoseState extends CrapsGameState{

    void play(int rolled){
        System.out.println( "LoseState.play() - rolled: "+rolled );
        System.out.println( "decrease credits");
        GameController.setState("CRAPS");
    }
}
class PointState extends CrapsGameState{

    int pointValue;

    void play(int rolled){
        System.out.println( "PointState.play()");
        if( rolled == pointValue ){
            resetPointValue();
            GameController.setState("WIN");
        }else if( rolled == 7 ){
            resetPointValue();
            GameController.setState("LOSE");
        }else{
			// (5) Record points and set the "POINT" state.
            GameController.setState("POINT", rolled);
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