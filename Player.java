import java.util.List;
public abstract class Player {

    private Hand mHand;
    private String mName;
    private int mOriginalOrder;

    public Player() {
        mHand = null;
        mName = "";
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setHand(Hand hand) {
        mHand = hand;
    }

    public Hand getHand() {
        return mHand;
    }
    
    public int getOriginalOrder() {
    	return mOriginalOrder;
    }
    
    public void setOriginalOrder(int order) {
    	mOriginalOrder = order;
    }

    abstract List<Card> discard(Poker poker);
}