package org.ragna.spring.prime.sample01.jsf.sample;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by ragnarokkrr on 07/04/15.
 */
@ManagedBean
@ViewScoped
public class SmallNumberBean {

    @Min(10)
    @Max(50)
    private int smallNumber = 42;

    public int getSmallNumber() {
        return smallNumber;
    }

    public void setSmallNumber(int smallNumber) {
        this.smallNumber = smallNumber;
    }

    public void showErrors() {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "The low priority message is displayed");
        FacesContext.getCurrentInstance().addMessage("smallNumberId", fm);
        FacesMessage fmError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "The error message is opressed , although it seems to be more important.");
        FacesContext.getCurrentInstance().addMessage("smallNumberId", fmError);
    }
}
