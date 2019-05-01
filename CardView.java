import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class CardView.
 */
public class CardView extends JPanel {
	
	/** The Constant DOWN_CARD_URL. */
	private final static String DOWN_CARD_URL = "images/back.GIF";
	
	/** The up image. */
	private Image mUpImage;
	
	/** The down image. */
	private Image mDownImage;
	
	/** The original up image. */
	private Image mOriginalUpImage;
	
	/** The original down image. */
	private Image mOriginalDownImage;
	
	/** The player pane. */
	private PlayerPane mPlayerPane;
	
	/** The mouse adapter. */
	private MouseAdapter mMouseAdapter;
	
	/** The card. */
	private Card mCard;
	
	/** Is up. */
	private boolean mIsUp;

	/**
	 * Instantiates a new card view.
	 *
	 * @param card the card
	 * @param upImage the up image
	 * @param downImage the down image
	 */
	private CardView(Card card, BufferedImage upImage, BufferedImage downImage) {
		mCard = card;
		mOriginalUpImage = upImage;
		mOriginalDownImage = downImage;
		setMinimumSize(new Dimension(71, 96));
		scaleImage();
		this.validate();
		this.repaint();
	}

	/**
	 * Scale image.
	 */
	public void scaleImage() {
		if (getHeight() == 0) {
			mDownImage = mOriginalDownImage.getScaledInstance(71, 96, Image.SCALE_FAST);
			mUpImage = (mOriginalUpImage != null ? mOriginalUpImage.getScaledInstance(71, 96, Image.SCALE_FAST) : null);
			return;
		}
		mDownImage = mOriginalDownImage.getScaledInstance(getHeight() * 71 / 96 * 9 / 10, getHeight() * 9 / 10,
				Image.SCALE_FAST);
		mUpImage = (mOriginalUpImage != null ? mOriginalUpImage.getScaledInstance(getHeight() * 71 / 96 * 9 / 10,
				getHeight() * 9 / 10, Image.SCALE_FAST) : null);
	}

	/**
	 * Make card view.
	 *
	 * @param card the card
	 * @return the card view
	 */
	public static CardView makeCardView(Card card) {
		try {
			BufferedImage upImage = ImageIO.read(new File("images/" + card.getImagePath()));
			BufferedImage downImage = ImageIO.read(new File(DOWN_CARD_URL));
			return new CardView(card, upImage, downImage);
		} catch (IOException ex) {
			// handle exception...
		}
		return null;
	}

	/**
	 * Make card view.
	 *
	 * @return the card view
	 */
	public static CardView makeCardView() {
		try {
			BufferedImage upImage = null;
			BufferedImage downImage = ImageIO.read(new File(DOWN_CARD_URL));
			return new CardView(null, upImage, downImage);
		} catch (IOException ex) {
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	/**
	 * Paint card up or down image
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getHeight() * 71 / 96 * 9 / 10;
		int yPos = getHeight() * 1 / 20;
		g.drawImage((mIsUp && mUpImage != null ? mUpImage : mDownImage), (getWidth() - width) / 2, yPos, this);
	}

	/**
	 * Sets isUp.
	 *
	 * @param isUp the new checks if is up
	 */
	public void setIsUp(boolean isUp) {
		if (this.mUpImage == null && isUp == true)
			return;
		mIsUp = isUp;
		redraw();
	}

	/**
	 * Sets the card.
	 *
	 * @param card the card
	 * @return true, if successful
	 */
	public boolean setCard(Card card) {
		if (card == null) {
			mCard = null;
			mOriginalUpImage = null;
			mUpImage = null;
			setIsUp(false);
			scaleImage();
			redraw();
			return true;
		}
		try {
			mCard = card;
			mOriginalUpImage = ImageIO.read(new File("images/" + card.getImagePath()));
			scaleImage();
			redraw();
			return true;
		} catch (IOException ex) {
			return false;
		}

	}

	/**
	 * Sets the checks if is selected.
	 *
	 * @param isSelected the new checks if is selected
	 */
	public void setIsSelected(boolean isSelected) {
		if (isSelected) {
			this.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
		} else {
			this.setBorder(null);
		}
		redraw();
	}

	/**
	 * Redraw.
	 * 
	 * Revalidates, repaints, and scales
	 */
	public void redraw() {
		scaleImage();
		this.revalidate();
		this.repaint();
	}

	/**
	 * Gets the card.
	 *
	 * @return the card
	 */
	public Card getCard() {
		return mCard;
	}

	/**
	 * Observes clicks.
	 *
	 * @param parent the parent PlayerPane
	 */
	public void observeClicks(PlayerPane parent) {
		mMouseAdapter = new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				parent.addCardToSelection(mCard, CardView.this);
			}
		};
		this.addMouseListener(mMouseAdapter);
	}

	/**
	 * Removes the click observer.
	 */
	public void removeClickObserver() {
		this.removeMouseListener(mMouseAdapter);
	}

}
