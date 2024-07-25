package com.zero.reservation.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Status {

    NULL(HttpStatus.NOT_FOUND, "null"),

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    PASSWORD_DOES_NOT_MATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    SUCCESS_SIGNUP(HttpStatus.CREATED, "회원가입 성공"),
    SUCCESS_PARTNER_SIGNUP(HttpStatus.CREATED, "파트너 회원가입 성공"),
    SIGNUP_FAILED_DUPLICATE_ID(HttpStatus.BAD_REQUEST, "회원가입 실패, 중복된 아이디 입니다."),

    SUCCESS_LOGIN(HttpStatus.OK, "로그인 성공"),
    NOT_LOGGING_IN(HttpStatus.BAD_REQUEST, "로그인을 해주세요"),
    NOT_PARTNER_USER(HttpStatus.BAD_REQUEST, "파트너 회원이 아닙니다. 파트너 회원가입 후 다시 이용해주세요"),

    SUCCESS_ADD_STORE(HttpStatus.CREATED, "매장 등록 성공"),
    FAILED_ADD_STORE(HttpStatus.CREATED, "매장 등록 실패, 이미 등록한 매장 입니다."),

    NOT_FOUND_STORE(HttpStatus.NOT_FOUND, "등록된 매장이 없습니다."),

    SUCCESS_UPDATE_STORE(HttpStatus.OK, "매장 수정 성공"),

    SUCCESS_DELETE_STORE(HttpStatus.OK, "매장 삭제 성공"),

    SUCCESS_RESERVATION_STORE(HttpStatus.OK, "예약을 완료하였습니다."),
    FAILED_RESERVATION_STORE(HttpStatus.OK, "이미 예약한 매장입니다."),
    SUCCESS_STORE_ENTRANCE(HttpStatus.OK, "입장 가능합니다. 이용해주셔서 감사합니다."),

    FAILED_GET_RESERVATION_LIST(HttpStatus.OK, "예약이 아직 없습니다."),

    FAILED_BEFORE_TIME(HttpStatus.BAD_REQUEST, "이미 지난 시간 입니다. 다시 입력해주세요"),
    FAILED_DATE_FORMATTER(HttpStatus.BAD_REQUEST, "시간 형식이 올바르지 않습니다"),
    FAILED_BEFORE_DATE(HttpStatus.BAD_REQUEST, "지난 날짜로 예약 할 수 없습니다. 다시 입력해주세요"),
    FAILED_KIOSK_AFTER_TIME(HttpStatus.BAD_REQUEST, "예약 시간 10분전에만 입장이 가능합니다."),
    FAILED_KIOSK_AFTER_DATE(HttpStatus.BAD_REQUEST, "예약하신 날이 지났습니다."),
    FAILED_KIOSK_BEFORE_DATE(HttpStatus.BAD_REQUEST, "예약하신 날은 오늘이 아닙니다."),
    FAILED_KIOSK_DIFFERENT_STORE(HttpStatus.BAD_REQUEST, "예약하신 매장은 저희 매장이 아닙니다."),

    RESERVATION_STATUS_WAITING(HttpStatus.OK, "매장에서 아직 승인 하지 않은 예약합니다."),
    RESERVATION_STATUS_REFUSE(HttpStatus.OK, "매장에서 거절한 예약합니다."),
    RESERVATION_STATUS_CANCEL(HttpStatus.OK, "최소된 예약입니다."),


    PARAMETER_APPROVE_RESERVATION_IS_FAILED(HttpStatus.BAD_REQUEST, "예약 승인 거절 여부를 정확하게 입력 해주세요"),
    SUCCESS_APPROVE_RESERVATION(HttpStatus.OK, "예약을 승인 했습니다.");

    private final HttpStatus status;
    private final String message;

}
