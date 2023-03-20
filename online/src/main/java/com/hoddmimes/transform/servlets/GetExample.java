package com.hoddmimes.transform.servlets;




import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


@Path("/example")
public class GetExample extends HttpServlet {
    private static String cXmlExampleFile;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        cXmlExampleFile = servletConfig.getInitParameter("xmlExample");
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getExample(@Context HttpServletRequest servletRequest) throws ServletException {
        try {
            File tFile = new File(cXmlExampleFile);
            Scanner tScanner = new Scanner(tFile);
            JsonArray jLines = new JsonArray();

            while(tScanner.hasNext()) {
                jLines.add( tScanner.nextLine());
            }
            JsonObject tResponse = new JsonObject();
            tResponse.add("xml", jLines);
            return tResponse.toString();
        }
        catch( IOException e) {
            e.printStackTrace();
            throw new ServletException( e );
        }
    }
}
