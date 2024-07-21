package com.zero.reservation.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Status {

    SUCCESS_SIGNUP(HttpStatus.CREATED, "회원가입 성공"),
    SUCCESS_PARTNER_SIGNUP(HttpStatus.CREATED, "파트너 회원가입 성공"),
    SIGNUP_FAILED_DUPLICATE_ID(HttpStatus.BAD_REQUEST, "회원가입 실패, 중복된 아이디 입니다."),
    PARTNER_SIGNUP_FAILED_DUPLICATE_ID(HttpStatus.BAD_REQUEST, "파트너 회원가입 실패"),

    SUCCESS_LOGIN(HttpStatus.OK, "로그인 성공"),
    FAILED_LOGIN_NOT_FOUND_USER(HttpStatus.NOT_FOUND, "로그인 실패, 존재하지 않는 회원입니다."),
    FAILED_LOGIN_PASSWORD_DOES_NOT_MATCH(HttpStatus.UNAUTHORIZED, "로그인 실패, 비밀번호가 일치하지 않습니다."),
    NOT_LOGGING_IN(HttpStatus.BAD_REQUEST, "로그인을 해주세요"),
    NOT_PARTNER_USER(HttpStatus.BAD_REQUEST, "파트너 회원이 아닙니다. 파트너 회원가입 후 다시 이용해주세요"),

    SUCCESS_ADD_STORE(HttpStatus.CREATED, "매장 등록 성공"),
    FAILED_ADD_STORE(HttpStatus.BAD_REQUEST, "매장 등록 실패"),

    STORE_EDIT_SUCCESS(HttpStatus.OK, "가게 수정 성공"),
    STORE_EDIT_FAIL(HttpStatus.BAD_REQUEST, "가게 수정 실패"),

    STORE_DELETE_SUCCESS(HttpStatus.OK, "가게 삭제 성공"),
    STORE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "가게 삭제 실패"),

    STORE_RESERVE_SUCCESS(HttpStatus.OK, "예약 성공"),
    STORE_RESERVE_FAIL(HttpStatus.BAD_REQUEST, "예약 실패");

    private final HttpStatus status;
    private final String message;

}
