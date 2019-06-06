package ru.mail.im.botapi.api;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ApiImplementationFactoryTest {

    private Request executedRequest;

    private final RequestExecutor requestExecutor = new RequestExecutor() {
        @Override
        public <T> T execute(final Request request, final Class<T> responseClass) {
            executedRequest = request;
            return null;
        }
    };

    private final ApiImplementationFactory factory = new ApiImplementationFactory(requestExecutor,
            "https://u.icq.net/rapi/botapi",
            "qwert");

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void getNoParams() throws Exception {
        final TestApi api = factory.createImplementation(TestApi.class);
        api.getNoParams();

        assertNotNull(executedRequest);
        assertEquals("https://u.icq.net/rapi/botapi/get/no-params?token=qwert", executedRequest.url().toString());
        assertNull(executedRequest.body());
    }

    @Test
    public void postNoParams() throws Exception {
        final TestApi api = factory.createImplementation(TestApi.class);
        api.postNoParams();

        assertNotNull(executedRequest);
        assertEquals("https://u.icq.net/rapi/botapi/post/no-params", executedRequest.url().toString());
        assertMultipartBody(executedRequest, "form-data; name=\"token\"", "qwert");
    }

    @Test
    public void getWithParams() throws Exception {
        final TestApi api = factory.createImplementation(TestApi.class);
        api.getUser(123L);

        assertNotNull(executedRequest);
        assertEquals("https://u.icq.net/rapi/botapi/get/user?token=qwert&user_id=123", executedRequest.url().toString());
        assertNull(executedRequest.body());
    }

    @Test
    public void postWithParams() throws Exception {
        final TestApi api = factory.createImplementation(TestApi.class);
        api.postUser(123L);

        assertNotNull(executedRequest);
        assertEquals("https://u.icq.net/rapi/botapi/post/user", executedRequest.url().toString());
        assertMultipartBody(executedRequest,
                "form-data; name=\"token\"", "qwert",
                "form-data; name=\"user_id\"", "123");
    }

    @Test
    public void postFile() throws Exception {
        final File file = temporaryFolder.newFile("qaz.txt");
        Files.write(file.toPath(), "Hello".getBytes());

        factory.createImplementation(TestApi.class).postFile(file);

        assertNotNull(executedRequest);
        assertEquals("https://u.icq.net/rapi/botapi/post/file", executedRequest.url().toString());
        assertMultipartBody(executedRequest,
                "form-data; name=\"token\"", "qwert",
                "form-data; name=\"some_file\"; filename=\"qaz.txt\"", "Hello");
    }

    @Test
    public void notAnnotatedMethod() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(is("Request must be GET or POST"));
        factory.createImplementation(TestApi.class).notAnnotated();
    }

    @Test
    public void notThrowingIOException() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(is("Request method must throw IOException"));
        factory.createImplementation(TestApi.class).noException();
    }

    private static void assertMultipartBody(final Request request, final String... expectedParts) throws IOException {
        assertNotNull(request.body());

        assertTrue(request.body() instanceof MultipartBody);
        final MultipartBody body = (MultipartBody) request.body();

        assertNotNull(body.contentType());
        assertEquals("multipart", body.contentType().type());
        assertEquals("form-data", body.contentType().subtype());

        assertEquals(expectedParts.length / 2, body.parts().size());
        for (int i = 0; i < body.parts().size(); i++) {
            final MultipartBody.Part actualPart = body.part(i);
            assertNotNull(actualPart.headers());
            assertEquals(expectedParts[2 * i], actualPart.headers().get("Content-Disposition"));
            assertEquals(expectedParts[2 * i + 1], bodyToString(actualPart.body()));
        }
    }

    @Nullable
    private static String bodyToString(@Nullable final RequestBody body) throws IOException {
        if (body == null) {
            return null;
        }
        final Buffer buffer = new Buffer();
        body.writeTo(buffer);
        return buffer.readUtf8();
    }

    interface TestApi {

        @GetRequest("get/no-params")
        void getNoParams() throws IOException;

        @PostRequest("post/no-params")
        void postNoParams() throws IOException;

        @GetRequest("get/user")
        void getUser(@RequestParam("user_id") long id) throws IOException;

        @PostRequest("post/user")
        void postUser(@RequestParam("user_id") long id) throws IOException;

        @PostRequest("post/file")
        void postFile(@RequestParam("some_file") File file) throws IOException;

        void notAnnotated() throws IOException;

        void noException();
    }
}