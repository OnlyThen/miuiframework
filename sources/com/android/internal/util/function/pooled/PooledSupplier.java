package com.android.internal.util.function.pooled;

import com.android.internal.util.FunctionalUtils.ThrowingSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

public interface PooledSupplier<T> extends PooledLambda, Supplier<T>, ThrowingSupplier<T> {

    public interface OfInt extends IntSupplier, PooledLambda {
        OfInt recycleOnUse();
    }

    public interface OfLong extends LongSupplier, PooledLambda {
        OfLong recycleOnUse();
    }

    public interface OfDouble extends DoubleSupplier, PooledLambda {
        OfDouble recycleOnUse();
    }

    PooledRunnable asRunnable();

    PooledSupplier<T> recycleOnUse();
}
