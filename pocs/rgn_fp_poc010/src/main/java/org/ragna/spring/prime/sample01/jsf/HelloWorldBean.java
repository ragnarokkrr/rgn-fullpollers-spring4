package org.ragna.spring.prime.sample01.jsf;

import org.ragna.spring.prime.sample01.beans.UserBO;
import org.springframework.context.annotation.Scope;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by ragnarokkrr on 07/04/15.
 */
@Named
//@ManagedBean
@Scope("session") //need this, JSR-330 in Spring context is singleton by default
@ViewScoped
public class HelloWorldBean {

    @Inject
    private UserBO userBO; //need by JSR-330

    public String getHello() {
        return "Hello from PrimeFaces and Spring Boot";
    }

    public String getHello2() {
        return userBO.getMessage();
    }

}
