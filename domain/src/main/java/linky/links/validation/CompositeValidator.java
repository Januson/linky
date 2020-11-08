package linky.links.validation;

import java.util.List;

public class CompositeValidator<T> implements Validator<T> {

    private final List<Validator<T>> validators;

    public CompositeValidator(final List<Validator<T>> validators) {
        this.validators = validators;
    }

    @Override
    public void test(final String item) {
        this.validators.forEach(validator -> validator.test(item));
    }
}
