package ru.mail.im.botapi.util;

import javax.annotation.concurrent.GuardedBy;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ListenerList<T> {

    @GuardedBy("this")
    private final List<Descriptor> descriptors = new ArrayList<>();

    private final T proxy;

    public ListenerList(final Class<T> clazz) {
        //noinspection unchecked
        proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                (proxy, method, args) -> {
                    synchronized (ListenerList.class) {
                        for (Descriptor descriptor : descriptors) {
                            method.invoke(descriptor.listener, args);
                        }
                    }
                    return null;
                });
    }

    public synchronized ListenerDescriptor add(final T listener) {
        final Descriptor descriptor = new Descriptor(listener);
        descriptors.add(descriptor);
        return descriptor;
    }

    public T asListener() {
        return proxy;
    }

    private class Descriptor implements ListenerDescriptor {

        private final T listener;

        Descriptor(final T listener) {
            this.listener = listener;
        }

        @Override
        public void remove() {
            synchronized (ListenerList.class) {
                descriptors.remove(this);
            }
        }
    }
}
