# Aho-Corasick

Java implementation of the Aho-Corasick string matching algorithm.

## Usage

```java
AhoCorasick ac = AhoCorasick.of("pattern1", "pattern2", "pattern3");
List<AhoCorasick.Match> matches = ac.search("text containing patterns");

for (AhoCorasick.Match match : matches) {
    System.out.println("Found: " + match.pattern() + " at " + match.startIndex());
}
```

## Build

```bash
./mvnw test
```

## Requirements

- Java 17+