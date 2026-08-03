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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_name", nullable = false, length = 255)
    private String name;

    @Column(name = "user_email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    private String about;

    public User(int id) {
        this.id = id;
    }
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Post> posts=new ArrayList<>();
}