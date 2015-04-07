package org.ragna.spring.prime.main;

import org.springframework.boot.context.embedded.ServletContextInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by ragnarokkrr on 07/04/15.
 */
public class Initializer implements ServletContextInitializer{
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.err.println("-----------------------------");
        servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION","true");
        servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
    }
}
