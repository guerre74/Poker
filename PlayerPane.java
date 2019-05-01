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

public class PlayerPane extends JLayeredPane {

	private Player mPlayer;
	private Poker mPoker;
	private boolean mIsHuman;
	private List<CardView> mCardViews;
	private List<Card> mSelectedCards;
	private List<CardView> mSelectedCardViews;
	private KeyAdapter mKeyAdapter;
	private HumanPlayer.HumanCardDiscardRunnable mContinueRunnable;
	private JLabel mPlaceLabel;

	public Player getPlayer() {
		return mPlayer;
	}

	public void setPlayer(Player player) {
		mPlayer = player;
		mIsHuman = player instanceof HumanPlayer;
	}

	public Poker getPoker() {
		return mPoker;
	}

	public void setPoker(Poker poker) {
		mPoker = poker;
	}

	public boolean getIsHuman() {
		return mIsHuman;
	}

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

	public void onPack() {
		for (CardView cardView : mCardViews) {
			cardView.scaleImage();
			cardView.redraw();
		}
	}

	public void selectCard(Player humanPlayer, HumanPlayer.HumanCardDiscardRunnable humanCardDiscardRunnable) {
		for (int i = 0; i < mCardViews.size(); i++) {
			System.out.println(mCardViews.get(i).setCard(humanPlayer.getHand().getCards().get(i)));
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

	public void showHand(Hand hand, int place) {
		mPlaceLabel = new JLabel();
		mPlaceLabel.setText((mIsHuman && place == 1 ? "You win!" : place + (place == 1 ? "st" : place == 2 ? "nd" : place == 3 ? "rd" : "th")));
		mPlaceLabel.setFont(new Font(Font.SERIF, Font.BOLD, 30));
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
