import java.util.Scanner;

public class Game {

    private int maxPick;

    public Game(int maxPick) {
        this.maxPick = maxPick;
    }

    public int performAlphaBeta(PileState current) {
        int bestMove = 0;
        float alpha = Float.NEGATIVE_INFINITY;
        float beta = Float.POSITIVE_INFINITY;
        float value = Float.NEGATIVE_INFINITY;
        for (PileState s : current.generateSuccessors(maxPick)) {
            float v = minValue(s, alpha, beta);
            if (v > value) {
                value = v;
            }
            if (value > alpha) {
                alpha = value;
                bestMove = s.path;
            }
        }
        return bestMove;
    }

    public float minValue(PileState current, float alpha, float beta) {
        if (current.isTerminal()) {
            return current.getUtility();
        }
        float value = Float.POSITIVE_INFINITY;
        for (PileState s : current.generateSuccessors(maxPick)) {
            float v = maxValue(s, alpha, beta);
            if (v < value) {
                value = v;
            }
            if (value <= alpha) {
                return value;
            }
            if (value < beta) {
                beta = value;
            }
        }
        return value;
    }

    public float maxValue(PileState current, float alpha, float beta) {
        if (current.isTerminal()) {
            return current.getUtility();
        }
        float value = Float.NEGATIVE_INFINITY;
        for (PileState s : current.generateSuccessors(maxPick)) {
            float v = minValue(s, alpha, beta);
            if (v > value) {
                value = v;
            }
            if (v >= beta) {
                return v;
            }
            if (value > alpha) {
                alpha = value;
            }
        }
        return value;
    }

    public static void main(String[] args) {
        // set up Scanner
        Scanner myScanner = new Scanner(System.in);

        System.out.println("Welcome to NIM.\n You will compete with a computer.\n You can decide how many sticks are there in the pile.\n You and the computer can pick up some sticks at a time.\n The computer always go first.\n The winner of the game is the player that takes the last stick from the pile.\n Please press enter if you are ready. ");
        myScanner.nextLine();
        System.out.println("Please enter the number of sticks in the pile: ");
        int setNumOfStick = myScanner.nextInt();
        System.out.println("Please specify how many sticks at most we can pick for each turn: ");
        int setMaxPick = myScanner.nextInt();

        if (setNumOfStick < 1) {
            System.out.println("You must have at least one stick in the pile.");
            System.exit(0);
        }
        // initialize the game
        PileState currState = new PileState(setNumOfStick);

        while (setNumOfStick != 0) {
            // computer's turn
            System.out.println("-------------------------------------------");
            System.out.println("It is computer's turn");

            // run the alpha beta pruning
            Game game = new Game(setMaxPick);
            int pickUpNumber = game.performAlphaBeta(currState);
            setNumOfStick = setNumOfStick - pickUpNumber;
            System.out.println("Computer picks up " + pickUpNumber + " sticks");
            System.out.println("There are " + setNumOfStick + " sticks left");
            if (setNumOfStick == 0) {
                System.out.println("Computer won!");
                break;
            }

            // human's turn
            System.out.println("-------------------------------------------");
            System.out.println("It is Your turn");
            int anotherNumber = myScanner.nextInt();
            if (anotherNumber < 1 || anotherNumber > setMaxPick) {
                System.out.println("You violate the rule!!!!!");
                System.out.println("YOU LOSE!!!!!");
                break;
            }

            setNumOfStick = setNumOfStick - anotherNumber;
            System.out.println("You pick up " + anotherNumber + " sticks");
            System.out.println("There are " + setNumOfStick + " sticks left");
            if (setNumOfStick == 0) {
                System.out.println("YOU WON!");
                break;
            }
            // set up current state after human's turn
            currState = new PileState(setNumOfStick);
        }
        myScanner.close();
    }
}