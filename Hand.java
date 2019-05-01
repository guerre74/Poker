import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

// TODO: Auto-generated Javadoc
/**
 * The Class Hand.
 */
public class Hand implements Comparable<Hand>
{   
    
    /** The cards. */
    private List<Card> mCards;
    
    /** The hand's patterns. */
    private List<Pattern> mPatterns;

    /**
     * Instantiates a new hand.
     *
     * @param cards the cards
     */
    private Hand(List<Card> cards) {
        mCards = cards;
    }

    /**
     * Instantiates a new hand.
     *
     * @param deck the deck
     */
    public Hand(Deck deck) {
        mCards = new ArrayList<Card>();
        mCards = deck.dealHand(5);
        if(mCards == null) {
            mCards = new ArrayList<Card>();
        }
        mPatterns = PatternMatcher.match(this);
    }

    /**
     * Create a hand from a string.
     *
     * @param stringValue the string value
     * @return the hand
     */
    public static Hand fromString(String stringValue) {
        List<Card> cards = new ArrayList<>();
        for(String cardString : stringValue.split(" ")) {
            cards.add(Card.fromString(cardString));
        }
        return new Hand(cards).match();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(Card card : mCards) {
            stringBuilder.append(card.toString());
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    /**
     * Gets the cards.
     *
     * @return the cards
     */
    public List<Card> getCards() {
        return mCards;
    }

    /**
     * Checks if is compliant.
     *
     * @return true, if the hand is the right size
     */
    public boolean isCompliant() {
        return mCards.size() == 5;
    }

    /**
     * Checks if is empty.
     *
     * @return true, if is empty
     */
    public boolean isEmpty() {
        return mCards == null || mCards.size() == 0;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Hand other) {
        calculatePatterns();
        other.calculatePatterns();
        int patternComparison = getHighestPattern().compareTo(other.getHighestPattern());
        if(patternComparison == 0) {
           return getHighestPattern().breakTie(this, other);
        }
        return patternComparison;
    }
    
    /**
     * Gets the highest pattern.
     *
     * @return the highest pattern
     */
    public Pattern getHighestPattern() {
        mPatterns.sort(new Comparator<Pattern> () {
            public int compare(Pattern a, Pattern b) {
                return a.compareTo(b);
            }
        });
        return mPatterns.get(mPatterns.size() - 1);
    }

    /**
     * Match.
     *
     * @return the hand, once the patterns are matched
     */
    public Hand match() {
        mPatterns = PatternMatcher.match(this);
        return this;
    }

    /**
     * Gets the patterns.
     *
     * @return the patterns
     */
    public List<Pattern> getPatterns() {
        return mPatterns;
    }
    
    /**
     * Discard cards.
     *
     * @param cards the cards
     * @param deck the deck
     * @param getMore the get more
     */
    public void discardCards(List<Card> cards, Deck deck, boolean getMore) {
    	int numDiscarding = cards.size();
        deck.receiveCards(cards);
        mCards.removeAll(cards);
        if(getMore) {
        	mCards.addAll(deck.dealHand(numDiscarding));
        }
    }
    
    /**
     * Calculate patterns.
     */
    public void calculatePatterns() {
        mPatterns = PatternMatcher.match(this);
    }

}
