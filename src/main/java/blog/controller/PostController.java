package blog.controller;

import blog.payloads.ApiResponse;
import blog.payloads.PostDto;
import blog.payloads.PostResponse;
import blog.services.FileService;
import blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    // create a new post
    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId
    ) {
        PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }

    // get all posts by a user
    @GetMapping("user/{userId}/post")
    public ResponseEntity<List<PostDto>> getPostByuser(@PathVariable Integer userId) {
        List<PostDto> posts = this.postService.getPostByUser(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // get all posts in a category
    @GetMapping("category/{categoryId}/post")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDto> posts = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // get all posts (paginated)
    @GetMapping("/post")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy
    ) {
        PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // get a single post by id
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        return ResponseEntity.ok(this.postService.getById(postId));
    }

    // delete a post
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(
                new ApiResponse("Post deleted successfully", true),
                HttpStatus.OK
        );
    }

    // update a post
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
        PostDto updatePost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }

    // search posts by title keyword
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchByTittleContaining(@PathVariable("keywords") String keywords) {
        List<PostDto> result = this.postService.searchPosts(keywords);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // upload / attach an image to a post
    // NOTE: in Postman this must be sent as Body -> form-data, with a key named
    // "image" set to type "File". Do NOT manually set a Content-Type header —
    // let Postman generate it, otherwise the multipart boundary breaks and the
    // request fails even though the same call works fine from Swagger.
    @PostMapping(
            value = "/post/image/upload/{postId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId
    ) throws IOException {

        String fileName = fileService.uploadImage(path, image);

        PostDto postDto = postService.getById(postId);
        postDto.setImageName(fileName);

        PostDto updatePost = postService.updatePost(postDto, postId);

        return ResponseEntity.ok(updatePost);
    }

    // method to serve files
    // NOTE: switched to try-with-resources. Previously the InputStream was
    // closed manually after StreamUtils.copy(), which meant that if the
    // copy itself threw partway through, the stream was never closed and
    // the file handle leaked.
    @GetMapping("/post/image/{imageName}")
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        String contentType = Files.probeContentType(
                Paths.get(path, imageName)
        );

        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        response.setContentType(contentType);

        try (InputStream resource = this.fileService.getResource(path, imageName)) {
            StreamUtils.copy(resource, response.getOutputStream());
            response.getOutputStream().flush();
        }
    }
}