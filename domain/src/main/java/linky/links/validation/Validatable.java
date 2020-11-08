package linky.links.validation;

public interface Validatable<T> {

    T valid(final Validator<T> validator);

}
