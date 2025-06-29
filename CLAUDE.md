# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this
repository.

**Build Commands:**

```bash
./mvnw clean spring-javaformat:apply compile                    # Compile application
./mvnw spring-javaformat:apply test                             # Run all tests
```

## Design Requirements
- **Package**: `am.ik.text` - Main package

## Implemented Features

### Aho-Corasick Algorithm
- **Main Class**: `am.ik.text.AhoCorasick` (final class)
- **Location**: `src/main/java/am/ik/text/AhoCorasick.java`
- **Purpose**: Efficient string matching algorithm for finding multiple patterns in text

#### Key Components:
1. **AhoCorasick class**: Main algorithm implementation
   - Uses trie structure with failure links
   - Factory methods: `AhoCorasick.of(String... patterns)` and `AhoCorasick.of(Collection<String> patterns)`
   - Methods: `search(String text)` returns `List<Match>`, `contains(String text)` returns boolean

2. **Match record**: Represents search results (Java Record)
   - Fields: `String pattern`, `int startIndex`, `int endIndex`
   - Methods: `pattern()`, `startIndex()`, `endIndex()`, `getLength()`
   - Located as static nested record within AhoCorasick class

3. **Node class**: Internal trie node structure (private static class)
   - Contains children map, failure links, and output patterns

#### Implementation Details:
- **Java Version**: Java 17 compatible (uses Records)
- **Algorithm**: Classic Aho-Corasick with trie construction and failure link building
- **Performance**: O(n + m + z) where n=text length, m=total pattern length, z=matches
- **Memory**: Efficient trie structure with HashMap-based children

#### Usage Example:
```java
AhoCorasick ac = AhoCorasick.of("pattern1", "pattern2", "pattern3");
List<AhoCorasick.Match> matches = ac.search("text containing patterns");
for (AhoCorasick.Match match : matches) {
    System.out.println("Found: " + match.pattern() + " at " + match.startIndex());
}
```

#### Test Coverage:
- **AhoCorasickTest**: Comprehensive algorithm testing (10 test cases)
- **MatchTest**: Record functionality testing (5 test cases)
- Tests cover: single/multiple patterns, overlapping patterns, edge cases, performance scenarios

## Development Requirements

### Prerequisites

- Java 17+

### Code Standards

- No external dependencies except for testing libraries
- Use builder pattern if the number of arguments is more than two
- Write javadoc and comments in English
- Spring Java Format enforced via Maven plugin
- All code must pass formatting validation before commit
- Use Java 17 compatible features (avoid Java 21+ specific APIs)

### Testing Strategy

- JUnit 5 with AssertJ
- All tests must pass before completing tasks

### After Task completion

- Ensure all code is formatted using `./mvnw spring-javaformat:apply`
- Run full test suite with `./mvnw test`
- For every task, notify that the task is complete and ready for review by the following command:

```
osascript -e 'display notification "<Message Body>" with title "<Message Title>"’
```
