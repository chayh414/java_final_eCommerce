package kr.co.javaex.sec23.repository;

import kr.co.javaex.sec23.domain.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    // Category 객체들을 저장할 리스트
    private List<Category> categoryList = new ArrayList<>();

    // 카테고리 등록
    public void addCategory(Category category) {
        categoryList.add(category);
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