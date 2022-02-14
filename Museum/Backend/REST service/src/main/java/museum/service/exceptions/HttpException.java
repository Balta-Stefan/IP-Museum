package museum.service.exceptions;

import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException
{
    private final HttpStatus status;
    private final Object data;

    public HttpException()
    {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        data = null;
    }

    public HttpException(HttpStatus status)
    {
        this.status = status;
        data = null;
    }

    public HttpException(HttpStatus status, Object data)
    {
        this.status = status;
        this.data = data;
    }
}
