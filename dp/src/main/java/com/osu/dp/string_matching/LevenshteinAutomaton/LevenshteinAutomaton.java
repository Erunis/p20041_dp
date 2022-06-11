package com.osu.dp.string_matching.LevenshteinAutomaton;

public class LevenshteinAutomaton {
    /** Construction of the initial state of the Levenshtein automaton. */
    private static final State initialState = new State(new Position[] {
            new Position(0, 0)
    });

    /** Method for calculation if the Levenshtein automaton, constructed for the pattern string, accepts the source string.
     * @param source ... input source string
     * @param target ... pattern string
     * @param distance ... maximal allowed Levenshtein distance */
    public static boolean isAccepted(String source, String target, int distance) {
        State currentState = initialState;

        for (int i = 0; i < target.length(); i++) {
            currentState = currentState.transition(source, target.charAt(i), distance);
            if (currentState == null) {
                return false;
            }
        }

        return isAcceptingState(currentState, source.length(), distance);
    }

    /** Method for determining if current state is accepting state. */
    public static boolean isAcceptingState(State state, int sourceLength, int levenshteinDist)
    {
        for (Position position : state.getPositions())
        {
            if (isAcceptingPosition(position, sourceLength, levenshteinDist)) {
                return true;
            }
        }

        return false;
    }

    /** Method for determining if current position in the automaton accepts the string. */
    private static boolean isAcceptingPosition(Position position, int sourceLength, int levenshteinDist) {
        return sourceLength - position.getIndex() <= levenshteinDist - position.getEditsCount();
    }
}