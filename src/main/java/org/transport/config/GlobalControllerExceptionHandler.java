package org.transport.config;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.transport.common.CommonUtils;
import org.transport.dto.ExceptionDto;

import java.sql.SQLException;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionDto> response(Exception e, HttpServletRequest request) {
        ExceptionDto exceptionDto = CommonUtils.getException(e);
        log.info("RequestURL:" + request.getRequestURL() + "  UUID=" + request.getHeader("X-UUID") + "  ServiceException:" + (CommonUtils.isNull(exceptionDto) ? e.getMessage().split("]:")[1] : exceptionDto.getErrorMessage()));
        return new ResponseEntity<>(ExceptionDto.builder()
                .errorMessage("internal server error")
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .uuid(request.getHeader("X-UUID"))
                .errorStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ExceptionDto> response(RuntimeException e, HttpServletRequest request) {
        String message = CommonUtils.getMessage(e.getMessage());
        log.info("RequestURL:" + request.getRequestURL() + "  UUID=" + request.getHeader("X-UUID") + "  ServiceRuntimeException:" + message);
        return new ResponseEntity<>(ExceptionDto.builder()
                .errorMessage(message)
                .errorCode(CommonUtils.longValue(e.getMessage()).intValue())
                .uuid(request.getHeader("X-UUID"))
                .errorStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = FeignException.Unauthorized.class)
    public ResponseEntity<ExceptionDto> response(FeignException.Unauthorized e, HttpServletRequest request) {
        ExceptionDto exceptionDto = CommonUtils.getException(e);
        log.info("RequestURL:" + request.getRequestURL() + "  UUID=" + request.getHeader("X-UUID") + "  ServiceUnauthorized:" + (CommonUtils.isNull(exceptionDto) ? e.getMessage().split("]:")[1] : exceptionDto.getErrorMessage()));
        return new ResponseEntity<>(ExceptionDto.builder()
                .errorMessage(CommonUtils.isNull(exceptionDto) ? "unauthorized exception" : exceptionDto.getErrorMessage())
                .errorCode(HttpStatus.UNAUTHORIZED.value())
                .uuid(request.getHeader("X-UUID"))
                .errorStatus(HttpStatus.UNAUTHORIZED.value())
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = FeignException.ServiceUnavailable.class)
    public ResponseEntity<ExceptionDto> response(FeignException.ServiceUnavailable e, HttpServletRequest request) {
        ExceptionDto exceptionDto = CommonUtils.getException(e);
        log.info("RequestURL:" + request.getRequestURL() + "  UUID=" + request.getHeader("X-UUID") + "  ServiceUnavailable:" + (CommonUtils.isNull(exceptionDto) ? e.getMessage().split("]:")[1] : exceptionDto.getErrorMessage()));
        return new ResponseEntity<>(ExceptionDto.builder()
                .errorMessage(CommonUtils.isNull(exceptionDto) ? "internal server error" : exceptionDto.getErrorMessage())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .uuid(request.getHeader("X-UUID"))
                .errorStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = FeignException.InternalServerError.class)
    public ResponseEntity<ExceptionDto> response(FeignException.InternalServerError e, HttpServletRequest request) {
        ExceptionDto exceptionDto = CommonUtils.getException(e);
        log.info("RequestURL:" + request.getRequestURL() + "  UUID=" + request.getHeader("X-UUID") + "  ServiceInternalServerError:" + (CommonUtils.isNull(exceptionDto) ? e.getMessage().split("]:")[1] : exceptionDto.getErrorMessage()));
        return new ResponseEntity<>(ExceptionDto.builder()
                .errorMessage(CommonUtils.isNull(exceptionDto) ? "internal server error" : exceptionDto.getErrorMessage())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .uuid(request.getHeader("X-UUID"))
                .errorStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDto> handleDuplicateKeyException(DataIntegrityViolationException  e, HttpServletRequest request) {
        ExceptionDto exceptionDto = CommonUtils.getException(e);
        log.info("RequestURL:" + request.getRequestURL() + "  UUID=" + request.getHeader("X-UUID") + "  DuplicateKey:" + e.getMessage());
        return new ResponseEntity<>(ExceptionDto.builder()
                .errorMessage(exceptionDto.getErrorMessage())
                .errorCode(exceptionDto.getErrorCode())
                .uuid(request.getHeader("X-UUID"))
                .errorStatus(HttpStatus.CONFLICT.value())
                .build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity<ExceptionDto> handleDuplicateKeyException(SQLException  e, HttpServletRequest request) {
        ExceptionDto exceptionDto = CommonUtils.getException(e);
        log.info("RequestURL:" + request.getRequestURL() + "  UUID=" + request.getHeader("X-UUID") + "  DuplicateKey:" + e.getMessage());
        return new ResponseEntity<>(ExceptionDto.builder()
                .errorMessage(exceptionDto.getErrorMessage())
                .errorCode(exceptionDto.getErrorCode())
                .uuid(request.getHeader("X-UUID"))
                .errorStatus(HttpStatus.CONFLICT.value())
                .build(), HttpStatus.CONFLICT);
    }

}

