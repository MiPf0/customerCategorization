package org.vaillant;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class DTOAbsatz {

    private final String kdnr;
    private final String produktGruppe;
    private final int absatz;
    private final int zeitpunkt;

    public DTOAbsatz(String kdnr, String produktGruppe, int absatz, int zeitpunkt) {
        this.kdnr = kdnr;
        this.produktGruppe = produktGruppe;
        this.absatz = absatz;
        this.zeitpunkt = zeitpunkt;
    }
}
