package vn.nvc.product.base.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomUserCredentials {
    private String password;
    private String authenticateMethod;
}
