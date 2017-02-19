package com.danigu.blog.post.service;

import com.danigu.blog.common.service.CommonService;
import com.danigu.blog.post.Post;
import com.danigu.blog.post.PostEntity;
import com.danigu.blog.post.persistence.*;
import javassist.NotFoundException;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Service for managing posts.
 * @see CommonService
 */
public class PostService extends CommonService<PostEntity, Post> {

    public PostService(PostRepository repository, PostEntityDTOTransformer transformer) {
        super(repository, transformer);
    }

    protected Class<PostEntity> getEntityClazz() {
        return PostEntity.class;
    }

    /**
     * Creates a new post.
     * @param name of the new post.
     * @param content of the new post.
     * @return the post created.
     * @throws IllegalArgumentException
     */
    public Post newPost(String name, String content) throws IllegalArgumentException {
        checkArgument(name != null, "Name can't be null.", IllegalArgumentException.class);
        checkArgument(name != null, "Name can't be null.", IllegalArgumentException.class);

        return transformer.toEntity(repository.save(new PostEntity((Long) null, name, content)));
    }

    /**
     * Changes a name of an existing post.
     * @param id of the post.
     * @param newName of the post to be modified.
     * @return the modified post.
     * @throws NotFoundException if the post is not found.
     * @throws IllegalArgumentException
     */
    public Post changeName(long id, String newName) throws NotFoundException, IllegalArgumentException {
        checkArgument(Long.valueOf(id) != null, "Id can't be null.", IllegalArgumentException.class);
        checkArgument(newName != null, "Name can't be null.", IllegalArgumentException.class);
        checkArgument(newName.length() > 0, "Name must be at least 1 character long.", IllegalArgumentException.class);

        PostEntity post = repository.getById(id);
        if(post == null) throw new NotFoundException("PostDTO not found.");

        post.setName(newName);
        return transformer.toEntity(repository.save(post));
    }
}
