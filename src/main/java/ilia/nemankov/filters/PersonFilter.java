package ilia.nemankov.filters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ilia.nemankov.dto.PersonDTO;
import ilia.nemankov.entity.Country;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;


@WebFilter("/api/person/*")
public class PersonFilter implements Filter {
    private final GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson;
    JsonParser jsonParser;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.gson = gsonBuilder.create();
        this.jsonParser = new JsonParser();
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        try {

            if (req.getMethod().equalsIgnoreCase("delete") && req.getPathInfo() == null) {
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson("Id must be specified"));
                return;
            }

            if (req.getMethod().equalsIgnoreCase("post")) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                JsonObject json = (JsonObject)jsonParser.parse(servletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
                PersonDTO dto = new PersonDTO();

                dto.setName(json.get("name").getAsString());
                dto.setHeight(json.get("height").getAsLong());
                dto.setPassportId(json.get("passportId").getAsString());
                dto.setNationality(Country.valueOf(json.get("nationality").getAsString()));

                req.setAttribute("person", dto);
            }

            if (req.getMethod().equalsIgnoreCase("get") || req.getMethod().equalsIgnoreCase("delete")) {
                if (req.getPathInfo() != null) {
                    Long id = Long.valueOf(req.getPathInfo().replace("/", ""));
                    req.setAttribute("id", id);
                }
            }

            filterChain.doFilter(req, resp);

        } catch (Exception e) {
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson("Invalid data"));
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}