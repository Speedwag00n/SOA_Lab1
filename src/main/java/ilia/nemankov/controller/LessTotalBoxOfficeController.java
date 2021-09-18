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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api/less")
public class LessTotalBoxOfficeController extends HttpServlet {
    private final MovieService movieService;
    private final GsonBuilder gsonBuilder;

    public LessTotalBoxOfficeController() {
        this.movieService = new MovieServiceImpl();
        this.gsonBuilder = new GsonBuilder();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();
        Gson gson = gsonBuilder.create();

        if (req.getParameter("lessThan") == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson("lessThan parameter must be specified"));
            return;
        }

        Double lessThan = Double.parseDouble(req.getParameter("lessThan"));

        List<MovieDTO> movies = new ArrayList<>();

        if (movies != null) {
            writer.write(gson.toJson(movies));
        } else {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
