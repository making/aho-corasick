package am.ik.text;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MatchTest {

	@Test
	void testMatchConstruction() {
		AhoCorasick.Match match = new AhoCorasick.Match("pattern", 5, 12);

		assertThat(match.pattern()).isEqualTo("pattern");
		assertThat(match.startIndex()).isEqualTo(5);
		assertThat(match.endIndex()).isEqualTo(12);
		assertThat(match.getLength()).isEqualTo(7);
	}

	@Test
	void testMatchEquality() {
		AhoCorasick.Match match1 = new AhoCorasick.Match("pattern", 5, 12);
		AhoCorasick.Match match2 = new AhoCorasick.Match("pattern", 5, 12);
		AhoCorasick.Match match3 = new AhoCorasick.Match("different", 5, 12);
		AhoCorasick.Match match4 = new AhoCorasick.Match("pattern", 6, 12);

		assertThat(match1).isEqualTo(match2);
		assertThat(match1).isNotEqualTo(match3);
		assertThat(match1).isNotEqualTo(match4);
		assertThat(match1).isNotEqualTo(null);
		assertThat(match1).isNotEqualTo("not a match");
	}

	@Test
	void testMatchHashCode() {
		AhoCorasick.Match match1 = new AhoCorasick.Match("pattern", 5, 12);
		AhoCorasick.Match match2 = new AhoCorasick.Match("pattern", 5, 12);

		assertThat(match1.hashCode()).isEqualTo(match2.hashCode());
	}

	@Test
	void testMatchToString() {
		AhoCorasick.Match match = new AhoCorasick.Match("pattern", 5, 12);
		String toString = match.toString();

		assertThat(toString).contains("pattern");
		assertThat(toString).contains("5");
		assertThat(toString).contains("12");
	}

	@Test
	void testZeroLengthMatch() {
		AhoCorasick.Match match = new AhoCorasick.Match("", 5, 5);

		assertThat(match.getLength()).isEqualTo(0);
		assertThat(match.pattern()).isEqualTo("");
	}

}