package jakarta.enterprise.inject;

import java.lang.annotation.Annotation;

public interface Instance<T> {
    T get();

    <U extends T> Instance<U> select(Class<U> subtype, Annotation... qualifiers);
}
