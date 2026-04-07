package kr.co.javaex.sec23.repository;

import kr.co.javaex.sec23.domain.Category;
import kr.co.javaex.sec23.util.JsonUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryRepository {

    // Category 객체들을 저장할 리스트
    private List<Category> categoryList = new ArrayList<>();
    private JsonUtil jsonUtil = new JsonUtil();
    private String fileName = "categories.json";

    // 🔥 추가: 생성자 (파일 읽기)
    public CategoryRepository() {
        Category[] arr = jsonUtil.load(fileName, Category[].class);
        if (arr != null) {
            categoryList = new ArrayList<>(Arrays.asList(arr));
        }
    }

    // 카테고리 등록
    public void addCategory(Category category) {
        categoryList.add(category);
        jsonUtil.save(fileName, categoryList);
    }

    // 전체 카테고리 조회
    public List<Category> getAllCategories() {
        return categoryList;
    }

    // 카테고리 ID로 찾기
    public Category findByCategoryId(Long categoryId) {
        for (Category category : categoryList) {
            if (category.getCategoryId().equals(categoryId)) {
                return category;
            }
        }
        return null;
    }

    // 카테고리 수정
    public boolean updateCategory(Category updatedCategory) {
        for (int i = 0; i < categoryList.size(); i++) {
            Category category = categoryList.get(i);

            if (category.getCategoryId().equals(updatedCategory.getCategoryId())) {
                categoryList.set(i, updatedCategory);
                jsonUtil.save(fileName, categoryList); // 🔥 추가
                return true;
            }
        }
        return false;
    }

    // 카테고리 삭제
    public boolean removeCategory(Long categoryId) {
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getCategoryId().equals(categoryId)) {
                categoryList.remove(i);
                jsonUtil.save(fileName, categoryList); // 🔥 추가
                return true;
            }
        }
        return false;
    }

    // 대분류 및 중분류 조회 영역
    // 대분류만 조회
    public List<Category> getMainCategories() {
        List<Category> mainCategoryList = new ArrayList<>();

        for (Category category : categoryList) {
            if (category.getParentCategoryId() == null) {
                mainCategoryList.add(category);
            }
        }
        return mainCategoryList;
    }

    // 특정 대분류의 중분류 조회
    public List<Category> getSubCategories(Long parentCategoryId) {
        List<Category> subCategoryList = new ArrayList<>();

        for (Category category : categoryList) {
            if (category.getParentCategoryId() != null &&
                    category.getParentCategoryId().equals(parentCategoryId)) {
                subCategoryList.add(category);
            }
        }
        return subCategoryList;
    }
}