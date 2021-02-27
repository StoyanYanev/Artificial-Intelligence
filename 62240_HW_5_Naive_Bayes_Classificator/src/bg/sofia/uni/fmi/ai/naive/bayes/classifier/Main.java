package bg.sofia.uni.fmi.ai.naive.bayes.classifier;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        final List<List<Entity>> validationSet = Loader.loadDataSet();
        double score = 0;
        for (int i = 0; i < NaiveBayesClassifierUtils.MAX_FOLD_NUMBER; ++i) {
            final List<Entity> testSet = validationSet.get(i);
            validationSet.remove(i);
            final NaiveBayesClassifier naiveBayesClassifier = new NaiveBayesClassifier(validationSet, testSet);
            naiveBayesClassifier.train();
            double accuracy = naiveBayesClassifier.accuracy();
            System.out.printf("Accuracy %s - %.2f%s%n", i + 1, accuracy, "%");

            validationSet.add(i, testSet);
            score += accuracy;
        }
        System.out.printf("Total: %.2f%s%n", (score / NaiveBayesClassifierUtils.MAX_FOLD_NUMBER), "%");
    }
}
