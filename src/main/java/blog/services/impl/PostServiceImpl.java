package blog.services.impl;

import blog.entity.Category;
import blog.entity.Post;
import blog.entity.User;
import blog.exception.ResourceNotFoundException;
import blog.payloads.PostDto;
import blog.payloads.PostResponse;
import blog.repo.CategoryRepo;
import blog.repo.PostRepo;
import blog.repo.Userrepo;
import blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service

public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Userrepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user", "userId", userId));

        Category Category = this.categoryRepo.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("category", "categoryId", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(Category);
        Post newpost = this.postRepo.save(post);
        return this.modelMapper.map(newpost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "postId", postId));
        post.setTittle(postDto.getTittle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "postId", postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> allPosts = pagePost.getContent();
        List<PostDto> postDtos = allPosts.stream().map(
                        (post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPosts(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDto getById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "postId", postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {

        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "categoryId", categoryId));

        List<Post> posts = this.postRepo.findByCategory(category);

        return posts.stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "userId", userId));

        List<Post> posts = this.postRepo.findByUser(user);

        return posts.stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {

        List<Post> posts = this.postRepo.findByTittleContaining(keyword);

        List<PostDto> postDtos = posts.stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        return postDtos;
    }
}
