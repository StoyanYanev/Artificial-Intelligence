package bg.sofia.uni.fmi.ai.naive.bayes.classifier;

public final class NaiveBayesClassifierUtils {
    private NaiveBayesClassifierUtils() {
        throw new IllegalArgumentException("The class can no be initialize!");
    }

    public static final String DEMOCRAT_FIELD_NAME = "democrat";
    public static final String REPUBLICAN_FIELD_NAME = "republican";
    public static final int ATTRIBUTES_LENGTH = 16;
    public static final int MAX_FOLD_NUMBER = 10;

}
