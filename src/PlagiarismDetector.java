import java.util.*;
import javax.annotation.*;


abstract class PlagiarismDetector {
	private List<String> synonyms;
	private List<String> baseTextLines;
	private List<String> comparisonTextLines;
	private int tupleSize;

	private final PlagiarismDetectionService plagiarismDetectionService;

  PlagiarismDetector(@Nonnull final PlagiarismDetectionService plagiarismDetectionService) {
	  this.plagiarismDetectionService = plagiarismDetectionService;
  }

  PlagiarismDetector() {
	  this(new PlagiarismDetectionService());
  }

  /**
   * Executes the plagiarism detection algorithm and returns the ratio.
   *
   * @return the plagiarism ratio
   */
  double execute() throws Exception {
		final Map<String, Set<String>> wordToSynonyms = plagiarismDetectionService.constructWordToSynonymsMap(synonyms);
		final List<NTuple> comparisonNTuples = plagiarismDetectionService.constructNTuples(comparisonTextLines, tupleSize);
		final List<NTuple> baseNTuples = plagiarismDetectionService.constructNTuples(baseTextLines, tupleSize);

		final double plagiarismRatio =
        plagiarismDetectionService.calculatePlagiarismRatio(comparisonNTuples, baseNTuples, wordToSynonyms);

		return plagiarismRatio;
	}

  /**
   * Get all the inputs needed for the calculation.
   */
  public void getInput() throws Exception {
		synonyms = readSynonyms();

		baseTextLines = readBaseText();

		comparisonTextLines = readComparisonText();

		tupleSize = readTupleSize();
	}

  /**
   * Read synonyms list.
   *
   * @return the list
   * @throws Exception the exception
   */
  abstract List<String> readSynonyms() throws Exception;

  /**
   * Read base text list.
   *
   * @return the list
   * @throws Exception the exception
   */
  abstract List<String> readBaseText() throws Exception;

  /**
   * Read comparison text list.
   *
   * @return the list
   * @throws Exception the exception
   */
  abstract List<String> readComparisonText() throws Exception;

  /**
   * Read tuple size int.
   *
   * @return the int
   * @throws Exception the exception
   */
  abstract int readTupleSize() throws Exception;
}
