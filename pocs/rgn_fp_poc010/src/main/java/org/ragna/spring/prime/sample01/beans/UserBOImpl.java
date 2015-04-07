package org.ragna.spring.prime.sample01.beans;

import javax.inject.Named;

/**
 * Created by ragnarokkrr on 07/04/15.
 */
@Named
public class UserBOImpl implements UserBO{
    @Override
    public String getMessage() {
        return "JSF 2 + Spring Integration";
    }
}
