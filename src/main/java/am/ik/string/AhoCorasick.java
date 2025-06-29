package am.ik.string;

import java.util.*;

/**
 * Implementation of the Aho-Corasick string matching algorithm. This algorithm
 * efficiently finds all occurrences of multiple patterns in a text.
 */
public final class AhoCorasick {

	private final Node root;

	private boolean built = false;

	/**
	 * Creates a new AhoCorasick instance with the given patterns.
	 * @param patterns the patterns to search for
	 */
	private AhoCorasick(String[] patterns) {
		this.root = new Node();
		for (String pattern : patterns) {
			addPattern(pattern);
		}
		buildFailureLinks();
		this.built = true;
	}

	/**
	 * Creates a new AhoCorasick instance with the given patterns.
	 * @param patterns the patterns to search for
	 * @return a new AhoCorasick instance
	 */
	public static AhoCorasick of(String... patterns) {
		return new AhoCorasick(patterns);
	}

	/**
	 * Creates a new AhoCorasick instance with the given patterns.
	 * @param patterns the patterns to search for
	 * @return a new AhoCorasick instance
	 */
	public static AhoCorasick of(Collection<String> patterns) {
		return new AhoCorasick(patterns.toArray(new String[0]));
	}

	/**
	 * Adds a pattern to the trie.
	 * @param pattern the pattern to add
	 */
	private void addPattern(String pattern) {
		if (pattern == null || pattern.isEmpty()) {
			return;
		}

		Node current = root;
		for (char c : pattern.toCharArray()) {
			current = current.children.computeIfAbsent(c, k -> new Node());
		}
		current.isEndOfPattern = true;
		current.pattern = pattern;
	}

	/**
	 * Builds failure links for the Aho-Corasick automaton.
	 */
	private void buildFailureLinks() {
		Queue<Node> queue = new LinkedList<>();

		// Initialize failure links for root's children
		for (Node child : root.children.values()) {
			child.failure = root;
			queue.offer(child);
		}

		while (!queue.isEmpty()) {
			Node current = queue.poll();

			for (Map.Entry<Character, Node> entry : current.children.entrySet()) {
				char c = entry.getKey();
				Node child = entry.getValue();

				// Find the failure link for the child
				Node failure = current.failure;
				while (failure != null && !failure.children.containsKey(c)) {
					failure = failure.failure;
				}

				if (failure != null) {
					child.failure = failure.children.get(c);
				}
				else {
					child.failure = root;
				}

				// Add patterns from failure node to current node
				if (child.failure.isEndOfPattern) {
					child.outputs.add(child.failure.pattern);
				}
				child.outputs.addAll(child.failure.outputs);

				queue.offer(child);
			}
		}
	}

	/**
	 * Searches for all patterns in the given text.
	 * @param text the text to search in
	 * @return a list of matches
	 */
	public List<Match> search(String text) {
		if (!built) {
			throw new IllegalStateException("AhoCorasick automaton not built");
		}

		List<Match> matches = new ArrayList<>();
		Node current = root;

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			// Follow failure links until we find a match or reach root
			while (current != null && !current.children.containsKey(c)) {
				current = current.failure;
			}

			if (current != null) {
				current = current.children.get(c);
			}
			else {
				current = root;
			}

			// Check if current node represents end of pattern
			if (current.isEndOfPattern) {
				int start = i - current.pattern.length() + 1;
				matches.add(new Match(current.pattern, start, i + 1));
			}

			// Check for other patterns ending at this position
			for (String pattern : current.outputs) {
				int start = i - pattern.length() + 1;
				matches.add(new Match(pattern, start, i + 1));
			}
		}

		return matches;
	}

	/**
	 * Checks if the given text contains any of the patterns.
	 * @param text the text to search in
	 * @return true if any pattern is found, false otherwise
	 */
	public boolean contains(String text) {
		return !search(text).isEmpty();
	}

	/**
	 * Represents a match found by the Aho-Corasick algorithm. Contains information about
	 * the matched pattern and its position in the text.
	 *
	 * @param pattern the matched pattern
	 * @param startIndex the start index of the match in the text
	 * @param endIndex the end index of the match in the text
	 */
	public static record Match(String pattern, int startIndex, int endIndex) {

		/**
		 * Returns the length of the matched pattern.
		 * @return the length
		 */
		public int getLength() {
			return endIndex - startIndex;
		}

	}

	/**
	 * Node class representing a node in the trie structure.
	 */
	private static class Node {

		private final Map<Character, Node> children = new HashMap<>();

		private Node failure;

		private boolean isEndOfPattern = false;

		private String pattern;

		private final Set<String> outputs = new HashSet<>();

	}

}