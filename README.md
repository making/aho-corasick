# Aho-Corasick

Java implementation of the Aho-Corasick string matching algorithm.


<dependency>
  <groupId>am.ik.text</groupId>
  <artifactId>aho-corasick</artifactId>
  <version>0.1.0</version>
</dependency>


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
