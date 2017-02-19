package com.danigu.blog.common.service;

import com.danigu.blog.post.Post;
import com.danigu.blog.post.PostEntity;
import com.danigu.blog.post.persistence.PostRepository;
import com.danigu.blog.post.service.PostTransformer;
import com.danigu.blog.post.service.PostService;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * TODO(dani): Dependent on {@link com.danigu.blog.post.service.PostService}, not nice, fixture neeeeeeeeded!
 * @author dani
 */
public class CommonServiceTest {
    private PostRepository repository;
    private PostTransformer transformer;
    private PostService service;

    @Before
    public void instantiate() {
        repository = mock(PostRepository.class);
        transformer = new PostTransformer(); //TODO(dani): fixture needed here as well.
        service = new PostService(repository, transformer);
    }

    @Test(expected = RuntimeException.class)
    public void testConstructorThrowsWithNullRepository() {
        new PostService(null, transformer);
    }

    @Test(expected = RuntimeException.class)
    public void testConstructorThrowsWithNullTransformer() {
        new PostService(repository, null);
    }

    @Test
    public void testGetAll() {
        List<PostEntity> resultMock = new ArrayList();
        resultMock.add(getPostEntityMock(0, "name0", "content0"));
        resultMock.add(getPostEntityMock(1, "name1", "content1"));

        when(repository.getAll()).thenReturn(resultMock);

        List<Post> result = service.getAll();
        assertEquals(resultMock.size(), result.size());
        assertPostEqualToPostEntity(resultMock.get(0), result.get(0));
        assertPostEqualToPostEntity(resultMock.get(1), result.get(1));
    }

    @Test(expected = RuntimeException.class)
    public void testGetByIdThrowsWhenCalledWithNull() {
        service.getById(null);
    }

    @Test
    public void testGetById() {
        PostEntity resultMock = getPostEntityMock(0, "name", "content");
        when(repository.getById(resultMock.getId())).thenReturn(resultMock);

        Post result = service.getById(resultMock.getId());
        assertPostEqualToPostEntity(resultMock, result);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteByIdThrowsWhenCalledWithNull() throws NotFoundException {
        service.deleteById((Long) null);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteByIdThrowsWhenNotFound() throws NotFoundException {
        final long id = 1;
        PostEntity resultMock = null;

        when(repository.getById(1)).thenReturn(resultMock);

        service.deleteById(id);
    }

    protected void assertPostEqualToPostEntity(PostEntity entity, Post dto) {
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), entity.getName());
        assertEquals(entity.getContent(), entity.getContent());
    }

    protected PostEntity getPostEntityMock(int id, String name, String content) {
        PostEntity result = mock(PostEntity.class);

        when(result.getId()).thenReturn(Long.valueOf(id));
        when(result.getName()).thenReturn(name);
        when(result.getContent()).thenReturn(content);

        return result;
    }
}
