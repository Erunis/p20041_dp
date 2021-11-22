package com.osu.dp.string_matching.LevenshteinAutomaton;

public class LevenshteinAutomaton {
    /** Sestrojení počátečního stavu. */
    private static final State initialState = new State(new Position[] {
            new Position(0, 0)
    });

    /** Metoda pro výpočet, zda-li Levenshteinův automat sestrojený pro cílové slovo přijímá slovo zdrojové.
     * @param source ... zdrojový řetězec
     * @param target ... cílový řetězec
     * @param distance ... maximální povolená Levenshteinova vzdálenost */
    public static boolean isAccepted(String source, String target, int distance) {
        State currentState = initialState;

        for(int i = 0; i < target.length(); i++) {
            currentState = currentState.transition(source, target.charAt(i), distance);
            if (currentState == null) {
                return false;
            }
        }

        return isAcceptingState(currentState, source.length(), distance);
    }

    /** Metoda pro zjištění, jestli je aktuální stav stavem akceptujícím řetězec. */
    public static boolean isAcceptingState(State state, int sourceLength, int levenshteinDist)
    {
        for(Position position : state.getPositions())
        {
            if(isAcceptingPosition(position, sourceLength, levenshteinDist)) {
                return true;
            }
        }

        return false;
    }

    /** Metoda pro zjištění, jestli daná pozice v automatu akceptuje řetězec. */
    private static boolean isAcceptingPosition(Position position, int sourceLength, int levenshteinDist) {
        return sourceLength - position.getIndex() <= levenshteinDist - position.getEditsCount();
    }
}