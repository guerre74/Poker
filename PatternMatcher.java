import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * The Class PatternMatcher.
 */
public final class PatternMatcher {

    /**
     * Match.
     *
     * @param hand the hand
     * @return the list
     */
    public static List<Pattern> match(Hand hand) {
        List<Pattern> patterns = new ArrayList<>();
        for(Pattern pattern : Pattern.values()) {
            if(pattern.matches(hand)) {
                patterns.add(pattern);
            }
        }
        return patterns;
    }

    /**
     * Checks if is straight flush.
     *
     * @param hand the hand
     * @return true, if is straight flush
     */
    public static boolean isStraightFlush(Hand hand) {
        return isStraight(hand) && isFlush(hand);
    }

    /**
     * Checks if is flush.
     *
     * @param hand the hand
     * @return true, if is flush
     */
    public static boolean isFlush(Hand hand) {
        if(hand.isEmpty() || !hand.isCompliant()) return false;
        boolean isFlush = true;
        Card lastCard = null;
        for(Card card : hand.getCards()) {
            if(lastCard == null) {
                lastCard = card; continue;
            }
            if(lastCard.getSuit() != card.getSuit()) {
                isFlush = false;
                break;
            }
            lastCard = card;
        }
        return isFlush;
    }

    /**
     * Checks if is straight.
     *
     * @param hand the hand
     * @return true, if is straight
     */
    public static boolean isStraight(Hand hand) {
        if(hand.isEmpty() || !hand.isCompliant()) return false;

        List<Card> tempCards = new ArrayList<>();
        tempCards.addAll(hand.getCards());
        int numAces = 0;
        Card ace = null;

        Iterator<Card> cardIterator = tempCards.iterator();
        while(cardIterator.hasNext()) {
            Card nextCard = cardIterator.next();
            if(nextCard.getValue() == Card.CardValue.ACE) {ace = nextCard; numAces++; cardIterator.remove(); }
        }

        if(numAces > 1) return false;

        sortByValue(tempCards);
        for(int cardIndex = 1; cardIndex < tempCards.size(); cardIndex++) {
            if(!areSequential(tempCards.get(cardIndex - 1), tempCards.get(cardIndex))) {
                return false;
            }
        }

        if(numAces == 0) return true;

        if(areSequential(ace, tempCards.get(0)) || areSequential(tempCards.get(tempCards.size() - 1), ace)) {
            return true;
        }

        return false;
    }

    /**
     * Are sequential.
     *
     * @param lowCard the low card
     * @param highCard the high card
     * @return true, if sequential
     */
    static boolean areSequential(Card lowCard, Card highCard) {
        if(lowCard.getValue() == Card.CardValue.ACE) {
            return highCard.getValue() == Card.CardValue.TWO;
        }
        if(highCard.getValue() == Card.CardValue.ACE) {
            return lowCard.getValue() == Card.CardValue.KING;
        }
        return highCard.getValue().getIntegerValue() == lowCard.getValue().getIntegerValue() + 1;
    }

    /**
     * Checks for full house.
     *
     * @param hand the hand
     * @return true, if a full house
     */
    public static boolean hasFullHouse(Hand hand) {
        return numPairs(hand) == 2 && hasThreeOfAKind(hand);
    }
    
    /**
     * Gets the full house values.
     *
     * @param hand the hand
     * @return the full house values
     */
    public static Card.CardValue[] getFullHouseValues(Hand hand) {
        Card.CardValue[] values = new Card.CardValue[2];
        int threeOfAKindPair = -1;
        for(int i = 0; i < 2; i++) {
            if(getThreeOfAKindValue(hand).equals(getPairValue(hand, i))) {
                threeOfAKindPair = i;
            }
        }
        values[0] = getPairValue(hand, threeOfAKindPair);
        values[1] = getPairValue(hand, 1 - threeOfAKindPair);
        return values;
    }

    /**
     * Checks for four of a kind.
     *
     * @param hand the hand
     * @return true, if four of a kind
     */
    public static boolean hasFourOfAKind(Hand hand) {
        return numPairs(hand) == 2 && 
        (getPairValue(hand, 0)
            .equals(getPairValue(hand, 1)));
    }
    
    /**
     * Gets the four of a kind value.
     *
     * @param hand the hand
     * @return the four of a kind value
     */
    public static Card.CardValue getFourOfAKindValue(Hand hand) {
        return getPairValue(hand, 0);
    }

    /**
     * Checks for two pair.
     *
     * @param hand the hand
     * @return true, if successful
     */
    public static boolean hasTwoPair(Hand hand) {
        return numPairs(hand) == 2;
    }

    /**
     * Checks for three of a kind.
     *
     * @param hand the hand
     * @return true, if successful
     */
    public static boolean hasThreeOfAKind(Hand hand) {
        if(hand.isEmpty() || !hand.isCompliant()) return false;
        List<Card> cards = hand.getCards();
        sortByValue(cards);
        for(int cardIndex = 2; cardIndex < cards.size(); cardIndex++) {
            Card currentCard = cards.get(cardIndex);
            Card lastCard = cards.get(cardIndex - 1);
            Card nextLastCard = cards.get(cardIndex - 2);
            Card nextCard = (cardIndex < cards.size() - 1) ? cards.get(cardIndex + 1) : null;
            if(currentCard == null || lastCard == null || nextLastCard == null) continue;
            if(currentCard.getValue().equals(lastCard.getValue()) && lastCard.getValue().equals(nextLastCard.getValue()) && !currentCard.getValue().equals(nextCard)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the three of a kind value.
     *
     * @param hand the hand
     * @return the three of a kind value
     */
    public static Card.CardValue getThreeOfAKindValue(Hand hand) {
        if(hand.isEmpty() || !hand.isCompliant() || !hasThreeOfAKind(hand)) return null;
        List<Card> cards = hand.getCards();
        sortByValue(cards);
        for(int cardIndex = 2; cardIndex < cards.size(); cardIndex++) {
            Card currentCard = cards.get(cardIndex);
            Card lastCard = cards.get(cardIndex - 1);
            Card nextLastCard = cards.get(cardIndex - 2);
            if(currentCard == null || lastCard == null || nextLastCard == null) continue;
            if(currentCard.getValue().equals(lastCard.getValue()) && lastCard.getValue().equals(nextLastCard.getValue())) {
                return currentCard.getValue();
            }
        }
        return null;
    }

    /**
     * Num pairs.
     *
     * @param hand the hand
     * @return the int
     */
    public static int numPairs(Hand hand) {
        if(hand.isEmpty() || !hand.isCompliant()) return 0;
        int numPairs = 0;
        List<Card> cards = hand.getCards();
        sortByValue(cards);
        Card lastCard = null;
        for(Card card : hand.getCards()) {
            if(lastCard == null) {
                lastCard = card;
                continue;
            }
            if(lastCard.getValue() == card.getValue()) {
                numPairs++;
                lastCard = null;
                continue;
            }
            lastCard = card;
        }
        return numPairs;
    }

    /**
     * Gets the pair value.
     *
     * @param hand the hand
     * @param pairNum the pair num
     * @return the pair value
     */
    public static Card.CardValue getPairValue(Hand hand, int pairNum) {
        if(hand.isEmpty() || !hand.isCompliant()) return null;
        boolean isEnoughPairs = numPairs(hand) > pairNum && pairNum >= 0;
        if(!isEnoughPairs) return null;
        List<Card> cards = hand.getCards();
        sortByValue(cards);
        Card lastCard = null;
        for(Card card : hand.getCards()) {
            if(lastCard == null) {
                lastCard = card;
                continue;
            }
            if(lastCard.getValue().equals(card.getValue())) {
                if(pairNum == 0) return card.getValue();
                pairNum--;
                lastCard = null;
                continue;
            }
            lastCard = card;
        }
        return null;
    }

    /**
     * High card.
     *
     * @param hand the hand
     * @return the card. card value
     */
    public static Card.CardValue highCard(Hand hand) {
        sortByValue(hand.getCards());
        return hand.getCards().get(hand.getCards().size() - 1).getValue();
    }

    /**
     * Sort by value.
     *
     * @param cards the cards
     */
    public static void sortByValue(List<Card> cards) {
        cards.sort(new Comparator<Card>() {
                public int compare(Card a, Card b) {
                    return a.getValue().compareTo(b.getValue());
                }
            });
    }
}