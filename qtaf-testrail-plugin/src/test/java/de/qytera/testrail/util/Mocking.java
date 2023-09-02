package de.qytera.testrail.util;

import jakarta.ws.rs.core.Response;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

/**
 * Utility class for setting up complicated mocks.
 */
public class Mocking {
    /**
     * Turn a locally made {@link Response} into a simulated inbound one, which can be used
     * as though it came from a real server.
     *
     * @param response response to mock as an incoming response
     * @return the inbound response
     * @see <a href="https://stackoverflow.com/q/19557672">Stackoverflow Discussion</a>
     */
    public static Response simulateInbound(Response response) {
        Response mockedResponse = Mockito.spy(response);
        Mockito.doAnswer(AdditionalAnswers.answer((Class<?> type) -> readEntity(mockedResponse, type)))
                .when(mockedResponse)
                .readEntity(ArgumentMatchers.<Class<?>>any());
        return mockedResponse;
    }

    private static <T> T readEntity(Response mockedResponse, Class<T> t) {
        return t.cast(mockedResponse.getEntity());
    }

}