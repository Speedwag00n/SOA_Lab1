package ilia.nemankov.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Utils {

    public static void writeError(HttpServletResponse resp, int returnCode, Error error) throws IOException {
        Gson gson = (new GsonBuilder()).create();

        resp.setContentType("application/json");
        resp.setStatus(returnCode);
        resp.getWriter().write(gson.toJson(error));
    }

    public static void writeError(HttpServletResponse resp, int returnCode, int errorId, String errorMessage) throws IOException {
        writeError(resp, returnCode, new Error(errorId, errorMessage));
    }

}
