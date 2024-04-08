package jakarta.ws.rs;

import jakarta.ws.rs.core.Response;

@SuppressWarnings("unused")
public class WebApplicationException extends RuntimeException {

    private Response.Status status;

    public WebApplicationException() {
    }

    public WebApplicationException(String message, Throwable cause, Response.Status status) {
        super(message, cause);
        this.status = status;
    }

    public WebApplicationException(final String message, final Response.Status status) {
        this(message, null, status);
    }

    public Response.Status status() {
        return status;
    }
}
