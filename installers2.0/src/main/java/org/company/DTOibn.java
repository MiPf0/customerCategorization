package org.company;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class DTOibn {

    private final String kdnr;
    private final String produktGruppe;
    private final int zeitpunkt;

    public DTOibn(String kdnr, String produktGruppe, int zeitpunkt) {
        this.kdnr = kdnr;
        this.produktGruppe = produktGruppe;
        this.zeitpunkt = zeitpunkt;
    }
}
