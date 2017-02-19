package com.danigu.blog.common.persistence;

import com.danigu.blog.post.PostEntity;
import com.danigu.blog.post.persistence.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests for generic repository.
 * TODO(dani): Add tests for {@link CommonRepository::getAll()}.
 * TODO(dani): Depends on {@link PostRepository}, decoupling with a fixture would be better.
 * @author dani
 */
public class CommonRepositoryTest {
    private PostRepository repository;

    private EntityManager em;
    private EntityManagerFactory efm;

    @Before
    public void instantiate() {
        em = mock(EntityManager.class);
        efm = mock(EntityManagerFactory.class);

        when(efm.createEntityManager()).thenReturn(em);

        repository = new PostRepository(efm);
    }

    @Test(expected = RuntimeException.class)
    public void testThrowsWhenConstructedWithNull() {
        new PostRepository(null);
    }

    @Test
    public void testGetById() {
        final long id = 42;
        final PostEntity mock = mock(PostEntity.class);
        when(em.find(PostEntity.class, id)).thenReturn(mock);

        final PostEntity result = repository.getById(id);

        assertEquals("The result from the entity manager is returned.", mock, result);
    }

    @Test
    public void testSave() {
        final long resultId = 42;
        final PostEntity mock = mock(PostEntity.class);
        final PostEntity resultMock = mock(PostEntity.class);

        when(resultMock.getId()).thenReturn(resultId);
        when(em.merge(mock)).thenReturn(resultMock);

        PostEntity result = repository.save(mock);
        assertEquals("Returns the merged entity correctly.", resultMock, result);
        assertEquals("The id is the same as returned.", resultMock.getId(), resultId);
    }

    @Test
    public void testRemove() {
        final PostEntity mock = mock(PostEntity.class);
        final ArgumentCaptor<PostEntity> arguments = ArgumentCaptor.forClass(PostEntity.class);

        repository.remove(mock);

        verify(em, times(1)).remove(mock);
    }
}
