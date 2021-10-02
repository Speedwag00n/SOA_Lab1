package ilia.nemankov.filters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ilia.nemankov.dto.CoordinatesDTO;
import ilia.nemankov.utils.Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;


@WebFilter("/api/coordinates/*")
public class CoordinatesFilter implements Filter {
    JsonParser jsonParser;

    public static final int MISSING_ID = 100;
    public static final int WRONG_ID_FORMAT = 101;

    public static final int MISSING_X = 110;
    public static final int WRONG_X_FORMAT = 111;
    public static final int INVALID_X_VALUE = 112;

    public static final int MISSING_Y = 120;
    public static final int WRONG_Y_FORMAT = 121;
    public static final int INVALID_Y_VALUE = 122;

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
            CoordinatesDTO dto = new CoordinatesDTO();

            if (json.get("x") == null) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, MISSING_X, "Field 'x' must be specified in request body");
                return;
            }

            if (json.get("y") == null) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, MISSING_Y, "Field 'y' must be specified in request body");
                return;
            }

            try {
                dto.setX(json.get("x").getAsDouble());

                if (!(dto.getX() > -593)) {
                    Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, INVALID_X_VALUE, "Field 'x' must be bigger than -593");
                    return;
                }
            } catch (NumberFormatException e) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, WRONG_X_FORMAT, "Field 'x' must be floating point number");
                return;
            }

            try {
                dto.setY(json.get("y").getAsLong());

                if (!(dto.getY() > -148)) {
                    Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, INVALID_Y_VALUE, "Field 'y' must be bigger than -148");
                    return;
                }
            } catch (NumberFormatException e) {
                Utils.writeError(resp, HttpServletResponse.SC_BAD_REQUEST, WRONG_Y_FORMAT, "Field 'y' must be integer");
                return;
            }

            req.setAttribute("coordinates", dto);
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