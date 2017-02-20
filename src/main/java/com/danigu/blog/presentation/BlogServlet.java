package com.danigu.blog.presentation;

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
    public final PostService postService;
    public final CommentService commentService;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getPathInfo() == null) {
            resp.getWriter().println("<html><body>");
            renderBlog(req, resp);
            resp.getWriter().println("</body></html>");
        } else {
            handleNotFound(req, resp);
        }
    }

    /**
     * Handler for getting all the posts.
     * @param req
     * @param resp
     * @throws IOException
     */
    public void renderBlog(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Post> posts = postService.getAll();
        PrintWriter pw = resp.getWriter();

        pw.printf("<a href=\"%s\">Create post</a><br>", (req.getContextPath() == null ? null : req.getContextPath()) + "/post/new");

        if(posts.size() == 0) {
            pw.println("No posts found.");
            resp.setStatus(HttpStatus.NOT_FOUND_404);
        } else {
            pw.printf("Found %d posts: <br>", posts.size());

            for(Post post : posts) {
                pw.println(this.formatPost(req.getContextPath(), post));

                List<Comment> comments = commentService.getComments(post);
                if(comments.size() > 0) {
                    pw.println("<ul>");
                    comments.stream().map(comment -> formatComment(req.getContextPath(), comment)).forEach(pw::println);
                    pw.println("</ul>");
                }

            }
            resp.setStatus(HttpStatus.OK_200);
        }
    }

    /**
     * @param post
     * @return HTML formatted output of the post.
     */
    public String formatPost(String contextPath, Post post) {
        return String.format("<p><code>#%d</code> - <b>%s</b>: %s... || <a href=\"%s\">[Comment]</a>  <a href=\"%s\">[Delete]</a></p>",
                post.getId(), post.getName(), post.getContent(), getCreatePathForComment(contextPath, post),
                getDeletePathForPost(contextPath, post));
    }

    public String getCreatePathForComment(String contextPath, Post post) {
        return (contextPath == null ? "" : contextPath) + "/comment/new?postId=" + post.getId();
    }

    public String getDeletePathForPost(String contextPath, Post post) {
        return (contextPath == null ? "" : contextPath) + "/post/delete?id=" + post.getId();
    }

    /**
     * @param comment
     * @return HTML formatted output of the add, in a <li> tag.
     */
    public String formatComment(String contextPath, Comment comment) {
        return String.format("<li><code>#%d<code>: %s || <a href=\"%s\">[Delete]</a>",
                comment.getId(), comment.getContent(), getDeletePathForComment(contextPath, comment));
    }

    public String getDeletePathForComment(String contextPath, Comment comment) {
        return (contextPath == null ? "" : contextPath) + "/comment/delete?id=" + comment.getId();
    }

    /**
     * Method for handling 404's.
     * @param req
     * @param resp
     * @throws IOException
     */
    public void handleNotFound(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpStatus.NOT_FOUND_404);
        resp.getWriter().println("Not found.");
    }
}
