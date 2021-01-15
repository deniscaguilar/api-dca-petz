package br.com.dca.gateways.http;

import br.com.dca.exceptions.CustomerAlreadyExistsException;
import br.com.dca.exceptions.ResourceNotFoundException;
import br.com.dca.gateways.http.contracts.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class RestErrorHandler extends DefaultHandlerExceptionResolver {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse resourceNotFoundException(final ResourceNotFoundException ex) {
        log.info(ex.getMessage(), ex);
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ErrorResponse customerAlreadyExistsException(final CustomerAlreadyExistsException ex) {
        log.info(ex.getMessage(), ex);
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public List<ErrorResponse> constraintViolationException(final ConstraintViolationException ex) {
        log.info(ex.getMessage(), ex);
        return ex.getConstraintViolations()
                .stream()
                .map(constraintViolation -> new ErrorResponse(constraintViolation.getMessageTemplate()))
                .collect(Collectors.toList());
    }
}
