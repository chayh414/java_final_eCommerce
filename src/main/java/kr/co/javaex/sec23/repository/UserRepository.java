package kr.co.javaex.sec23.repository;

import kr.co.javaex.sec23.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private List<User> userList = new ArrayList<>();

    // 회원 추가
    public void addUser(User user) {
        userList.add(user);
    }

    // 전체 회원 조회
    public List<User> getAllUsers() {
        return userList;
    }

    // 회원 ID로 찾기
    public User findByUserId(String userId) {
        for (User user : userList) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    // 이메일로 회원 찾기
    public User findByEmail(String email) {
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    // 이메일 + 비밀번호 로그인
    public User login(String email, String password) {
        for (User user : userList) {
            if (user.getEmail().equals(email) &&
                    user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}