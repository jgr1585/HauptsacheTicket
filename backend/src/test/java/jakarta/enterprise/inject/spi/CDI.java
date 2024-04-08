package jakarta.enterprise.inject.spi;

import jakarta.enterprise.inject.Instance;
import org.mockito.Mockito;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class CDI<T> implements Instance<T> {
    private static final CDI<Object> instance = new CDI<>();
    private static final List<Object> beans = new ArrayList<>();

    private final T bean;

    private CDI() {
        bean = null;
    }

    public CDI(T bean) {
        this.bean = bean;
    }

    public static CDI<Object> current() {
        return instance;
    }

    @Override
    public <U extends T> Instance<U> select(Class<U> subtype, Annotation... qualifiers) {

        var bean = beans.stream()
                .filter(b -> subtype.isAssignableFrom(b.getClass()))
                .findFirst()
                .orElse(null);

        if (bean == null) {
            bean = Mockito.mock(subtype);
            beans.add(bean);
            System.out.println("Created new bean of type " + subtype + " with hashcode " + bean.hashCode());
        }

        return new CDI<>(subtype.cast(bean));
    }

    @Override
    public T get() {
        return bean;
    }
}
