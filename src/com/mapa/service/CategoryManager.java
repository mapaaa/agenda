package com.mapa.service;

import com.mapa.model.Category;

import java.util.List;

public class CategoryManager {
    private static int userId;
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
        userId = uid;
        categories = CSVIO.LoadCategories(uid);
    }
    
    public void AddCategory(Category category) {
        Logger.Log("Added new category locally");
        saveCategory(category);
        categories.add(category);
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

    private void saveCategory(Category category) {
        CSVIO.SaveCategory(category, userId);
    }
}
