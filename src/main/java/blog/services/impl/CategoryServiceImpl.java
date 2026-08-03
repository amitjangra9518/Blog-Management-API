package blog.services.impl;

import blog.entity.Post;
import blog.exception.ResourceNotFoundException;
import blog.payloads.CategoryDto;
import blog.payloads.CategoryResponse;
import blog.payloads.PostDto;
import blog.repo.CategoryRepo;
import blog.services.CategoryService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import blog.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {
   @Autowired
    private CategoryRepo categoryRepo;
   @Autowired
   private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category addedCat=this.categoryRepo.save(category);
        return this.modelMapper.map(addedCat,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cat=this.categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category","category Id",categoryId));
        cat.setCategoryTittle(categoryDto.getCategoryTittle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = this.categoryRepo.save(cat);
        return this.modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category deletecat=this.categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category","category Id",categoryId));
        this.categoryRepo.delete(deletecat);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category getcat=this.categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category","category Id",categoryId));
        return this.modelMapper.map(getcat,CategoryDto.class);
    }

    @Override
    public CategoryResponse getAllCategory(Integer pageNumber,Integer pageSize,String sortBy) {
        PageRequest p= PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        Page<Category> page=this.categoryRepo.findAll(p);
        List<Category> categories=page.getContent();
        List<CategoryDto> categoryDtos=categories.stream()
                .map((category)->this.modelMapper
                        .map(category,CategoryDto.class))
                .collect(Collectors.toList());
        CategoryResponse categoryResponse=new CategoryResponse();
        categoryResponse.setContent(categoryDtos);
        categoryResponse.setPageNumber(page.getNumber());
        categoryResponse.setPageSize(page.getSize());
        categoryResponse.setTotalCategory(page.getTotalElements());
        categoryResponse.setTotalPages(page.getTotalPages());
        categoryResponse.setLastPage(page.isLast());
        return categoryResponse;
    }

    @Override
    public List<CategoryDto> searchCategory(String keyword) {
        List<Category> categories = this.categoryRepo.findByCategoryTittleContaining(keyword);

        List<CategoryDto> categoryDtos = categories.stream()
                .map(category -> this.modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());

        return categoryDtos;

    }
}
