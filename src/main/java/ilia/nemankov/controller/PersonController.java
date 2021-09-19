package ilia.nemankov.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ilia.nemankov.dto.MovieDTO;
import ilia.nemankov.dto.PersonDTO;
import ilia.nemankov.service.MovieService;
import ilia.nemankov.service.MovieServiceImpl;
import ilia.nemankov.service.PersonService;
import ilia.nemankov.service.PersonServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api/person")
public class PersonController extends HttpServlet {

    private final GsonBuilder gsonBuilder;
    private final PersonService personService;

    public PersonController() {
        this.gsonBuilder = new GsonBuilder();
        this.personService = new PersonServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();
        Gson gson = gsonBuilder.create();

        List<PersonDTO> persons = personService.findAll();

        if (persons != null) {
            writer.write(gson.toJson(persons));
        } else {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
