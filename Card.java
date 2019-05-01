// TODO: Auto-generated Javadoc
/**
 * The Class Card.
 */
public class Card {

	/**
	 * The Enum Suit.
	 */
	public enum Suit {

		/** The spades. */
		SPADES(CardColor.BLACK, 's'),

		/** The diamonds. */
		DIAMONDS(CardColor.RED, 'd'),

		/** The clubs. */
		CLUBS(CardColor.BLACK, 'c'),

		/** The hearts. */
		HEARTS(CardColor.RED, 'h');

		/**
		 * Instantiates a new suit.
		 *
		 * @param color
		 *            the colour of the suit
		 * @param charValue
		 *            the char value of the suit
		 */
		Suit(CardColor color, char charValue) {
			mColor = color;
			mCharValue = charValue;
		}

		/**
		 * Gets the string value.
		 *
		 * @return the string value
		 */
		public String getStringValue() {
			return name().toLowerCase();
		}

		/**
		 * Gets the colour.
		 *
		 * @return the colour
		 */
		public CardColor getColor() {
			return mColor;
		}

		/**
		 * Gets the char value.
		 *
		 * @return the char value
		 */
		public char getCharValue() {
			return mCharValue;
		}

		/**
		 * Creates a Suit from char.
		 *
		 * @param charValue
		 *            the char value
		 * @return the suit
		 */
		public static Suit fromChar(char charValue) {
			for (Suit suit : values()) {
				if (suit.getCharValue() == charValue) {
					return suit;
				}
			}
			return Suit.DIAMONDS;
		}

		/** The color. */
		private CardColor mColor;

		/** The char value. */
		private char mCharValue;
	}

	/**
	 * The Enum CardColor.
	 */
	public enum CardColor {

		/** Red. */
		RED,
		/** Black. */
		BLACK;
	}

	/**
	 * The Enum CardValue.
	 */
	public enum CardValue {

		/** two. */
		TWO(2, false, '2'),

		/** three. */
		THREE(3, false, '3'),

		/** four. */
		FOUR(4, false, '4'),

		/** five. */
		FIVE(5, false, '5'),

		/** six. */
		SIX(6, false, '6'),

		/** seven. */
		SEVEN(7, false, '7'),

		/** eight. */
		EIGHT(8, false, '8'),

		/** nine. */
		NINE(9, false, '9'),

		/** ten. */
		TEN(10, false, '0'),

		/** jack. */
		JACK(11, true, 'J', "jack"),

		/** queen. */
		QUEEN(12, true, 'Q', "queen"),

		/** king. */
		KING(13, true, 'K', "king"),

		/** ace. */
		ACE(0, false, 'A', "ace");

		/** m integer value. */
		int mIntegerValue;

		/**  is face card. */
		boolean mIsFaceCard;

		/**  char value. */
		char mCharValue;

		/**  card name. */
		String mCardName;

		/**
		 * Instantiates a new card value.
		 *
		 * @param integerValue
		 *            the integer value
		 * @param isFaceCard
		 *            the is face card
		 * @param charValue
		 *            the char value
		 */
		CardValue(int integerValue, boolean isFaceCard, char charValue) {
			mIntegerValue = integerValue;
			mIsFaceCard = isFaceCard;
			mCharValue = charValue;
			mCardName = String.valueOf(integerValue);
		}

		/**
		 * Instantiates a new card value.
		 *
		 * @param integerValue
		 *            the integer value
		 * @param isFaceCard
		 *            the is face card
		 * @param charValue
		 *            the char value
		 * @param cardName
		 *            the card name
		 */
		CardValue(int integerValue, boolean isFaceCard, char charValue, String cardName) {
			mIntegerValue = integerValue;
			mIsFaceCard = isFaceCard;
			mCharValue = charValue;
			mCardName = cardName;
		}

		/**
		 * Gets the checks if is face card.
		 *
		 * @return the checks if is face card
		 */
		public boolean getIsFaceCard() {
			return mIsFaceCard;
		}

		/**
		 * Gets the integer value.
		 *
		 * @return the integer value
		 */
		public int getIntegerValue() {
			return mIntegerValue;
		}

		/**
		 * Gets the char value.
		 *
		 * @return the char value
		 */
		public char getCharValue() {
			return mCharValue;
		}

		/**
		 * From char.
		 *
		 * @param charValue
		 *            the char value
		 * @return the card value
		 */
		public static CardValue fromChar(char charValue) {
			for (CardValue value : values()) {
				if (value.getCharValue() == charValue) {
					return value;
				}
			}
			return CardValue.TWO;
		}

		/**
		 * Gets the card name.
		 *
		 * @return the card name
		 */
		public String getCardName() {
			return mCardName;
		}
	}

	/**  suit. */
	private Suit mSuit;

	/**  value. */
	private CardValue mValue;

	/**
	 * Instantiates a new card.
	 *
	 * @param suit
	 *            the suit
	 * @param value
	 *            the value
	 */
	public Card(Suit suit, CardValue value) {
		mSuit = suit;
		mValue = value;
	}

	/**
	 * Gets the suit.
	 *
	 * @return the suit
	 */
	public Suit getSuit() {
		return mSuit;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public CardValue getValue() {
		return mValue;
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(CardValue value) {
		mValue = value;
	}

	/**
	 * Sets the suit.
	 *
	 * @param suit
	 *            the new suit
	 */
	public void setSuit(Suit suit) {
		mSuit = suit;
	}

	/**
	 * Return a string value of the Card
	 * @return String value of the card
	 */
	public String toString() {
		return Character.toString(mValue.getCharValue()) + Character.toString(mSuit.getCharValue());
	}

	/**
	 * From string.
	 *
	 * @param stringValue
	 *            the string value
	 * @return the card
	 */
	public static Card fromString(String stringValue) {
		if (stringValue.length() != 2)
			return null;

		char valueChar = stringValue.charAt(0);
		char suitChar = stringValue.charAt(1);

		return new Card(Suit.fromChar(suitChar), CardValue.fromChar(valueChar));
	}

	/**
	 * Equals.
	 *
	 * @param other
	 *            the other
	 * @return true, if successful
	 */
	public boolean equals(Card other) {
		if (other == null)
			return false;
		return toString().equals(other.toString());
	}

	/**
	 * Gets the image path.
	 *
	 * @return the image path
	 */
	public String getImagePath() {
		String cardName = mValue.getCardName();
		String cardSuit = mSuit.getStringValue();
		String ending = ".GIF";
		return cardName + cardSuit + ending;
	}

}