package kr.co.javaex.sec23.controller;

import kr.co.javaex.sec23.domain.Category;
import kr.co.javaex.sec23.service.CategoryService;
import kr.co.javaex.sec23.service.UserService;

import java.util.List;
import java.util.Scanner;

public class CategoryController {

    private CategoryService categoryService;
    private UserService userService;
    private Scanner scanner;

    public CategoryController(CategoryService categoryService, UserService userService, Scanner scanner) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.scanner = scanner;
    }

    // 카테고리 메뉴 (관리자만)
    public void showMenu() {
        // 관리자 확인
        if (!userService.isLogin() || !userService.getLoginUser().isAdmin()) {
            System.out.println("관리자만 접근할 수 있습니다.");
            return;
        }

        while (true) {
            System.out.println("\n===== 카테고리 관리 =====");
            System.out.println("1. 카테고리 전체 조회");
            System.out.println("2. 카테고리 추가");
            System.out.println("3. 카테고리 수정");
            System.out.println("4. 카테고리 삭제");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            String input = scanner.nextLine();

            if (input.equals("1")) {
                showAllCategories();
            } else if (input.equals("2")) {
                addCategory();
            } else if (input.equals("3")) {
                updateCategory();
            } else if (input.equals("4")) {
                removeCategory();
            } else if (input.equals("0")) {
                break;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 전체 카테고리 조회
    private void showAllCategories() {
        System.out.println("\n===== 카테고리 목록 =====");

        List<Category> mainList = categoryService.getMainCategories();

        if (mainList.isEmpty()) {
            System.out.println("등록된 카테고리가 없습니다.");
            return;
        }

        for (Category main : mainList) {
            System.out.println("[대분류] " + main.getCategoryId() + ". " + main.getCategoryName());

            List<Category> subList = categoryService.getSubCategories(main.getCategoryId());
            for (Category sub : subList) {
                System.out.println("  ㄴ [중분류] " + sub.getCategoryId() + ". " + sub.getCategoryName());
            }
        }
    }

    // 카테고리 추가
    private void addCategory() {
        System.out.println("\n===== 카테고리 추가 =====");

        System.out.print("카테고리ID: ");
        Long categoryId = Long.parseLong(scanner.nextLine());

        System.out.print("카테고리명: ");
        String categoryName = scanner.nextLine();

        System.out.print("대분류입니까? (1.예 / 2.아니오): ");
        String input = scanner.nextLine();

        Long parentCategoryId = null;

        if (input.equals("2")) {
            System.out.print("상위 카테고리ID: ");
            parentCategoryId = Long.parseLong(scanner.nextLine());
        }

        Category category = new Category(categoryId, parentCategoryId, categoryName);
        categoryService.addCategory(category);

        System.out.println("카테고리가 추가되었습니다.");
    }

    // 카테고리 수정
    private void updateCategory() {
        System.out.println("\n===== 카테고리 수정 =====");
        showAllCategories();

        System.out.print("수정할 카테고리ID: ");
        Long categoryId = Long.parseLong(scanner.nextLine());

        System.out.print("새 카테고리명: ");
        String categoryName = scanner.nextLine();

        Category category = new Category(categoryId, null, categoryName);
        boolean result = categoryService.updateCategory(category);

        if (result) {
            System.out.println("카테고리가 수정되었습니다.");
        } else {
            System.out.println("해당 카테고리를 찾을 수 없습니다.");
        }
    }

    // 카테고리 삭제
    private void removeCategory() {
        System.out.println("\n===== 카테고리 삭제 =====");
        showAllCategories();

        System.out.print("삭제할 카테고리ID: ");
        Long categoryId = Long.parseLong(scanner.nextLine());

        boolean result = categoryService.removeCategory(categoryId);

        if (result) {
            System.out.println("카테고리가 삭제되었습니다.");
        } else {
            System.out.println("해당 카테고리를 찾을 수 없습니다.");
        }
    }
}