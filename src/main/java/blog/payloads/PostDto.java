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
    private String tittle;
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryDto category;
    private UserDto user;
}
