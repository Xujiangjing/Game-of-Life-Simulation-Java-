import java.util.Arrays;

/**
 * Demonstrates the setup and running of a Nondeterministic Finite Automaton
 * (NFA).
 *
 * @author Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.28
 */
public class NFARun {

	public static void main(String[] args) {
		// Creating states for the NFA.
		// The names of the states are given and whether they are accepting states is
		// also defined.
		NFAState s = new NFAState("S", false);
		NFAState p = new NFAState("P", false);
		NFAState t = new NFAState("T", false);
		NFAState q = new NFAState("Q", false);
		NFAState r = new NFAState("R", false);
		NFAState trueState = new NFAState("True", true); // Assuming this is the accepting state.

		// Setting up transitions for each state.
		// These transitions define how the automaton moves from one state to another
		// based on input.
		s.addTransition("1", Arrays.asList(s, trueState)); // S to S and True on '1'
		s.addTransition("ε", Arrays.asList(p)); // S to P on 'ε'
		s.addTransition("0", Arrays.asList(t)); // S to T on '0'
		t.addTransition("0", Arrays.asList(s)); // T to S on '0'
		p.addTransition("0", Arrays.asList(s)); // P to S on '0'
		p.addTransition("1", Arrays.asList(q)); // P to Q on '1'
		q.addTransition("0", Arrays.asList(r, trueState)); // Q to R and True on '0'
		r.addTransition("0", Arrays.asList(r, trueState)); // R loops on '0' and True
		r.addTransition("1", Arrays.asList(r, trueState)); // R loops on '1' and True

		// Create NFA
		NFA nfa = new NFA(s);
		nfa.addNFAState(s);
		nfa.addNFAState(p);
		nfa.addNFAState(t);
		nfa.addNFAState(q);
		nfa.addNFAState(r);
		nfa.addNFAState(trueState);
	}
}