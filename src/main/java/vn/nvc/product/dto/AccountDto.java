package vn.nvc.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
    private int id;
    private String username;
    private String password;

    public AccountDto(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
