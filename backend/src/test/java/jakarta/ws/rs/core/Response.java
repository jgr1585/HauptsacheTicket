package jakarta.ws.rs.core;

import java.util.Arrays;

public class Response implements AutoCloseable {
    @Override
    public void close() {

    }

    @SuppressWarnings("UnusedDeclaration")
    public enum Status implements StatusType {

        /**
         * 200 OK, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.1">HTTP/1.1 documentation</a>.
         */
        OK(200, "OK"),
        /**
         * 201 Created, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.2">HTTP/1.1
         * documentation</a>.
         */
        CREATED(201, "Created"),
        /**
         * 202 Accepted, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.3">HTTP/1.1
         * documentation</a>.
         */
        ACCEPTED(202, "Accepted"),
        /**
         * 204 No Content, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.5">HTTP/1.1
         * documentation</a>.
         */
        NO_CONTENT(204, "No Content"),
        /**
         * 205 Reset Content, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.6">HTTP/1.1
         * documentation</a>.
         *
         * @since 2.0
         */
        RESET_CONTENT(205, "Reset Content"),
        /**
         * 206 Reset Content, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.7">HTTP/1.1
         * documentation</a>.
         *
         * @since 2.0
         */
        PARTIAL_CONTENT(206, "Partial Content"),
        /**
         * 300 Multiple Choices, see <a href="https://datatracker.ietf.org/doc/html/rfc7231#section-6.4.1">HTTP/1.1:
         * Semantics and Content</a>.
         *
         * @since 3.1
         */
        MULTIPLE_CHOICES(300, "Multiple Choices"),
        /**
         * 301 Moved Permanently, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.2">HTTP/1.1
         * documentation</a>.
         */
        MOVED_PERMANENTLY(301, "Moved Permanently"),
        /**
         * 302 Found, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.3">HTTP/1.1 documentation</a>.
         *
         * @since 2.0
         */
        FOUND(302, "Found"),
        /**
         * 303 See Other, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.4">HTTP/1.1
         * documentation</a>.
         */
        SEE_OTHER(303, "See Other"),
        /**
         * 304 Not Modified, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.5">HTTP/1.1
         * documentation</a>.
         */
        NOT_MODIFIED(304, "Not Modified"),
        /**
         * 305 Use Proxy, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.6">HTTP/1.1
         * documentation</a>.
         *
         * @since 2.0
         */
        USE_PROXY(305, "Use Proxy"),
        /**
         * 307 Temporary Redirect, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.8">HTTP/1.1
         * documentation</a>.
         */
        TEMPORARY_REDIRECT(307, "Temporary Redirect"),
        /**
         * 308 Permanent Redirect, see <a href="https://tools.ietf.org/html/rfc7538">RFC 7538:
         * The Hypertext Transfer Protocol Status Code 308 (Permanent Redirect)</a>.
         *
         * @since 3.1
         */
        PERMANENT_REDIRECT(308, "Permanent Redirect"),
        /**
         * 400 Bad Request, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1">HTTP/1.1
         * documentation</a>.
         */
        BAD_REQUEST(400, "Bad Request"),
        /**
         * 401 Unauthorized, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.2">HTTP/1.1
         * documentation</a>.
         */
        UNAUTHORIZED(401, "Unauthorized"),
        /**
         * 402 Payment Required, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.3">HTTP/1.1
         * documentation</a>.
         *
         * @since 2.0
         */
        PAYMENT_REQUIRED(402, "Payment Required"),
        /**
         * 403 Forbidden, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.4">HTTP/1.1
         * documentation</a>.
         */
        FORBIDDEN(403, "Forbidden"),
        /**
         * 404 Not Found, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.5">HTTP/1.1
         * documentation</a>.
         */
        NOT_FOUND(404, "Not Found"),
        /**
         * 405 Method Not Allowed, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.6">HTTP/1.1
         * documentation</a>.
         *
         * @since 2.0
         */
        METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
        /**
         * 406 Not Acceptable, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.7">HTTP/1.1
         * documentation</a>.
         */
        NOT_ACCEPTABLE(406, "Not Acceptable"),
        /**
         * 407 Proxy Authentication Required, see
         * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.8">HTTP/1.1 documentation</a>.
         *
         * @since 2.0
         */
        PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
        /**
         * 408 Request Timeout, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.9">HTTP/1.1
         * documentation</a>.
         *
         * @since 2.0
         */
        REQUEST_TIMEOUT(408, "Request Timeout"),
        /**
         * 409 Conflict, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.10">HTTP/1.1
         * documentation</a>.
         */
        CONFLICT(409, "Conflict"),
        /**
         * 410 Gone, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.11">HTTP/1.1 documentation</a>.
         */
        GONE(410, "Gone"),
        /**
         * 411 Length Required, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.12">HTTP/1.1
         * documentation</a>.
         *
         * @since 2.0
         */
        LENGTH_REQUIRED(411, "Length Required"),
        /**
         * 412 Precondition Failed, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.13">HTTP/1.1
         * documentation</a>.
         */
        PRECONDITION_FAILED(412, "Precondition Failed"),
        /**
         * 413 Request Entity Too Large, see
         * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.14">HTTP/1.1 documentation</a>.
         *
         * @since 2.0
         */
        REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large"),
        /**
         * 414 Request-URI Too Long, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.15">HTTP/1.1
         * documentation</a>.
         *
         * @since 2.0
         */
        REQUEST_URI_TOO_LONG(414, "Request-URI Too Long"),
        /**
         * 415 Unsupported Media Type, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.16">HTTP/1.1
         * documentation</a>.
         */
        UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
        /**
         * 416 Requested Range Not Satisfiable, see
         * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.17">HTTP/1.1 documentation</a>.
         *
         * @since 2.0
         */
        REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested Range Not Satisfiable"),
        /**
         * 417 Expectation Failed, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.18">HTTP/1.1
         * documentation</a>.
         *
         * @since 2.0
         */
        EXPECTATION_FAILED(417, "Expectation Failed"),
        /**
         * 428 Precondition required, see <a href="https://tools.ietf.org/html/rfc6585#section-3">RFC 6585: Additional HTTP
         * Status Codes</a>.
         *
         * @since 2.1
         */
        PRECONDITION_REQUIRED(428, "Precondition Required"),
        /**
         * 429 Too Many Requests, see <a href="https://tools.ietf.org/html/rfc6585#section-4">RFC 6585: Additional HTTP Status
         * Codes</a>.
         *
         * @since 2.1
         */
        TOO_MANY_REQUESTS(429, "Too Many Requests"),
        /**
         * 431 Request Header Fields Too Large, see <a href="https://tools.ietf.org/html/rfc6585#section-5">RFC 6585: Additional
         * HTTP Status Codes</a>.
         *
         * @since 2.1
         */
        REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large"),
        /**
         * 451 Unavailable For Legal Reasons, see <a href="https://tools.ietf.org/html/rfc7725">RFC 7725:
         * An HTTP Status Code to Report Legal Obstacles</a>.
         *
         * @since 3.1
         */
        UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),
        /**
         * 500 Internal Server Error, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1">HTTP/1.1
         * documentation</a>.
         */
        INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
        /**
         * 501 Not Implemented, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.2">HTTP/1.1
         * documentation</a>.
         *
         * @since 2.0
         */
        NOT_IMPLEMENTED(501, "Not Implemented"),
        /**
         * 502 Bad Gateway, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.3">HTTP/1.1
         * documentation</a>.
         *
         * @since 2.0
         */
        BAD_GATEWAY(502, "Bad Gateway"),
        /**
         * 503 Service Unavailable, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.4">HTTP/1.1
         * documentation</a>.
         */
        SERVICE_UNAVAILABLE(503, "Service Unavailable"),
        /**
         * 504 Gateway Timeout, see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.5">HTTP/1.1
         * documentation</a>.
         *
         * @since 2.0
         */
        GATEWAY_TIMEOUT(504, "Gateway Timeout"),
        /**
         * 505 HTTP Version Not Supported, see
         * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.6">HTTP/1.1 documentation</a>.
         *
         * @since 2.0
         */
        HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported"),
        /**
         * 511 Network Authentication Required, see <a href="https://tools.ietf.org/html/rfc6585#section-6">RFC 6585: Additional
         * HTTP Status Codes</a>.
         *
         * @since 2.1
         */
        NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required");

        private final int code;
        private final String reason;
        private final Family family;

        Status(int code, String reason) {
            this.code = code;
            this.reason = reason;
            this.family = null;
        }

        public static Status fromStatusCode(int statusCode) {
            return Arrays.stream(Status.values()).findFirst().orElseThrow(() -> new IllegalArgumentException("No matching constant for [" + statusCode + "]"));
        }

        @Override
        public int getStatusCode() {
            return this.code;
        }

        @Override
        public Family getFamily() {
            return this.family;
        }

        @Override
        public String getReasonPhrase() {
            return this.reason;
        }

        /**
         * An enumeration representing the class of status code. Family is used here since class is overloaded in Java.
         */
        public enum Family {

            /**
             * {@code 1xx} HTTP status codes.
             */
            INFORMATIONAL,
            /**
             * {@code 2xx} HTTP status codes.
             */
            SUCCESSFUL,
            /**
             * {@code 3xx} HTTP status codes.
             */
            REDIRECTION,
            /**
             * {@code 4xx} HTTP status codes.
             */
            CLIENT_ERROR,
            /**
             * {@code 5xx} HTTP status codes.
             */
            SERVER_ERROR,
            /**
             * Other, unrecognized HTTP status codes.
             */
            OTHER;

            /**
             * Get the response status family for the status code.
             *
             * @param statusCode response status code to get the family for.
             * @return family of the response status code.
             */
            public static Family familyOf(final int statusCode) {
                return switch (statusCode / 100) {
                    case 1 -> Family.INFORMATIONAL;
                    case 2 -> Family.SUCCESSFUL;
                    case 3 -> Family.REDIRECTION;
                    case 4 -> Family.CLIENT_ERROR;
                    case 5 -> Family.SERVER_ERROR;
                    default -> Family.OTHER;
                };
            }
        }
    }

    @SuppressWarnings("unused")
    public interface StatusType {

        /**
         * Get the associated status code.
         *
         * @return the status code.
         */
        int getStatusCode();

        /**
         * Get the class of status code.
         *
         * @return the class of status code.
         */
        Status.Family getFamily();

        /**
         * Get the reason phrase.
         *
         * @return the reason phrase.
         */
        String getReasonPhrase();

        /**
         * Get the this Status Type as a {@link Status}.
         * <p>
         * Please note that returned status contains only a status code, the reason phrase is set to default one (corresponding
         * to the status code).
         *
         * @return {@link Status} representing this status type.
         * @since 2.1
         */
        default Status toEnum() {
            return Status.fromStatusCode(getStatusCode());
        }
    }
}
