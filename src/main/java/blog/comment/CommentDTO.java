package blog.comment;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author dani
 */
@AllArgsConstructor
public class CommentDTO {
    @Getter private final Long id;
    @Getter private final String content;

    public static CommentDTO fromEntityObject(Comment comment) {
        if(comment == null) return null;
        return new CommentDTO(comment.getId(), comment.getContent());
    }
}
