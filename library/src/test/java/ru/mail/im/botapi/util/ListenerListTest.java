package ru.mail.im.botapi.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ListenerListTest {

    private final ListenerList<Foo> list = new ListenerList<>(Foo.class);

    @Test
    public void addNotify() {
        final FooImpl foo = new FooImpl();
        list.add(foo);

        list.asListener().bar("Hello");

        assertEquals("Hello", foo.lastValue);
    }

    @Test
    public void addRemove() {
        final FooImpl foo = new FooImpl();
        list.add(foo).remove();

        list.asListener().bar("Hello");

        assertNull(foo.lastValue);
    }

    @Test
    public void twoListeners() {
        final FooImpl foo1 = new FooImpl();
        final FooImpl foo2 = new FooImpl();

        final ListenerDescriptor descriptor = list.add(foo1);
        list.add(foo2);
        descriptor.remove();

        list.asListener().bar("World");

        assertNull(foo1.lastValue);
        assertEquals("World", foo2.lastValue);
    }

    interface Foo {
        void bar(String value);
    }

    static class FooImpl implements Foo {

        String lastValue;

        @Override
        public void bar(final String value) {
            lastValue = value;
        }
    }
}