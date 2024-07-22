package com.zero.reservation.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Status {

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    PASSWORD_DOES_NOT_MATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    SUCCESS_SIGNUP(HttpStatus.CREATED, "회원가입 성공"),
    SUCCESS_PARTNER_SIGNUP(HttpStatus.CREATED, "파트너 회원가입 성공"),
    SIGNUP_FAILED_DUPLICATE_ID(HttpStatus.BAD_REQUEST, "회원가입 실패, 중복된 아이디 입니다."),

    SUCCESS_LOGIN(HttpStatus.OK, "로그인 성공"),
    NOT_LOGGING_IN(HttpStatus.BAD_REQUEST, "로그인을 해주세요"),
    NOT_PARTNER_USER(HttpStatus.BAD_REQUEST, "파트너 회원이 아닙니다. 파트너 회원가입 후 다시 이용해주세요"),

    SUCCESS_ADD_STORE(HttpStatus.CREATED, "매장 등록 성공"),

    NOT_FOUND_STORE(HttpStatus.NOT_FOUND, "등록된 매장이 없습니다."),

    SUCCESS_UPDATE_STORE(HttpStatus.OK, "매장 수정 성공"),

    SUCCESS_DELETE_STORE(HttpStatus.OK, "매장 삭제 성공");

    private final HttpStatus status;
    private final String message;

}
