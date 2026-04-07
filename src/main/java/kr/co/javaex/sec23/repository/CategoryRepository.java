package kr.co.javaex.sec23.repository;

import kr.co.javaex.sec23.domain.Category;
import kr.co.javaex.sec23.domain.User;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    // 1. Category 객체들을 저장할 리스트(창고 역할느낌)
    private List<Category> categoryList = new ArrayList<>();

    // 2.
    public void addCategory(Category category) {
        categoryList.add(category);
    }

    // 3. 전체 카테고리 조회
    public List<Category> getAllCategories() {
        return categoryList;
    }

    // 4. 아이디로 회원 찾기(로그인 핵심)
    public Category findByCategoryId(Long categoryId) {
        for(Category category : categoryList) {
            if(category.getCategoryId().equals(categoryId)){
                return category;
            }
        }
        return null;  // -> 못 찾으면 null 반환
    }

    // 5
     /*
    public Category login(Long categoryId) {
        for(User user : userList) {
            if (user.getUserId().equals(userId) &&
                    user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    */






}
