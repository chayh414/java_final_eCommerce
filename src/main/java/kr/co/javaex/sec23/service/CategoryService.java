package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.Category;
import kr.co.javaex.sec23.repository.CategoryRepository;

import java.util.List;

public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // 카테고리 추가
    public void addCategory(Category category) {
        categoryRepository.addCategory(category);
    }

    // 전체 카테고리 조회
    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    // 카테고리 수정
    public boolean updateCategory(Category category) {
        return categoryRepository.updateCategory(category);
    }

    // 카테고리 삭제
    public boolean removeCategory(Long categoryId) {
        return categoryRepository.removeCategory(categoryId);
    }

    // 대분류 조회
    public List<Category> getMainCategories() {
        return categoryRepository.getMainCategories();
    }

    // 중분류 조회
    public List<Category> getSubCategories(Long parentCategoryId) {
        return categoryRepository.getSubCategories(parentCategoryId);
    }
}