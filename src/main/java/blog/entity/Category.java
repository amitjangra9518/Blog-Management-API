package blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    @Column(name="tittle",length=100,nullable = false)
    private String categoryTittle;
    @Column(name ="description")
    private String categoryDescription;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Post> posts=new ArrayList<>();
}
