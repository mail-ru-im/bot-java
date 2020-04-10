package ru.mail.im.botapi.api;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class QueryParameterTest {

    @Test
    public void getValueAsStringNull() {
        assertNull(new QueryParameter(new Gson(), "qwe", null).getValueAsString());
    }

    @Test
    public void getValueAsStringInt() {
        assertEquals("123", new QueryParameter(new Gson(), "qwe", 123).getValueAsString());
    }

    @Test
    public void getValueAsStringBool() {
        assertEquals("true", new QueryParameter(new Gson(), "qwe", true).getValueAsString());
    }

    @Test
    public void getValueAsStringString() {
        assertEquals("value", new QueryParameter(new Gson(), "qwe", "value").getValueAsString());
    }

    @Test
    public void getValueAsStringPojo() {
        assertEquals("[\"value\"]", new QueryParameter(new Gson(), "qwe", Collections.singletonList("value")).getValueAsString());
    }
}