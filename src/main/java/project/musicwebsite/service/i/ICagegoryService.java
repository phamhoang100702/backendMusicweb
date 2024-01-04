package project.musicwebsite.service.i;

import project.musicwebsite.entity.Category;
import project.musicwebsite.service.implement.CategoryService;

import java.util.List;

public interface ICagegoryService {
    public List<Category> getAll();
    public List<Category> addList(List<Category> categories);
    public void deleteById(Long id);

    public Category searchByName(String name);

    public Category save(Category category);

    public Category findById(Long id);
}
