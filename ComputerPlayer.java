import java.util.List;
import java.util.ArrayList;
// TODO: Auto-generated Javadoc

/**
 * The Class ComputerPlayer.
 */
public class ComputerPlayer extends Player {
    
    /* (non-Javadoc)
     * @see Player#discard(Poker)
     */
    public List<Card> discard(Poker poker) {
        List<Card> toDiscard = new ArrayList<>();
        Pattern highestPattern = getHand().getHighestPattern();
        PatternMatcher.sortByValue(getHand().getCards());
        switch(highestPattern) {
            case NONE:
                toDiscard.addAll(getHand().getCards().subList(0, 3));
                break;
            case PAIR:
                Card.CardValue pairValue = PatternMatcher.getPairValue(getHand(), 0);
                for(Card card : getHand().getCards()) {
                    if(toDiscard.size() < 3 && !card.getValue().equals(pairValue)) {
                        toDiscard.add(card);
                    }
                }
                break;
            case TWOPAIR:
                Card.CardValue[] twoPairValues = {PatternMatcher.getPairValue(getHand(), 0), PatternMatcher.getPairValue(getHand(), 1)};
                for(Card card : getHand().getCards()) {
                    if(!card.getValue().equals(twoPairValues[0]) && !card.getValue().equals(twoPairValues[1])) {
                        toDiscard.add(card);
                    }
                }
                break;
           default:
                    
        }
        for(Card card : toDiscard) {
        }
        return toDiscard;
    }
}