package vn.cjack.demo.oauth2.server.service.impl;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.cjack.demo.oauth2.server.constant.UserStatus;
import vn.cjack.demo.oauth2.server.model.request.ChangePasswordReq;
import vn.cjack.demo.oauth2.server.model.request.RegisterReq;
import vn.cjack.demo.oauth2.server.repository.UserRepository;
import vn.cjack.demo.oauth2.server.repository.entity.User;
import vn.cjack.demo.oauth2.server.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final String USER_NOT_FOUND_MESSAGE = "User not found!";

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(RegisterReq req) {

    }

    private User getById(Long userId) throws NotFoundException {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
    }

    @Override
    public void lockUser(Long userId) throws NotFoundException {
        User user = getById(userId);
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
    }

    @Override
    public void changePassword(ChangePasswordReq req) {
        
    }

    @Override
    public void resetPassword() {

    }
}
