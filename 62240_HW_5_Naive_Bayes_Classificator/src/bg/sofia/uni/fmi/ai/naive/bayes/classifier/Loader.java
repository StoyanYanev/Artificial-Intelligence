package bg.sofia.uni.fmi.ai.naive.bayes.classifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public final class Loader {
    private static final String DATA_PATH = "src/bg/sofia/uni/fmi/ai/naive/bayes/classifier/house-votes-84.data";

    private Loader() {
        throw new IllegalArgumentException("The class can no be initialize!");
    }

    public static List<List<Entity>> loadDataSet() throws FileNotFoundException {
        final File file = new File(DATA_PATH);
        final List<Entity> entities = new ArrayList<>();
        try (final Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                final String line = scanner.nextLine();
                entities.add(createEntity(line));
            }
        }
        Collections.shuffle(entities);
        return createValidationSets(entities);
    }

    private static Entity createEntity(final String line) {
        final String[] attributeValues = line.split(",");
        final String attributeName = attributeValues[0];
        final Entity entity = new Entity(attributeName, new Boolean[NaiveBayesClassifierUtils.ATTRIBUTES_LENGTH]);

        for (int i = 1; i < attributeValues.length; ++i) {
            if (attributeValues[i].equals("y")) {
                entity.getAttributes()[i - 1] = true;
            } else if (attributeValues[i].equals("n")) {
                entity.getAttributes()[i - 1] = false;
            } else {
                entity.getAttributes()[i - 1] = null;
            }
        }
        return entity;
    }

    private static List<List<Entity>> createValidationSets(final List<Entity> entities) {
        final List<List<Entity>> validationSets = new ArrayList<>();
        int leftIndex = 0;
        int dataSetSize = entities.size() / NaiveBayesClassifierUtils.MAX_FOLD_NUMBER;
        int rightIndex = dataSetSize;
        for (int i = 0; i < NaiveBayesClassifierUtils.MAX_FOLD_NUMBER; ++i) {
            if (rightIndex > entities.size()) {
                rightIndex = entities.size();
            }
            final List<Entity> result = entities.subList(leftIndex, rightIndex);
            validationSets.add(result);
            leftIndex = rightIndex;
            rightIndex += dataSetSize;
        }
        return validationSets;
    }
}
