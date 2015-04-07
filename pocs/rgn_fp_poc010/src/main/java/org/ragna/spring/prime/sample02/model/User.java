package org.ragna.spring.prime.sample02.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by ragnarokkrr on 08/04/15.
 */
@Named
@SessionScoped
public class User implements Serializable {
    Logger logger = LoggerFactory.getLogger(User.class);

    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        logger.info("'{}' is logged!!!", name);
        return "welcome";
    }
}
