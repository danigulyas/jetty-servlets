package com.danigu.blog.comment.persistence;

import com.danigu.blog.comment.CommentEntity;
import com.danigu.blog.base.persistence.BaseRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author dani
 */
public class CommentRepository extends BaseRepository<CommentEntity> {
    public CommentRepository(EntityManagerFactory emf) {
        super(emf, CommentEntity.class);
    }

    public List<CommentEntity> getAllWithPostId(long id) {
        TypedQuery<CommentEntity> query = getEntityManager()
                .createQuery("SELECT c from CommentDto c WHERE c.post.id = :postId", clazz);

        return query.setParameter("postId", id).getResultList();
    }
}
