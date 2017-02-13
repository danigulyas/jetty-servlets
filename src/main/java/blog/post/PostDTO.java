package blog.post;

import blog.comment.Comment;
import com.google.common.collect.ImmutableList;
import blog.comment.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dani
 */
@AllArgsConstructor
public class PostDTO {
    @Getter private final Long id;
    @Getter private final String content;
    @Getter private final ImmutableList<CommentDTO> comments;

    public static PostDTO fromEntityObject(Post post) {
        if(post == null) return null;

        List<CommentDTO> comments = post
                .getComments()
                .stream()
                .map(CommentDTO::fromEntityObject)
                .collect(Collectors.toList());

        return new PostDTO(post.getId(), post.getContent(), ImmutableList.copyOf(comments));
    }
}
