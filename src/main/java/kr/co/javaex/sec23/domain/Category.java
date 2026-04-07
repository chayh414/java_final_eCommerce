package kr.co.javaex.sec23.domain;

public class Category {

    private Long categoryId;
    private Long parentCategoryId;   // null이면 대분류
    private String categoryName;

    public Category() {
    }

    public Category(Long categoryId, Long parentCategoryId, String categoryName) {
        this.categoryId = categoryId;
        this.parentCategoryId = parentCategoryId;
        this.categoryName = categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", parentCategoryId=" + parentCategoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}