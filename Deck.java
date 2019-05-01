import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

// TODO: Auto-generated Javadoc
/**
 * The Class Deck.
 */
public class Deck
{
    
    /** The cards. */
    private List<Card> mCards;

    /**
     * Instantiates a new deck.
     *
     * @param numDecks the number of decks
     */
    public Deck(int numDecks) {
        List<Card> cards = new ArrayList<>();

        Card.Suit[] suits = Card.Suit.values();
        Card.CardValue[] values = Card.CardValue.values();
        for(int i = 0; i < numDecks; i++) {
            for(Card.Suit suit : suits) {
                for(Card.CardValue value : values) {
                    cards.add(new Card(suit, value));
                }
            }
        }
        mCards = shuffleCards(cards);
    }

    /**
     * Instantiates a new deck of 52 cards.
     */
    public Deck() {
        this(1);
    }

    /**
     * Deal card.
     *
     * @return the card
     */
    public Card dealCard() {
        if(mCards.size() == 0) return null;
        Card card = mCards.get(mCards.size() - 1);
        mCards.remove(card);
        return card;
    }

    /**
     * Deal hand.
     *
     * @param size the size of the hand
     * @return the list
     */
    public List<Card> dealHand(int size) {
        if(mCards.size() < size) return null;
        List<Card> outputHand = new ArrayList<Card>();
        for(int i = 0; i < size; i++) {
            outputHand.add(dealCard());
        }
        return outputHand;
    }

    /**
     * Shuffle cards.
     *
     * @param cards the cards
     * @return the list of cards, shuffled
     */
    public List<Card> shuffleCards(List<Card> cards) {
        List<Card> tempCards = new ArrayList<Card>();
        tempCards.addAll(cards);
        Collections.shuffle(tempCards);
        return tempCards;
    }
    
    /**
     * Receive cards.
     *
     * @param cards the cards
     */
    public void receiveCards(List<Card> cards) {
        mCards.addAll(cards);
        mCards = shuffleCards(mCards);
    }
    
    /**
     * Gets the cards.
     *
     * @return the cards
     */
    public List<Card> getCards() {
    	return mCards;
    }

}
