package blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    @Column(name = "Post_tittle",length = 100,nullable = false)
    private String tittle;
    @Column(length = 500)
    private String content;
    private String imageName;
    private Date addedDate;
    @ManyToOne
    private Category category;
    @ManyToOne
    private User user;

}
