package blog.controller;

import blog.entity.Post;
import blog.payloads.ApiResponse;
import blog.payloads.PostDto;
import blog.payloads.PostResponse;
import blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {
    @Autowired
    private PostService postService;
    //create
    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<PostDto> createPost
    (@RequestBody PostDto postDto,
     @PathVariable Integer userId,
     @PathVariable Integer categoryId
    )
    {

        PostDto createPost=this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
    }
    //get posts by users
    @GetMapping("user/{userId}/post")
    //get by user
    public ResponseEntity<List<PostDto>> getPostByuser(
            @PathVariable Integer userId
    )
    {
      List<PostDto> posts=  this.postService.getPostByUser(userId);
      return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }
    //get post by category
    @GetMapping("category/{categoryId}/post")
    public ResponseEntity<List<PostDto>> getPostByCategory(
            @PathVariable Integer categoryId)
    {
        List<PostDto> posts=  this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }
    @GetMapping("/post")
    //get all posts
    public ResponseEntity<PostResponse>getAllPost(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy)

    {
        PostResponse postResponse=this.postService.getAllPost(pageNumber,pageSize,sortBy);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }
    //get post by id
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {

        return ResponseEntity.ok(this.postService.getById(postId));
    }
    //delete post
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId) {

        this.postService.deletePost(postId);

        return new ResponseEntity<>(
                new ApiResponse("Post deleted successfully", true),
                HttpStatus.OK
        );
    }
    // update post
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDto> updatePost
    (@RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto updatePost=this.postService.updatePost(postDto,postId);
    return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
    }
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>>searchByTittleContaining(
            @PathVariable("keywords") String keywords
    ){
        List<PostDto> result=this.postService.searchPosts(keywords);
        return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
    }
}
