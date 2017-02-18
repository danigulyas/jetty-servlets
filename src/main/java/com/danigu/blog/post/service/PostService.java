package com.danigu.blog.post.service;

import com.danigu.blog.post.persistence.*;
import com.danigu.blog.post.persistence.PostDTO;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author dani
 */
@AllArgsConstructor
public class PostService {
    final PostRepository repository;
    final ServiceDTOAdaptor adaptor;

    /**
     * Returns a list of posts.
     */
    public List<Post> getAll() {
        return repository.getAll().stream().map(adaptor::convert).collect(Collectors.toList());
    }

    /**
     * Creates a new post.
     * @param name of the new post.
     * @param content of the new post.
     * @return the post created.
     * @throws InvalidArgumentException
     */
    public Post newPost(String name, String content) throws IllegalArgumentException {
        checkArgument(name != null, "Name can't be null.", IllegalArgumentException.class);
        checkArgument(name != null, "Name can't be null.", IllegalArgumentException.class);

        return adaptor.convert(repository.save(new PostDTO(name, content)));
    }

    /**
     * Changes a name of an existing post.
     * @param id of the post.
     * @param newName of the post to be modified.
     * @return the modified post.
     * @throws NotFoundException if the post is not found.
     * @throws InvalidArgumentException
     */
    public Post changeName(long id, String newName) throws NotFoundException, IllegalArgumentException {
        checkArgument(Long.valueOf(id) != null, "Id can't be null.", IllegalArgumentException.class);
        checkArgument(newName != null, "Name can't be null.", IllegalArgumentException.class);
        checkArgument(newName.length() > 0, "Name must be at least 1 character long.", IllegalArgumentException.class);

        PostDTO post = repository.getById(id);
        if(post == null) throw new NotFoundException("Post not found.");

        post.setName(newName);
        return adaptor.convert(repository.save(post));
    }

    /**
     * Deletes a post.
     * @param id of the post to be deleted.
     * @throws NotFoundException if the post is not found.
     */
    public void delete(long id) throws NotFoundException {
        checkArgument(Long.valueOf(id) != null, "Id can't be null.");

        PostDTO post = repository.getById(id);
        if(post == null) throw new NotFoundException("Post not found.");

        repository.remove(post);
    }
}
