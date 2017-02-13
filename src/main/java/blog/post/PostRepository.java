package blog.post;

import blog.PersistenceUtil;
import javassist.NotFoundException;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dani
 */
public class PostRepository {
    private final EntityManager entityManager = PersistenceUtil.getEntityManager();

    public PostDTO getById(Long id) {
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();
        Post post = entityManager.find(Post.class, id);
        tx.commit();

        return PostDTO.fromEntityObject(post);
    }

    public List<Post> getAll() {
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();
        List<Post> posts = entityManager.createQuery("FROM Post").getResultList();
        tx.commit();

        return posts;
    }

    public void save(Post post) {
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();
        entityManager.persist(post);
        tx.commit();
    }

    public PostDTO editContent(PostDTO postDto, String newContent) throws NotFoundException {
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        Post post = entityManager.find(Post.class, postDto.getId());
        if(post == null) {
            tx.commit();
            throw new NotFoundException("Post with id " + postDto.getId().toString() + " not found.");
        }

        post.setContent(newContent);
        entityManager.persist(post);

        tx.commit();

        return PostDTO.fromEntityObject(post);
    }

    public void delete(PostDTO postDto) {
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();
        Post post = entityManager.find(Post.class, postDto.getId());
        if(post != null) entityManager.remove(post);
        tx.commit();
    }
}
