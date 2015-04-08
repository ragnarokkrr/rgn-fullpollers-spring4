package org.ragna.spring.jfaces01;

import org.ragna.spring.jfaces01.bo.UserBo;

/**
 * Created by ragnarokkrr on 06/04/15.
 */
public class UserBean {
    UserBo userBo;

    public void setUserBo(UserBo userBo) {
        this.userBo = userBo;
    }

    public String printMsgFromSpring() {
        return userBo.getMessage();
    }
}
