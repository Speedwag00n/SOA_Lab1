package ilia.nemankov.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ilia.nemankov.dto.PersonDTO;
import ilia.nemankov.service.PersonService;
import ilia.nemankov.service.PersonServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/person/*")
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

        Long id = (Long) req.getAttribute("id");

        if (id == null) {
            List<PersonDTO> persons = null;

            persons = personService.findAll();

            if (persons != null) {
                writer.write(gson.toJson(persons));
            } else {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            PersonDTO person = personService.findById(id);

            if (person != null) {
                writer.write(gson.toJson(person));
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

        PersonDTO person = (PersonDTO) req.getAttribute("person");

        try {
            PersonDTO savedValue = personService.save(person);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            writer.write(gson.toJson(savedValue));
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
            personService.delete(id);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.write(gson.toJson(e.getMessage()));
        }
    }
}
