package ilia.nemankov.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ilia.nemankov.dto.CoordinatesDTO;
import ilia.nemankov.service.CoordinatesService;
import ilia.nemankov.service.CoordinatesServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/coordinates/*")
public class CoordinatesController extends HttpServlet {

    private final GsonBuilder gsonBuilder;
    private final CoordinatesService coordinatesService;

    public CoordinatesController() {
        this.gsonBuilder = new GsonBuilder();
        this.coordinatesService = new CoordinatesServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();
        Gson gson = gsonBuilder.create();

        Long id = (Long) req.getAttribute("id");

        if (id == null) {
            List<CoordinatesDTO> coordinates = null;

            coordinates = coordinatesService.findAll();

            if (coordinates != null) {
                writer.write(gson.toJson(coordinates));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            CoordinatesDTO coordinate = coordinatesService.findById(id);

            if (coordinate != null) {
                writer.write(gson.toJson(coordinate));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();
        Gson gson = gsonBuilder.create();

        CoordinatesDTO coordinate = (CoordinatesDTO) req.getAttribute("coordinates");

        CoordinatesDTO savedValue = coordinatesService.save(coordinate);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        writer.write(gson.toJson(savedValue));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        Long id = (Long) req.getAttribute("id");
        coordinatesService.delete(id);
    }
}
