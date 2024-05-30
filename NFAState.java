import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A part of Nondeterministic finite automaton
 * Each state can have transitions based on input symbols (0, 1, or ε) to other states. 
 *
 * @author Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.06
 */
public class NFAState {
    private String name;
    public boolean isAccepting;
    private Map<String, List<NFAState>> transitions; //transitions will map input symbols to lists of states to account for nondeterminism.

    /**
     * Constructor for objects of class NFAState
     */
    public NFAState(String name, boolean isAccepting) {
        this.name = name;
        this.isAccepting = isAccepting;
        transitions = new HashMap<>();
    }

    /**
     * @param String symbol input symbol for which the next states are to be determined.0,1,epsilon
     * @param List<NFAState> 
     */
    public void addTransition(String symbol, List<NFAState> nextStates) {
        transitions.put(symbol, nextStates); 
    }
    
    /**
     * Designed to fetch the next possible states for a given input symbol from the current state.
     * @param String symbol
     */   
    public List<NFAState> getNextStates(String symbol) {
        //Fetching the list of next states for the given symbol. 
        //If the symbol is not found in the map, it returns an empty list (List.of()).
        return transitions.getOrDefault(symbol, List.of()); 
    }
    
    public boolean isAccepting() {
        return isAccepting;
    }
    
    public String toString() {
        return name;
    }
}
