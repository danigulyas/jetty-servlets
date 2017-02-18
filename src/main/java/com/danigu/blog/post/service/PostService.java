package com.danigu.blog.post.service;

import com.danigu.blog.post.persistence.*;
import com.danigu.blog.post.persistence.PostDTO;
import com.sun.javaws.exceptions.InvalidArgumentException;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dani
 */
@AllArgsConstructor
public class PostService {
    final PostRepository repository;
    final ServiceDTOAdaptor adaptor;

    public Post newPost(String name, String content) throws InvalidArgumentException {
        checkArgument(name != null, "Name can't be null.");
        checkArgument(name != null, "Name can't be null.");

        return adaptor.convert(repository.save(new PostDTO(name, content)));
    }

    public Post changeName(long id, String newName) throws NotFoundException, InvalidArgumentException {
        checkArgument(Long.valueOf(id) != null, "Id can't be null.");
        checkArgument(newName != null, "Name can't be null.");
        checkArgument(newName.length() > 0, "Name must be at least 1 character long.");

        PostDTO post = repository.getById(id);
        if(post == null) throw new NotFoundException("Post not found.");

        post.setName(newName);
        return adaptor.convert(repository.save(post));
    }

    public void delete(long id) throws NotFoundException {
        checkArgument(Long.valueOf(id) != null, "Id can't be null.");

        PostDTO post = repository.getById(id);
        if(post == null) throw new NotFoundException("Post not found.");

        repository.remove(post);
    }
}
