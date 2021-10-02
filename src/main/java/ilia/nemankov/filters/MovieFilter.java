package ilia.nemankov.filters;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import ilia.nemankov.dto.CoordinatesDTO;
import ilia.nemankov.dto.MovieDTO;
import ilia.nemankov.dto.PersonDTO;
import ilia.nemankov.entity.MPAARating;
import ilia.nemankov.utils.Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;


@WebFilter("/api/movie/*")
public class MovieFilter implements Filter {
    private JsonParser jsonParser;

    public static final int INVALID_JSON = 1;

    public static final int MISSING_ID = 100;
    public static final int WRONG_ID_FORMAT = 101;

    public static final int MISSING_NAME = 110;
    public static final int INVALID_NAME_VALUE = 111;

    public static final int MISSING_COORDINATES = 120;
    public static final int WRONG_COORDINATES_FORMAT = 121;
    public static final int MISSING_COORDINATES_ENTITY = 122;

    public static final int WRONG_CREATION_DATE_FORMAT = 131;

    public static final int MISSING_OSCARS_COUNT = 140;
    public static final int WRONG_OSCARS_COUNT_FORMAT = 141;
    public static final int INVALID_OSCARS_COUNT_VALUE = 142;

    public static final int MISSING_GOLDEN_PALM_COUNT = 150;
    public static final int WRONG_GOLDEN_PALM_COUNT_FORMAT = 151;
    public static final int INVALID_GOLDEN_PALM_COUNT_VALUE = 152;

    public static final int WRONG_TOTAL_BOX_OFFICE_FORMAT = 161;
    public static final int INVALID_TOTAL_BOX_OFFICE_VALUE = 162;

    public static final int WRONG_MPAA_RATING_FORMAT = 171;

    public static final int WRONG_SCREEN_WRITER_FORMAT = 181;
    public static final int MISSING_SCREEN_WRITER_ENTITY = 182;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.jsonParser = new JsonParser();
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if (req.getMethod().equalsIgnoreCase("delete") && req.getPathInfo() == null) {
            Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, MISSING_ID, "Id must be specified in the end of request");
            return;
        }

        if (req.getMethod().equalsIgnoreCase("post") || req.getMethod().equalsIgnoreCase("put")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            JsonObject json;

            try {
                json = (JsonObject)jsonParser.parse(servletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
            } catch (JsonSyntaxException e) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, INVALID_JSON, "Invalid JSON in request body");
                return;
            }

            MovieDTO dto = new MovieDTO();

            if (json.get("id") != null) {
                try {
                    dto.setId(json.get("id").getAsLong());
                } catch (NumberFormatException e) {
                    Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, WRONG_ID_FORMAT, "Field 'id' must be integer");
                    return;
                }
            }

            if (json.get("name") == null) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, MISSING_NAME, "Field 'name' must be specified in request body");
                return;
            }

            if (json.get("coordinates") == null) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, MISSING_COORDINATES, "Field 'coordinates' must be specified in request body");
                return;
            }

            if (json.get("oscarsCount") == null) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, MISSING_OSCARS_COUNT, "Field 'oscarsCount' must be specified in request body");
                return;
            }

            if (json.get("goldenPalmCount") == null) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, MISSING_GOLDEN_PALM_COUNT, "Field 'goldenPalmCount' must be specified in request body");
                return;
            }

            if (req.getMethod().equalsIgnoreCase("post")) {
                dto.setCreationDate(new Date());
            }

            if (req.getMethod().equalsIgnoreCase("put")) {
                if (req.getMethod().equalsIgnoreCase("put") && json.get("id") == null) {
                    Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, MISSING_ID, "Field 'id' must be specified in request body");
                    return;
                }

                try {
                    dto.setCreationDate(formatter.parse(json.get("creationDate").getAsString()));
                } catch (ParseException e) {
                    Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, WRONG_CREATION_DATE_FORMAT, "Field 'creationDate' must have format yyyy-MM-dd");
                    return;
                }
            }

            dto.setName(json.get("name").getAsString());

            if (!(dto.getName().length() > 0)) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, INVALID_NAME_VALUE, "Length of field 'name' must be bigger than 0");
                return;
            }

            CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
            dto.setCoordinates(coordinatesDTO);

            try {
                coordinatesDTO.setId(json.get("coordinates").getAsLong());
            } catch (NumberFormatException e) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, WRONG_COORDINATES_FORMAT, "Field 'coordinates' must be integer");
                return;
            }

            try {
                dto.setOscarsCount(json.get("oscarsCount").getAsInt());

                if (!(dto.getOscarsCount() > 0)) {
                    Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, INVALID_OSCARS_COUNT_VALUE, "Field 'oscarsCount' must be bigger than 0");
                    return;
                }
            } catch (NumberFormatException e) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, WRONG_OSCARS_COUNT_FORMAT, "Field 'oscarsCount' must be integer");
                return;
            }

            try {
                dto.setGoldenPalmCount(json.get("goldenPalmCount").getAsLong());

                if (!(dto.getGoldenPalmCount() > 0)) {
                    Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, INVALID_GOLDEN_PALM_COUNT_VALUE, "Field 'goldenPalmCount' must be bigger than 0");
                    return;
                }
            } catch (NumberFormatException e) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, WRONG_GOLDEN_PALM_COUNT_FORMAT, "Field 'goldenPalmCount' must be integer");
                return;
            }

            if (json.get("totalBoxOffice") != null) {
                try {
                    dto.setTotalBoxOffice(json.get("totalBoxOffice").getAsDouble());

                    if (!(dto.getTotalBoxOffice() > 0)) {
                        Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, INVALID_TOTAL_BOX_OFFICE_VALUE, "Field 'totalBoxOffice' must be bigger than 0");
                        return;
                    }
                } catch (NumberFormatException e) {
                    Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, WRONG_TOTAL_BOX_OFFICE_FORMAT, "Field 'totalBoxOffice' must be floating point number");
                    return;
                }
            }

            if (json.get("mpaaRating") != null) {
                try {
                    dto.setMpaaRating(MPAARating.valueOf(json.get("mpaaRating").getAsString()));
                } catch (IllegalArgumentException e) {
                    Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, WRONG_MPAA_RATING_FORMAT, "Field 'mpaaRating' must be one of expected value. Check documentation for details");
                    return;
                }
            }

            PersonDTO personDTO = new PersonDTO();

            if (json.get("screenWriter") != null) {
                try {
                    personDTO.setId(json.get("screenWriter").getAsLong());
                    dto.setScreenWriter(personDTO);
                } catch (NumberFormatException e) {
                    Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, WRONG_SCREEN_WRITER_FORMAT, "Field 'screenWriter' must be integer");
                    return;
                }
            }

            req.setAttribute("movie", dto);
        }

        if (req.getMethod().equalsIgnoreCase("get") || req.getMethod().equalsIgnoreCase("delete")) {
            if (req.getPathInfo() != null) {
                try {
                    Long id = Long.valueOf(req.getPathInfo().replaceAll("^/", ""));
                    req.setAttribute("id", id);
                } catch (NumberFormatException e) {
                    Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, WRONG_ID_FORMAT, "Field 'id' must be integer");
                    return;
                }
            }
        }

        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}