package linky.visits;

public class Country {

    private final String name;

    public Country(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
