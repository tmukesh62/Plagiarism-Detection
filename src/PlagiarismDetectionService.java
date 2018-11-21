import java.util.*;
import java.util.regex.*;
import javax.annotation.*;


class PlagiarismDetectionService {

  PlagiarismDetectionService() {}

  /**
   * Construct word to synonyms map given the list of synonym texts.
   *
   * For example, given a synonym text "run go jog", the map will be constructed as follows
   *    [ "run" -> {"run", "go", "jog"},
   *      "go" -> {"run", "go", "jog"},
   *      "jog" -> {"run", "go", "jog"}]
   *
   * @param synonyms a list synonyms in format "word1 word2 .... wordN"
   * @return the word to synonyms map
   */
  @Nonnull
  Map<String, Set<String>> constructWordToSynonymsMap(@Nonnull final  List<String> synonyms) {
    final Map<String, Set<String>> wordToSynonyms = new HashMap<>();

    synonyms.stream().forEach(synonym -> {
      // assuming the words are separated by only one space
      final String[] words = synonym.split(" ");
      final Set<String> wordsInSet = new HashSet<>(Arrays.asList(words));
      for(final String word: words) {
        if (!wordToSynonyms.containsKey(word)) {
          wordToSynonyms.put(word, wordsInSet);
        }
      }
    });

    return wordToSynonyms;
  }

  /**
   * Constructs NTuples from given set of lines.
   *
   * For example, if the line is "go for a run" and tupleSize is 3, the NTuples will be as follows
   * [{"go for a", 3}, {"for a run", 3}]
   *
   * @param lines the lines containing the text
   * @param tupleSize the tuple size
   * @return the list of NTuples
   * @throws Exception if there is exception while reading extracting words out of line
   */
  @Nonnull
  List<NTuple> constructNTuples(@Nonnull final List<String> lines, final int tupleSize) throws Exception {
    final List<NTuple> nTuples = new ArrayList<>();

    for (final String line: lines) {
      final List<String> words = getWordsFromLine(line);

      if (words.size() < tupleSize) {
        continue;
      }

      for (int i = 0; i <= words.size() - tupleSize; i++) {

        final NTuple nTuple = new NTuple(tupleSize);

        for (int j = i ; j < i + tupleSize; j++) {
          nTuple.addWord(words.get(j));
        }

        nTuples.add(nTuple);

      }
    }

    return nTuples;
  }

  /**
   * Calculates plagiarism ratio which is percentage of matching tuples of comparison text in base text.
   *
   * @param comparisonNTuples the comparison n tuples
   * @param baseNTuples the base n tuples
   * @param wordToSynonyms the word to synonyms map
   * @return the plagiarism ratio
   */
  double calculatePlagiarismRatio(@Nonnull final List<NTuple> comparisonNTuples,
      @Nonnull final List<NTuple> baseNTuples, @Nonnull final Map<String, Set<String>> wordToSynonyms) {
    int matchingTuples = 0;

    for (final NTuple comparison: comparisonNTuples) {
      for (final NTuple base: baseNTuples) {
        if (base.areEqual(comparison, wordToSynonyms)) {
          matchingTuples++;
          break;
        }
      }
    }


    return matchingTuples * 1.0 / baseNTuples.size();
  }

  /**
   * Get words from a line.
   *
   * @param line the line
   * @return list of words
   */
  @Nonnull
  private List<String> getWordsFromLine(@Nonnull final String line) {
    final List<String> words = new ArrayList<String>();

    final Pattern pattern = Pattern.compile("[\\w']+");
    final Matcher matcher = pattern.matcher(line);

    while (matcher.find()) {
      final String word = line.substring(matcher.start(), matcher.end());

      words.add(word);
    }

    return words;
  }
}
