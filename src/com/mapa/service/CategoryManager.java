package com.mapa.service;

import com.mapa.model.Category;

import java.io.*;
import java.util.ArrayList;
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
        categories = loadCategories();
    }

    private List<Category> loadCategories() {
        List<Category> categoryList = new ArrayList<>();
        try {
            FileReader categories = new FileReader("data/categories.csv");
            BufferedReader buffer = new BufferedReader(categories);
            String line;
            while((line = buffer.readLine()) != null){
                String[] values = line.split(",");
                int id = Integer.valueOf(values[0]);
                int uid = Integer.valueOf(values[1]);
                String label = values[2];
                if (uid == userId) {
                    categoryList.add(new Category(id, label));
                }
            }
            buffer.close();
            categories.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categoryList;
    }
    
    public void AddCategory(Category category) {
        saveCategory(category);
        categories.add(category);
    }


    public Category GetCategory(int id) {
        for (var category : categories) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }

    public List<Category> GetCategories() {
        return categories;
    }

    private void saveCategory(Category category) {
        try {
            FileWriter categories = new FileWriter("data/categories.csv", true);
            String[] values = {String.valueOf(category.getId()), String.valueOf(userId), category.getLabel()};
            categories.append(String.join(",", values));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
