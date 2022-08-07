package linky.links;

import java.util.Objects;
import java.util.Set;
import linky.links.validation.Validatable;
import linky.links.validation.Validator;

public class Name {
    private final String text;

    public Name(final String text) {
        this.text = text;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Name name = (Name) o;
        return text.equals(name.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return this.text;
    }

    public static class Unvalidated implements Validatable<Name> {

        private final String name;

        public Unvalidated(final String name) {
            if (name == null) {
                throw new IllegalArgumentException("Name cannot be null!");
            }
            if (name.isBlank()) {
                throw new IllegalArgumentException("Name cannot be blank!");
            }
            this.name = name;
        }

        @Override
        public Name valid(final Validator<Name> validator) {
            validator.test(this.name);
            return new Name(this.name);
        }
    }

    static class IsAbusive implements Validator<Name> {
        private final Set<String> blacklist = Set.of("fuck", "shit", "cunt");

        @Override
        public void test(final String text) {
            final var loweredText = text.toLowerCase();
            final var isAbusive = this.blacklist.stream().anyMatch(loweredText::contains);
            if (isAbusive) {
                throw new NameIsAbusive(text);
            }
        }
    }

    public static class NameIsAbusive extends RuntimeException {
        public NameIsAbusive(final String name) {
            super(String.format("Abusive names: [%s] are not allowed!", name));
        }
    }

    static class IsUnique implements Validator<Name> {
        private final IsNameUsed isNameUsed;

        IsUnique(final IsNameUsed isNameUsed) {
            this.isNameUsed = isNameUsed;
        }

        @Override
        public void test(final String text) {
            if (this.isNameUsed.isInUse(new Name(text))) {
                throw new NameAlreadyInUse(text);
            }
        }
    }

    public static class NameAlreadyInUse extends RuntimeException {
        public NameAlreadyInUse(final String name) {
            super(String.format("Link names have to be unique. [%s] is already in use!", name));
        }
    }
}
