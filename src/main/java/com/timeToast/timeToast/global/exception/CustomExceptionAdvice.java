package com.timeToast.timeToast.global.exception;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class CustomExceptionAdvice {
    public CustomExceptionAdvice() {
    }

    /**
     * 커스텀 예외 처리
     */

    // 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public Response handleException(final BadRequestException e,
                                         final HttpServletRequest httpServletRequest,
                                         @Login final LoginMember loginMember) {

        log.info("\nMember: {}\nAPI: {}\nDetail: {}\n",
                loginMember.id(), httpServletRequest.getRequestURI(), e.getMessage());

        return Response.of(e.getStatusCode(), e.getMessage());
    }

    // 401
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public Response handleException(final UnauthorizedException e,
                                         final HttpServletRequest httpServletRequest,
                                    @Login final LoginMember loginMember) {
        log.info("\nMember: {}\nAPI: {}\nDetail: {}\n",
                loginMember.id(), httpServletRequest.getRequestURI(), e.getMessage());

        return Response.of(e.getStatusCode(), e.getMessage());
    }

    // 403
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public Response handleException(final ForbiddenException e,
                                         final HttpServletRequest httpServletRequest,
                                    @Login final LoginMember loginMember) {

        log.info("\nMember: {}\nAPI: {}\nDetail: {}\n",
                loginMember.id(), httpServletRequest.getRequestURI(), e.getMessage());

        return Response.of(e.getStatusCode(), e.getMessage());
    }

    // 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Response handleException(final NotFoundException e,
                                    final HttpServletRequest httpServletRequest,
                                    @Login final LoginMember loginMember) {

        log.info("\nMember: {}\nAPI: {}\nDetail: {}\n",
                loginMember.id(), httpServletRequest.getRequestURI(), e.getMessage());

        return Response.of(e.getStatusCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public Response handleException(final ConflictException e,
                                    final HttpServletRequest httpServletRequest,
                                    @Login final LoginMember loginMember) {

        log.info("\nMember: {}\nAPI: {}\nDetail: {}\n",
                loginMember.id(), httpServletRequest.getRequestURI(), e.getMessage());

        return Response.of(e.getStatusCode(), e.getMessage());
    }

    // 500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerException.class)
    public Response handleInternalServerError(final InternalServerException e,
                                                   final HttpServletRequest httpServletRequest,
                                              @Login final LoginMember loginMember) {
        log.info("\nMember: {}\nAPI: {}\nDetail: {}\n",
                loginMember.id(), httpServletRequest.getRequestURI(), e.getMessage());

        return Response.of(e.getStatusCode(), e.getMessage());
    }
}
