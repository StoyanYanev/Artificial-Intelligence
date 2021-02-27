package bg.sofia.uni.fmi.ai.naive.bayes.classifier;

import java.util.Objects;

public class Attribute {
    private double yesDemocrats;
    private double noDemocrats;
    private double yesRepublican;
    private double noRepublican;

    public Attribute(final double yesDemocrats, final double noDemocrats, final double yesRepublican, final double noRepublican) {
        this.yesDemocrats = yesDemocrats;
        this.noDemocrats = noDemocrats;
        this.yesRepublican = yesRepublican;
        this.noRepublican = noRepublican;
    }

    public double getYesDemocrats() {
        return yesDemocrats;
    }

    public void setYesDemocrats(final double yesDemocrats) {
        this.yesDemocrats = yesDemocrats;
    }

    public double getNoDemocrats() {
        return noDemocrats;
    }

    public void setNoDemocrats(final double noDemocrats) {
        this.noDemocrats = noDemocrats;
    }

    public double getYesRepublican() {
        return yesRepublican;
    }

    public void setYesRepublican(final double yesRepublican) {
        this.yesRepublican = yesRepublican;
    }

    public double getNoRepublican() {
        return noRepublican;
    }

    public void setNoRepublican(final double noRepublican) {
        this.noRepublican = noRepublican;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Attribute attribute = (Attribute) o;
        return Double.compare(attribute.yesDemocrats, yesDemocrats) == 0 && Double.compare(attribute.noDemocrats, noDemocrats) == 0 &&
                Double.compare(attribute.yesRepublican, yesRepublican) == 0 && Double.compare(attribute.noRepublican, noRepublican) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(yesDemocrats, noDemocrats, yesRepublican, noRepublican);
    }
}
