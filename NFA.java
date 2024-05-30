import java.util.List;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Nondeterministic Finite Automaton (NFA). NFAs are used in
 * computing for situations where the machine can be in several states at once.
 * This class allows for the creation and evaluation of NFAs.
 *
 * @author Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.28
 */
public class NFA {
	private NFAState startState; // The starting state of the NFA.
	private Set<NFAState> states; // A set of all states in the NFA.

	/**
	 * Constructor for creating an NFA with a specified start state.
	 * 
	 * @param startState The initial state of the NFA.
	 */
	public NFA(NFAState startState) {
		this.startState = startState;
		states = new HashSet<>(); // Initialize the set of states.
	}

	/**
	 * Adds a new state to the NFA.
	 * 
	 * @param state The state to be added to the NFA.
	 */
	public void addNFAState(NFAState state) {
		states.add(state);
	}

	/**
	 * Determines if the NFA accepts a given input string.
	 * 
	 * @param input The string to be checked against the NFA.
	 * @return true if the NFA accepts the string, false otherwise.
	 */
	public boolean accepts(String input) {
		Set<NFAState> currentStates = new HashSet<>();
		currentStates.add(startState); // Start with the initial state.
		currentStates.addAll(eClosure(startState)); // Include states reachable by epsilon-transitions.

		// Process each character of the input string.
		for (char c : input.toCharArray()) {
			Set<NFAState> nextStates = new HashSet<>();
			// For each current state, find and add the next states based on the input
			// character.
			for (NFAState state : currentStates) {
				List<NFAState> transitions = state.getNextStates(String.valueOf(c));
				for (NFAState nextState : transitions) {
					nextStates.add(nextState);
					nextStates.addAll(eClosure(nextState)); // Include states reachable by epsilon-transitions.
				}
			}
			currentStates = nextStates; // Update current states for the next iteration.
		}

		// Check if any of the current states is an accepting state.
		for (NFAState state : currentStates) {
			if (state.isAccepting) {
				return true; // Accept the input if an accepting state is reached.
			}
		}
		return false; // Reject the input if no accepting state is reached.
	}

	/**
	 * Computes the epsilon closure of a given state. The epsilon closure includes
	 * the state itself and any state reachable by epsilon-transitions.
	 * 
	 * @param state The state for which the epsilon closure is to be computed.
	 * @return A set of states forming the epsilon closure.
	 */
	public Set<NFAState> eClosure(NFAState state) {
		Set<NFAState> closure = new HashSet<>();
		exploreEClosure(state, closure); // Recursively find all states in the epsilon closure.
		return closure;
	}

	/**
	 * Helper method to recursively explore the epsilon closure of a state.
	 * 
	 * @param state   The state to explore.
	 * @param closure The set of states forming the epsilon closure.
	 */
	private void exploreEClosure(NFAState state, Set<NFAState> closure) {
		closure.add(state); // Add the state to the closure.
		// Get states reachable from the current state via epsilon-transitions.
		List<NFAState> epsilonTransitions = state.getNextStates("epsilon");
		for (NFAState nextState : epsilonTransitions) {
			// Recursively explore each state if not already in the closure.
			if (!closure.contains(nextState)) {
				exploreEClosure(nextState, closure);
			}
		}
	}
}