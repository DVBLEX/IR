package ie.irishrail.server.rest.aspect;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.MimeType;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ie.irishrail.server.base.common.ServerResponseConstants;
import ie.irishrail.server.base.exception.FPNException;
import ie.irishrail.server.base.exception.FPNNotFoundException;
import ie.irishrail.server.base.exception.FPNValidationException;
import ie.irishrail.server.base.jsonentity.ApiResponseJsonEntity;
import ie.irishrail.server.rest.exception.BadRequestException;

/**
 * CustomRestControllerAdvice
 *
 * To convert FPNException into Generic API Failure with BAD_REQUEST HTTP response code.
 * Convert FPNValidationException into Generic API Failure with custom response code & text and BAD_REQUEST HTTP response code.
 *
 * Set FPNValidationException when response code/text needs to be sent back to front-end, otherwise use FPNException that will log
 * response on server but only send generic response to front-end.
 */

@RestControllerAdvice
@ResponseBody
public class CustomRestControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String details = extractDetailedMessage(ex);
        ApiResponseJsonEntity result = new ApiResponseJsonEntity();
        result.setResponseCode(ServerResponseConstants.API_FAILURE_CODE);
        result.setResponseText(ServerResponseConstants.API_FAILURE_TEXT + " " + details);
        result.setResponseDate(new Date());

        return new ResponseEntity<>(result, BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String details = Arrays.stream(ex.getSupportedMethods()).reduce((a, b) -> a + ", " + b).toString();

        ApiResponseJsonEntity result = new ApiResponseJsonEntity();
        result.setResponseCode(METHOD_NOT_ALLOWED.value());
        result.setResponseText("Supported methods: " + details);
        result.setResponseDate(new Date());
        return new ResponseEntity<>(result, METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String details = ex.getSupportedMediaTypes().stream().map(MimeType::toString).reduce((a, b) -> a + ", " + b).toString();

        ApiResponseJsonEntity result = new ApiResponseJsonEntity();
        result.setResponseCode(UNSUPPORTED_MEDIA_TYPE.value());
        result.setResponseText("Supported media types: " + details);
        result.setResponseDate(new Date());
        return new ResponseEntity<>(result, UNSUPPORTED_MEDIA_TYPE);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String details = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).reduce((a, b) -> a + ", " + b).toString();

        ApiResponseJsonEntity result = new ApiResponseJsonEntity();
        result.setResponseCode(ServerResponseConstants.API_FAILURE_CODE);
        result.setResponseText(details);
        result.setResponseDate(new Date());
        return new ResponseEntity<>(result, BAD_REQUEST);
    }

    @ExceptionHandler({ FPNException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ApiResponseJsonEntity handleFPNException(FPNException e) {

        ApiResponseJsonEntity result = new ApiResponseJsonEntity();
        result.setResponseCode(ServerResponseConstants.API_FAILURE_CODE);
        result.setResponseText(ServerResponseConstants.API_FAILURE_TEXT);
        result.setResponseDate(new Date());

        return result;
    }

    @ExceptionHandler({ FPNValidationException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponseJsonEntity handleFPNValidationException(HttpServletRequest request, FPNValidationException fpnv) {

        ApiResponseJsonEntity result = new ApiResponseJsonEntity();
        result.setResponseCode(fpnv.getResponseCode());
        result.setResponseText(fpnv.getResponseText());
        result.setResponseDate(new Date());

        return result;
    }

    @ExceptionHandler({ FPNNotFoundException.class })
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiResponseJsonEntity handleFPNNotFoundException(HttpServletRequest request, FPNNotFoundException nfe) {

        ApiResponseJsonEntity result = new ApiResponseJsonEntity();
        result.setResponseCode(nfe.getCode());
        result.setResponseText(nfe.getMessage());
        result.setResponseDate(new Date());

        return result;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public final ApiResponseJsonEntity handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        ApiResponseJsonEntity result = new ApiResponseJsonEntity();
        result.setResponseCode(ServerResponseConstants.API_FAILURE_CODE);
        result.setResponseText(ServerResponseConstants.API_FAILURE_TEXT);
        result.setResponseDate(new Date());

        return result;
    }

    @ExceptionHandler({ Exception.class })
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    protected ApiResponseJsonEntity handleGenericException(Exception e) {

        ApiResponseJsonEntity result = new ApiResponseJsonEntity();
        result.setResponseCode(ServerResponseConstants.API_FAILURE_CODE);
        result.setResponseText(ServerResponseConstants.API_FAILURE_TEXT);
        result.setResponseDate(new Date());

        return result;
    }

    private String extractDetailedMessage(Throwable e) {
        final String message = e.getMessage();
        if (message == null) {
            return "";
        }
        final int tailIndex = StringUtils.indexOf(message, "; nested exception is");
        if (tailIndex == -1) {
            return message;
        }
        return StringUtils.left(message, tailIndex);
    }
}
