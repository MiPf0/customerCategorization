package org.vaillant;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode
public class DTOInstaller {

    private final String kdnr;
    private final String name;
    private final String vad;
    private final String kundenGruppe;
    private final String channel;
    private final String zielgruppe;
    private final List<DTOAbsatz> absaetze;
    private final List<DTOPotential> potentials;
    String programme;

    public DTOInstaller(String kdnr, String name, String vad, String kundenGruppe, String channel, String zielgruppe, String programme) {
        this.kdnr = kdnr;
        this.name = name;
        this.vad = vad;
        this.kundenGruppe = kundenGruppe;
        this.channel = channel;
        this.zielgruppe = zielgruppe;
        this.programme = programme;
        absaetze = null;
        potentials = null;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }


    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Installer)) {
            return false;
        }
        return kdnr.equals(((Installer) other).kdnr);
    }

    @Override
    public int hashCode() {
        return kdnr.hashCode();
    }
}