import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Deck
{
    private List<Card> mCards;

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

    public Deck() {
        this(1);
    }

    public Card dealCard() {
        if(mCards.size() == 0) return null;
        Card card = mCards.get(mCards.size() - 1);
        mCards.remove(card);
        return card;
    }

    public List<Card> dealHand(int size) {
        if(mCards.size() < size) return null;
        List<Card> outputHand = new ArrayList<Card>();
        for(int i = 0; i < size; i++) {
            outputHand.add(dealCard());
        }
        System.out.println("Dealt " + size + " cards");
        System.out.println("Size " + mCards.size());
        return outputHand;
    }

    public List<Card> shuffleCards(List<Card> cards) {
        List<Card> tempCards = new ArrayList<Card>();
        tempCards.addAll(cards);
        Collections.shuffle(tempCards);
        return tempCards;
    }
    
    public void receiveCards(List<Card> cards) {
        mCards.addAll(cards);
        mCards = shuffleCards(mCards);
    }
    
    public List<Card> getCards() {
    	return mCards;
    }

}
