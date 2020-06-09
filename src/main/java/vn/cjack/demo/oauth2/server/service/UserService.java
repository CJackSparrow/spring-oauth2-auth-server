package vn.cjack.demo.oauth2.server.service;

import javassist.NotFoundException;
import vn.cjack.demo.oauth2.server.model.request.ChangePasswordReq;
import vn.cjack.demo.oauth2.server.model.request.RegisterReq;

public interface UserService {
    void register(RegisterReq req);
    void lockUser(Long userId) throws NotFoundException;
    void changePassword(ChangePasswordReq req);
    void resetPassword();
}
