package ilia.nemankov.filters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ilia.nemankov.dto.PersonDTO;
import ilia.nemankov.entity.Country;
import ilia.nemankov.entity.MPAARating;
import ilia.nemankov.utils.Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;


@WebFilter("/api/person/*")
public class PersonFilter implements Filter {
    JsonParser jsonParser;

    public static final int MISSING_ID = 100;
    public static final int WRONG_ID_FORMAT = 101;

    public static final int MISSING_NAME = 110;
    public static final int INVALID_NAME_VALUE = 111;

    public static final int MISSING_HEIGHT = 120;
    public static final int WRONG_HEIGHT_FORMAT = 121;
    public static final int INVALID_HEIGHT_VALUE = 122;

    public static final int MISSING_PASSPORT_ID = 130;
    public static final int INVALID_PASSPORT_ID_VALUE = 131;
    public static final int PASSPORT_ID_IN_USE = 132;

    public static final int MISSING_NATIONALITY = 140;
    public static final int WRONG_NATIONALITY_FORMAT = 141;

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

        if (req.getMethod().equalsIgnoreCase("post")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            JsonObject json = (JsonObject)jsonParser.parse(servletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
            PersonDTO dto = new PersonDTO();

            if (json.get("name") == null) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, MISSING_NAME, "Field 'name' must be specified in request body");
                return;
            }

            if (json.get("height") == null) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, MISSING_HEIGHT, "Field 'height' must be specified in request body");
                return;
            }

            if (json.get("passportId") == null) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, MISSING_PASSPORT_ID, "Field 'passportId' must be specified in request body");
                return;
            }

            if (json.get("nationality") == null) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, MISSING_NATIONALITY, "Field 'nationality' must be specified in request body");
                return;
            }

            dto.setName(json.get("name").getAsString());

            if (!(dto.getName().length() > 0)) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, INVALID_NAME_VALUE, "Length of field 'name' must be bigger than 0");
                return;
            }

            try {
                dto.setHeight(json.get("height").getAsLong());

                if (!(dto.getHeight() > 0)) {
                    Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, INVALID_HEIGHT_VALUE, "Field 'height' must be bigger than ");
                    return;
                }
            } catch (NumberFormatException e) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, WRONG_HEIGHT_FORMAT, "Field 'height' must be floating point number");
                return;
            }

            dto.setPassportId(json.get("passportId").getAsString());

            if (!(dto.getPassportId().length() < 32) || !(dto.getPassportId().length() > 0)) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, INVALID_PASSPORT_ID_VALUE, "Length of field 'passportId' must be bigger than 0 and less than 32");
                return;
            }

            try {
                dto.setNationality(Country.valueOf(json.get("nationality").getAsString()));
            } catch (IllegalArgumentException e) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, WRONG_NATIONALITY_FORMAT, "Field 'nationality' must be one of expected value. Check documentation for details");
                return;
            }

            req.setAttribute("person", dto);
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