package org.vaillant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class InstallersFileWriter {

    public static InstallersFileWriter instance;

    private InstallersFileWriter() {
        //
    }

    public static InstallersFileWriter getInstance() {
        if(instance == null) {
            instance = new InstallersFileWriter();
        }
        return instance;
    }

    public void writeToFile(List<Installer> installers) throws IOException {
        FileWriter csvFw = new FileWriter(new File("installers2.0/src/main/resources/output","hpInstallers.csv"),StandardCharsets.ISO_8859_1);
        csvFw.append("KDNr");
        csvFw.append(";");
        csvFw.append("Partner-Name");
        csvFw.append(";");
        csvFw.append("VAD");
        csvFw.append(";");
        csvFw.append("Channel");
        csvFw.append(";");
        csvFw.append("Zielgruppe");
        csvFw.append(";");

        csvFw.append("Role AY");
        csvFw.append(";");
        csvFw.append("Role LY");
        csvFw.append(";");
        csvFw.append("Role LLY");
        csvFw.append(";");

        csvFw.append("Reactivation AY");
        csvFw.append(";");
        csvFw.append("Reactivation LY");
        csvFw.append(";");

        csvFw.append("Fisch 2021");
        csvFw.append(";");
        csvFw.append("Fisch 2020");
        csvFw.append(";");
        csvFw.append("Fisch 2019");
        csvFw.append(";");
        csvFw.append("Fisch 2018");
        csvFw.append(";");
        csvFw.append("Besuche AY YTD");
        csvFw.append(";");
        csvFw.append("Besuche LY YTD");
        csvFw.append(";");
        csvFw.append("Besuche LLY YTD");
        csvFw.append(";");
        csvFw.append("Besuche LLLY YTD");
        csvFw.append(";");
        csvFw.append("Besuche AY roll.");
        csvFw.append(";");
        csvFw.append("Besuche LY roll.");
        csvFw.append(";");
        csvFw.append("Besuche LLY roll.");
        csvFw.append(";");
        csvFw.append("Besuche LLLY roll.");
        csvFw.append(";");
        csvFw.append("HP Absatz dir. AY YTD");
        csvFw.append(";");
        csvFw.append("HP Absatz dir. LY YTD");
        csvFw.append(";");
        csvFw.append("HP Absatz dir. LLY YTD");
        csvFw.append(";");
        csvFw.append("HP Absatz dir. AY roll.");
        csvFw.append(";");
        csvFw.append("HP Absatz dir. LY roll.");
        csvFw.append(";");
        csvFw.append("HP Absatz dir. LLY roll.");
        csvFw.append(";");
        csvFw.append("HP Absatz dir. LLLY roll.");
        csvFw.append(";");
        csvFw.append("WHB Absatz dir. YTD");
        csvFw.append(";");
        csvFw.append("WHB Absatz dir. AY roll.");
        csvFw.append(";");
        csvFw.append("WHB Absatz dir. LY roll.");
        csvFw.append(";");
        csvFw.append("WHB Absatz dir. LLY roll.");
        csvFw.append(";");
        csvFw.append("Partnergr��e");
        csvFw.append(";");
        csvFw.append("Marktanteil (SoW)");
        csvFw.append(";");
        csvFw.append("HP Pot. VAI");
        csvFw.append(";");
        csvFw.append("HP Pot. Rest");
        csvFw.append(";");
        csvFw.append("HP Pot. Gesamt");
        csvFw.append(";");
        csvFw.append("WHB Pot. VAI");
        csvFw.append(";");
        csvFw.append("WHB Pot. Rest");
        csvFw.append(";");
        csvFw.append("WHB Pot. Gesamt");
        csvFw.append(";");
        csvFw.append("HP IBNs AY roll.");
        csvFw.append(";");
        csvFw.append("HP IBNs LY roll.");
        csvFw.append(";");
        csvFw.append("HP IBNs YTD");
        csvFw.append(";");
        csvFw.append("HP IBNs YTD LY");
        csvFw.append(";");
        csvFw.append("WHB IBNs AY roll.");
        csvFw.append(";");
        csvFw.append("WHB IBNs LY roll.");
        csvFw.append(";");
        csvFw.append("WHB IBNs YTD");
        csvFw.append(";");
        csvFw.append("WHB IBNs YTD LY");
        csvFw.append("\n");

        for(Installer i : installers) {

            csvFw.append(i.getKdnr());
            csvFw.append(";");
            csvFw.append(i.getName());
            csvFw.append(";");
            csvFw.append(i.getVad());
            csvFw.append(";");
            csvFw.append(i.getChannel());
            csvFw.append(";");
            csvFw.append(i.getZielgruppe());
            csvFw.append(";");
            csvFw.append(i.getRoleAY());
            csvFw.append(";");
            csvFw.append(i.getRoleLY());
            csvFw.append(";");
            csvFw.append(i.getRoleLLY());
            csvFw.append(";");

            csvFw.append(i.getReactivationAY());
            csvFw.append(";");
            csvFw.append(i.getReactivationLY());
            csvFw.append(";");

            csvFw.append(i.getFishprogram2021());
            csvFw.append(";");
            csvFw.append(i.getFishprogram2020());
            csvFw.append(";");
            csvFw.append(i.getFishprogram2019());
            csvFw.append(";");
            csvFw.append(i.getFishprogram2018());
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantVisitsYtdThisYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantVisitsYtdLastYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantVisitsYtdLastLastYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantVisitsYtdLastLastLastYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantVisitsThisYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantVisitsLastYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantVisitsLastLastYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantVisitsLastLastLastYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantHPAbsatzYtdThisYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantHPAbsatzYtdLastYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantHPAbsatzYtdLastLastYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantHPAbsatzThisYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantHPAbsatzLastYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantHPAbsatzLastLastYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantHPAbsatzLastLastLastYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantWHBAbsatzYtdThisYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantWHBAbsatzThisYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantWHBAbsatzLastYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantWHBAbsatzLastLastYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getCustomerSize()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getShareOfWallet()).replace('.',','));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getHpPotentialVai()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getHpPotentialCompetitors()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getHpPotentialVai()+i.getHpPotentialCompetitors()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getWhbPotentialVai()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getWhbPotentialCompetitors()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getWhbPotentialVai()+i.getWhbPotentialCompetitors()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantHPIbnsThisYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantHPIbnsLastYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantHPIbnsYtdThisYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantHPIbnsYtdLastYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantWHBIbnsThisYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantWHBIbnsLastYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantWHBIbnsYtdThisYear()));
            csvFw.append(";");
            csvFw.append(String.valueOf(i.getRelevantWHBIbnsYtdLastYear()));
            csvFw.append("\n");
        }

        csvFw.flush();
        csvFw.close();
    }

    public void writeSummaryFile(Summary summary) throws IOException {
        FileWriter csvFw = new FileWriter(new File("installers2.0/src/main/resources/output", "hpSummary.csv"), StandardCharsets.ISO_8859_1);
        csvFw.append("timeframe");
        csvFw.append("\n");
        csvFw.append("Rolling 12m from");
        csvFw.append(";");
        csvFw.append("from");
        csvFw.append(";");
        csvFw.append(summary.getAbWannRoll());
        csvFw.append(";");
        csvFw.append("to");
        csvFw.append(";");
        csvFw.append(summary.getBisWannRoll());
        csvFw.append("\n");
        csvFw.append("YTD");
        csvFw.append(";");
        csvFw.append("from");
        csvFw.append(";");
        csvFw.append(summary.getAbWannYtd());
        csvFw.append(";");
        csvFw.append("to");
        csvFw.append(";");
        csvFw.append(summary.getBisWannYtd());
        csvFw.append("\n");
        csvFw.append("\n");

        csvFw.append("number of installers & units (without Grosshandel)");
        csvFw.append("\n");

        csvFw.append("category");
        csvFw.append(";");
        csvFw.append("installers (12mr)");
        csvFw.append(";");
        csvFw.append("units AY 12mr");
        csvFw.append(";");
        csvFw.append("units LY 12mr");
        csvFw.append(";");
        csvFw.append("units LLY 12mr");
        csvFw.append(";");
        csvFw.append("units AY ytd");
        csvFw.append(";");
        csvFw.append("units LY ytd");
        csvFw.append(";");
        csvFw.append("units LLY ytd");
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Stable AY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersAY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersAYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersAYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersAYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersAYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersAYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersAYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Stable LY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Stable LLY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLLY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLLYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLLYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLLYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLLYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLLYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLLYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("New AY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersAY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersAYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersAYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersAYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersAYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersAYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersAYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("New LY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersLY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersLYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersLYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersLYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersLYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersLYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersLYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("New LLY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersLLY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersLLYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersLLYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersLLYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersLLYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersLLYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNewInstallersLLYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Converted AY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersAY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersAYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersAYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersAYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersAYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersAYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersAYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Converted LY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersLY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersLYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersLYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersLYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersLYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersLYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersLYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Converted LLY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersLLY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersLLYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersLLYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersLLYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersLLYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersLLYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getConvertedInstallersLLYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Active AY (Stable+New+Converted)");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersAY()+summary.getNewInstallersAY()+summary.getConvertedInstallersAY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersAYUnitsAY12mr()+summary.getNewInstallersAYUnitsAY12mr()+summary.getConvertedInstallersAYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersAYUnitsLY12mr()+summary.getNewInstallersAYUnitsLY12mr()+summary.getConvertedInstallersAYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersAYUnitsLLY12mr()+summary.getNewInstallersAYUnitsLLY12mr()+summary.getConvertedInstallersAYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersAYUnitsAYytd()+summary.getNewInstallersAYUnitsAYytd()+summary.getConvertedInstallersAYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersAYUnitsLYytd()+summary.getNewInstallersAYUnitsLYytd()+summary.getConvertedInstallersAYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersAYUnitsLLYytd()+summary.getNewInstallersAYUnitsLLYytd()+summary.getConvertedInstallersAYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Active LY (Stable+New+Converted)");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLY()+summary.getNewInstallersLY()+summary.getConvertedInstallersLY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLYUnitsAY12mr()+summary.getNewInstallersLYUnitsAY12mr()+summary.getConvertedInstallersLYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLYUnitsLY12mr()+summary.getNewInstallersLYUnitsLY12mr()+summary.getConvertedInstallersLYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLYUnitsLLY12mr()+summary.getNewInstallersLYUnitsLLY12mr()+summary.getConvertedInstallersLYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLYUnitsAYytd()+summary.getNewInstallersLYUnitsAYytd()+summary.getConvertedInstallersLYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLYUnitsLYytd()+summary.getNewInstallersLYUnitsLYytd()+summary.getConvertedInstallersLYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getStableInstallersLYUnitsLLYytd()+summary.getNewInstallersLYUnitsLLYytd()+summary.getConvertedInstallersLYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Churn AY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersAY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersAYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersAYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersAYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersAYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersAYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersAYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Churn LY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersLY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersLYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersLYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersLYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersLYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersLYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersLYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Churn LLY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersLLY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersLLYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersLLYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersLLYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersLLYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersLLYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChurnInstallersLLYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Inactive AY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersAY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersAYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersAYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersAYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersAYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersAYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersAYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Inactive LY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersLY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersLYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersLYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersLYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersLYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersLYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersLYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Inactive LLY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersLLY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersLLYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersLLYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersLLYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersLLYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersLLYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getInactiveInstallersLLYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Prospect AY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersAY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersAYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersAYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersAYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersAYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersAYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersAYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Prospect LY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersLY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersLYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersLYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersLYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersLYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersLYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersLYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Prospect LLY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersLLY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersLLYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersLLYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersLLYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersLLYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersLLYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getProspectInstallersLLYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Reactivated Churn AY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersAY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersAYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersAYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersAYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersAYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersAYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersAYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Reactivated Churn LY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersLY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersLYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersLYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersLYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersLYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersLYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersLYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Reactivated Inactive AY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedInactiveInstallersAY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedInactiveInstallersAYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedInactiveInstallersAYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedInactiveInstallersAYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedInactiveInstallersAYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedInactiveInstallersAYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedInactiveInstallersAYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Reactivated Inactive LY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedInactiveInstallersLY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedInactiveInstallersLYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedInactiveInstallersLYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedInactiveInstallersLYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedInactiveInstallersLYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedInactiveInstallersLYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedInactiveInstallersLYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Reactivated Churn+Inactive AY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersAY()+summary.getReactivatedInactiveInstallersAY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersAYUnitsAY12mr()+summary.getReactivatedInactiveInstallersAYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersAYUnitsLY12mr()+summary.getReactivatedInactiveInstallersAYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersAYUnitsLLY12mr()+summary.getReactivatedInactiveInstallersAYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersAYUnitsAYytd()+summary.getReactivatedInactiveInstallersAYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersAYUnitsLYytd()+summary.getReactivatedInactiveInstallersAYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersAYUnitsLLYytd()+summary.getReactivatedInactiveInstallersAYUnitsLLYytd()));
        csvFw.append(";");
        csvFw.append("\n");

        csvFw.append("Reactivated Churn+Inactive LY");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersLY()+summary.getReactivatedInactiveInstallersLY()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersLYUnitsAY12mr()+summary.getReactivatedInactiveInstallersLYUnitsAY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersLYUnitsLY12mr()+summary.getReactivatedInactiveInstallersLYUnitsLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersLYUnitsLLY12mr()+summary.getReactivatedInactiveInstallersLYUnitsLLY12mr()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersLYUnitsAYytd()+summary.getReactivatedInactiveInstallersLYUnitsAYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersLYUnitsLYytd()+summary.getReactivatedInactiveInstallersLYUnitsLYytd()));
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getReactivatedChurnInstallersLYUnitsLLYytd()+summary.getReactivatedInactiveInstallersLYUnitsLLYytd()));
        csvFw.append(";");

        csvFw.append("\n");
        csvFw.append("\n");


        csvFw.append("Plus/Minus YTD Units of AY in comparison to LY (without Grosshandel) (\"Waterfall\")");
        csvFw.append("\n");
        csvFw.append("Churn+Inactives (AY YTD lost Units of Churn+Inactives AY12mr compared to LY YTD lost Units of Churn+Inactives LY12mr " +
                "VS. LY YTD lost Units of Churn+Inactives LY12mr compared to LLY YTD lost Units of Churn+Inactives LLY12mr): ");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getAufsatzChurnInactiveAYLYUnitsYtd()));
        csvFw.append("\n");
        csvFw.append("Reactivated Churn+Inactives (AY YTD Units of Reactivated Churn+Inactives AY12mr VS. LY YTD Units of Reactivated Churn+Inactives LY12mr): ");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getAufsatzReactivatedChurnInactiveAYLYUnitsYtd()));
        csvFw.append("\n");
        csvFw.append("New (AY YTD Units of New AY12mr VS. LY YTD Units of New LY12mr): ");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getAufsatzNewCustomerAYLYUnitsYtd()));
        csvFw.append("\n");
        csvFw.append("Converted (AY YTD Units of Converted AY12mr VS. LY YTD Units of Converted& LY12mr): ");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getAufsatzConvertedCustomerAYLYUnitsYtd()));
        csvFw.append("\n");
        csvFw.append("Stable (AY YTD Units of Stable AY12mr VS. LY YTD Units of Stable LY12mr): ");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getAufsatzStableCustomerAYLYUnitsYtd()));
        csvFw.append("\n");
        csvFw.append("New+Conv+Stables (AY YTD Units of New&Converted&Stable AY12mr VS. LY YTD Units of New&Converted&Stable LY12mr): ");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getAufsatzNewConvertedStableCustomerAYLYUnitsYtd()));

        csvFw.append("\n");
        csvFw.append("\n");

        csvFw.append("Numbers of installers per channel with sales YTD (without Grosshandel)");
        csvFw.append("\n");
        csvFw.append("Heating installers with sales YTD");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChannelHeatingInstallersYtd()));
        csvFw.append("\n");
        csvFw.append("Electro installers with sales YTD");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChannelElectroInstallersYtd()));
        csvFw.append("\n");
        csvFw.append("AirCon installers with sales YTD");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getChannelAirConInstallersYtd()));
        csvFw.append("\n");
        csvFw.append("installers with sales YTD");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNumberInstallersWithSalesYtd()));

        csvFw.append("\n");
        csvFw.append("\n");

        csvFw.append("Numbers of installers per Zielgruppe with sales YTD (without Grosshandel)");
        csvFw.append("\n");
        csvFw.append("Fachhandwerker with sales YTD");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNumberZielgruppeFachhandwerkerWithSalesYtd()));
        csvFw.append("\n");
        csvFw.append("Grosshandel with sales YTD");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNumberZielgruppeGrosshandelWithSalesYtd()));
        csvFw.append("\n");
        csvFw.append("Projekt with sales YTD");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNumberZielgruppeProjektWithSalesYtd()));
        csvFw.append("\n");
        csvFw.append("Sonstiges with sales YTD");
        csvFw.append(";");
        csvFw.append(String.valueOf(summary.getNumberZielgruppeSonstigesWithSalesYtd()));


        csvFw.flush();
        csvFw.close();
    }

}