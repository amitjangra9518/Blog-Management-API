package blog.services;

import blog.payloads.CategoryDto;
import blog.payloads.CategoryResponse;
import blog.payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    //create
    CategoryDto createCategory(CategoryDto categoryDto);
    //update
    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
    //delete
   void deleteCategory(Integer categoryId);
    //get
    CategoryDto getCategory(Integer categoryId);
    //get all
    CategoryResponse getAllCategory(Integer pageNumber,Integer pageSize,String sortBy);
    List<CategoryDto>searchCategory(String keyword);
}
