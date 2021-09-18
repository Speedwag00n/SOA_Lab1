package ilia.nemankov.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ilia.nemankov.service.MovieService;
import ilia.nemankov.service.MovieServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/avrg")
public class AvrgGoldenPalmCountController extends HttpServlet {
    private final MovieService movieService;
    private final GsonBuilder gsonBuilder;

    public AvrgGoldenPalmCountController() {
        this.movieService = new MovieServiceImpl();
        this.gsonBuilder = new GsonBuilder();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();
        Gson gson = gsonBuilder.create();

        writer.write(gson.toJson(movieService.getAvrgGoldenPalmCount()));
    }
}
