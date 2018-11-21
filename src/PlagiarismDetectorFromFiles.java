import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import javax.annotation.*;


class PlagiarismDetectorFromFiles extends PlagiarismDetector {
	private static final int DEFAULT_TUPLE_SIZE = 3;

	private final Scanner scanner;

	PlagiarismDetectorFromFiles() {
		super();
		scanner = new Scanner(System.in);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nonnull
	List<String> readSynonyms() throws Exception {
		System.out.print("Enter path of the file with list of synonyms: ");

		return readFile();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nonnull
	List<String> readBaseText() throws Exception {
		System.out.print("Enter path of the file with base text: ");

		return readFile();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nonnull
	List<String> readComparisonText() throws Exception {
		System.out.print("Enter path of the file with comparison text: ");

		return readFile();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	int readTupleSize() throws Exception {
		System.out.print(String.format("Would you like to enter tuple size? (y/n) [Default = %d]: ", DEFAULT_TUPLE_SIZE));

		final String decision = scanner.next();

		if ("y".equals(decision)) {
			System.out.print("Tuple size = ");
			return scanner.nextInt();
		}

		return DEFAULT_TUPLE_SIZE;
	}

	/**
	 * Reads the file path as an input from command line and returns the list of lines from the file
	 * @return lines of the file
	 * @throws IOException if there is error during file read
	 */
	@Nonnull
	private List<String> readFile() throws IOException {
		final String filePath = scanner.next();

		return Files.readAllLines(Paths.get(filePath));
	}
}
