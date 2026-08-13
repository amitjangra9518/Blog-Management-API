package blog.payloads;

import blog.entity.Category;
import blog.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    // NOTE: this field was missing entirely. Without it, every
    // response from getAllPost/getById/createPost/searchPosts
    // etc. never told the client which post it was looking at,
    // so there was no way to then call PUT/DELETE on that post
    // using data taken from a prior response.
    private Integer postId;

    private String tittle;
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryDto category;
    private UserDto user;
}