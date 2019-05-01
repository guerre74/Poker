import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

// TODO: Auto-generated Javadoc
/**
 * The Class HumanPlayer.
 */
public class HumanPlayer extends Player {
    
    /* (non-Javadoc)
     * @see Player#discard(Poker)
     */
    public List<Card> discard(Poker poker) {
        return new ArrayList<>();
    }
    
    /**
     * Discard.
     *
     * @param poker the poker
     * @param humanCardRunnable the human card runnable
     */
    public void discard(Poker poker, HumanCardDiscardRunnable humanCardRunnable) {
    	
        
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
    
    
    /**
     * The Interface HumanCardDiscardRunnable.
     * Basically Runnable but for cards; allows for asynchronous discarding of cards
     */
    public interface HumanCardDiscardRunnable {
    	
	    /**
	     * Run.
	     *
	     * @param cards the cards to run
	     */
	    void run(List<Card> cards);
    }
}