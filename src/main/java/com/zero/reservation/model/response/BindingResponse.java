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
