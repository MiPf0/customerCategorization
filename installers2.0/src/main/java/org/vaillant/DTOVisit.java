package org.vaillant;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class DTOVisit {

    private final String kdnr;
    private final int date;
    private final int zeitpunkt;

    public DTOVisit(String kdnr, int date) {
        this.kdnr = kdnr;
        this.date = date;
        this.zeitpunkt = (date/100);
    }
}
