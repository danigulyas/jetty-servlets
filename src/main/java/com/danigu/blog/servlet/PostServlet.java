package com.danigu.blog.servlet;

import com.danigu.blog.post.Post;
import com.danigu.blog.post.PostService;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author dani
 */
@RequiredArgsConstructor
public class PostServlet extends HttpServlet {
    private final String GET_ALL_URI = "/all";
    private final PostService postService;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if(path == null) path = GET_ALL_URI;

        resp.getWriter().println("<html><body>");
        switch(path) {
            case GET_ALL_URI:
                handleGetAll(req, resp);
                break;

            default:
                handleMethodNotFound(req, resp);
        }
        resp.getWriter().println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getPathInfo() != null) {
            handleMethodNotFound(req, resp);
            return;
        }

        /**
         * TODO(dani): Itt csak a get paramokat huzza, vagy postot is?
         */
        String name = req.getParameter("name");
        String content = req.getParameter("content");

        if(name == null || content == null || name.length() == 0 || content.length() == 0)
        {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            resp.getWriter().println("Post parameter name and content is required, please set them to a string with at " +
                    "least one character.");
            return;
        }

        resp.getWriter().println("<html><body>");

        Post newPost = postService.create(name, content);
        resp.setStatus(HttpStatus.CREATED_201);
        resp.getWriter().println(formatPost(newPost));

        resp.getWriter().println("</body></html>");
    }

    public void handleMethodNotFound(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().println("No handler found.");
        resp.setStatus(HttpStatus.NOT_FOUND_404);
    }

    public void handleGetAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Post> posts = postService.getAll();
        PrintWriter pw = resp.getWriter();

        if(posts.size() == 0) {
            pw.println("No posts found.");
            resp.setStatus(HttpStatus.NOT_FOUND_404);
        } else {
            pw.printf("Found %d posts: <br>", posts.size());
            posts.stream().map(this::formatPost).forEach(pw::println);
            resp.setStatus(HttpStatus.OK_200);
        }
    }

    private String formatPost(Post post) {
        return String.format("<p><code>#%d</code> - <b>%s</b>: %s...</p>", post.getId(), post.getName(), post.getContent());
    }

}
