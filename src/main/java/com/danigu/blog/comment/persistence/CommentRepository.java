package com.danigu.blog.comment.persistence;

import com.danigu.blog.common.persistence.CommonRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author dani
 */
public class CommentRepository extends CommonRepository<CommentEntity> {
    public CommentRepository(EntityManagerFactory emf) {
        super(emf);
    }

    public List<CommentEntity> getAllWithPostId(long id) {
        TypedQuery<CommentEntity> query = getEntityManager()
                .createQuery("SELECT c from Comment c WHERE c.post.id = :postId", getClazz());

        return query.setParameter("postId", id).getResultList();
    }

    protected Class<CommentEntity> getClazz() {
        return CommentEntity.class;
    }
}
