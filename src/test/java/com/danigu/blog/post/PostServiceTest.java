package com.danigu.blog.post;

import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author dani
 */
public class PostServiceTest {
    private PostRepository repository;
    private PostService postService;

    @Before
    public void instantiate() {
        repository = getRepositoryMock();
        postService = new PostService(repository);
    }

    @Test
    public void testGetAll() {
        List<PostEntity> posts = new ArrayList();

        posts.add(postEntity(0, "n0", "c0"));
        posts.add(postEntity(1, "n1", "c1"));

        when(repository.getAll()).thenReturn(posts);

        List<Post> result = postService.getAll();

        assertEquals("Repository return and result has the same size.", result.size(), 2);
        assertPostEntityPostEquals(posts.get(0), result.get(0));
        assertPostEntityPostEquals(posts.get(1), result.get(1));
    }

    @Test
    public void testGetById() {
        PostEntity entity = postEntity(11, "name", "content");
        when(repository.getById(11)).thenReturn(entity);

        Post post = postService.getById(Long.valueOf(11));
        assertNotNull("Returned post is not null.", post);
        assertPostEntityPostEquals(entity, post);
    }

    @Test
    public void testGetByIdWithNull() {
        PostEntity entity = null;
        when(repository.getById(11)).thenReturn(entity);

        Post post = postService.getById(Long.valueOf(11));
        assertNull("When post is not found, null is returned.", post);
    }

    @Test
    public void testCreate() {
        final String NAME = "nameofthepost";
        final String CONTENT = "contentofthepost";
        ArgumentCaptor<PostEntity> captor = ArgumentCaptor.forClass(PostEntity.class);

        postService.create(NAME, CONTENT);

        verify(repository).persist(captor.capture());
        PostEntity entity = captor.getValue();

        assertEquals("Entity and name argument is the same.", entity.getName(), NAME);
        assertEquals("Entity and content argument is the same.", entity.getContent(), CONTENT);
    }

    @Test
    public void testDeleteById() throws NotFoundException {
        PostEntity entity  = postEntity(0, "name", "content");
        ArgumentCaptor<PostEntity> arguments = ArgumentCaptor.forClass(PostEntity.class);
        when(repository.getById(Long.valueOf(0))).thenReturn(entity);

        postService.deleteById(Long.valueOf(0));

        verify(repository).remove(arguments.capture());
        assertEquals("The same entity is removed as passed.", entity, arguments.getValue());
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteByIdThrowsNotFoundException() throws NotFoundException {
        when(repository.getById(Long.valueOf(1))).thenReturn(null);
        postService.deleteById(Long.valueOf(1));
    }

    public void assertPostEntityPostEquals(PostEntity entity, Post dto) {
        assertEquals("DTO and entity has the same id..", entity.getId(), dto.getId());
        assertEquals("DTO and entity has the same name.", entity.getName(), dto.getName());
        assertEquals("DTO and entity has the same content.", entity.getContent(), dto.getContent());
        //TODO(dani): add assertion for comments!
    }

    public PostRepository getRepositoryMock() {
        return mock(PostRepository.class);
    }

    public PostEntity postEntity(Integer id, String name, String content) {
        return new PostEntity(Long.valueOf(id), name, content, new ArrayList());
    }
}
