package ru.mail.im.botapi;

import org.junit.Test;

import static org.junit.Assert.*;

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