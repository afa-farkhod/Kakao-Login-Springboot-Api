package co.kr.khsystems.bookreader3.validator;

import co.kr.khsystems.bookreader3.request.AuthenticationRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AuthenticationRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AuthenticationRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required", "Email is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required", "Password is required.");
    }
}
