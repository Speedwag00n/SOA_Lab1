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

@WebServlet("/api/contains")
public class NameContainsController extends HttpServlet {
    private final MovieService movieService;
    private final GsonBuilder gsonBuilder;

    public NameContainsController() {
        this.movieService = new MovieServiceImpl();
        this.gsonBuilder = new GsonBuilder();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();
        Gson gson = gsonBuilder.create();

        if (req.getParameter("name") == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson("name parameter must be specified"));
            return;
        }

        String name = req.getParameter("name");

        FilterConfiguration filterConfiguration = new FilterConfiguration();
        filterConfiguration.setFilter(new String[]{"name,contains," + name});
        List<MovieDTO> movies = movieService.findAll(filterConfiguration);

        if (movies != null) {
            writer.write(gson.toJson(movies));
        } else {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
