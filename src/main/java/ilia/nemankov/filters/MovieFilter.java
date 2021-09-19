package ilia.nemankov.filters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ilia.nemankov.dto.CoordinatesDTO;
import ilia.nemankov.dto.MovieDTO;
import ilia.nemankov.dto.PersonDTO;
import ilia.nemankov.entity.MPAARating;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.stream.Collectors;


@WebFilter("/api/movie")
public class MovieFilter implements Filter {
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

            if (req.getMethod().equalsIgnoreCase("delete") && !req.getParameterMap().containsKey("id")) {
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson("Id must be specified"));
                return;
            }

            if (req.getMethod().equalsIgnoreCase("post") || req.getMethod().equalsIgnoreCase("put")) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                JsonObject json = (JsonObject)jsonParser.parse(servletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
                MovieDTO dto = new MovieDTO();

                if (json.get("id") != null) {
                    dto.setId(json.get("id").getAsLong());
                }
                dto.setName(json.get("name").getAsString());
                CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
                coordinatesDTO.setId(json.get("coordinates").getAsLong());
                dto.setCoordinates(coordinatesDTO);
                dto.setCreationDate(formatter.parse(json.get("creationDate").getAsString()));
                dto.setOscarsCount(json.get("oscarsCount").getAsInt());
                dto.setGoldenPalmCount(json.get("goldenPalmCount").getAsLong());
                dto.setTotalBoxOffice(json.get("totalBoxOffice").getAsDouble());
                dto.setMpaaRating(MPAARating.valueOf(json.get("mpaaRating").getAsString()));
                PersonDTO personDTO = new PersonDTO();
                personDTO.setId(json.get("screenWriter").getAsLong());
                dto.setScreenWriter(personDTO);

                req.setAttribute("movie", dto);
            }

            if (req.getMethod().equalsIgnoreCase("get") || req.getMethod().equalsIgnoreCase("delete")) {
                if (req.getParameter("id") != null) {
                    Long id = Long.valueOf(req.getParameter("id"));
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