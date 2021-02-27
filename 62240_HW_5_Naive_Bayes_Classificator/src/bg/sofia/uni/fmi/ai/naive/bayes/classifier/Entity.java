package bg.sofia.uni.fmi.ai.naive.bayes.classifier;

import java.util.Arrays;
import java.util.Objects;

public class Entity {
    private String className;
    private Boolean[] attributes;

    public Entity(final String className, final Boolean[] attributes) {
        this.className = className;
        this.attributes = attributes;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    public Boolean[] getAttributes() {
        return attributes;
    }

    public void setAttributes(final Boolean[] attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Entity entity = (Entity) o;
        return Objects.equals(className, entity.className) && Arrays.equals(attributes, entity.attributes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(className);
        result = 31 * result + Arrays.hashCode(attributes);
        return result;
    }
}
