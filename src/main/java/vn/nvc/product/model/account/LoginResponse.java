package vn.nvc.product.model.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String username;
    private String accessToken;
    private String tokenType = "Bearer";

    public LoginResponse(String username, String jwt) {
        this.username = username;
        this.accessToken = jwt;
    }
}
