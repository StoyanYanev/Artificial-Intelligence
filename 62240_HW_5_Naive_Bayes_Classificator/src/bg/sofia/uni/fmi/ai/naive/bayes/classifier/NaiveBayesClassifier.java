package bg.sofia.uni.fmi.ai.naive.bayes.classifier;

import java.util.List;

public class NaiveBayesClassifier {
    private final List<List<Entity>> validationSet;
    private final Attribute[] attributes;
    private final List<Entity> testSet;
    private double probDemocrat;
    private double probRepublican;

    public NaiveBayesClassifier(final List<List<Entity>> validationSet, final List<Entity> testSet) {
        this.validationSet = validationSet;
        this.testSet = testSet;
        probDemocrat = 0;
        probRepublican = 0;
        attributes = new Attribute[NaiveBayesClassifierUtils.ATTRIBUTES_LENGTH];
    }

    public void train() {
        int numberOfDemocrats = 0;
        int numberOfRepublicans = 0;
        for (int i = 0; i < attributes.length; ++i) {
            double yesDemocrat = 1;
            double noDemocrat = 1;
            double yesRepublican = 1;
            double noRepublican = 1;
            for (final List<Entity> dataSet : validationSet) {
                for (final Entity entity : dataSet) {
                    final boolean isDemocrat = entity.getClassName()
                            .equals(NaiveBayesClassifierUtils.DEMOCRAT_FIELD_NAME);
                    final boolean isEmptyAttribute = entity.getAttributes()[i] == null;
                    if (isDemocrat) {
                        numberOfDemocrats++;
                        if (!isEmptyAttribute && entity.getAttributes()[i]) {
                            yesDemocrat++;
                        } else if (!isEmptyAttribute && !entity.getAttributes()[i]) {
                            noDemocrat++;
                        }
                    } else {
                        numberOfRepublicans++;
                        if (!isEmptyAttribute && entity.getAttributes()[i]) {
                            yesRepublican++;
                        } else if (!isEmptyAttribute && !entity.getAttributes()[i]) {
                            noRepublican++;
                        }
                    }
                }
                attributes[i] = calculateAttributeProbability(yesDemocrat, noDemocrat, yesRepublican, noRepublican);
            }
        }
        probDemocrat = (double) numberOfDemocrats / (numberOfDemocrats + numberOfRepublicans);
        probRepublican = (double) numberOfRepublicans / (numberOfDemocrats + numberOfRepublicans);
    }

    private Attribute calculateAttributeProbability(final double yesDemocrat,
                                                    final double noDemocrat,
                                                    final double yesRepublican,
                                                    final double noRepublican) {

        final double probYesDemocrats = yesDemocrat / (yesDemocrat + noDemocrat) + 1;
        final double probNoDemocrats = noDemocrat / (yesDemocrat + noDemocrat) + 1;
        final double probYesRepublican = yesRepublican / (yesRepublican + noRepublican) + 1;
        final double probNoRepublican = noRepublican / (yesRepublican + noRepublican) + 1;

        return new Attribute(probYesDemocrats, probNoDemocrats, probYesRepublican, probNoRepublican);
    }

    public double accuracy() {
        double score = 0;
        for (final Entity test : testSet) {
            double proportialDemocrat = Math.log(probDemocrat);
            double proportialRepublican = Math.log(probRepublican);
            for (int i = 0; i < attributes.length; ++i) {
                final boolean isEmptyAttribute = test.getAttributes()[i] == null;
                if (!isEmptyAttribute && test.getAttributes()[i]) {
                    proportialDemocrat += Math.log(attributes[i].getYesDemocrats());
                    proportialRepublican += Math.log(attributes[i].getYesRepublican());
                } else if (!isEmptyAttribute && !test.getAttributes()[i]) {
                    proportialDemocrat += Math.log(attributes[i].getNoDemocrats());
                    proportialRepublican += Math.log(attributes[i].getNoRepublican());
                }
            }
            if ((proportialDemocrat > proportialRepublican && test.getClassName()
                    .equals(NaiveBayesClassifierUtils.DEMOCRAT_FIELD_NAME)) || (proportialDemocrat < proportialRepublican &&
                    test.getClassName()
                            .equals(NaiveBayesClassifierUtils.REPUBLICAN_FIELD_NAME))) {
                score++;
            }
        }
        return (score / testSet.size()) * 100;
    }
}
