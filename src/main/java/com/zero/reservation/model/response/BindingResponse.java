package com.zero.reservation.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BindingResponse {

    // 중복적으로 사용되는 부분이 많아서 따로 클래스로 구현

    boolean status;
    String message;


    // 매개 변수값에 null이 존재할경우 DTO 클래스에 작성한 메시지를 반환
    public static BindingResponse failedResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasErrors()) {
                FieldError fieldError = bindingResult.getFieldError();
                if (fieldError != null) {
                    return new BindingResponse(false, bindingResult.getFieldError().getDefaultMessage());
                }
            }
        }

        return null;
    }
}
