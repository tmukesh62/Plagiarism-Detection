public class CommandLineApp {
  public static void main(String[] args) throws Exception {
    final PlagiarismDetectorFromFiles plagiarismDetectionFromFiles = new PlagiarismDetectorFromFiles();

    plagiarismDetectionFromFiles.getInput();

    double plagiarismPercentage = plagiarismDetectionFromFiles.execute() * 100;

    System.out.println(String.format("The plagiarism percentage is %4.2f", plagiarismPercentage));
  }
}
