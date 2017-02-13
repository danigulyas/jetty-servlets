package blog.post;

import com.google.common.collect.ImmutableMap;
import blog.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dani
 */
@Entity
@Table(name = "post")
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Column
    @Getter
    @Setter
    private String content;

    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.PERSIST)
    @Getter
    @Setter
    private List<Comment> comments = new ArrayList();
}
