import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class PokerUI {

	final static int NUM_PLAYERS = 3;
	final static int NUM_DECKS = 1;
	final static int WINDOW_PADDING = 10;
	final static int ENVIRONMENT_BLUEJ = 0;
	final static int ENVIRONMENT_ECLIPSE = 1;

	final static int CURRENT_ENVIRONMENT = ENVIRONMENT_BLUEJ;

	public static void main(String[] args) {
		JFrame pokerFrame = new JFrame();
		GridLayout layout = new GridLayout(NUM_PLAYERS, 1);

		Dimension screenSize;

		if (CURRENT_ENVIRONMENT != ENVIRONMENT_BLUEJ) {
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		} else {
			screenSize = new Dimension(800, 700);
		}

		pokerFrame.setLayout(layout);
		pokerFrame.setTitle("Poker");
		pokerFrame.setVisible(true);

		Poker poker = Poker.makePoker(NUM_PLAYERS, new Deck(NUM_DECKS));

		List<PlayerPane> playerPanes = renderUI(pokerFrame, poker);
		pokerFrame.pack();
		pokerFrame.setSize(screenSize);
		pokerFrame.setPreferredSize(screenSize);
		pokerFrame.setMinimumSize(new Dimension((int) (screenSize.getWidth() / 2), (int) (screenSize.getHeight() / 2)));

		pokerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		poker.setPokerObserver(new Poker.PokerObserver() {

			@Override
			public void onHumanSelectionReady(Poker poker, Player player,
					HumanPlayer.HumanCardDiscardRunnable runnable) {
				PlayerPane humanPlayerPane = null;
				for (PlayerPane playerPane : playerPanes) {
					if (playerPane.getIsHuman()) {
						humanPlayerPane = playerPane;
						break;
					}
				}
				if (humanPlayerPane == null) {
					runnable.run(new ArrayList<>());
					return;
				}

				humanPlayerPane.selectCard(player, new HumanPlayer.HumanCardDiscardRunnable() {

					@Override
					public void run(List<Card> cards) {
						runnable.run(cards);
					}
				});
			}

			@Override
			public void readyToShowPlayerHands(List<Player> players) {
				int place = NUM_PLAYERS;
				for (Player player : players) {
					for (PlayerPane playerPane : playerPanes) {
						if (playerPane.getPlayer() == player) {
							playerPane.showHand(player.getHand(), place);
							break;
						}
					}
					place--;
				}
				pokerFrame.setFocusable(true);
				pokerFrame.requestFocus();
				pokerFrame.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							for (PlayerPane playerPane : playerPanes) {
								playerPane.hideHand();
							}
							poker.executeRound();
							pokerFrame.removeKeyListener(this);
							pokerFrame.setFocusable(false);
						}
					}
				});
			}
		});

		for (PlayerPane playerPane : playerPanes) {
			playerPane.onPack();
		}
		
		pokerFrame.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
		    	for(PlayerPane playerPane : playerPanes) {
		    		playerPane.onPack();
		    	}
		    }
		});

		poker.executeRound();

	}

	static List<PlayerPane> renderUI(JFrame pokerFrame, Poker poker) {
		List<PlayerPane> toReturn = new ArrayList<>();
		for (int i = 0; i < NUM_PLAYERS; i++) {
			Player player = poker.getPlayers().get(i);
			PlayerPane playerPane = new PlayerPane(player, poker);
			toReturn.add(playerPane);
			playerPane.setVisible(true);
			pokerFrame.getContentPane().add(playerPane);
		}
		return toReturn;
	}

}
