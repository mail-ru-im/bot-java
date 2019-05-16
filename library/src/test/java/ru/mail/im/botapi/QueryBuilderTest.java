package ru.mail.im.botapi;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;

public class QueryBuilderTest {

    @Test
    public void noArg() throws Exception {
        final Method method = Foo.class.getDeclaredMethod("noArg");
        List<QueryParameter> params = new QueryBuilder(method, null).build();
        assertTrue(params.isEmpty());
    }

    @Test
    public void noArg2() throws Exception {
        final Method method = Foo.class.getDeclaredMethod("noArg");
        List<QueryParameter> params = new QueryBuilder(method, new Object[0]).build();
        assertTrue(params.isEmpty());
    }

    @Test
    public void oneSingleArg() throws Exception {
        final Method method = Foo.class.getDeclaredMethod("oneArg", String.class);
        List<QueryParameter> params = new QueryBuilder(method, new Object[]{"Hello"}).build();
        assertEquals(1, params.size());
        assertEquals("test", params.get(0).name);
        assertEquals("Hello", params.get(0).value);
    }

    @Test
    public void oneArrayArg() throws Exception {
        final Method method = Foo.class.getDeclaredMethod("oneArrayArg", int[].class);
        List<QueryParameter> params = new QueryBuilder(method, new Object[]{new int[]{111, 222}}).build();
        assertEquals(2, params.size());

        assertEquals("values", params.get(0).name);
        assertEquals(111, params.get(0).value);

        assertEquals("values", params.get(1).name);
        assertEquals(222, params.get(1).value);
    }

    @Test
    public void noArgAnnotation() throws Exception {
        final Method method = Foo.class.getDeclaredMethod("noAnnotation", int.class, boolean.class, String.class);
        try {
            new QueryBuilder(method, new Object[]{123, true, "qwe"}).build();
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Parameter with index 1 was not annotated with @RequestParam", e.getMessage());
        }
    }

    interface Foo {
        void noArg();

        void oneArg(@RequestParam("test") String s);

        void oneArrayArg(@RequestParam("values") int[] arr);

        void noAnnotation(@RequestParam("a") int a, boolean b, @RequestParam("c") String c);
    }
}