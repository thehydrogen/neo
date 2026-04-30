package me.hydro.emulator.util;

import lombok.NonNull;

public interface GuavaPredicate<T> extends java.util.function.Predicate<T> {
    boolean apply(T input);

    boolean equals(@NonNull Object object);

    default boolean test(T input) {
        return this.apply(input);
    }
}
