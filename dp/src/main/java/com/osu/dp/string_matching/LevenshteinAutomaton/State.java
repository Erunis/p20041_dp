package com.osu.dp.string_matching.LevenshteinAutomaton;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/** Třída reprezentující množinu pozic. */
public class State {
    private final Position[] positions;

    public State(Position[] positions) {
        this.positions = positions;
    }

    public Position[] getPositions() {
        return positions;
    }

    public State(Collection<State> stateCollection, int levenshteinDist) {
        HashSet<Position> positionsHSet = new HashSet<>(); //vytvoření HashSetu pro pozice

        for(State state : stateCollection) {
            positionsHSet.addAll(Arrays.asList(state.positions)); //přidání pozic pro každý stav do positionHSet
        }

        /* Iterování skrz positionsHSet a vyhození všech pozic, které už jsou v HashSetu zahrnuty v nějaké jiné */
        Iterator iterator1 = positionsHSet.iterator();
        while(iterator1.hasNext()) {
            Position position1 = (Position)iterator1.next();
            Iterator iterator2 = positionsHSet.iterator();
            while(iterator2.hasNext()) {
                Position position2 = (Position)iterator2.next();

                if(position2.subsumes(position1)) {
                    iterator1.remove();
                    break;
                }
            }
        }

        /* Převod positionsHSet na pole a jeho setřízení */
        positions = positionsHSet.toArray(new Position[positionsHSet.size()]);
        Arrays.sort(positions);
    }

    /** Metoda pro vracení aktuálního stavu.
     * @param source ... zdrojový řetězec
     * @param targetChar ... aktuální zpracovávaný znak v cílovém řetězci
     * @param levenshteinDist ... maximální možná Levenshteinova vzdálenost
     */
    public State transition(String source, char targetChar, int levenshteinDist) {
        HashSet<State> statesHSet = new HashSet<>(); //HashSet pro ukládání všech stavů, do kterých se po přechodu pomocí zadaných údajů dostaneme
        int index = positions[0].getIndex(); //index v bit-vektorové reprezentaci
        BitVector bitVector = getBitVector(source, targetChar, levenshteinDist); //převod na bit-vektorovou reprezentaci pro NULA

        //provedení všech přechodů do dalších stavů, které se přidají do statesHSet
        for(Position position : positions)
        {
            State transitionState = position.transition(levenshteinDist, position.getIndex() - index, bitVector);
            if(transitionState != null) {
                statesHSet.add(transitionState);
            }
        }

        return (statesHSet.isEmpty() ? null : new State(statesHSet, levenshteinDist));
    }

    /**
     * Procures an object representation of the characteristic vector of this State's relevant subword.
     */
    public BitVector getBitVector(String source, char targetChar, int levenshteinDist) {
        int minIndex = positions[0].getIndex();
        int bitVectorSize = Math.min(2 * levenshteinDist + 1, source.length() - minIndex);
        String sourceSub = source.substring(minIndex, minIndex + bitVectorSize); //oříznutí zdrojového řetězce
        BitVector bitVector = new BitVector(bitVectorSize); //vytvoření bit-vektorové reprezentace

        /* Cyklus prochází znaky v sourceSub a do bit-vektoru nastavuje 1, pokud se znak rovná vybranému znaku cílového řetězce, v opačném případě nastavuje 0. */
        for(int i = 0; i < bitVectorSize; i++) {
            if(sourceSub.charAt(i) == targetChar)
                bitVector.set(i);
        }

        return bitVector;
    }

}