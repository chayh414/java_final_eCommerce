package kr.co.javaex.sec23.repository;

import kr.co.javaex.sec23.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    // 1. User 객체들을 저장할 리스트(창고 역할느낌)
    private List<User> userList = new ArrayList<>();

    // 2. 회원 추가 (회원가입)
    public void addUser(User user) {
        userList.add(user);
    }

    // 3. 전체 회원 조회
    public List<User> getAllUsers() {
        return userList;
    }

    // 4. 아이디로 회원 찾기(로그인 핵심)
    public User findByUserId(String userId) {
        for(User user : userList) {
            if(user.getUserId().equals(userId)){
                return user;
            }
        }
        return null;  // -> 못 찾으면 null 반환
    }

    // 5. 아이디 + 비밀번호 확인(로그인용)
    public User login(String userId, String password) {
        for(User user : userList) {
            if (user.getUserId().equals(userId) &&
                    user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
