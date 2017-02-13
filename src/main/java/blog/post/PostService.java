package blog.post;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author dani
 */
public class PostService {
    private final PostRepository repository = new PostRepository();

    public Integer count() {
        return repository.getAll().size();
    }

    public List<PostDTO> getAll() {
        return repository.getAll().stream().map(PostDTO::fromEntityObject).collect(Collectors.toList());
    }

    public void createNewPost(Map<String, String[]> parameters) throws InvalidArgumentException {
        checkArgument(parameters.containsKey("content"), "Content must be present in the arguments.");

    }
}
