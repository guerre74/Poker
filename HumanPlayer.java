import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HumanPlayer extends Player {
    public List<Card> discard(Poker poker) {
        return new ArrayList<>();
    }
    public void discard(Poker poker, HumanCardDiscardRunnable humanCardRunnable) {
    	System.out.println("New round: \n");
        System.out.print("\tHere is your hand:\t");
        System.out.println(getHand());
        
        List<Card> toDiscard = new ArrayList<>();

        if(poker.getPokerObserver() == null) {
        	
        } else {
        	poker.getPokerObserver().onHumanSelectionReady(poker, this, new HumanCardDiscardRunnable() {

				@Override
				public void run(List<Card> cards) {
					humanCardRunnable.run(cards);
				}});
        }
        
    }
    
    
    public interface HumanCardDiscardRunnable {
    	void run(List<Card> cards);
    }
}