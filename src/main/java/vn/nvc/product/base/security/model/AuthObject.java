package vn.nvc.product.base.security.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthObject {
    private String token;
    private String password;
}
