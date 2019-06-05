package ru.mail.im.botapi.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class QueryParameterTest {

    @Test
    public void getValueAsStringNull() {
        assertNull(new QueryParameter("qwe", null).getValueAsString());
    }

    @Test
    public void getValueAsStringInt() {
        assertEquals("123", new QueryParameter("qwe", 123).getValueAsString());
    }

    @Test
    public void getValueAsStringBool() {
        assertEquals("true", new QueryParameter("qwe", true).getValueAsString());
    }
}