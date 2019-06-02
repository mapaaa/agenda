package com.mapa.service;

import com.mapa.model.Category;

import java.util.List;

public class CategoryManager {
    private static CategoryManager instance;
    private static List<Category> categories;

    static CategoryManager getInstance(int userId) {
        if (instance == null) {
            instance = new CategoryManager(userId);
        }
        return instance;
    }

    public static CategoryManager getInstanceWhenLoggedIn() {
        return instance;
    }

    private CategoryManager(int uid) {
        categories = DatabaseManager.getInstance().SelectAllCategories(uid);
    }
    
    public void AddCategory(String label) {
        Category category = new Category(0, AccountManager.getInstance().getUser().getId(), label);
        int c_id = DatabaseManager.getInstance().Create(category);
        categories.add(new Category(c_id, category.getUid(), category.getLabel()));
    }


    public Category GetCategory(int id) {
        Logger.Log("Get category by id");
        for (var category : categories) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }

    public List<Category> GetCategories() {
        Logger.Log("Get all categories");
        return categories;
    }

    public void deleteCategory(int id) {
        categories.removeIf(c -> c.getId() == id);
        DatabaseManager.getInstance().DeleteCategory(id);
    }

    public int AddCategoryAndGetId(String label) {
        Category category = new Category(0, AccountManager.getInstance().getUser().getId(), label);
        int c_id = DatabaseManager.getInstance().Create(category);
        categories.add(new Category(c_id, category.getUid(), category.getLabel()));
        return c_id;
    }
}
