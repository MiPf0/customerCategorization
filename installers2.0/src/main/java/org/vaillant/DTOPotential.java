package org.vaillant;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class DTOPotential {

    private final String kdnr;
    private final String dateOfCreation;
    private final String dateLatestChange;
    private final String potentialAnalysisId;
    private final String brand;
    private final int whbAndFscu;
    private final int heatPump;

    public DTOPotential(String kdnr, String dateOfCreation, String dateLatestChange, String potentialAnalysisId, String brand, int whbAndFscu, int heatPump) {
        this.kdnr = kdnr;
        this.dateOfCreation = dateOfCreation;
        this.dateLatestChange = dateLatestChange;
        this.potentialAnalysisId = potentialAnalysisId;
        this.brand = brand;
        this.whbAndFscu = whbAndFscu;
        this.heatPump = heatPump;
    }
}
