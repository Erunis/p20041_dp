package com.osu.dp.string_matching.LevenshteinAutomaton;

import java.util.Arrays;
import java.util.HashSet;

/** Třída reprezentující pozici v Levenshteinově NKA */
public class Position implements Comparable<Position> {
    private final int index; //číslo reprezentující index pozice v automatu
    private final int editsCount; //číslo reprezentující předpokládaný počet operací úprav

    public int getEditsCount() {
        return editsCount;
    }

    public int getIndex() {
        return index;
    }

    private enum levenshteinDistRatio { //poměr mezi Levenshteinovou vzdáleností a provedeným počtem úprav
        MAX, MIDDLE, ZERO
    }

    private enum BitVectorTransitionType { //kategorie velikostí bit-vektorů pro přechody mezi pozicemi
        ATLEAST_TWO, ONE, ZERO
    }

    private  enum IndexPositionType {
        FIRST_INDEX, TRAILING_INDEX, NO_INDEX //kategorie pozic indexů
    }

    private enum Transitions { //definice souboru možných přechodů mezi pozicemi
        MATCH(1, 0), //znaky se shodují
        INSERTION(0, 1), //vkládání znaku
        SUBSTITUTION(1, 1), //substituce
        DELETION(0, 0),  //mazání znaku
        FAILURE(0, 0); //error

        private final int INDEX_OFFSET;
        private final int EDITS_COUNT_OFFSET;

        Transitions(int iOffset, int eOffset) { //konstruktor pro zadávání offsetů pro index pozice v automatu a předpokládaný počet edit operations
            INDEX_OFFSET = iOffset; EDITS_COUNT_OFFSET = eOffset;
        }

        /* V případě operace mazání je potřeba "posunout" celé slovo */
        public Position execute(Position p, int hitIndex) {
            if (!this.equals(FAILURE)) {
                int newI = p.getIndex() + (this.equals(DELETION) ? hitIndex + 1 : INDEX_OFFSET);
                int newE = p.getEditsCount() + (this.equals(DELETION) ? (hitIndex + 1) - 1 : EDITS_COUNT_OFFSET);
                return new Position(newI, newE);
            }
            else {
                return null;
            }
        }
    }

    private static final Transitions[] MATCH_TRANSITION = new Transitions[] {Transitions.MATCH};
    private static final Transitions[] INSERTION_SUBSTITUTION_DELETION_TRANSITION = new Transitions[] {Transitions.INSERTION, Transitions.SUBSTITUTION, Transitions.DELETION};
    private static final Transitions[] INSERTION_SUBSTITUTION_TRANSITION = new Transitions[] {Transitions.INSERTION, Transitions.SUBSTITUTION};
    private static final Transitions[] INSERTION_TRANSITION = new Transitions[] {Transitions.INSERTION};
    private static final Transitions[] FAILURE_TRANSITION = new Transitions[] {Transitions.FAILURE};

    public Position(int index, int editsCount)
    {
        this.index = index;
        this.editsCount = editsCount;
    }

    public Transitions[] getTransition(levenshteinDistRatio levDistRatio, BitVectorTransitionType bVTType, IndexPositionType iPType) {
        switch(levDistRatio) {
            case ZERO:
            case MIDDLE: {
                switch(bVTType) {
                    case ATLEAST_TWO: {
                        switch(iPType) {
                            case FIRST_INDEX:
                                return MATCH_TRANSITION;
                            case TRAILING_INDEX:
                                return INSERTION_SUBSTITUTION_DELETION_TRANSITION;
                            default:
                                return INSERTION_SUBSTITUTION_TRANSITION;
                        }
                    }
                    case ONE: {
                        switch(iPType) {
                            case FIRST_INDEX:
                                return MATCH_TRANSITION;
                            default:
                                return INSERTION_SUBSTITUTION_TRANSITION;
                        }
                    }
                    default:
                        return INSERTION_TRANSITION;
                }
            }
            default:
            {
                switch(bVTType)
                {
                    case ZERO:
                        return FAILURE_TRANSITION;
                    default:
                    {
                        switch(iPType)
                        {
                            case FIRST_INDEX:
                                return MATCH_TRANSITION;
                            default:
                                return FAILURE_TRANSITION;
                        }
                    }
                }
            }
        }
    }


    public State internalTransitions(int levenshteinDist, int bitVectorSize, int index) {
        levenshteinDistRatio levDistRatio = (editsCount < levenshteinDist ?
                (editsCount == 0 ? Position.levenshteinDistRatio.ZERO : Position.levenshteinDistRatio.MIDDLE) : Position.levenshteinDistRatio.MAX);
        BitVectorTransitionType bVTransType = (bitVectorSize >= 2 ? BitVectorTransitionType.ATLEAST_TWO : (bitVectorSize == 1 ?
                BitVectorTransitionType.ONE : BitVectorTransitionType.ZERO));
        IndexPositionType idType;

        switch(index) {
            case -1:    idType = IndexPositionType.NO_INDEX;          break;
            case 0:     idType = IndexPositionType.FIRST_INDEX;       break;
            default:    idType = IndexPositionType.TRAILING_INDEX;    break;
        }

        Transitions[] transition = getTransition(levDistRatio, bVTransType, idType);
        HashSet<Position> newPositionHSet = new HashSet<>();

        for (Transitions current : transition) {
            Position transitionPosition = current.execute(this, index);
            if (transitionPosition != null) {
                newPositionHSet.add(transitionPosition);
            }
        }

        if (!newPositionHSet.isEmpty()) {
            Position[] positionArray = newPositionHSet.toArray(new Position[newPositionHSet.size()]);
            Arrays.sort(positionArray);
            return new State(positionArray);
        }
        else
            return null;
    }

    public int[] getTransitionData(int levenshteinDist, int bitVectorIndex, BitVector parentBitVector) {
        int bitVectorSize;
        int firstIndex;
        BitVector bitVector;

        if (bitVectorIndex < parentBitVector.getBitSetSize()) {
            bitVectorSize = Math.min(levenshteinDist - editsCount + 1, parentBitVector.getBitSetSize() - bitVectorIndex);
            bitVector = parentBitVector.get(bitVectorIndex, bitVectorIndex + bitVectorSize);
            firstIndex = bitVector.nextSetBit(0);
        }
        else {
            bitVectorSize = 0;
            firstIndex = -1;
        }

        return new int[]{bitVectorSize, firstIndex};
    }

    public State transition(int levenshteinDist, int bitVectorIndex, BitVector bitVector) {
        int[] transitionData = getTransitionData(levenshteinDist, bitVectorIndex, bitVector);
        return internalTransitions(levenshteinDist, transitionData[0], transitionData[1]);
    }


    public boolean subsumes(Position p) {
         return (this.editsCount < p.editsCount && !(Math.abs(p.index - this.index) > (p.editsCount - this.editsCount)));
    }

    @Override
    public int compareTo(Position position) {
        if(this.index < position.index) {
            return -1;
        }
        else {
            return 1;
        }
    }
}