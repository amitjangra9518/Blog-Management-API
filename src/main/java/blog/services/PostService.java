package blog.services;

import blog.entity.Post;
import blog.payloads.PostDto;
import blog.payloads.PostResponse;

import java.util.List;

public interface PostService {
    //create post
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    //update post
    PostDto updatePost(PostDto postDto,Integer postId);
    //delete post
    void deletePost(Integer postId);
    //get all post
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortby);
    //get single post
    PostDto getById(Integer postId);
    //get all post by category
    List<PostDto>getPostByCategory(Integer categoryId);
    //get all user post
    List<PostDto>getPostByUser(Integer userId);
    //search post
    List<PostDto>searchPosts(String keyword);
}
