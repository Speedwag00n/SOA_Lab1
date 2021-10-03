package ilia.nemankov.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ilia.nemankov.dto.MovieDTO;
import ilia.nemankov.service.MovieService;
import ilia.nemankov.service.MovieServiceImpl;
import ilia.nemankov.utils.MissingEntityException;
import ilia.nemankov.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;

@WebServlet("/api/movies/*")
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

        if (req.getPathInfo() != null && req.getPathInfo().equals("/golden_palm_count/avrg")) {
            resp.setContentType("application/json");
            writer.write(gson.toJson(movieService.getAvrgGoldenPalmCount()));
        } else if (id == null) {
            FilterConfiguration filterConfiguration = new FilterConfiguration();
            if (req.getAttribute("count") != null && req.getAttribute("page") != null) {
                filterConfiguration.setCount((int) req.getAttribute("count"));
                filterConfiguration.setPage((int) req.getAttribute("page"));
                System.out.println(filterConfiguration.getCount());
                System.out.println(filterConfiguration.getPage());
            }

            if (req.getParameter("order") != null) {
                filterConfiguration.setOrder(req.getParameterValues("order"));
            }

            if (req.getParameter("filter") != null) {
                filterConfiguration.setFilter(req.getParameterValues("filter"));
            }

            List<MovieDTO> movies = null;
            try {
                movies = movieService.findAll(filterConfiguration);
            } catch (ParseException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }

            if (movies != null) {
                writer.write(gson.toJson(movies));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            MovieDTO movie = movieService.findById(id);

            if (movie != null) {
                writer.write(gson.toJson(movie));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");

            PrintWriter writer = resp.getWriter();
            Gson gson = gsonBuilder.create();

            MovieDTO movie = (MovieDTO) req.getAttribute("movie");

            MovieDTO savedValue = movieService.saveOrUpdate(movie);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            writer.write(gson.toJson(savedValue));
        } catch (MissingEntityException e) {
            Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getError());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");

            PrintWriter writer = resp.getWriter();
            Gson gson = gsonBuilder.create();

            MovieDTO movie = (MovieDTO) req.getAttribute("movie");

            writer.write(gson.toJson(movieService.saveOrUpdate(movie)));
        } catch (MissingEntityException e) {
            Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getError());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        Long id = (Long) req.getAttribute("id");
        movieService.delete(id);
    }
}
