package blog.controller;

import blog.payloads.ApiResponse;
import blog.payloads.CategoryDto;
import blog.payloads.CategoryResponse;
import blog.payloads.PostDto;
import blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
   @Autowired
    private CategoryService categoryService;
    //create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid  @RequestBody CategoryDto categoryDto ){
        CategoryDto createcategory=this.categoryService.createCategory(categoryDto);
    return new ResponseEntity<CategoryDto>(createcategory, HttpStatus.CREATED);
    }
    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @Valid @RequestBody CategoryDto categoryDto,
            @PathVariable Integer categoryId) {

        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);

        return ResponseEntity.ok(updatedCategory);
    }
    //delete
    @DeleteMapping("/{CategoryId}")
    public ResponseEntity<ApiResponse> deletecategory(@PathVariable Integer CategoryId)
    {
        this.categoryService.deleteCategory(CategoryId);
        return new ResponseEntity
                (new ApiResponse("Category deleted successfully",true),HttpStatus.OK);
    }
    //get
    @GetMapping("/{CategoryId}")
    public ResponseEntity<CategoryDto> getSingleUser(@PathVariable Integer CategoryId)
    {
        return ResponseEntity.ok(this.categoryService.getCategory(CategoryId));
    }
    //getall
    @GetMapping
    public ResponseEntity<CategoryResponse> getAllCategory(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "categoryId",required = false)String sortBy
    )
    {
        return ResponseEntity.ok(this.categoryService.getAllCategory(pageNumber,pageSize,sortBy));
    }
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<CategoryDto>>searchByTittleContaining(
            @PathVariable("keywords") String keywords
    ){
        List<CategoryDto> result=this.categoryService.searchCategory(keywords);
        return new ResponseEntity<List<CategoryDto>>(result,HttpStatus.OK);
    }
}
