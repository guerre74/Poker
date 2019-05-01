import java.util.Comparator;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DeckTester.
 */
public class DeckTester {

    /** The hands to test. */
    static String[] handsToTest = {
            "3h 4s 5h 6d 7c",
            "3h 4h 5h 6h 7h",
            "Ah 2h 3h 4h 5h",
            "0h Jh Qh Kh Ah",
            "Ah Ad Ac As 2h",
            "Ah Ad 2c 4s 3h"
        };

    /**
     * Test.
     */
    public static void test() {
        System.out.print('\u000C');
        for(String handString: handsToTest) {
            testString(handString);
        }
    }

    /**
     * Test string.
     *
     * @param s the string value of the hand
     */
    public static void testString(String s) {
        Hand hand = Hand.fromString(s);
        System.out.println("Testing hand:\n" + hand.toString() + "\n");
        runTest(hand, "Straight flush", PatternMatcher.isFlush(hand) && PatternMatcher.isStraight(hand));
        runTest(hand, "Four of a kind", PatternMatcher.hasFourOfAKind(hand));
        runTest(hand, "Full house    ", PatternMatcher.hasFullHouse(hand));
        runTest(hand, "Flush         ", PatternMatcher.isFlush(hand));
        runTest(hand, "Straight      ", PatternMatcher.isStraight(hand));
        runTest(hand, "Three of kind ", PatternMatcher.hasThreeOfAKind(hand));
        runTest(hand, "Two pair      ", PatternMatcher.hasTwoPair(hand));
        runTest(hand, "Pair          ", PatternMatcher.numPairs(hand) > 0);
        runTest(hand, "High card     ", Character.toString(PatternMatcher.highCard(hand).getCharValue()));
        runTest(hand, "Highest patt. ", hand.getHighestPattern());
    }

    /**
     * Test random hands.
     *
     * @param n the number of hands to test
     */
    public static void testRandomHands(int n) {
        System.out.print('\u000C');
        ArrayList<Hand> a = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            a.add(new Hand(new Deck()));
            testString(a.get(i).toString());
        }
        a.sort(new Comparator<Hand>() {
            public int compare(Hand a, Hand b) {
                return a.compareTo(b);
            }
        });
        System.out.println("Hands in ascending order: ");
        for(Hand h : a) {
            System.out.println("\t " + h + " ; Highest pattern: " + h.getHighestPattern());
        }
    }

    /**
     * Run test.
     *
     * @param hand the hand
     * @param label the label
     * @param value the value
     */
    static void runTest(Hand hand, String label, Object value) {
        System.out.println("\t" + label + ": " + value + "\n");
    }
}