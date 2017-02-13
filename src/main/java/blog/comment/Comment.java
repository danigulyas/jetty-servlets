package blog.comment;

import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import blog.post.Post;

import javax.persistence.*;
import java.util.Map;

/**
 * @author dani
 */
@Entity
@Table(name = "comment")
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @Column
    @Getter
    @Setter
    private String content;

    @OneToOne(targetEntity = Post.class)
    @Getter
    @Setter
    private Post post;
}
