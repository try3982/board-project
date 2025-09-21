package com.singleton.exception;



import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND("존재하지 않는 사용자입니다.",BAD_REQUEST),
    DUPICATE_EMAIL("이미 사용중인 이메일입니다.",BAD_REQUEST),
    INVALID_LOGIN("이메일 또는 비밀번호가 올바르지 않습니다",BAD_REQUEST),
    POST_NOT_FOUND("게시글을 찾을 수 없습니다.",BAD_REQUEST),
    AUTHORIZATION_FAILED("작성자를 찾을 수 없습니다.",BAD_REQUEST),
    COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다.",BAD_REQUEST),
    UTHORIZATION_FAILED("다른 사용자의 댓글은 수정/삭제할 수 없습니다.",BAD_REQUEST),



    ;


    private  final String description;
    private final HttpStatus status;

}
