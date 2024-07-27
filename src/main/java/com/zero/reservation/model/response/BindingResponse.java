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
    boolean status;
    String message;


    // 매개 변수값에 null이 있을경우 DTO 클래스에 작성한 메시지를 반환
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
