package ru.mail.im.botapi.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class QueryBuilder {
    private final Method method;
    private final Object[] args;

    QueryBuilder(final Method method, final Object[] args) {
        this.method = method;
        this.args = args;
    }

    List<QueryParameter> build() {
        if (args == null) {
            return Collections.emptyList();
        }
        final List<QueryParameter> result = new ArrayList<>(args.length);
        for (int i = 0; i < args.length; i++) {
            final String name = getParamName(i);
            for (Object value : getParamValues(i)) {
                result.add(new QueryParameter(name, value));
            }
        }
        return result;
    }

    private String getParamName(final int index) {
        final Annotation[] annotations = method.getParameterAnnotations()[index];
        for (Annotation annotation : annotations) {
            if (annotation instanceof RequestParam) {
                return ((RequestParam) annotation).value();
            }
        }
        throw new IllegalArgumentException("Parameter with index " + index + " was not annotated with @RequestParam");
    }

    private List<Object> getParamValues(final int index) {
        Object arg = args[index];
        if (arg != null && arg.getClass().isArray()) {
            final int length = Array.getLength(arg);
            final List<Object> list = new ArrayList<>(length);
            for (int i = 0; i < length; i++) {
                list.add(Array.get(arg, i));
            }
            return list;
        }
        return Collections.singletonList(arg);
    }
}
