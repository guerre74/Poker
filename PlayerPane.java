import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerPane.
 */
public class PlayerPane extends JLayeredPane {

	/** The player. */
	private Player mPlayer;
	
	/** The poker. */
	private Poker mPoker;
	
	/** The is human. */
	private boolean mIsHuman;
	
	/** The card views. */
	private List<CardView> mCardViews;
	
	/** The selected cards. */
	private List<Card> mSelectedCards;
	
	/** The selected card views. */
	private List<CardView> mSelectedCardViews;
	
	/** The key adapter. */
	private KeyAdapter mKeyAdapter;
	
	/** The continue runnable. */
	private HumanPlayer.HumanCardDiscardRunnable mContinueRunnable;
	
	/** The place label. */
	private JLabel mPlaceLabel;

	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	public Player getPlayer() {
		return mPlayer;
	}

	/**
	 * Sets the player.
	 *
	 * @param player the new player
	 */
	public void setPlayer(Player player) {
		mPlayer = player;
		mIsHuman = player instanceof HumanPlayer;
	}

	/**
	 * Gets the poker.
	 *
	 * @return the poker
	 */
	public Poker getPoker() {
		return mPoker;
	}

	/**
	 * Sets the poker.
	 *
	 * @param poker the new poker
	 */
	public void setPoker(Poker poker) {
		mPoker = poker;
	}

	/**
	 * Gets the checks if is human.
	 *
	 * @return the checks if is human
	 */
	public boolean getIsHuman() {
		return mIsHuman;
	}

	/**
	 * Instantiates a new player pane.
	 *
	 * @param player the player
	 * @param poker the poker
	 */
	public PlayerPane(Player player, Poker poker) {
		setPlayer(player);
		mPoker = poker;
		mCardViews = new ArrayList<>();
		mSelectedCards = new ArrayList<>();
		mSelectedCardViews = new ArrayList<>();
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), player.getName()));
		setLayout(new GridLayout(1, 5));
		for (int i = 0; i < 5; i++) {
			CardView cardView = CardView.makeCardView();
			add(cardView, JLayeredPane.DEFAULT_LAYER);
			mCardViews.add(cardView);
		}

	}

	/**
	 * On pack.
	 */
	public void onPack() {
		for (CardView cardView : mCardViews) {
			cardView.scaleImage();
			cardView.redraw();
		}
	}

	/**
	 * Select card.
	 *
	 * @param humanPlayer the human player
	 * @param humanCardDiscardRunnable the human card discard runnable
	 */
	public void selectCard(Player humanPlayer, HumanPlayer.HumanCardDiscardRunnable humanCardDiscardRunnable) {
		for (int i = 0; i < mCardViews.size(); i++) {
			mCardViews.get(i).setCard(humanPlayer.getHand().getCards().get(i));
			mCardViews.get(i).setIsUp(true);
			mCardViews.get(i).observeClicks(this);
		}
		mSelectedCards = new ArrayList<>();
		mSelectedCardViews = new ArrayList<>();
		mContinueRunnable = humanCardDiscardRunnable;
		mKeyAdapter = new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					ascend(mSelectedCards);
				}
			}
		};
		this.addKeyListener(mKeyAdapter);
		this.setFocusable(true);
		this.requestFocus();
	}

	/**
	 * Adds the card to selection.
	 *
	 * @param card the card
	 * @param cardView the card view
	 */
	public void addCardToSelection(Card card, CardView cardView) {
		if (mSelectedCards.contains(card)) {
			mSelectedCards.remove(card);
			mSelectedCardViews.remove(cardView);
			cardView.setIsSelected(false);
			return;
		}
		mSelectedCards.add(card);
		mSelectedCardViews.add(cardView);
		cardView.setIsSelected(true);
		if (mSelectedCards.size() == 3) {
			List<Card> toAscend = new ArrayList<>(mSelectedCards);
			ascend(toAscend);
		}
	}

	/**
	 * Ascend.
	 *
	 * @param cards the cards
	 */
	private void ascend(List<Card> cards) {
		for (CardView view : mCardViews) {
			view.setIsSelected(false);
			view.removeClickObserver();
			view.setCard(null);
		}
		this.removeKeyListener(mKeyAdapter);
		this.setFocusable(false);
		mContinueRunnable.run(cards);
	}

	/**
	 * Show hand.
	 *
	 * @param hand the hand
	 * @param place the place
	 */
	public void showHand(Hand hand, int place) {
		mPlaceLabel = new JLabel();
		mPlaceLabel.setText("<html><h1>" + (mIsHuman && place == 1 ? "You win!" : place + (place == 1 ? "st" : place == 2 ? "nd" : place == 3 ? "rd" : "th")) + 
		"</h1><br>" + hand.getHighestPattern().getReadableName() + "</html>");
		mPlaceLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		mPlaceLabel.setSize(200, 50);
		mPlaceLabel.setVisible(true);
		if(place == 1) {
			this.setBackground(new Color(255, 0, 0, 100));
		}
		add(mPlaceLabel, JLayeredPane.POPUP_LAYER);
		for (int i = 0; i < 5; i++) {
			mCardViews.get(i).setCard(hand.getCards().get(i));
			mCardViews.get(i).setIsUp(true);
			mCardViews.get(i).setBackground((place == 1 ? new Color(255, 0, 0, 50) : null));
		}

	}

	/**
	 * Hide hand.
	 */
	public void hideHand() {
		remove(mPlaceLabel);
		mPlaceLabel = null;
		this.setBackground(null);
		for (CardView cardView : mCardViews) {
			cardView.setCard(null);
			cardView.setBackground(null);
		}
	}
}
