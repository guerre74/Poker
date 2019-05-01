public class Card {
    public enum Suit {
        SPADES(CardColor.BLACK, 's'), 

        DIAMONDS(CardColor.RED, 'd'), 

        CLUBS(CardColor.BLACK, 'c'), 

        HEARTS(CardColor.RED, 'h');

        Suit(CardColor color, char charValue) {
            mColor = color;
            mCharValue = charValue;
        }

        public String getStringValue() {
        	return name().toLowerCase();
        }
        
        public CardColor getColor() {
            return mColor;
        }

        public char getCharValue() {
            return mCharValue;
        }

        public static Suit fromChar(char charValue) {
            for(Suit suit : values()) {
                if(suit.getCharValue() == charValue) {
                    return suit;
                }
            }
            return Suit.DIAMONDS;
        }

        private CardColor mColor;
        private char mCharValue;
    }
    public enum CardColor {
        RED, BLACK;
    }
    public enum CardValue {

        TWO(2, false, '2'), 

        THREE(3, false, '3'), 

        FOUR(4, false, '4'), 

        FIVE(5, false, '5'), 

        SIX(6, false, '6'), 

        SEVEN(7, false, '7'), 

        EIGHT(8, false, '8'), 

        NINE(9, false, '9'), 

        TEN(10, false, '0'), 

        JACK(11, true, 'J', "jack"), 

        QUEEN(12, true, 'Q', "queen"), 

        KING(13, true, 'K', "king"),

        ACE(0, false, 'A', "ace");

        int mIntegerValue;
        boolean mIsFaceCard;
        char mCharValue;
        String mCardName;

        CardValue(int integerValue, boolean isFaceCard, char charValue) {
            mIntegerValue = integerValue;
            mIsFaceCard = isFaceCard;
            mCharValue = charValue;
            mCardName = String.valueOf(integerValue);
        }
        
        CardValue(int integerValue, boolean isFaceCard, char charValue, String cardName) {
            mIntegerValue = integerValue;
            mIsFaceCard = isFaceCard;
            mCharValue = charValue;
            mCardName = cardName;
        }

        public boolean getIsFaceCard() {
            return mIsFaceCard;
        }

        public int getIntegerValue() {
            return mIntegerValue;
        }

        public char getCharValue() {
            return mCharValue;
        }

        public static CardValue fromChar(char charValue) {
            for(CardValue value : values()) {
                if(value.getCharValue() == charValue) {
                    return value;
                }
            }
            return CardValue.TWO;
        }
        
        public String getCardName() {
        	return mCardName;
        }
    }
    private Suit mSuit;
    private CardValue mValue;

    public Card(Suit suit, CardValue value) {
        mSuit = suit;
        mValue = value;
    }

    public Suit getSuit() {
        return mSuit;
    }

    public CardValue getValue() {
        return mValue;
    }

    public void setValue(CardValue value) {
        mValue = value;
    }

    public void setSuit(Suit suit) {
        mSuit = suit;
    }

    public String toString() {
        return Character.toString(mValue.getCharValue()) + Character.toString(mSuit.getCharValue());
    }

    public static Card fromString(String stringValue) {
        if(stringValue.length() != 2) return null;

        char valueChar = stringValue.charAt(0);
        char suitChar = stringValue.charAt(1);

        return new Card(Suit.fromChar(suitChar), CardValue.fromChar(valueChar));
    }

    public boolean equals(Card other) {
        if(other == null) return false;
        return toString().equals(other.toString());
    }
    
    public String getImagePath() {
    	String cardName = mValue.getCardName();
    	String cardSuit = mSuit.getStringValue();
    	String ending = ".GIF";
    	return cardName + cardSuit + ending;
    }
    
}