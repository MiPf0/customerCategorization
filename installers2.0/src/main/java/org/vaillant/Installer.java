package org.vaillant;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class Installer {

    String kdnr;
    String name;
    String vad;
    String kundenGruppe;
    String channel;
    String zielgruppe;
    List<DTOAbsatz> absaetze;
    List<DTOPotential> potentials;
    List<DTOibn> ibns;
    List<DTOVisit> visits;

    int relevantHPAbsatzThisYear;
    int relevantWHBAbsatzThisYear;
    int relevantHPAbsatzLastYear;
    int relevantWHBAbsatzLastYear;
    int relevantHPAbsatzLastLastYear;
    int relevantWHBAbsatzLastLastYear;
    int relevantHPAbsatzLastLastLastYear;
    int relevantWHBAbsatzLastLastLastYear;
    int relevantHPAbsatzYtdThisYear;
    int relevantWHBAbsatzYtdThisYear;
    int relevantHPAbsatzYtdLastYear;
    int relevantWHBAbsatzYtdLastYear;
    int relevantHPAbsatzYtdLastLastYear;
    int relevantWHBAbsatzYtdLastLastYear;

    int whbPotentialVai;
    int whbPotentialCompetitors;
    int hpPotentialVai;
    int hpPotentialCompetitors;
    int customerSize;
    double shareOfWallet;

    int relevantWHBIbnsThisYear;
    int relevantWHBIbnsLastYear;
    int relevantWHBIbnsYtdThisYear;
    int relevantWHBIbnsYtdLastYear;
    int relevantHPIbnsThisYear;
    int relevantHPIbnsLastYear;
    int relevantHPIbnsYtdThisYear;
    int relevantHPIbnsYtdLastYear;

    int relevantVisitsThisYear;
    int relevantVisitsLastYear;
    int relevantVisitsLastLastYear;
    int relevantVisitsLastLastLastYear;
    int relevantVisitsYtdThisYear;
    int relevantVisitsYtdLastYear;
    int relevantVisitsYtdLastLastYear;
    int relevantVisitsYtdLastLastLastYear;

    boolean stableAY;
    boolean stableLY;
    boolean stableLLY;
    boolean churnAY;
    boolean churnLY;
    boolean churnLLY;
    boolean newAY;
    boolean newLY;
    boolean newLLY;
    boolean newConvertedAY;
    boolean newConvertedLY;
    boolean newConvertedLLY;
    boolean inactiveAY;
    boolean inactiveLY;
    boolean inactiveLLY;
    boolean prospectAY;
    boolean prospectLY;
    boolean prospectLLY;
    String roleAY;
    String roleLY;
    String roleLLY;

    boolean reactivatedChurnAY;
    boolean reactivatedChurnLY;
    boolean reactivatedInactiveAY;
    boolean reactivatedInactiveLY;
    String reactivationAY;
    String reactivationLY;

    String programme;
    String fishprogram2021;
    String fishprogram2020;
    String fishprogram2019;
    String fishprogram2018;

    public Installer(String kdnr, String name, String vad, String kundenGruppe,String channel, String zielgruppe, String programme) {
        this.kdnr = kdnr;
        this.name = name;
        this.vad = vad;
        this.kundenGruppe = kundenGruppe;
        this.channel = channel;
        this.zielgruppe = zielgruppe;
        this.programme = programme;
        this.absaetze = null;
        this.potentials = null;
    }

    @Override
    public String toString() {
        return "Installer{" +
                "kdnr='" + kdnr + '\'' +
                ", name='" + name + '\'' +
                ", vad='" + vad + '\'' +
                ", kundenGruppe='" + kundenGruppe + '\'' +
                ", relevantHPAbsatzThisYear=" + relevantHPAbsatzThisYear +
                ", relevantWHBAbsatzThisYear=" + relevantWHBAbsatzThisYear +
                ", relevantHPAbsatzLastYear=" + relevantHPAbsatzLastYear +
                ", relevantWHBAbsatzLastYear=" + relevantWHBAbsatzLastYear +
                ", relevantHPAbsatzLastLastYear=" + relevantHPAbsatzLastLastYear +
                ", relevantWHBAbsatzLastLastYear=" + relevantWHBAbsatzLastLastYear +
                ", relevantHPAbsatzYtdThisYear=" + relevantHPAbsatzYtdThisYear +
                ", relevantWHBAbsatzYtdThisYear=" + relevantWHBAbsatzYtdThisYear +
                ", relevantHPAbsatzYtdLastYear=" + relevantHPAbsatzYtdLastYear +
                ", relevantWHBAbsatzYtdLastYear=" + relevantWHBAbsatzYtdLastYear +
                ", stableAY=" + stableAY +
                ", stableLY=" + stableLY +
                ", churnAY=" + churnAY +
                ", churnLY=" + churnLY +
                ", newAY=" + newAY +
                ", newLY=" + newLY +
                ", newConvertedAY=" + newConvertedAY +
                ", newConvertedLY=" + newConvertedLY +
                ", inactiveAY=" + inactiveAY +
                ", inactiveLY=" + inactiveLY +
                ", programme='" + programme + '\'' +
                ", fishprogram2021='" + fishprogram2021 + '\'' +
                '}';
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