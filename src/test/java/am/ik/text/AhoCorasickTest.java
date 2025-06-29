package am.ik.text;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AhoCorasickTest {

	@Test
	void testSinglePattern() {
		AhoCorasick ac = AhoCorasick.of("he");
		List<AhoCorasick.Match> matches = ac.search("hello");

		assertThat(matches).hasSize(1);
		assertThat(matches.get(0).pattern()).isEqualTo("he");
		assertThat(matches.get(0).startIndex()).isEqualTo(0);
		assertThat(matches.get(0).endIndex()).isEqualTo(2);
	}

	@Test
	void testMultiplePatterns() {
		AhoCorasick ac = AhoCorasick.of("he", "she", "his", "hers");
		List<AhoCorasick.Match> matches = ac.search("ushers");

		assertThat(matches).hasSize(3);

		// Sort matches by start index for consistent testing
		matches.sort((a, b) -> Integer.compare(a.startIndex(), b.startIndex()));

		assertThat(matches.get(0).pattern()).isEqualTo("she");
		assertThat(matches.get(0).startIndex()).isEqualTo(1);

		assertThat(matches.get(1).pattern()).isEqualTo("he");
		assertThat(matches.get(1).startIndex()).isEqualTo(2);

		assertThat(matches.get(2).pattern()).isEqualTo("hers");
		assertThat(matches.get(2).startIndex()).isEqualTo(2);
	}

	@Test
	void testOverlappingPatterns() {
		AhoCorasick ac = AhoCorasick.of("abc", "bcd", "cde");
		List<AhoCorasick.Match> matches = ac.search("abcde");

		assertThat(matches).hasSize(3);

		matches.sort((a, b) -> Integer.compare(a.startIndex(), b.startIndex()));

		assertThat(matches.get(0).pattern()).isEqualTo("abc");
		assertThat(matches.get(0).startIndex()).isEqualTo(0);

		assertThat(matches.get(1).pattern()).isEqualTo("bcd");
		assertThat(matches.get(1).startIndex()).isEqualTo(1);

		assertThat(matches.get(2).pattern()).isEqualTo("cde");
		assertThat(matches.get(2).startIndex()).isEqualTo(2);
	}

	@Test
	void testNoMatches() {
		AhoCorasick ac = AhoCorasick.of("abc", "def");
		List<AhoCorasick.Match> matches = ac.search("xyz");

		assertThat(matches).isEmpty();
	}

	@Test
	void testEmptyText() {
		AhoCorasick ac = AhoCorasick.of("abc");
		List<AhoCorasick.Match> matches = ac.search("");

		assertThat(matches).isEmpty();
	}

	@Test
	void testEmptyPattern() {
		AhoCorasick ac = AhoCorasick.of("", "abc");
		List<AhoCorasick.Match> matches = ac.search("abc");

		assertThat(matches).hasSize(1);
		assertThat(matches.get(0).pattern()).isEqualTo("abc");
	}

	@Test
	void testContains() {
		AhoCorasick ac = AhoCorasick.of("he", "she");

		assertThat(ac.contains("hello")).isTrue();
		assertThat(ac.contains("she")).isTrue();
		assertThat(ac.contains("xyz")).isFalse();
	}

	@Test
	void testSingleCharacterPatterns() {
		AhoCorasick ac = AhoCorasick.of("a", "b", "c");
		List<AhoCorasick.Match> matches = ac.search("abcabc");

		assertThat(matches).hasSize(6);

		matches.sort((a, b) -> Integer.compare(a.startIndex(), b.startIndex()));

		assertThat(matches.get(0).pattern()).isEqualTo("a");
		assertThat(matches.get(0).startIndex()).isEqualTo(0);

		assertThat(matches.get(1).pattern()).isEqualTo("b");
		assertThat(matches.get(1).startIndex()).isEqualTo(1);

		assertThat(matches.get(2).pattern()).isEqualTo("c");
		assertThat(matches.get(2).startIndex()).isEqualTo(2);
	}

	@Test
	void testRepeatedPattern() {
		AhoCorasick ac = AhoCorasick.of("aa");
		List<AhoCorasick.Match> matches = ac.search("aaaa");

		assertThat(matches).hasSize(3);

		matches.sort((a, b) -> Integer.compare(a.startIndex(), b.startIndex()));

		assertThat(matches.get(0).startIndex()).isEqualTo(0);
		assertThat(matches.get(1).startIndex()).isEqualTo(1);
		assertThat(matches.get(2).startIndex()).isEqualTo(2);
	}

	@Test
	void testLongText() {
		AhoCorasick ac = AhoCorasick.of("pattern");
		String longText = "This is a long text with pattern in it and another pattern here.";
		List<AhoCorasick.Match> matches = ac.search(longText);

		assertThat(matches).hasSize(2);
		assertThat(matches.get(0).pattern()).isEqualTo("pattern");
		assertThat(matches.get(1).pattern()).isEqualTo("pattern");
	}

}