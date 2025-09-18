package com.singleton.exception;



import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Getter
@AllArgsConstructor
public enum ErrorCode {

    USE_Not_FOUNDO("존재하지 않는 사용자입니다.",BAD_REQUEST),
    DUPICATE_EMAIL("이미 사용중인 이메일입니다.",BAD_REQUEST),



    ;


    private  final String description;
    private final HttpStatus status;

}
