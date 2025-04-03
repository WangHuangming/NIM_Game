import java.util.ArrayList;

public class PileState {
    public int remainingSticks;
    public int utility;
    public int path;

    public PileState(int remainingSticks) {
        this.remainingSticks = remainingSticks;
        utility = -1;
        path = 0;
    }

    public void setRemainingSticks(int remainingSticks) {
        this.remainingSticks = remainingSticks;
    }

    public ArrayList<PileState> generateSuccessors(int maxPickUpNumber) {
        ArrayList<PileState> successors = new ArrayList<>();
        for (int i = maxPickUpNumber; i > 0; i--) {
            if (remainingSticks - i >= 0) {
                PileState s = new PileState(remainingSticks - i);
                s.utility = -this.utility;
                s.path = i;
                successors.add(s); 
                // System.out.println("Generated successor: ");
                // s.printState();
            }
        }
        return successors;
    }

    public boolean isTerminal() {
        return remainingSticks == 0;
    }

    public int getUtility() {
        return utility;
    }

    public void setUtility(int utility) {
        this.utility = utility;
    }

    // public void printState() {
    //     System.out.println("++++++++++++++++++++");
    //     System.out.println("Remaining sticks: " + remainingSticks);
    //     System.out.println("Utility: " + utility);
    //     System.out.println("*******************");
    // }
}
