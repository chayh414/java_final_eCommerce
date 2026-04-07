package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.User;
import kr.co.javaex.sec23.repository.UserRepository;

public class UserService {

    private UserRepository userRepository;
    private User loginUser;   // 현재 로그인한 사용자 저장

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public void addUser(User user) {
        userRepository.addUser(user);
    }

    // 로그인
    public boolean login(String email, String password) {
        User user = userRepository.login(email, password);

        if (user != null) {
            loginUser = user;
            return true;
        } else {
            return false;
        }
    }

    // 로그아웃
    public void logout() {
        loginUser = null;
    }

    // 현재 로그인한 사용자 확인
    public User getLoginUser() {
        return loginUser;
    }

    // 로그인 여부 확인
    public boolean isLogin() {
        if (loginUser != null) {
            return true;
        } else {
            return false;
        }
    }
}