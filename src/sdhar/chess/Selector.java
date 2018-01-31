package sdhar.chess;

import java.util.List;

public interface Selector<T> {
    T select(final List<T> items);
}
