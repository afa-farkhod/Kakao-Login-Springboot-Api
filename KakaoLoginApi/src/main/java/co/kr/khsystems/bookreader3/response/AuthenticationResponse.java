package co.kr.khsystems.bookreader3.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private List<String> errors;
    private String token;
    public AuthenticationResponse(List<String> errors) {
        this.errors = errors;
    }
}

