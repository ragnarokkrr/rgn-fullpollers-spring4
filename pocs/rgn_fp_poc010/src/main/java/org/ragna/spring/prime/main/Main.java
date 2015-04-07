package org.ragna.spring.prime.main;

import com.sun.faces.config.ConfigureListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.faces.webapp.FacesServlet;

/**
 * Created by ragnarokkrr on 07/04/15.
 */
@Configuration
@ComponentScan(basePackages = {"org.ragna.spring.prime"})
@EnableAutoConfiguration
public class Main extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Main.class, Initializer.class);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        FacesServlet servlet = new FacesServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(servlet);
        registrationBean.addUrlMappings("*.xhtml");
        return registrationBean;
    }


/*
    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean() {
        ConfigureListener configureListener = new ConfigureListener();

        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean(configureListener);


        return servletListenerRegistrationBean;
    }
*/

}
