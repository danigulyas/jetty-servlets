package com.danigu.blog.post;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dani
 * view layer, egyszeru cucc, ez oldja majd meg a conversiont
 */
@AllArgsConstructor
public class PostService {
    private final PostRepository repository;

    public List<Post> getAll() {
        return repository.getAll().stream().map(Post::from).collect(Collectors.toList());
    }

    public Post getById(long id) {
        return Post.from(repository.getById(id));
    }

    public Post create(String name, String content) {
        PostEntity entity = new PostEntity();

        entity.setName(name);
        entity.setContent(content);

        repository.persist(entity);

        return Post.from(entity);
    }

    public void deleteById(long id) throws NotFoundException {
        PostEntity entity = repository.getById(id);

        if(entity == null) throw new NotFoundException("Entity with id " + id + " not found.");

        repository.remove(entity);
    };
}
