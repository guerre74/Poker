import java.util.List;
// TODO: Auto-generated Javadoc

/**
 * The Class Player.
 */
public abstract class Player {

    /** The hand. */
    private Hand mHand;
    
    /** The name. */
    private String mName;
    
    /** The original order. */
    private int mOriginalOrder;

    /**
     * Instantiates a new player.
     */
    public Player() {
        mHand = null;
        mName = "";
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        mName = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return mName;
    }

    /**
     * Sets the hand.
     *
     * @param hand the new hand
     */
    public void setHand(Hand hand) {
        mHand = hand;
    }

    /**
     * Gets the hand.
     *
     * @return the hand
     */
    public Hand getHand() {
        return mHand;
    }
    
    /**
     * Gets the original order.
     *
     * @return the original order
     */
    public int getOriginalOrder() {
    	return mOriginalOrder;
    }
    
    /**
     * Sets the original order.
     *
     * @param order the new original order
     */
    public void setOriginalOrder(int order) {
    	mOriginalOrder = order;
    }

    /**
     * Discard.
     *
     * @param poker the poker
     * @return the list
     */
    abstract List<Card> discard(Poker poker);
}