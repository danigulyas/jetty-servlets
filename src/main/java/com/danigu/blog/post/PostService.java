package com.danigu.blog.post;

import com.danigu.blog.comment.Comment;
import com.danigu.blog.comment.CommentEntity;
import com.google.common.collect.ImmutableList;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
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
        return repository.getAll().stream().map(Post::fromEntity).collect(Collectors.toList());
    }

    public Post getById(long id) {
        return Post.fromEntity(repository.getById(id));
    }

    public Post create(String name, String content) {
        PostEntity entity = new PostEntity(name, content, new ArrayList());

        repository.persist(entity);

        return Post.fromEntity(entity);
    }

    public void deleteById(long id) throws NotFoundException {
        PostEntity entity = repository.getById(id);

        if(entity == null) throw new NotFoundException("Entity with id " + id + " not found.");

        repository.remove(entity);
    };
}
