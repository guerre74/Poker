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

public class CardView extends JPanel {
	private final static String DOWN_CARD_URL = "images/back.GIF";
	private Image mUpImage;
	private Image mDownImage;
	private Image mOriginalUpImage;
	private Image mOriginalDownImage;
	private PlayerPane mPlayerPane;
	private MouseAdapter mMouseAdapter;
	private Card mCard;
	private boolean mIsUp;

	private CardView(Card card, BufferedImage upImage, BufferedImage downImage) {
		mCard = card;
		mOriginalUpImage = upImage;
		mOriginalDownImage = downImage;
		setMinimumSize(new Dimension(71, 96));
		scaleImage();
		this.validate();
		this.repaint();
	}

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

	public static CardView makeCardView() {
		try {
			BufferedImage upImage = null;
			BufferedImage downImage = ImageIO.read(new File(DOWN_CARD_URL));
			return new CardView(null, upImage, downImage);
		} catch (IOException ex) {
		}
		return null;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getHeight() * 71 / 96 * 9 / 10;
		int yPos = getHeight() * 1 / 20;
		g.drawImage((mIsUp && mUpImage != null ? mUpImage : mDownImage), (getWidth() - width) / 2, yPos, this);
	}

	public void setIsUp(boolean isUp) {
		if (this.mUpImage == null && isUp == true)
			return;
		mIsUp = isUp;
		redraw();
	}

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

	public void setIsSelected(boolean isSelected) {
		if (isSelected) {
			this.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
		} else {
			this.setBorder(null);
		}
		redraw();
	}

	public void redraw() {
		scaleImage();
		this.revalidate();
		this.repaint();
	}

	public Card getCard() {
		return mCard;
	}

	public void observeClicks(PlayerPane parent) {
		mMouseAdapter = new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				parent.addCardToSelection(mCard, CardView.this);
			}
		};
		this.addMouseListener(mMouseAdapter);
	}

	public void removeClickObserver() {
		this.removeMouseListener(mMouseAdapter);
	}

}
