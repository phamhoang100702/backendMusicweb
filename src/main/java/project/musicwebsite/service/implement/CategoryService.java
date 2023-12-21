package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.Category;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.repositories.CategoryRepository;
import project.musicwebsite.service.i.ICagegoryService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICagegoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Category> getAll() {
        List<Category> list = categoryRepository.findAll();
        if(list.isEmpty()) throw new NotFoundException("Dont have any category");
        return list;
    }

    @Override
    public List<Category> addList(List<Category> categories) {
        return categoryRepository.saveAll(categories);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()) throw new NotFoundException("Dont have any category");
        categoryRepository.deleteById(id);
    }

    @Override
    public Category searchByName(String name) {
        Optional<Category> category = categoryRepository.findCategoryByName(name);
        if(category.isEmpty()) throw new NotFoundException("Dont have any category");
        return category.get();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}
