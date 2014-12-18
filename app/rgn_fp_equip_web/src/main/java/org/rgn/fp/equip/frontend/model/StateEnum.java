package org.rgn.fp.equip.frontend.model;

/**
 * StateEnum.
 *
 * @author ragnarokkrr
 */
public enum StateEnum {
    SP("Sao Paulo"),
    RS("Rio Grande do Sul"),
    RJ("Rio de Janeiro");

    StateEnum(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

}
