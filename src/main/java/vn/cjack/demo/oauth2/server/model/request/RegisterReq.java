package vn.cjack.demo.oauth2.server.model.request;

import lombok.Data;

@Data
public class RegisterReq {
    private String username;
    private String password;
}
