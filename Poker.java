import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Poker {

	private List<Player> mPlayers;
	private Deck mDeck;
	private PokerObserver mPokerObserver;
	final static int MAX_PLAYERS = 9;
	final static int MIN_PLAYERS = 2;

	public static Poker makePoker(int numPlayers, Deck deck) {
		if (numPlayers > MAX_PLAYERS || numPlayers < MIN_PLAYERS)
			return null;
		System.out.println("makePoker called and continuing");
		return new Poker(numPlayers, deck);
	}

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
		System.out.println("END OF ROUND:\n");
		for (Player player : mPlayers) {
			System.out.println("\t" + player.getName() + ": " + player.getHand());
		}
		mPokerObserver.readyToShowPlayerHands(mPlayers);
	}

	public List<Player> getPlayers() {
		return mPlayers;
	}

	public Deck getDeck() {
		return mDeck;
	}

	public Poker setPokerObserver(PokerObserver pokerObserver) {
		mPokerObserver = pokerObserver;
		return this;
	}

	public PokerObserver getPokerObserver() {
		return mPokerObserver;
	}

	public interface PokerObserver {
		void onHumanSelectionReady(Poker poker, Player player, HumanPlayer.HumanCardDiscardRunnable runnable);

		void readyToShowPlayerHands(List<Player> players);
	}
}