package blog.comment;

import blog.PersistenceUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import javassist.NotFoundException;
import blog.post.Post;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

/**
 * @author dani
 */
public class CommentRepository {
    private final EntityManager entityManager = PersistenceUtil.getEntityManager();

    public CommentDTO getById(Long id) {
        Comment comment = entityManager.find(Comment.class, id);
        if(comment == null) return null;

        return CommentDTO.fromEntityObject(comment);
    }

    public CommentDTO editContent(CommentDTO commentDto, String newContent) throws NotFoundException {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        Comment comment;

        try {
            comment = findByDTO(commentDto);
        } catch(NotFoundException ex) {
            tx.rollback();
            throw ex;
        }

        comment.setContent(newContent);
        entityManager.persist(comment);

        tx.commit();

        return CommentDTO.fromEntityObject(comment);
    }

    public void delete(CommentDTO commentDto) throws NotFoundException {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        Comment comment;

        try {
            comment = findByDTO(commentDto);
        } catch(NotFoundException ex) {
            tx.rollback();
            throw ex;
        }

        entityManager.remove(comment);
    }

    private Comment findByDTO(CommentDTO commentDto) throws NotFoundException {
        Comment comment = entityManager.find(Comment.class, commentDto.getId());
        if(comment == null) throw new NotFoundException("Comment with id " + commentDto.getId() + " not found.");

        return comment;
    }
}
