import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class Hand implements Comparable<Hand>
{   
    private List<Card> mCards;
    private List<Pattern> mPatterns;

    private Hand(List<Card> cards) {
        mCards = cards;
    }

    public Hand(Deck deck) {
        mCards = new ArrayList<Card>();
        mCards = deck.dealHand(5);
        if(mCards == null) {
            mCards = new ArrayList<Card>();
        }
        mPatterns = PatternMatcher.match(this);
    }

    public static Hand fromString(String stringValue) {
        List<Card> cards = new ArrayList<>();
        for(String cardString : stringValue.split(" ")) {
            cards.add(Card.fromString(cardString));
        }
        return new Hand(cards).match();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(Card card : mCards) {
            stringBuilder.append(card.toString());
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    public List<Card> getCards() {
        return mCards;
    }

    public boolean isCompliant() {
        return mCards.size() == 5;
    }

    public boolean isEmpty() {
        return mCards == null || mCards.size() == 0;
    }

    public int compareTo(Hand other) {
        calculatePatterns();
        other.calculatePatterns();
        int patternComparison = getHighestPattern().compareTo(other.getHighestPattern());
        if(patternComparison == 0) {
           return getHighestPattern().breakTie(this, other);
        }
        return patternComparison;
    }
    
    public Pattern getHighestPattern() {
        mPatterns.sort(new Comparator<Pattern> () {
            public int compare(Pattern a, Pattern b) {
                return a.compareTo(b);
            }
        });
        return mPatterns.get(mPatterns.size() - 1);
    }

    public Hand match() {
        mPatterns = PatternMatcher.match(this);
        return this;
    }

    public List<Pattern> getPatterns() {
        return mPatterns;
    }
    
    public void discardCards(List<Card> cards, Deck deck, boolean getMore) {
    	int numDiscarding = cards.size();
        deck.receiveCards(cards);
        mCards.removeAll(cards);
        if(getMore) {
        	System.out.println(deck.getCards().size());
        	mCards.addAll(deck.dealHand(numDiscarding));
        }
    }
    
    public void calculatePatterns() {
        mPatterns = PatternMatcher.match(this);
    }

}
