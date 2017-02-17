package com.danigu.blog;

import com.danigu.blog.comment.Comment;
import com.danigu.blog.dic.StationLocator;
import com.danigu.blog.servlet.PostServlet;
import com.google.common.collect.ImmutableList;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.List;

/**
 * @author dani
 */
public class ServerApplication {
    public static void main(String[] args) throws Exception {
        StationLocator locator = new StationLocator();

        Server server = new Server(8081);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.setContextPath("/v1/");
        configureServlets(locator, context);

        server.setHandler(context);
        server.start();
        server.join();
    }

    public static void configureServlets(StationLocator locator, ServletContextHandler context) {
        PostServlet postServlet = new PostServlet(locator.getPostService());
        context.addServlet(new ServletHolder(postServlet), "/post/*");
    }
}
