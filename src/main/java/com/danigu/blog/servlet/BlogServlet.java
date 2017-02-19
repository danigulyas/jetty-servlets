package com.danigu.blog.servlet;

import com.danigu.blog.comment.Comment;
import com.danigu.blog.comment.service.CommentService;
import com.danigu.blog.post.Post;
import com.danigu.blog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.http.HttpStatus;

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
public class BlogServlet extends HttpServlet {
    public final String GET_ALL_URI = "/all";
    public final PostService postService;
    public final CommentService commentService;

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
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getPathInfo() != null) {
            handleMethodNotFound(req, resp);
            return;
        }

        resp.getWriter().println("<html><body>");
        handleNewPostRequest(req, resp);
        resp.getWriter().println("</body></html>");
    }

    /**
     * Handler for getting all the posts.
     * @param req
     * @param resp
     * @throws IOException
     */
    public void handleGetAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Post> posts = postService.getAll();
        PrintWriter pw = resp.getWriter();

        if(posts.size() == 0) {
            pw.println("No posts found.");
            resp.setStatus(HttpStatus.NOT_FOUND_404);
        } else {
            pw.printf("Found %d posts: <br>", posts.size());

            for(Post post : posts) {
                pw.println(this.formatPost(post));

                List<Comment> comments = commentService.getComments(post);
                if(comments.size() > 0) {
                    pw.println("<ul>");
                    comments.stream().map(this::formatComment).forEach(pw::println);
                    pw.println("</ul>");
                }

            }
            posts.stream().map(this::formatPost).forEach(pw::println);
            resp.setStatus(HttpStatus.OK_200);
        }
    }

    /**
     * Handling new post requests.
     * TODO(dani): Handle validation with AOP!
     * @param req
     * @param resp
     * @throws IOException
     */
    public void handleNewPostRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String content = req.getParameter("content");

        try {
            Post newPost = postService.newPost(name, content);
            resp.setStatus(HttpStatus.CREATED_201);
            resp.getWriter().println(formatPost(newPost));
        } catch (IllegalArgumentException iae) {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            resp.getWriter().println("Invalid request, please put name and content into the post parameters" +
                    "with a minimum length of 1 characters both.");
        }
    }

    /**
     * Method for handling 404's.
     * @param req
     * @param resp
     * @throws IOException
     */
    public void handleMethodNotFound(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().println("No handler found.");
        resp.setStatus(HttpStatus.NOT_FOUND_404);
    }

    /**
     * @param post
     * @return HTML formatted output of the post.
     */
    public String formatPost(Post post) {
        return String.format("<p><code>#%d</code> - <b>%s</b>: %s...</p>", post.getId(), post.getName(), post.getContent());
    }

    /**
     * @param comment
     * @return HTML formatted output of the add, in a <li> tag.
     */
    public String formatComment(Comment comment) {
        return String.format("<li><code>#%d<code>: %s", comment.getId(), comment.getContent());
    }

}
