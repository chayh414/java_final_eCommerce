package kr.co.javaex.sec23;

import kr.co.javaex.sec23.domain.User;
import kr.co.javaex.sec23.repository.UserRepository;
import kr.co.javaex.sec23.service.UserService;

import java.util.Scanner;

public class Ecommerce {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);

        while (true) {
            System.out.println("===== 쇼핑몰 프로그램 =====");
            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("3. 로그아웃");
            System.out.println("0. 종료");
            System.out.print("번호 입력: ");

            int no = sc.nextInt();
            sc.nextLine();

            if (no == 1) {
                System.out.print("회원아이디: ");
                String userId = sc.nextLine();

                System.out.print("회원이름: ");
                String userName = sc.nextLine();

                System.out.print("비밀번호: ");
                String password = sc.nextLine();

                System.out.print("휴대전화: ");
                String phone = sc.nextLine();

                System.out.print("이메일: ");
                String email = sc.nextLine();

                User user = new User(userId, userName, password, phone, email, "정상", "일반회원");
                userService.addUser(user);

                System.out.println("회원가입이 완료되었습니다.");

            } else if (no == 2) {
                System.out.print("이메일: ");
                String email = sc.nextLine();

                System.out.print("비밀번호: ");
                String password = sc.nextLine();

                boolean result = userService.login(email, password);

                if (result == true) {
                    System.out.println("로그인 성공");
                } else {
                    System.out.println("로그인 실패");
                }

            } else if (no == 3) {
                userService.logout();
                System.out.println("로그아웃 되었습니다.");

            } else if (no == 0) {
                System.out.println("프로그램 종료");
                break;

            } else {
                System.out.println("다시 입력해주세요.");
            }

            System.out.println();
        }
    }
}