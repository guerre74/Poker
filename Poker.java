import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Poker.
 */
public class Poker {

	/** The players. */
	private List<Player> mPlayers;
	
	/** The deck. */
	private Deck mDeck;
	
	/** The poker observer. */
	private PokerObserver mPokerObserver;
	
	/** The Constant MAX_PLAYERS. */
	final static int MAX_PLAYERS = 9;
	
	/** The Constant MIN_PLAYERS. */
	final static int MIN_PLAYERS = 2;

	/**
	 * Make poker.
	 *
	 * @param numPlayers the num players
	 * @param deck the deck
	 * @return the poker
	 */
	public static Poker makePoker(int numPlayers, Deck deck) {
		if (numPlayers > MAX_PLAYERS || numPlayers < MIN_PLAYERS)
			return null;
		return new Poker(numPlayers, deck);
	}

	/**
	 * Instantiates a new poker.
	 *
	 * @param numPlayers the num players
	 * @param deck the deck
	 */
	private Poker(int numPlayers, Deck deck) {
		List<Player> players = new ArrayList<>();
		Player hPlayer = new HumanPlayer();
		hPlayer.setOriginalOrder(0);
		hPlayer.setName("You");
		players.add(hPlayer);
		for (int i = 0; i < numPlayers - 1; i++) {
			Player cPlayer = new ComputerPlayer();
			cPlayer.setName("Computer #" + (i + 1));
			cPlayer.setOriginalOrder(i + 1);
			players.add(cPlayer);
		}
		// Collections.shuffle(players);
		mPlayers = players;
		mDeck = deck;
	}

	/**
	 * Execute round.
	 */
	public void executeRound() {
		for (Player player : mPlayers) {
			if (player.getHand() != null) {
				player.getHand().discardCards(player.getHand().getCards(), mDeck, false);
			}
			player.setHand(new Hand(mDeck));
		}
		mPlayers.sort(new Comparator<Player>() {

			@Override
			public int compare(Player o1, Player o2) {
				// TODO Auto-generated method stub
				return o1.getOriginalOrder() - o2.getOriginalOrder();
			}
		});

		HumanPlayer humanPlayer = (HumanPlayer) mPlayers.get(0);

		humanPlayer.discard(this, new HumanPlayer.HumanCardDiscardRunnable() {

			@Override
			public void run(List<Card> cards) {
				// TODO Auto-generated method stub
				int numDiscarded = cards.size();

				if (numDiscarded > 3) {
					continueRound();
					return;
				}
				for (Card card : cards) {
					if (!humanPlayer.getHand().getCards().contains(card)) {
						continueRound();
						return;
					}
				}
				humanPlayer.getHand().discardCards(cards, mDeck, true);
				humanPlayer.getHand().calculatePatterns();
				continueRound();
			}
		});
	}

	/**
	 * Continue round.
	 */
	public void continueRound() {
		playerLoop: for (Player player : mPlayers) {
			if (player instanceof HumanPlayer)
				continue;

			List<Card> cards = player.discard(this);
			int numDiscarded = cards.size();
			if (numDiscarded > 3) {
				continue;
			}
			for (Card card : cards) {
				if (!player.getHand().getCards().contains(card)) {
					continue playerLoop;
				}
			}
			player.getHand().discardCards(cards, mDeck, true);
			player.getHand().calculatePatterns();
		}
		mPlayers.sort(new Comparator<Player>() {
			public int compare(Player a, Player b) {
				return a.getHand().compareTo(b.getHand());
			}
		});
		mPokerObserver.readyToShowPlayerHands(mPlayers);
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return mPlayers;
	}

	/**
	 * Gets the deck.
	 *
	 * @return the deck
	 */
	public Deck getDeck() {
		return mDeck;
	}

	/**
	 * Sets the poker observer.
	 *
	 * @param pokerObserver the poker observer
	 * @return the poker
	 */
	public Poker setPokerObserver(PokerObserver pokerObserver) {
		mPokerObserver = pokerObserver;
		return this;
	}

	/**
	 * Gets the poker observer.
	 *
	 * @return the poker observer
	 */
	public PokerObserver getPokerObserver() {
		return mPokerObserver;
	}

	/**
	 * An asynchronous update interface for receiving notifications
	 * about Poker information as the Poker is constructed.
	 */
	public interface PokerObserver {
		
		/**
		 * This method is called when information about an Poker
		 * which was previously requested using an asynchronous
		 * interface becomes available.
		 *
		 * @param poker the poker
		 * @param player the player
		 * @param runnable the runnable
		 */
		void onHumanSelectionReady(Poker poker, Player player, HumanPlayer.HumanCardDiscardRunnable runnable);

		/**
		 * This method is called when information about an Poker
		 * which was previously requested using an asynchronous
		 * interface becomes available.
		 *
		 * @param players the players
		 */
		void readyToShowPlayerHands(List<Player> players);
	}
}