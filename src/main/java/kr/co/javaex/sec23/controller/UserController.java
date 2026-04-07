package kr.co.javaex.sec23.controller;

import kr.co.javaex.sec23.domain.User;
import kr.co.javaex.sec23.service.UserService;

import java.util.Scanner;

public class UserController {

    private UserService userService;
    private Scanner scanner;

    public UserController(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    // 회원 메뉴
    public void showMenu() {
        while (true) {
            System.out.println("\n===== 회원 메뉴 =====");
            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("3. 로그아웃");
            System.out.println("4. 회원정보 수정");
            System.out.println("5. 비밀번호 변경");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            String input = scanner.nextLine();

            if (input.equals("1")) {
                join();
            } else if (input.equals("2")) {
                login();
            } else if (input.equals("3")) {
                logout();
            } else if (input.equals("4")) {
                updateUser();
            } else if (input.equals("5")) {
                changePassword();
            } else if (input.equals("0")) {
                break;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 회원가입
    private void join() {
        System.out.println("\n===== 회원가입 =====");

        System.out.print("회원ID (영문+숫자, 5~15자리): ");
        String userId = scanner.nextLine();

        // 아이디 유효성 검사
        if (!userId.matches("[a-zA-Z0-9]{5,15}")) {
            System.out.println("아이디는 영문자, 숫자만 사용 가능하고 5~15자리여야 합니다.");
            return;
        }

        System.out.print("회원명: ");
        String userName = scanner.nextLine();

        System.out.print("비밀번호 (대문자+소문자+숫자 포함, 5~15자리): ");
        String password = scanner.nextLine();

        // 비밀번호 유효성 검사
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{5,15}$")) {
            System.out.println("비밀번호는 대문자, 소문자, 숫자를 각 1개 이상 포함하고 5~15자리여야 합니다.");
            return;
        }

        System.out.print("휴대전화: ");
        String phone = scanner.nextLine();

        System.out.print("이메일: ");
        String email = scanner.nextLine();

        System.out.print("회원구분 (1.일반회원 / 2.관리자): ");
        String roleInput = scanner.nextLine();
        String userRole = roleInput.equals("2") ? "관리자" : "일반회원";

        User user = new User(userId, userName, password, phone, email, "정상", userRole);
        userService.addUser(user);

        System.out.println("회원가입이 완료되었습니다.");
    }

    // 로그인
    public void login() {
        System.out.println("\n===== 로그인 =====");

        System.out.print("이메일: ");
        String email = scanner.nextLine();

        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        boolean result = userService.login(email, password);

        if (result) {
            System.out.println(userService.getLoginUser().getUserName() + "님 환영합니다!");
        } else {
            System.out.println("이메일 또는 비밀번호가 틀렸습니다.");
        }
    }

    // 로그아웃
    private void logout() {
        if (!userService.isLogin()) {
            System.out.println("로그인 상태가 아닙니다.");
            return;
        }

        userService.logout();
        System.out.println("로그아웃 되었습니다.");
    }

    // 회원정보 수정
    private void updateUser() {
        if (!userService.isLogin()) {
            System.out.println("로그인이 필요합니다.");
            return;
        }

        User loginUser = userService.getLoginUser();

        System.out.println("\n===== 회원정보 수정 =====");
        System.out.println("현재 회원명: " + loginUser.getUserName());
        System.out.print("새 회원명 (변경 없으면 엔터): ");
        String userName = scanner.nextLine();

        System.out.println("현재 휴대전화: " + loginUser.getPhone());
        System.out.print("새 휴대전화 (변경 없으면 엔터): ");
        String phone = scanner.nextLine();

        System.out.println("현재 이메일: " + loginUser.getEmail());
        System.out.print("새 이메일 (변경 없으면 엔터): ");
        String email = scanner.nextLine();

        // 입력값이 없으면 기존값 유지
        if (!userName.isEmpty()) loginUser.setUserName(userName);
        if (!phone.isEmpty()) loginUser.setPhone(phone);
        if (!email.isEmpty()) loginUser.setEmail(email);

        System.out.println("회원정보가 수정되었습니다.");
    }

    // 비밀번호 변경
    private void changePassword() {
        if (!userService.isLogin()) {
            System.out.println("로그인이 필요합니다.");
            return;
        }

        User loginUser = userService.getLoginUser();

        System.out.println("\n===== 비밀번호 변경 =====");
        System.out.print("현재 비밀번호: ");
        String currentPassword = scanner.nextLine();

        if (!loginUser.getPassword().equals(currentPassword)) {
            System.out.println("현재 비밀번호가 틀렸습니다.");
            return;
        }

        System.out.print("새 비밀번호 (대문자+소문자+숫자 포함, 5~15자리): ");
        String newPassword = scanner.nextLine();

        if (!newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{5,15}$")) {
            System.out.println("비밀번호는 대문자, 소문자, 숫자를 각 1개 이상 포함하고 5~15자리여야 합니다.");
            return;
        }

        loginUser.setPassword(newPassword);
        System.out.println("비밀번호가 변경되었습니다.");
    }
}