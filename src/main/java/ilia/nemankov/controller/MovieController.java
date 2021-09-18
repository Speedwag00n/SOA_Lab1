package ilia.nemankov.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ilia.nemankov.dto.MovieDTO;
import ilia.nemankov.service.MovieService;
import ilia.nemankov.service.MovieServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/movie")
public class MovieController extends HttpServlet {

    private final GsonBuilder gsonBuilder;
    private final MovieService movieService;

    public MovieController() {
        this.gsonBuilder = new GsonBuilder();
        this.movieService = new MovieServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();
        Gson gson = gsonBuilder.create();

        Long id = (Long) req.getAttribute("id");

        if (id == null) {
            FilterConfiguration filterConfiguration = new FilterConfiguration();
            if (req.getParameter("count") != null) {
                filterConfiguration.setCount(Integer.valueOf(req.getParameter("count")));

                if (req.getParameter("page") != null) {
                    filterConfiguration.setPage(Integer.valueOf(req.getParameter("page")));
                } else {
                    filterConfiguration.setPage(1);
                }
            }

            if (req.getParameter("order") != null) {
                filterConfiguration.setOrder(req.getParameterValues("order"));
            }

            if (req.getParameter("filter") != null) {
                filterConfiguration.setFilter(req.getParameterValues("filter"));
            }

            System.out.println("Resultt");
            System.out.println(filterConfiguration.getCount());
            System.out.println(filterConfiguration.getPage());
            System.out.println(filterConfiguration.getFilter());
            System.out.println(filterConfiguration.getOrder());

            List<MovieDTO> movies = movieService.findAll(filterConfiguration);

            if (movies != null) {
                writer.write(gson.toJson(movies));
            } else {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            MovieDTO movie = movieService.findById(id);

            if (movie != null) {
                writer.write(gson.toJson(movie));
            } else {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();
        Gson gson = gsonBuilder.create();

        MovieDTO movie = (MovieDTO) req.getAttribute("movie");

        try {
            MovieDTO savedValue = movieService.save(movie);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            writer.write(gson.toJson(savedValue));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.write(gson.toJson(e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();
        Gson gson = gsonBuilder.create();

        MovieDTO movie = (MovieDTO) req.getAttribute("movie");

        try {
            writer.write(gson.toJson(movieService.update(movie)));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.write(gson.toJson(e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();
        Gson gson = gsonBuilder.create();

        Long id = (Long) req.getAttribute("id");
        try {
            movieService.delete(id);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.write(gson.toJson(e.getMessage()));
        }
    }
}
