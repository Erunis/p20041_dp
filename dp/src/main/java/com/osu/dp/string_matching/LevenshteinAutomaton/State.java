package com.osu.dp.string_matching.LevenshteinAutomaton;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/** Class representing the set of positions in the automaton. */
public class State {
    private final Position[] positions;

    public State(Position[] positions) {
        this.positions = positions;
    }

    public Position[] getPositions() {
        return positions;
    }

    public State(Collection<State> stateCollection, int levenshteinDist) {
        HashSet<Position> positionsHSet = new HashSet<>(); // Creation of HashSet for the positions

        for(State state : stateCollection) {
            positionsHSet.addAll(Arrays.asList(state.positions)); // Adding positions for every state.
        }

        /** Iteration through {@code positionsHSet} and deleting every position that is already included. */
        Iterator iterator1 = positionsHSet.iterator();
        while(iterator1.hasNext()) {
            Position position1 = (Position)iterator1.next();
            Iterator iterator2 = positionsHSet.iterator();
            while(iterator2.hasNext()) {
                Position position2 = (Position)iterator2.next();

                if(position2.contains(position1)) {
                    iterator1.remove();
                    break;
                }
            }
        }

        /** Converting {@code positionsHSet} to array and its sorting. */
        positions = positionsHSet.toArray(new Position[positionsHSet.size()]);
        Arrays.sort(positions);
    }

    /** Method for calculating the current state of the automaton.
     * @param source ... input source string
     * @param targetChar ... currently examined letter in pattern string
     * @param levenshteinDist ... maximal allowed Levenshtein distance */
    public State transition(String source, char targetChar, int levenshteinDist) {
        HashSet<State> statesHSet = new HashSet<>(); // HashSet of all the states we can get in with transitions.
        int index = positions[0].getIndex(); // Index in bit-vector representation.
        BitVector bitVector = getBitVector(source, targetChar, levenshteinDist); // Conversion to bit-vector representation.

        // Performing all of the transitions to next states that are added to the stateHSet
        for(Position position : positions) {
            State transitionState = position.transition(levenshteinDist, position.getIndex() - index, bitVector);
            if(transitionState != null) {
                statesHSet.add(transitionState);
            }
        }

        return (statesHSet.isEmpty() ? null : new State(statesHSet, levenshteinDist));
    }

    /** Method for converting string into their bit-vector represantation so it's easier for algorithms to work with them. */
    public BitVector getBitVector(String source, char targetChar, int levenshteinDist) {
        int minIndex = positions[0].getIndex();
        int bitVectorSize = Math.min(2 * levenshteinDist + 1, source.length() - minIndex);
        String sourceSub = source.substring(minIndex, minIndex + bitVectorSize); // Cropping of the source string
        BitVector bitVector = new BitVector(bitVectorSize); // Creating bit-vector representation

        /** Loops through every letter in {@code sourceSub} and sets 1 in the bit-vector, if the letter is identical
         * with the one in pattern string, otherwise it sets the value to 0. */
        for (int i = 0; i < bitVectorSize; i++) {
            if (sourceSub.charAt(i) == targetChar) bitVector.set(i);
        }

        return bitVector;
    }

}