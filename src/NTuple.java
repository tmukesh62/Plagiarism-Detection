import java.util.*;


class NTuple {
	private final List<String> words;
	private int N;

	NTuple(final int N) {
		words = new ArrayList<>();
		this.N = N;
	}

	void addWord(final String word) throws Exception {
		if (words.size() == N) {
			throw new Exception(String.format("Tuple size is already N(%d)", N));
		}

		words.add(word);
	}

	List<String> getWords() {
		return words;
	}

	/**
	 * Checks to see if the given NTuple is equal to this using word to synonyms map.
	 *
	 * @param other the other NTuple
	 * @param wordToSynonyms the word to synonyms map
	 * @return true if other NTuple is equal to this and fals otherwise
	 */
	boolean areEqual(final NTuple other, final Map<String, Set<String>> wordToSynonyms) {
		final List<String> words1 = this.getWords();
        final List<String> words2 = other.getWords();

        if (words1.size() != words2.size())
            return false;

        for (int i = 0; i < words1.size(); i++) {
            final String word1 = words1.get(i);
            final String word2 = words2.get(i);

            if (!word1.equals(word2)) {

                if (!wordToSynonyms.containsKey(word1))
                    return false;

                Set<String> synonyms = wordToSynonyms.get(word1);

                if (!synonyms.contains(word2))
                    return false;
            }
        }

        return true;
	}
}
