package linky.infrastructure;

public interface Events<T> {
    void fire(T event);
}
