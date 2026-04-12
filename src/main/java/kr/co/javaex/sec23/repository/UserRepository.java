package kr.co.javaex.sec23.repository;

import kr.co.javaex.sec23.domain.User;
import kr.co.javaex.sec23.util.JsonUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserRepository {

    private List<User> userList = new ArrayList<>();
    private JsonUtil jsonUtil = new JsonUtil();
    private String fileName = "use.json";

    public UserRepository() {
        User[] arr = jsonUtil.load(fileName, User[].class);
        if (arr != null) {
            userList = new ArrayList<>(Arrays.asList(arr));
        }
    }

    public void addUser(User user) {
        userList.add(user);
        jsonUtil.save(fileName, userList);
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