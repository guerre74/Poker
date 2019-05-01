public enum Pattern
{
    NONE {
        public boolean matches(Hand hand) {
            return true;
        }
        public int breakTie(Hand a, Hand b) {
            return PatternMatcher.highCard(a).compareTo(PatternMatcher.highCard(b));
        }
    },
    PAIR {
        public boolean matches(Hand hand) {
            return PatternMatcher.numPairs(hand) >= 1;
        }
        public int breakTie(Hand a, Hand b) {
            Card.CardValue aHighCard = PatternMatcher.getPairValue(a, 0);
            Card.CardValue bHighCard = PatternMatcher.getPairValue(b, 0);
            if(aHighCard.compareTo(bHighCard) == 0) {
                return NONE.breakTie(a, b);
            }
            return aHighCard.compareTo(bHighCard);
        }
    },
    TWOPAIR {
        public boolean matches(Hand hand) {
            return PatternMatcher.hasTwoPair(hand);
        }
        public int breakTie(Hand a, Hand b) {
            Card.CardValue aHighCard1 = PatternMatcher.getPairValue(a, 1);
            Card.CardValue bHighCard1 = PatternMatcher.getPairValue(b, 1);
            if(aHighCard1.compareTo(bHighCard1) != 0) {
                return aHighCard1.compareTo(bHighCard1);
            }
            Card.CardValue aHighCard0 = PatternMatcher.getPairValue(a, 0);
            Card.CardValue bHighCard0 = PatternMatcher.getPairValue(b, 0);
            if(aHighCard0.compareTo(bHighCard0) != 0) {
                return aHighCard0.compareTo(bHighCard0);
            }
            return NONE.breakTie(a,b);
        }
    },
    THREEOFKIND {
        public boolean matches(Hand hand) {
            return PatternMatcher.hasThreeOfAKind(hand);
        }
        public int breakTie(Hand a, Hand b) {
            Card.CardValue aHighCard = PatternMatcher.getThreeOfAKindValue(a);
            Card.CardValue bHighCard = PatternMatcher.getThreeOfAKindValue(b);
            if(aHighCard.compareTo(bHighCard) != 0) {
                return aHighCard.compareTo(bHighCard);
            }
            return NONE.breakTie(a,b);
        }
    },
    STRAIGHT {
        public boolean matches(Hand hand) {
            return PatternMatcher.isStraight(hand);
        }
        public int breakTie(Hand a, Hand b) {
            Card.CardValue aHighCard = PatternMatcher.getThreeOfAKindValue(a);
            Card.CardValue bHighCard = PatternMatcher.getThreeOfAKindValue(b);
            return aHighCard.compareTo(bHighCard);
        }
    },
    FLUSH {
        public boolean matches(Hand hand) {
            return PatternMatcher.isFlush(hand);
        }
        public int breakTie(Hand a, Hand b) {
            return NONE.breakTie(a,b);
        }
    },
    FULLHOUSE {
        public boolean matches(Hand hand) {
            return PatternMatcher.hasFullHouse(hand);
        }
        public int breakTie(Hand a, Hand b) {
            Card.CardValue[] aValues = PatternMatcher.getFullHouseValues(a);
            Card.CardValue[] bValues = PatternMatcher.getFullHouseValues(b);
            for(int i = 0; i < 4; i++) {
                if((i < 2 ? aValues : bValues)[(i < 2 ? i : i - 2)] == null) {
                    return 0;
                }
            }
            if(aValues[0].compareTo(bValues[0]) != 0) {
                return aValues[0].compareTo(bValues[0]);
            }
            if(aValues[1].compareTo(bValues[1]) != 0) {
                return aValues[1].compareTo(bValues[1]);
            }
            return 0;
        }
    },
    FOUROFKIND {
        public boolean matches(Hand hand) {
            return PatternMatcher.hasFourOfAKind(hand);
        }
        public int breakTie(Hand a, Hand b) {
            Card.CardValue aValue = PatternMatcher.getFourOfAKindValue(a);
            Card.CardValue bValue = PatternMatcher.getFourOfAKindValue(b);
            return aValue.compareTo(bValue);
        }
    },
    STRAIGHTFLUSH {
        public boolean matches(Hand hand) {
            return PatternMatcher.isStraightFlush(hand);
        }
        public int breakTie(Hand a, Hand b) {
            return NONE.breakTie(a, b);
        }
    };
    abstract boolean matches(Hand hand);
    abstract int breakTie(Hand a, Hand b);
    String readableName;
}
