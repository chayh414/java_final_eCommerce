package kr.co.javaex.sec23.domain;

public class User {

    // private, getter/setter로 캡슐화
    private String userId;
    private String userName;
    private String password;
    private String phone;
    private String email;
    private String status;     // 정상
    private String userRole;   // 사용자권한, 일반회원, 관리자

    public User() {
    }

    // 생성자
    // 밖에서 받아온 userID 값을 여기 객체의 userI에 넣기
    public User(String userId, String userName, String password, String phone, String email, String status, String userRole) {
        this.userId = userId;             // 회원 ID
        this.userName = userName;         // 회원명
        this.password = password;         // 비밀번호
        this.phone = phone;               // 전화번호
        this.email = email;               // 이메일(로그인처리, Unique)
        this.status = status;             // 상태 '정상'
        this.userRole = userRole;         // 회원 구분 '일반회원', '관리자'
    }

    //
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    // 관리자인지 아닌지를 확인해서 관리자이면 관리자에게만 보이는 특정 메뉴 도출
    public boolean isAdmin() {
        return "관리자".equals(this.userRole);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}