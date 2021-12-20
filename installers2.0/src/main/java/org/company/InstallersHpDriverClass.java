package org.company;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InstallersHpDriverClass {

    //DRIVER METHOD

    public static void main(String[] args) throws IOException {

        //neue Summary kreieren
        Summary summary = new Summary();

        //Zeitraum definieren
        int aktuellesMonat = 202111; //aktuelles Monat, das die aktuellesten Zahlen inkludiert
        int bisWannRoll = aktuellesMonat;
        int bisWannYtd = aktuellesMonat;
        int abWannYtd = Integer.parseInt((String.valueOf(aktuellesMonat).substring(0,4) + "01"));
        int abWannRoll = calculateAbWannRollierend(aktuellesMonat);

        //Zeitraum in Summary kopieren
        summary.setAbWannRoll(String.valueOf(abWannRoll));
        summary.setBisWannRoll(String.valueOf(bisWannRoll));
        summary.setAbWannYtd(String.valueOf(abWannYtd));
        summary.setBisWannYtd(String.valueOf(bisWannYtd));

        //Pfade zum Einlesen definieren
        Path installerPath = Paths.get("installers2.0/src/main/resources/input/customers.csv");
        Path absaetzePath = Paths.get("installers2.0/src/main/resources/input/absaetze.csv");
        Path fischePath = Paths.get("installers2.0/src/main/resources/input/fische.csv");
        Path potentialsPath = Paths.get("installers2.0/src/main/resources/input/potentials.csv");
        Path ibnPath = Paths.get("installers2.0/src/main/resources/input/ibn.csv");
        Path visitsPath = Paths.get("installers2.0/src/main/resources/input/visits.csv");

        //Einen FileReader und einen FileWriter instanzieren
        InstallersFileReader instanceFr = InstallersFileReader.getInstance();
        InstallersFileWriter instanceFw = InstallersFileWriter.getInstance();

        //DTOs aus Input-CSV-Listen erstellen und in Listen abspeichern
        List<DTOInstaller> installers = instanceFr.loadInstallers(installerPath,fischePath);
        List<DTOAbsatz> absaetze = instanceFr.loadAbsaetze(absaetzePath);
        List<DTOPotential> potentials = instanceFr.loadPotentials(potentialsPath);
        List<DTOibn> ibns = instanceFr.loadIbn(ibnPath);
        List<DTOVisit> visits = instanceFr.loadVisits(visitsPath);

        //Final installers Liste erstellen mit allen Zugehörigkeiten
        List<Installer> finIns = setBooleans(
                setVisits(
                        setInstallerIbns(
                                setPotentials(
                                        setFishType(
                                                setRelevanteAbsaetze(
                                                        createFhwInstallers(installers, absaetze)
                                                        , abWannRoll, bisWannRoll, abWannYtd, bisWannYtd))
                                        , potentials)
                                , ibns, abWannRoll, bisWannRoll, abWannYtd, bisWannYtd),
                        visits, abWannRoll, bisWannRoll, abWannYtd, bisWannYtd)
        );

        //Summen Installers und deren Units berechnen
        setSummaryValues(finIns,summary);

        //Bestimmte Ergebnisse in Files schreiben
        instanceFw.writeToFile(finIns);
        instanceFw.writeSummaryFile(summary);
    }


    //CALCULATE TIME STAMPS

    public static int calculateAbWannRollierend(int aktuellesMonat) {
        int abWannRoll;
        if (String.valueOf(aktuellesMonat).substring(4).equals("12")) {
            abWannRoll = Integer.parseInt((String.valueOf(aktuellesMonat).substring(0,4) + "01"));
        } else {
            abWannRoll = aktuellesMonat-100+1;
        }
        return abWannRoll;
    }



    //CREATE INSTALLERS AND SET THEIR PROPERTIES

    /**
     * legt aus den DTOs der 'customers.csv' Liste (durch FileReader erzeugt) Installer-Objekte,
     * fügt denen die Absätze der 'absaetze.csv' Datei hinzu und speichert diese in eine Liste
     * @param dtoInstallers Liste der DTO-Installers (keine Doppelten dürfen vorhanden sein)
     * @param dtoAbsaetze Liste der DTO-Absätze
     * @return Liste an Installer Objekten
     */
    public static List<Installer> createFhwInstallers(List<DTOInstaller> dtoInstallers, List<DTOAbsatz> dtoAbsaetze) {

        List<Installer> installers = new ArrayList<>();

        for (DTOInstaller i : dtoInstallers) {
            if(i.getKundenGruppe().equals("Fachhandwerker")) {
                    Installer installer = new Installer(i.getKdnr(), i.getName(), i.getVad(), i.getKundenGruppe(), i.getChannel(), i.getZielgruppe(), i.getProgramme());
                    installers.add(installer);
            }
        }
        for (Installer i : installers) {
            List<DTOAbsatz> absaetze = new ArrayList<>();
            for (DTOAbsatz a : dtoAbsaetze) {
                if (i.getKdnr().equals(a.getKdnr())) {
                    absaetze.add(a);
                }
            }
            i.setAbsaetze(absaetze);
        }
        return installers;
    }

    /**
     * fügt den Installers einer Liste die Absätze je nach Zeitraum den jeweiligen Variablen hinzu
     * @param installers i
     * @param abWannRoll date
     * @param bisWannRoll date
     * @param abWannYtd date
     * @param bisWannYtd date
     * @return Installer Liste mit berechneten Variablen je nach Zeitraum
     */
    public static List<Installer> setRelevanteAbsaetze(List<Installer> installers, int abWannRoll, int bisWannRoll, int abWannYtd, int bisWannYtd) {

        for (Installer i : installers) {

            int relevantHPAbsatzThisYear = 0;
            int relevantWHBAbsatzThisYear = 0;
            int relevantHPAbsatzLastYear = 0;
            int relevantWHBAbsatzLastYear = 0;
            int relevantHPAbsatzLastLastYear = 0;
            int relevantWHBAbsatzLastLastYear = 0;
            int relevantHPAbsatzLastLastLastYear = 0;
            int relevantWHBAbsatzLastLastLastYear = 0;
            int relevantHPAbsatzYtdThisYear = 0;
            int relevantWHBAbsatzYtdThisYear = 0;
            int relevantHPAbsatzYtdLastYear = 0;
            int relevantWHBAbsatzYtdLastYear = 0;
            int relevantHPAbsatzYtdLastLastYear = 0;
            int relevantWHBAbsatzYtdLastLastYear = 0;

            for (DTOAbsatz a : i.absaetze) {
                if (a.getZeitpunkt() >= abWannRoll && a.getZeitpunkt() <= bisWannRoll) {
                    if (a.getProduktGruppe().equals("Heat pumps")) {
                        relevantHPAbsatzThisYear += a.getAbsatz();
                    }
                    if (a.getProduktGruppe().equals("WHB condensing") || a.getProduktGruppe().equals("WHB non condensing") || a.getProduktGruppe().equals("WHB")) {
                        relevantWHBAbsatzThisYear += a.getAbsatz();
                    }
                }

                if (a.getZeitpunkt() >= (abWannRoll - 100) && a.getZeitpunkt() <= (bisWannRoll - 100)) {
                    if (a.getProduktGruppe().equals("Heat pumps")) {
                        relevantHPAbsatzLastYear += a.getAbsatz();
                    }
                    if (a.getProduktGruppe().equals("WHB condensing") || a.getProduktGruppe().equals("WHB non condensing") || a.getProduktGruppe().equals("WHB")) {
                        relevantWHBAbsatzLastYear += a.getAbsatz();
                    }
                }

                if (a.getZeitpunkt() >= (abWannRoll - 200) && a.getZeitpunkt() <= (bisWannRoll - 200)) {
                    if (a.getProduktGruppe().equals("Heat pumps")) {
                        relevantHPAbsatzLastLastYear += a.getAbsatz();
                    }
                    if (a.getProduktGruppe().equals("WHB condensing") || a.getProduktGruppe().equals("WHB non condensing") || a.getProduktGruppe().equals("WHB")) {
                        relevantWHBAbsatzLastLastYear += a.getAbsatz();
                    }
                }

                if (a.getZeitpunkt() >= (abWannRoll - 300) && a.getZeitpunkt() <= (bisWannRoll - 300)) {
                    if (a.getProduktGruppe().equals("Heat pumps")) {
                        relevantHPAbsatzLastLastLastYear += a.getAbsatz();
                    }
                    if (a.getProduktGruppe().equals("WHB condensing") || a.getProduktGruppe().equals("WHB non condensing") || a.getProduktGruppe().equals("WHB")) {
                        relevantWHBAbsatzLastLastLastYear += a.getAbsatz();
                    }
                }

                if (a.getZeitpunkt() >= abWannYtd && a.getZeitpunkt() <= bisWannYtd) {
                    if (a.getProduktGruppe().equals("Heat pumps")) {
                        relevantHPAbsatzYtdThisYear += a.getAbsatz();
                    }
                    if (a.getProduktGruppe().equals("WHB condensing") || a.getProduktGruppe().equals("WHB non condensing") || a.getProduktGruppe().equals("WHB")) {
                        relevantWHBAbsatzYtdThisYear += a.getAbsatz();
                    }
                }

                if (a.getZeitpunkt() >= (abWannYtd-100) && a.getZeitpunkt() <= (bisWannYtd-100)) {
                    if (a.getProduktGruppe().equals("Heat pumps")) {
                        relevantHPAbsatzYtdLastYear += a.getAbsatz();
                    }
                    if (a.getProduktGruppe().equals("WHB condensing") || a.getProduktGruppe().equals("WHB non condensing") || a.getProduktGruppe().equals("WHB")) {
                        relevantWHBAbsatzYtdLastYear += a.getAbsatz();
                    }
                }

                if (a.getZeitpunkt() >= (abWannYtd-200) && a.getZeitpunkt() <= (bisWannYtd-200)) {
                    if (a.getProduktGruppe().equals("Heat pumps")) {
                        relevantHPAbsatzYtdLastLastYear += a.getAbsatz();
                    }
                    if (a.getProduktGruppe().equals("WHB condensing") || a.getProduktGruppe().equals("WHB non condensing") || a.getProduktGruppe().equals("WHB")) {
                        relevantWHBAbsatzYtdLastLastYear += a.getAbsatz();
                    }
                }
            }

            i.setRelevantHPAbsatzThisYear(relevantHPAbsatzThisYear);
            i.setRelevantWHBAbsatzThisYear(relevantWHBAbsatzThisYear);
            i.setRelevantHPAbsatzLastYear(relevantHPAbsatzLastYear);
            i.setRelevantWHBAbsatzLastYear(relevantWHBAbsatzLastYear);
            i.setRelevantHPAbsatzLastLastYear(relevantHPAbsatzLastLastYear);
            i.setRelevantWHBAbsatzLastLastYear(relevantWHBAbsatzLastLastYear);
            i.setRelevantHPAbsatzLastLastLastYear(relevantHPAbsatzLastLastLastYear);
            i.setRelevantWHBAbsatzLastLastLastYear(relevantWHBAbsatzLastLastLastYear);
            i.setRelevantHPAbsatzYtdThisYear(relevantHPAbsatzYtdThisYear);
            i.setRelevantWHBAbsatzYtdThisYear(relevantWHBAbsatzYtdThisYear);
            i.setRelevantHPAbsatzYtdLastYear(relevantHPAbsatzYtdLastYear);
            i.setRelevantWHBAbsatzYtdLastYear(relevantWHBAbsatzYtdLastYear);
            i.setRelevantHPAbsatzYtdLastLastYear(relevantHPAbsatzYtdLastLastYear);
            i.setRelevantWHBAbsatzYtdLastLastYear(relevantWHBAbsatzYtdLastLastYear);
        }
        return installers;
    }

    /**
     * fügt Fisch-Zuordnung 2021 dem Installer hinzu
     * @param installers i
     * @return Installer Liste mit gesetzten Fisch-Zuordnungen 2021
     */
    public static List<Installer> setFishType(List<Installer> installers) {
        for (Installer i : installers) {

            i.setFishprogram2021("");
            if (i.getProgramme().contains("Gruengoldfisch 2021")) {
                i.setFishprogram2021("Gruengoldfisch 2021");
            }
            if (i.getProgramme().contains("Frischer Fisch 2021")) {
                i.setFishprogram2021("Frischer Fisch 2021");
            }
            if (i.getProgramme().contains("Gruener Fisch 2021")) {
                i.setFishprogram2021("Gruener Fisch 2021");
            }
            if (i.getProgramme().contains("Silberfisch 2021")) {
                i.setFishprogram2021("Silberfisch 2021");
            }
            if (i.getProgramme().contains("Goldfisch 2021")) {
                i.setFishprogram2021("Goldfisch 2021");
            }

            i.setFishprogram2020("");
            if (i.getProgramme().contains("Gruengoldfisch 2020")) {
                i.setFishprogram2020("Gruengoldfisch 2020");
            }
            if (i.getProgramme().contains("Frischer Fisch 2020")) {
                i.setFishprogram2020("Frischer Fisch 2020");
            }
            if (i.getProgramme().contains("Gruener Fisch 2020")) {
                i.setFishprogram2020("Gruener Fisch 2020");
            }
            if (i.getProgramme().contains("Silberfisch 2020")) {
                i.setFishprogram2020("Silberfisch 2020");
            }
            if (i.getProgramme().contains("Goldfisch 2020")) {
                i.setFishprogram2020("Goldfisch 2020");
            }

            i.setFishprogram2019("");
            if (i.getProgramme().contains("Gruengoldfisch 2019")) {
                i.setFishprogram2019("Gruengoldfisch 2019");
            }
            if (i.getProgramme().contains("Frischer Fisch 2019")) {
                i.setFishprogram2019("Frischer Fisch 2019");
            }
            if (i.getProgramme().contains("Gruener Fisch 2019")) {
                i.setFishprogram2019("Gruener Fisch 2019");
            }
            if (i.getProgramme().contains("Silberfisch 2019")) {
                i.setFishprogram2019("Silberfisch 2019");
            }
            if (i.getProgramme().contains("Goldfisch 2019")) {
                i.setFishprogram2019("Goldfisch 2019");
            }

            i.setFishprogram2018("");
            if (i.getProgramme().contains("Gruengoldfisch 2018")) {
                i.setFishprogram2018("Gruengoldfisch 2018");
            }
            if (i.getProgramme().contains("Frischer Fisch 2018")) {
                i.setFishprogram2018("Frischer Fisch 2018");
            }
            if (i.getProgramme().contains("Gruener Fisch 2018")) {
                i.setFishprogram2018("Gruener Fisch 2018");
            }
            if (i.getProgramme().contains("Silberfisch 2018")) {
                i.setFishprogram2018("Silberfisch 2018");
            }
            if (i.getProgramme().contains("Goldfisch 2018")) {
                i.setFishprogram2018("Goldfisch 2018");
            }
        }
        return installers;
    }

    /**
     * setzt Booleans für Customer-Gruppe (new, converted, churn, ...)
     * @param installers i
     * @return Installer Liste mit gesetzten Booleans für Customer-Gruppe
     */
    public static List<Installer> setBooleans(List<Installer> installers) {
        for (Installer i : installers) {

            //set current booleans for new calculation method
            int relHPAbsThisYear = i.getRelevantHPAbsatzThisYear();
            int relHPAbsLastYear = i.getRelevantHPAbsatzLastYear();
            int relHPAbsLastLastYear = i.getRelevantHPAbsatzLastLastYear();
            int relHPAbsLastLastLastYear = i.getRelevantHPAbsatzLastLastLastYear();
            boolean greaterFiftyAY = (float) relHPAbsThisYear / relHPAbsLastYear >= 0.5;
            boolean greaterFiftyLY = (float) relHPAbsLastYear / relHPAbsLastLastYear >= 0.5;
            boolean greaterFiftyLLY = (float) relHPAbsLastLastYear / relHPAbsLastLastLastYear >= 0.5;

            if (i.getRelevantHPAbsatzThisYear() >= 1 && i.getRelevantHPAbsatzLastYear() >= 1 && greaterFiftyAY) {
                i.setStableAY(true);
                i.setRoleAY("Stable");
            }

            if (i.getRelevantHPAbsatzLastYear() >= 1 && i.getRelevantHPAbsatzLastLastYear() >= 1 && greaterFiftyLY) {
                i.setStableLY(true);
                i.setRoleLY("Stable");
            }

            if (i.getRelevantHPAbsatzLastLastYear() >= 1 && i.getRelevantHPAbsatzLastLastLastYear() >= 1 && greaterFiftyLLY) {
                i.setStableLLY(true);
                i.setRoleLLY("Stable");
            }

            if (i.getRelevantHPAbsatzThisYear() >= 1 && i.getRelevantHPAbsatzLastYear() >= 1 && !greaterFiftyAY) {
                i.setChurnAY(true);
                i.setRoleAY("Churn");
            }

            if (i.getRelevantHPAbsatzLastYear() >= 1 && i.getRelevantHPAbsatzLastLastYear() >= 1 && !greaterFiftyLY) {
                i.setChurnLY(true);
                i.setRoleLY("Churn");
            }

            if (i.getRelevantHPAbsatzLastLastYear() >= 1 && i.getRelevantHPAbsatzLastLastLastYear() >= 1 && !greaterFiftyLLY) {
                i.setChurnLLY(true);
                i.setRoleLLY("Churn");
            }

            if (i.getRelevantHPAbsatzThisYear() >= 1 && i.getRelevantHPAbsatzLastYear() <= 0 && i.getRelevantWHBAbsatzLastYear() <= 0) {
                i.setNewAY(true);
                i.setRoleAY("New");
            }

            if (i.getRelevantHPAbsatzLastYear() >= 1 && i.getRelevantHPAbsatzLastLastYear() <= 0 && i.getRelevantWHBAbsatzLastLastYear() <= 0) {
                i.setNewLY(true);
                i.setRoleLY("New");
            }

            if (i.getRelevantHPAbsatzLastLastYear() >= 1 && i.getRelevantHPAbsatzLastLastLastYear() <= 0 && i.getRelevantWHBAbsatzLastLastLastYear() <= 0) {
                i.setNewLLY(true);
                i.setRoleLLY("New");
            }

            if (i.getRelevantHPAbsatzThisYear() >= 1 && i.getRelevantHPAbsatzLastYear() <= 0 && i.getRelevantWHBAbsatzLastYear() >= 1) {
                i.setNewConvertedAY(true);
                i.setRoleAY("Converted");
            }

            if (i.getRelevantHPAbsatzLastYear() >= 1 && i.getRelevantHPAbsatzLastLastYear() <= 0 && i.getRelevantWHBAbsatzLastLastYear() >= 1) {
                i.setNewConvertedLY(true);
                i.setRoleLY("Converted");
            }

            if (i.getRelevantHPAbsatzLastLastYear() >= 1 && i.getRelevantHPAbsatzLastLastLastYear() <= 0 && i.getRelevantWHBAbsatzLastLastLastYear() >= 1) {
                i.setNewConvertedLLY(true);
                i.setRoleLLY("Converted");
            }

            if (i.getRelevantHPAbsatzThisYear() <= 0 && i.getRelevantHPAbsatzLastYear() >= 1) {
                i.setInactiveAY(true);
                i.setRoleAY("Inactive");
            }

            if (i.getRelevantHPAbsatzLastYear() <= 0 && i.getRelevantHPAbsatzLastLastYear() >= 1) {
                i.setInactiveLY(true);
                i.setRoleLY("Inactive");
            }

            if (i.getRelevantHPAbsatzLastLastYear() <= 0 && i.getRelevantHPAbsatzLastLastLastYear() >= 1) {
                i.setInactiveLLY(true);
                i.setRoleLLY("Inactive");
            }

            if (i.getRelevantHPAbsatzThisYear() <= 0 && i.getRelevantHPAbsatzLastYear() <= 0) {
                i.setProspectAY(true);
                i.setRoleAY("Prospect");
            }

            if (i.getRelevantHPAbsatzLastYear() <= 0 && i.getRelevantHPAbsatzLastLastYear() <= 0) {
                i.setProspectLY(true);
                i.setRoleLY("Prospect");
            }

            if (i.getRelevantHPAbsatzLastLastYear() <= 0 && i.getRelevantHPAbsatzLastLastLastYear() <= 0) {
                i.setProspectLLY(true);
                i.setRoleLLY("Prospect");
            }

            if (i.isChurnLY() && (i.isNewConvertedAY() || i.isNewAY() || i.isStableAY())) {
                i.setReactivatedChurnAY(true);
                i.setReactivationAY("Reactivated Churn");
            }

            if (i.isChurnLLY() && (i.isNewConvertedLY() || i.isNewLY() || i.isStableLY())) {
                i.setReactivatedChurnLY(true);
                i.setReactivationLY("Reactivated Churn");
            }

            if (i.isInactiveLY() && (i.isNewConvertedAY() || i.isNewAY() || i.isStableAY())) {
                i.setReactivatedInactiveAY(true);
                i.setReactivationAY("Reactivated Inactive");
            }

            if (i.isInactiveLLY() && (i.isNewConvertedLY() || i.isNewLY() || i.isStableLY())) {
                i.setReactivatedInactiveLY(true);
                i.setReactivationLY("Reactivated Inactive");
            }

        }
        return installers;
    }

    /**
     * fügt den Installers einer Liste die Potentiale hinzu
     * @param installers i
     * @param dtoPotentials p
     * @return list
     */
    public static List<Installer> setPotentials(List<Installer> installers, List<DTOPotential> dtoPotentials) {

        for (Installer i : installers) {
            List<DTOPotential> potentials = new ArrayList<>();
            for (DTOPotential p : dtoPotentials) {
                if (i.getKdnr().equals(p.getKdnr())) {
                    potentials.add(p);
                }
            }
            i.setPotentials(potentials);

            int whbVaiCounter = 0;
            int whbCompCounter = 0;
            int hpVaiCounter = 0;
            int hpCompCounter = 0;

            for (DTOPotential p : i.getPotentials()) {
                if (p.getBrand().equals("Vaillant")) {
                    whbVaiCounter += p.getWhbAndFscu();
                    hpVaiCounter += p.getHeatPump();
                } else {
                    whbCompCounter += p.getWhbAndFscu();
                    hpCompCounter += p.getHeatPump();
                }
            }
            i.setWhbPotentialVai(whbVaiCounter);
            i.setWhbPotentialCompetitors(whbCompCounter);
            i.setHpPotentialVai(hpVaiCounter);
            i.setHpPotentialCompetitors(hpCompCounter);
            i.setCustomerSize(whbVaiCounter+whbCompCounter+hpVaiCounter+hpCompCounter);
            if (i.getCustomerSize()!=0) {
                i.setShareOfWallet((double) (whbVaiCounter+hpVaiCounter)/(whbVaiCounter+whbCompCounter+hpVaiCounter+hpCompCounter));
            } else {
                i.setShareOfWallet(0);
            }
        }
        return installers;
    }

    /**
     * fügt den Installers einer Liste die IBNs hinzu
     * @param installers i
     * @param ibns zahl
     * @param abWannRoll date
     * @param bisWannRoll date
     * @param abWannYtd date
     * @param bisWannYtd date
     * @return list
     */
    public static List<Installer> setInstallerIbns(List<Installer> installers, List<DTOibn> ibns, int abWannRoll, int bisWannRoll, int abWannYtd, int bisWannYtd) {

        for (Installer i : installers) {
            List<DTOibn> ibnsInstaller = new ArrayList<>();
            for (DTOibn ibn : ibns) {
                if (ibn.getKdnr().equals(i.getKdnr())) {
                    ibnsInstaller.add(ibn);
                }
            }
            i.setIbns(ibnsInstaller);

            int relevantWHBIbnsThisYear = 0;
            int relevantWHBIbnsLastYear = 0;
            int relevantWHBIbnsYtdThisYear = 0;
            int relevantWHBIbnsYtdLastYear = 0;
            int relevantHPIbnsThisYear = 0;
            int relevantHPIbnsLastYear = 0;
            int relevantHPIbnsYtdThisYear = 0;
            int relevantHPIbnsYtdLastYear = 0;

            for (DTOibn ibn : i.getIbns()) {

                if (ibn.getZeitpunkt() >= abWannRoll && ibn.getZeitpunkt() <= bisWannRoll) {
                    if (ibn.getProduktGruppe().equals("WHB")) {
                        relevantWHBIbnsThisYear++;
                    }
                    if (ibn.getProduktGruppe().equals("HP")) {
                        relevantHPIbnsThisYear++;
                    }
                }

                if (ibn.getZeitpunkt() >= (abWannRoll-100) && ibn.getZeitpunkt() <= (bisWannRoll-100)) {
                    if (ibn.getProduktGruppe().equals("WHB")) {
                        relevantWHBIbnsLastYear++;
                    }
                    if (ibn.getProduktGruppe().equals("HP")) {
                        relevantHPIbnsLastYear++;
                    }
                }

                if (ibn.getZeitpunkt() >= abWannYtd && ibn.getZeitpunkt() <= bisWannYtd) {
                    if (ibn.getProduktGruppe().equals("WHB")) {
                        relevantWHBIbnsYtdThisYear++;
                    }
                    if (ibn.getProduktGruppe().equals("HP")) {
                        relevantHPIbnsYtdThisYear++;
                    }
                }

                if (ibn.getZeitpunkt() >= (abWannYtd - 100) && ibn.getZeitpunkt() <= (bisWannYtd - 100)) {
                    if (ibn.getProduktGruppe().equals("WHB")) {
                        relevantWHBIbnsYtdLastYear++;
                    }
                    if (ibn.getProduktGruppe().equals("HP")) {
                        relevantHPIbnsYtdLastYear++;
                    }
                }
            }
            i.setRelevantWHBIbnsThisYear(relevantWHBIbnsThisYear);
            i.setRelevantWHBIbnsLastYear(relevantWHBIbnsLastYear);
            i.setRelevantWHBIbnsYtdThisYear(relevantWHBIbnsYtdThisYear);
            i.setRelevantWHBIbnsYtdLastYear(relevantWHBIbnsYtdLastYear);
            i.setRelevantHPIbnsThisYear(relevantHPIbnsThisYear);
            i.setRelevantHPIbnsLastYear(relevantHPIbnsLastYear);
            i.setRelevantHPIbnsYtdThisYear(relevantHPIbnsYtdThisYear);
            i.setRelevantHPIbnsYtdLastYear(relevantHPIbnsYtdLastYear);
        }
        return installers;
    }

    /**
     * fügt den Installers einer Liste die Visits hinzu
     */
    public static List<Installer> setVisits(List<Installer> installers, List<DTOVisit> visits, int abWannRoll, int bisWannRoll, int abWannYtd, int bisWannYtd) {

        for (Installer i : installers) {
            List<DTOVisit> visitsInstaller = new ArrayList<>();
            for (DTOVisit visit : visits) {
                if (visit.getKdnr().equals(i.getKdnr())) {
                    visitsInstaller.add(visit);
                }
            }
            i.setVisits(visitsInstaller);

            int relevantVisitsThisYear = 0;
            int relevantVisitsLastYear = 0;
            int relevantVisitsLastLastYear = 0;
            int relevantVisitsLastLastLastYear = 0;
            int relevantVisitsYtdThisYear = 0;
            int relevantVisitsYtdLastYear = 0;
            int relevantVisitsYtdLastLastYear = 0;
            int relevantVisitsYtdLastLastLastYear = 0;

            for (DTOVisit visit : i.getVisits()) {

                if (visit.getZeitpunkt() >= abWannRoll && visit.getZeitpunkt() <= bisWannRoll) {
                    relevantVisitsThisYear++;
                }

                if (visit.getZeitpunkt() >= (abWannRoll-100) && visit.getZeitpunkt() <= (bisWannRoll-100)) {
                    relevantVisitsLastYear++;
                }

                if (visit.getZeitpunkt() >= (abWannRoll-200) && visit.getZeitpunkt() <= (bisWannRoll-200)) {
                    relevantVisitsLastLastYear++;
                }

                if (visit.getZeitpunkt() >= (abWannRoll-300) && visit.getZeitpunkt() <= (bisWannRoll-300)) {
                    relevantVisitsLastLastLastYear++;
                }

                if (visit.getZeitpunkt() >= abWannYtd && visit.getZeitpunkt() <= bisWannYtd) {
                    relevantVisitsYtdThisYear++;
                }

                if (visit.getZeitpunkt() >= (abWannYtd-100) && visit.getZeitpunkt() <= (bisWannYtd-100)) {
                    relevantVisitsYtdLastYear++;
                }

                if (visit.getZeitpunkt() >= (abWannYtd-200) && visit.getZeitpunkt() <= (bisWannYtd-200)) {
                    relevantVisitsYtdLastLastYear++;
                }

                if (visit.getZeitpunkt() >= (abWannYtd-300) && visit.getZeitpunkt() <= (bisWannYtd-300)) {
                    relevantVisitsYtdLastLastLastYear++;
                }
            }

            i.setRelevantVisitsThisYear(relevantVisitsThisYear);
            i.setRelevantVisitsLastYear(relevantVisitsLastYear);
            i.setRelevantVisitsLastLastYear(relevantVisitsLastLastYear);
            i.setRelevantVisitsLastLastLastYear(relevantVisitsLastLastLastYear);
            i.setRelevantVisitsYtdThisYear(relevantVisitsYtdThisYear);
            i.setRelevantVisitsYtdLastYear(relevantVisitsYtdLastYear);
            i.setRelevantVisitsYtdLastLastYear(relevantVisitsYtdLastLastYear);
            i.setRelevantVisitsYtdLastLastLastYear(relevantVisitsYtdLastLastLastYear);
        }
        return installers;
    }



    //COUNT INSTALLERS & UNITS

    /**
     * gives back the number of installers depending on condition
     * @param installers list of installers (booleans for customer property have to be set)
     * @param condition text String in form of defined boolean
     * @return number of installers depending on condition
     */
    public static int countInstallersByCondition(List<Installer> installers, String condition) {
        boolean cond = false;
        int counter = 0;

        for (Installer i : installers) {

            switch (condition) {
                case "isStableAY":
                    cond = i.isStableAY();
                    break;
                case "isStableLY":
                    cond = i.isStableLY();
                    break;
                case "isStableLLY":
                    cond = i.isStableLLY();
                    break;
                case "isChurnAY":
                    cond = i.isChurnAY();
                    break;
                case "isChurnLY":
                    cond = i.isChurnLY();
                    break;
                case "isChurnLLY":
                    cond = i.isChurnLLY();
                    break;
                case "isNewAY":
                    cond = i.isNewAY();
                    break;
                case "isNewLY":
                    cond = i.isNewLY();
                    break;
                case "isNewLLY":
                    cond = i.isNewLLY();
                    break;
                case "isNewConvertedAY":
                    cond = i.isNewConvertedAY();
                    break;
                case "isNewConvertedLY":
                    cond = i.isNewConvertedLY();
                    break;
                case "isNewConvertedLLY":
                    cond = i.isNewConvertedLLY();
                    break;
                case "isInactiveAY":
                    cond = i.isInactiveAY();
                    break;
                case "isInactiveLY":
                    cond = i.isInactiveLY();
                    break;
                case "isInactiveLLY":
                    cond = i.isInactiveLLY();
                    break;
                case "isProspectAY":
                    cond = i.isProspectAY();
                    break;
                case "isProspectLY":
                    cond = i.isProspectLY();
                    break;
                case "isProspectLLY":
                    cond = i.isProspectLLY();
                    break;
                case "isReactivatedChurnAY":
                    cond = i.isReactivatedChurnAY();
                    break;
                case "isReactivatedChurnLY":
                    cond = i.isReactivatedChurnLY();
                    break;
                case "isReactivatedInactiveAY":
                    cond = i.isReactivatedInactiveAY();
                    break;
                case "isReactivatedInactiveLY":
                    cond = i.isReactivatedInactiveLY();
                    break;
            }


            if(cond) {
                counter++;
            }
        }
        return counter;
    }

    public static int countInstallersUnitsByCondition(List<Installer> installers, String condition, String period) {

        boolean cond = false;
        int relHPAbsatz = 0;
        int counter = 0;

        for (Installer i : installers) {

            switch (condition) {
                case "isStableAY":
                    cond = i.isStableAY();
                    break;
                case "isStableLY":
                    cond = i.isStableLY();
                    break;
                case "isStableLLY":
                    cond = i.isStableLLY();
                    break;
                case "isChurnAY":
                    cond = i.isChurnAY();
                    break;
                case "isChurnLY":
                    cond = i.isChurnLY();
                    break;
                case "isChurnLLY":
                    cond = i.isChurnLLY();
                    break;
                case "isNewAY":
                    cond = i.isNewAY();
                    break;
                case "isNewLY":
                    cond = i.isNewLY();
                    break;
                case "isNewLLY":
                    cond = i.isNewLLY();
                    break;
                case "isNewConvertedAY":
                    cond = i.isNewConvertedAY();
                    break;
                case "isNewConvertedLY":
                    cond = i.isNewConvertedLY();
                    break;
                case "isNewConvertedLLY":
                    cond = i.isNewConvertedLLY();
                    break;
                case "isInactiveAY":
                    cond = i.isInactiveAY();
                    break;
                case "isInactiveLY":
                    cond = i.isInactiveLY();
                    break;
                case "isInactiveLLY":
                    cond = i.isInactiveLLY();
                    break;
                case "isProspectAY":
                    cond = i.isProspectAY();
                    break;
                case "isProspectLY":
                    cond = i.isProspectLY();
                    break;
                case "isProspectLLY":
                    cond = i.isProspectLLY();
                    break;
                case "isReactivatedChurnAY":
                    cond = i.isReactivatedChurnAY();
                    break;
                case "isReactivatedChurnLY":
                    cond = i.isReactivatedChurnLY();
                    break;
                case "isReactivatedInactiveAY":
                    cond = i.isReactivatedInactiveAY();
                    break;
                case "isReactivatedInactiveLY":
                    cond = i.isReactivatedInactiveLY();
                    break;
            }

            switch (period) {
                case "RelevantHPAbsatzThisYear":
                    relHPAbsatz = i.getRelevantHPAbsatzThisYear();
                    break;
                case "RelevantHPAbsatzLastYear":
                    relHPAbsatz = i.getRelevantHPAbsatzLastYear();
                    break;
                case "RelevantHPAbsatzLastLastYear":
                    relHPAbsatz = i.getRelevantHPAbsatzLastLastYear();
                    break;
                case "RelevantHPAbsatzYtdThisYear":
                    relHPAbsatz = i.getRelevantHPAbsatzYtdThisYear();
                    break;
                case "RelevantHPAbsatzYtdLastYear":
                    relHPAbsatz = i.getRelevantHPAbsatzYtdLastYear();
                    break;
                case "RelevantHPAbsatzYtdLastLastYear":
                    relHPAbsatz = i.getRelevantHPAbsatzYtdLastLastYear();
                    break;
            }


            if(cond) {
                counter += relHPAbsatz;
            }

        }
        return counter;
    }

    /**
     * counts Heating Channel installers with Sales YTD
     * @param installers i
     * @return int
     */
    public static int countChannelInstallersWithYtdAbsatz(List<Installer> installers, String channel) {
        int counter = 0;
        for (Installer i : installers) {
            if (i.getChannel().equals(channel)&&i.getRelevantHPAbsatzYtdThisYear()>0) {
                counter++;
            }
        }
        return counter;
    }

    public static int countZielgruppeInstallers(List<Installer> installers, String zielgruppe) {
        int counter = 0;
        for (Installer i : installers) {
            if (i.getZielgruppe().equals(zielgruppe)&&i.getRelevantHPAbsatzYtdThisYear()>0) {
                counter++;
            }
        }
        return counter;
    }



    //SET SUMMARY VALUES
    public static void setSummaryValues(List<Installer> finIns, Summary summary) {
        //AY

        //AY Stable
        int numStableInsAY = countInstallersByCondition(finIns,"isStableAY");
        int numStableInsAYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isStableAY","RelevantHPAbsatzThisYear");
        int numStableInsAYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isStableAY","RelevantHPAbsatzLastYear");
        int numStableInsAYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isStableAY","RelevantHPAbsatzLastLastYear");
        int numStableInsAYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isStableAY","RelevantHPAbsatzYtdThisYear");
        int numStableInsAYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isStableAY","RelevantHPAbsatzYtdLastYear");
        int numStableInsAYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isStableAY","RelevantHPAbsatzYtdLastLastYear");

        //AY New
        int numNewInsAY = countInstallersByCondition(finIns,"isNewAY");
        int numNewInsAYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isNewAY","RelevantHPAbsatzThisYear");
        int numNewInsAYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isNewAY","RelevantHPAbsatzLastYear");
        int numNewInsAYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isNewAY","RelevantHPAbsatzLastLastYear");
        int numNewInsAYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isNewAY","RelevantHPAbsatzYtdThisYear");
        int numNewInsAYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isNewAY","RelevantHPAbsatzYtdLastYear");
        int numNewInsAYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isNewAY","RelevantHPAbsatzYtdLastLastYear");

        //AY Converted
        int numConvertedInsAY = countInstallersByCondition(finIns,"isNewConvertedAY");
        int numConvertedInsAYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isNewConvertedAY","RelevantHPAbsatzThisYear");
        int numConvertedInsAYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isNewConvertedAY","RelevantHPAbsatzLastYear");
        int numConvertedInsAYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isNewConvertedAY","RelevantHPAbsatzLastLastYear");
        int numConvertedInsAYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isNewConvertedAY","RelevantHPAbsatzYtdThisYear");
        int numConvertedInsAYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isNewConvertedAY","RelevantHPAbsatzYtdLastYear");
        int numConvertedInsAYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isNewConvertedAY","RelevantHPAbsatzYtdLastLastYear");

        //AY Churn
        int numChurnInsAY = countInstallersByCondition(finIns,"isChurnAY");
        int numChurnInsAYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isChurnAY","RelevantHPAbsatzThisYear");
        int numChurnInsAYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isChurnAY","RelevantHPAbsatzLastYear");
        int numChurnInsAYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isChurnAY","RelevantHPAbsatzLastLastYear");
        int numChurnInsAYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isChurnAY","RelevantHPAbsatzYtdThisYear");
        int numChurnInsAYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isChurnAY","RelevantHPAbsatzYtdLastYear");
        int numChurnInsAYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isChurnAY","RelevantHPAbsatzYtdLastLastYear");

        //AY Inactive
        int numInactiveInsAY = countInstallersByCondition(finIns,"isInactiveAY");
        int numInactiveInsAYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isInactiveAY","RelevantHPAbsatzThisYear");
        int numInactiveInsAYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isInactiveAY","RelevantHPAbsatzLastYear");
        int numInactiveInsAYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isInactiveAY","RelevantHPAbsatzLastLastYear");
        int numInactiveInsAYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isInactiveAY","RelevantHPAbsatzYtdThisYear");
        int numInactiveInsAYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isInactiveAY","RelevantHPAbsatzYtdLastYear");
        int numInactiveInsAYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isInactiveAY","RelevantHPAbsatzYtdLastLastYear");

        //AY Prospect
        int numProspectInsAY = countInstallersByCondition(finIns,"isProspectAY");
        int numProspectInsAYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isProspectAY","RelevantHPAbsatzThisYear");
        int numProspectInsAYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isProspectAY","RelevantHPAbsatzLastYear");
        int numProspectInsAYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isProspectAY","RelevantHPAbsatzLastLastYear");
        int numProspectInsAYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isProspectAY","RelevantHPAbsatzYtdThisYear");
        int numProspectInsAYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isProspectAY","RelevantHPAbsatzYtdLastYear");
        int numProspectInsAYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isProspectAY","RelevantHPAbsatzYtdLastLastYear");

        //AY Reactivated Churn
        int numReactivatedChurnInsAY = countInstallersByCondition(finIns,"isReactivatedChurnAY");
        int numReactivatedChurnInsAYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isReactivatedChurnAY","RelevantHPAbsatzThisYear");
        int numReactivatedChurnInsAYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isReactivatedChurnAY","RelevantHPAbsatzLastYear");
        int numReactivatedChurnInsAYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isReactivatedChurnAY","RelevantHPAbsatzLastLastYear");
        int numReactivatedChurnInsAYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isReactivatedChurnAY","RelevantHPAbsatzYtdThisYear");
        int numReactivatedChurnInsAYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isReactivatedChurnAY","RelevantHPAbsatzYtdLastYear");
        int numReactivatedChurnInsAYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isReactivatedChurnAY","RelevantHPAbsatzYtdLastLastYear");

        //AY Reactivated Inactive
        int numReactivatedInactiveInsAY = countInstallersByCondition(finIns,"isReactivatedInactiveAY");
        int numReactivatedInactiveInsAYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isReactivatedInactiveAY","RelevantHPAbsatzThisYear");
        int numReactivatedInactiveInsAYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isReactivatedInactiveAY","RelevantHPAbsatzLastYear");
        int numReactivatedInactiveInsAYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isReactivatedInactiveAY","RelevantHPAbsatzLastLastYear");
        int numReactivatedInactiveInsAYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isReactivatedInactiveAY","RelevantHPAbsatzYtdThisYear");
        int numReactivatedInactiveInsAYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isReactivatedInactiveAY","RelevantHPAbsatzYtdLastYear");
        int numReactivatedInactiveInsAYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isReactivatedInactiveAY","RelevantHPAbsatzYtdLastLastYear");

        //Variance Churn and Inactive
        int numChurnInactiveInsAYUnitsLY = numChurnInsAYUnitsLYytd + numInactiveInsAYUnitsLYytd;
        int numChurnInactiveInsUnitsAY = numChurnInsAYUnitsAYytd + numInactiveInsAYUnitsAYytd;
        int varianceChurnInactiveInsUnitsAY = numChurnInactiveInsUnitsAY-numChurnInactiveInsAYUnitsLY;
        //Reactivated Churn and Reactivated Inactive
        int numReactivatedChurnUnitsAY = countInstallersUnitsByCondition(finIns,"isReactivatedChurnAY","RelevantHPAbsatzYtdThisYear");
        int numReactivatedInactiveUnitsAY = countInstallersUnitsByCondition(finIns,"isReactivatedInactiveAY","RelevantHPAbsatzYtdThisYear");


        //LY

        //LY Stable
        int numStableInsLY = countInstallersByCondition(finIns,"isStableLY");
        int numStableInsLYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isStableLY","RelevantHPAbsatzThisYear");
        int numStableInsLYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isStableLY","RelevantHPAbsatzLastYear");
        int numStableInsLYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isStableLY","RelevantHPAbsatzLastLastYear");
        int numStableInsLYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isStableLY","RelevantHPAbsatzYtdThisYear");
        int numStableInsLYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isStableLY","RelevantHPAbsatzYtdLastYear");
        int numStableInsLYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isStableLY","RelevantHPAbsatzYtdLastLastYear");

        //LY New
        int numNewInsLY = countInstallersByCondition(finIns,"isNewLY");
        int numNewInsLYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isNewLY","RelevantHPAbsatzThisYear");
        int numNewInsLYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isNewLY","RelevantHPAbsatzLastYear");
        int numNewInsLYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isNewLY","RelevantHPAbsatzLastLastYear");
        int numNewInsLYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isNewLY","RelevantHPAbsatzYtdThisYear");
        int numNewInsLYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isNewLY","RelevantHPAbsatzYtdLastYear");
        int numNewInsLYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isNewLY","RelevantHPAbsatzYtdLastLastYear");

        //LY Converted
        int numConvertedInsLY = countInstallersByCondition(finIns,"isNewConvertedLY");
        int numConvertedInsLYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isNewConvertedLY","RelevantHPAbsatzThisYear");
        int numConvertedInsLYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isNewConvertedLY","RelevantHPAbsatzLastYear");
        int numConvertedInsLYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isNewConvertedLY","RelevantHPAbsatzLastLastYear");
        int numConvertedInsLYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isNewConvertedLY","RelevantHPAbsatzYtdThisYear");
        int numConvertedInsLYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isNewConvertedLY","RelevantHPAbsatzYtdLastYear");
        int numConvertedInsLYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isNewConvertedLY","RelevantHPAbsatzYtdLastLastYear");

        //LY Churn
        int numChurnInsLY = countInstallersByCondition(finIns,"isChurnLY");
        int numChurnInsLYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isChurnLY","RelevantHPAbsatzThisYear");
        int numChurnInsLYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isChurnLY","RelevantHPAbsatzLastYear");
        int numChurnInsLYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isChurnLY","RelevantHPAbsatzLastLastYear");
        int numChurnInsLYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isChurnLY","RelevantHPAbsatzYtdThisYear");
        int numChurnInsLYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isChurnLY","RelevantHPAbsatzYtdLastYear");
        int numChurnInsLYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isChurnLY","RelevantHPAbsatzYtdLastLastYear");

        //LY Inactive
        int numInactiveInsLY = countInstallersByCondition(finIns,"isInactiveLY");
        int numInactiveInsLYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isInactiveLY","RelevantHPAbsatzThisYear");
        int numInactiveInsLYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isInactiveLY","RelevantHPAbsatzLastYear");
        int numInactiveInsLYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isInactiveLY","RelevantHPAbsatzLastLastYear");
        int numInactiveInsLYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isInactiveLY","RelevantHPAbsatzYtdThisYear");
        int numInactiveInsLYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isInactiveLY","RelevantHPAbsatzYtdLastYear");
        int numInactiveInsLYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isInactiveLY","RelevantHPAbsatzYtdLastLastYear");

        //LY Prospect
        int numProspectInsLY = countInstallersByCondition(finIns,"isProspectLY");
        int numProspectInsLYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isProspectLY","RelevantHPAbsatzThisYear");
        int numProspectInsLYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isProspectLY","RelevantHPAbsatzLastYear");
        int numProspectInsLYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isProspectLY","RelevantHPAbsatzLastLastYear");
        int numProspectInsLYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isProspectLY","RelevantHPAbsatzYtdThisYear");
        int numProspectInsLYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isProspectLY","RelevantHPAbsatzYtdLastYear");
        int numProspectInsLYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isProspectLY","RelevantHPAbsatzYtdLastLastYear");

        //LY Reactivated Churn
        int numReactivatedChurnInsLY = countInstallersByCondition(finIns,"isReactivatedChurnLY");
        int numReactivatedChurnInsLYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isReactivatedChurnLY","RelevantHPAbsatzThisYear");
        int numReactivatedChurnInsLYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isReactivatedChurnLY","RelevantHPAbsatzLastYear");
        int numReactivatedChurnInsLYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isReactivatedChurnLY","RelevantHPAbsatzLastLastYear");
        int numReactivatedChurnInsLYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isReactivatedChurnLY","RelevantHPAbsatzYtdThisYear");
        int numReactivatedChurnInsLYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isReactivatedChurnLY","RelevantHPAbsatzYtdLastYear");
        int numReactivatedChurnInsLYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isReactivatedChurnLY","RelevantHPAbsatzYtdLastLastYear");

        //LY Reactivated Inactive
        int numReactivatedInactiveInsLY = countInstallersByCondition(finIns,"isReactivatedInactiveLY");
        int numReactivatedInactiveInsLYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isReactivatedInactiveLY","RelevantHPAbsatzThisYear");
        int numReactivatedInactiveInsLYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isReactivatedInactiveLY","RelevantHPAbsatzLastYear");
        int numReactivatedInactiveInsLYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isReactivatedInactiveLY","RelevantHPAbsatzLastLastYear");
        int numReactivatedInactiveInsLYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isReactivatedInactiveLY","RelevantHPAbsatzYtdThisYear");
        int numReactivatedInactiveInsLYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isReactivatedInactiveLY","RelevantHPAbsatzYtdLastYear");
        int numReactivatedInactiveInsLYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isReactivatedInactiveLY","RelevantHPAbsatzYtdLastLastYear");

        //Variance Churn and Inactive
        int numChurnInactiveInsLYUnitsLLY = numChurnInsLYUnitsLLYytd + numInactiveInsLYUnitsLLYytd;
        int numChurnInactiveInsUnitsLY = numChurnInsLYUnitsLYytd + numInactiveInsLYUnitsLYytd;
        int varianceChurnInactiveInsUnitsLY = numChurnInactiveInsUnitsLY-numChurnInactiveInsLYUnitsLLY;
        //Reactivated Churn and Reactivated Inactive
        int numReactivatedChurnUnitsLY = countInstallersUnitsByCondition(finIns,"isReactivatedChurnLY","RelevantHPAbsatzYtdLastYear");
        int numReactivatedInactiveUnitsLY = countInstallersUnitsByCondition(finIns,"isReactivatedInactiveLY","RelevantHPAbsatzYtdLastYear");


        //LLY

        //LLY Stable
        int numStableInsLLY = countInstallersByCondition(finIns,"isStableLLY");
        int numStableInsLLYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isStableLLY","RelevantHPAbsatzThisYear");
        int numStableInsLLYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isStableLLY","RelevantHPAbsatzLastYear");
        int numStableInsLLYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isStableLLY","RelevantHPAbsatzLastLastYear");
        int numStableInsLLYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isStableLLY","RelevantHPAbsatzYtdThisYear");
        int numStableInsLLYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isStableLLY","RelevantHPAbsatzYtdLastYear");
        int numStableInsLLYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isStableLLY","RelevantHPAbsatzYtdLastLastYear");

        //LLY New
        int numNewInsLLY = countInstallersByCondition(finIns,"isNewLLY");
        int numNewInsLLYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isNewLLY","RelevantHPAbsatzThisYear");
        int numNewInsLLYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isNewLLY","RelevantHPAbsatzLastYear");
        int numNewInsLLYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isNewLLY","RelevantHPAbsatzLastLastYear");
        int numNewInsLLYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isNewLLY","RelevantHPAbsatzYtdThisYear");
        int numNewInsLLYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isNewLLY","RelevantHPAbsatzYtdLastYear");
        int numNewInsLLYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isNewLLY","RelevantHPAbsatzYtdLastLastYear");

        //LLY Converted
        int numConvertedInsLLY = countInstallersByCondition(finIns,"isNewConvertedLLY");
        int numConvertedInsLLYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isNewConvertedLLY","RelevantHPAbsatzThisYear");
        int numConvertedInsLLYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isNewConvertedLLY","RelevantHPAbsatzLastYear");
        int numConvertedInsLLYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isNewConvertedLLY","RelevantHPAbsatzLastLastYear");
        int numConvertedInsLLYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isNewConvertedLLY","RelevantHPAbsatzYtdThisYear");
        int numConvertedInsLLYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isNewConvertedLLY","RelevantHPAbsatzYtdLastYear");
        int numConvertedInsLLYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isNewConvertedLLY","RelevantHPAbsatzYtdLastLastYear");

        //LLY Churn
        int numChurnInsLLY = countInstallersByCondition(finIns,"isChurnLLY");
        int numChurnInsLLYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isChurnLLY","RelevantHPAbsatzThisYear");
        int numChurnInsLLYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isChurnLLY","RelevantHPAbsatzLastYear");
        int numChurnInsLLYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isChurnLLY","RelevantHPAbsatzLastLastYear");
        int numChurnInsLLYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isChurnLLY","RelevantHPAbsatzYtdThisYear");
        int numChurnInsLLYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isChurnLLY","RelevantHPAbsatzYtdLastYear");
        int numChurnInsLLYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isChurnLLY","RelevantHPAbsatzYtdLastLastYear");

        //LLY Inactive
        int numInactiveInsLLY = countInstallersByCondition(finIns,"isInactiveLLY");
        int numInactiveInsLLYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isInactiveLLY","RelevantHPAbsatzThisYear");
        int numInactiveInsLLYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isInactiveLLY","RelevantHPAbsatzLastYear");
        int numInactiveInsLLYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isInactiveLLY","RelevantHPAbsatzLastLastYear");
        int numInactiveInsLLYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isInactiveLLY","RelevantHPAbsatzYtdThisYear");
        int numInactiveInsLLYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isInactiveLLY","RelevantHPAbsatzYtdLastYear");
        int numInactiveInsLLYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isInactiveLLY","RelevantHPAbsatzYtdLastLastYear");

        //LLY Prospect
        int numProspectInsLLY = countInstallersByCondition(finIns,"isProspectLLY");
        int numProspectInsLLYUnitsAY12mr = countInstallersUnitsByCondition(finIns,"isProspectLLY","RelevantHPAbsatzThisYear");
        int numProspectInsLLYUnitsLY12mr = countInstallersUnitsByCondition(finIns,"isProspectLLY","RelevantHPAbsatzLastYear");
        int numProspectInsLLYUnitsLLY12mr = countInstallersUnitsByCondition(finIns,"isProspectLLY","RelevantHPAbsatzLastLastYear");
        int numProspectInsLLYUnitsAYytd = countInstallersUnitsByCondition(finIns,"isProspectLLY","RelevantHPAbsatzYtdThisYear");
        int numProspectInsLLYUnitsLYytd = countInstallersUnitsByCondition(finIns,"isProspectLLY","RelevantHPAbsatzYtdLastYear");
        int numProspectInsLLYUnitsLLYytd = countInstallersUnitsByCondition(finIns,"isProspectLLY","RelevantHPAbsatzYtdLastLastYear");


        //Waterfall Werte
        int aufsatzChurnInactiveAYLYUnitsYtd = varianceChurnInactiveInsUnitsAY-varianceChurnInactiveInsUnitsLY;
        int aufsatzReactivatedChurnInactiveAYLYUnitsYtd = (numReactivatedChurnUnitsAY+numReactivatedInactiveUnitsAY)-(numReactivatedChurnUnitsLY+numReactivatedInactiveUnitsLY);
        int aufsatzNewCustomerAYLYUnitsYtd = numNewInsAYUnitsAYytd-numNewInsLYUnitsLYytd;
        int aufsatzConvertedCustomerAYLYUnitsYtd = numConvertedInsAYUnitsAYytd-numConvertedInsLYUnitsLYytd;
        int aufsatzStableCustomerAYLYUnitsYtd = numStableInsAYUnitsAYytd-numStableInsLYUnitsLYytd;
        int aufsatzNewConvertedStableCustomerAYLYUnitsYtd = (numNewInsAYUnitsAYytd+numConvertedInsAYUnitsAYytd+numStableInsAYUnitsAYytd)-
                (numNewInsLYUnitsLYytd+numConvertedInsLYUnitsLYytd+numStableInsLYUnitsLYytd);


        //Summary-Werte setzen

        //Stable

        //Stable AY
        summary.setStableInstallersAY(numStableInsAY);
        summary.setStableInstallersAYUnitsAY12mr(numStableInsAYUnitsAY12mr);
        summary.setStableInstallersAYUnitsLY12mr(numStableInsAYUnitsLY12mr);
        summary.setStableInstallersAYUnitsLLY12mr(numStableInsAYUnitsLLY12mr);
        summary.setStableInstallersAYUnitsAYytd(numStableInsAYUnitsAYytd);
        summary.setStableInstallersAYUnitsLYytd(numStableInsAYUnitsLYytd);
        summary.setStableInstallersAYUnitsLLYytd(numStableInsAYUnitsLLYytd);

        //Stable LY
        summary.setStableInstallersLY(numStableInsLY);
        summary.setStableInstallersLYUnitsAY12mr(numStableInsLYUnitsAY12mr);
        summary.setStableInstallersLYUnitsLY12mr(numStableInsLYUnitsLY12mr);
        summary.setStableInstallersLYUnitsLLY12mr(numStableInsLYUnitsLLY12mr);
        summary.setStableInstallersLYUnitsAYytd(numStableInsLYUnitsAYytd);
        summary.setStableInstallersLYUnitsLYytd(numStableInsLYUnitsLYytd);
        summary.setStableInstallersLYUnitsLLYytd(numStableInsLYUnitsLLYytd);

        //Stable LLY
        summary.setStableInstallersLLY(numStableInsLLY);
        summary.setStableInstallersLLYUnitsAY12mr(numStableInsLLYUnitsAY12mr);
        summary.setStableInstallersLLYUnitsLY12mr(numStableInsLLYUnitsLY12mr);
        summary.setStableInstallersLLYUnitsLLY12mr(numStableInsLLYUnitsLLY12mr);
        summary.setStableInstallersLLYUnitsAYytd(numStableInsLLYUnitsAYytd);
        summary.setStableInstallersLLYUnitsLYytd(numStableInsLLYUnitsLYytd);
        summary.setStableInstallersLLYUnitsLLYytd(numStableInsLLYUnitsLLYytd);

        //New

        //New AY
        summary.setNewInstallersAY(numNewInsAY);
        summary.setNewInstallersAYUnitsAY12mr(numNewInsAYUnitsAY12mr);
        summary.setNewInstallersAYUnitsLY12mr(numNewInsAYUnitsLY12mr);
        summary.setNewInstallersAYUnitsLLY12mr(numNewInsAYUnitsLLY12mr);
        summary.setNewInstallersAYUnitsAYytd(numNewInsAYUnitsAYytd);
        summary.setNewInstallersAYUnitsLYytd(numNewInsAYUnitsLYytd);
        summary.setNewInstallersAYUnitsLLYytd(numNewInsAYUnitsLLYytd);

        //New LY
        summary.setNewInstallersLY(numNewInsLY);
        summary.setNewInstallersLYUnitsAY12mr(numNewInsLYUnitsAY12mr);
        summary.setNewInstallersLYUnitsLY12mr(numNewInsLYUnitsLY12mr);
        summary.setNewInstallersLYUnitsLLY12mr(numNewInsLYUnitsLLY12mr);
        summary.setNewInstallersLYUnitsAYytd(numNewInsLYUnitsAYytd);
        summary.setNewInstallersLYUnitsLYytd(numNewInsLYUnitsLYytd);
        summary.setNewInstallersLYUnitsLLYytd(numNewInsLYUnitsLLYytd);

        //New LLY
        summary.setNewInstallersLLY(numNewInsLLY);
        summary.setNewInstallersLLYUnitsAY12mr(numNewInsLLYUnitsAY12mr);
        summary.setNewInstallersLLYUnitsLY12mr(numNewInsLLYUnitsLY12mr);
        summary.setNewInstallersLLYUnitsLLY12mr(numNewInsLLYUnitsLLY12mr);
        summary.setNewInstallersLLYUnitsAYytd(numNewInsLLYUnitsAYytd);
        summary.setNewInstallersLLYUnitsLYytd(numNewInsLLYUnitsLYytd);
        summary.setNewInstallersLLYUnitsLLYytd(numNewInsLLYUnitsLLYytd);

        //Converted

        //Converted AY
        summary.setConvertedInstallersAY(numConvertedInsAY);
        summary.setConvertedInstallersAYUnitsAY12mr(numConvertedInsAYUnitsAY12mr);
        summary.setConvertedInstallersAYUnitsLY12mr(numConvertedInsAYUnitsLY12mr);
        summary.setConvertedInstallersAYUnitsLLY12mr(numConvertedInsAYUnitsLLY12mr);
        summary.setConvertedInstallersAYUnitsAYytd(numConvertedInsAYUnitsAYytd);
        summary.setConvertedInstallersAYUnitsLYytd(numConvertedInsAYUnitsLYytd);
        summary.setConvertedInstallersAYUnitsLLYytd(numConvertedInsAYUnitsLLYytd);

        //Converted LY
        summary.setConvertedInstallersLY(numConvertedInsLY);
        summary.setConvertedInstallersLYUnitsAY12mr(numConvertedInsLYUnitsAY12mr);
        summary.setConvertedInstallersLYUnitsLY12mr(numConvertedInsLYUnitsLY12mr);
        summary.setConvertedInstallersLYUnitsLLY12mr(numConvertedInsLYUnitsLLY12mr);
        summary.setConvertedInstallersLYUnitsAYytd(numConvertedInsLYUnitsAYytd);
        summary.setConvertedInstallersLYUnitsLYytd(numConvertedInsLYUnitsLYytd);
        summary.setConvertedInstallersLYUnitsLLYytd(numConvertedInsLYUnitsLLYytd);

        //Converted LLY
        summary.setConvertedInstallersLLY(numConvertedInsLLY);
        summary.setConvertedInstallersLLYUnitsAY12mr(numConvertedInsLLYUnitsAY12mr);
        summary.setConvertedInstallersLLYUnitsLY12mr(numConvertedInsLLYUnitsLY12mr);
        summary.setConvertedInstallersLLYUnitsLLY12mr(numConvertedInsLLYUnitsLLY12mr);
        summary.setConvertedInstallersLLYUnitsAYytd(numConvertedInsLLYUnitsAYytd);
        summary.setConvertedInstallersLLYUnitsLYytd(numConvertedInsLLYUnitsLYytd);
        summary.setConvertedInstallersLLYUnitsLLYytd(numConvertedInsLLYUnitsLLYytd);

        //Churn

        //Churn AY
        summary.setChurnInstallersAY(numChurnInsAY);
        summary.setChurnInstallersAYUnitsAY12mr(numChurnInsAYUnitsAY12mr);
        summary.setChurnInstallersAYUnitsLY12mr(numChurnInsAYUnitsLY12mr);
        summary.setChurnInstallersAYUnitsLLY12mr(numChurnInsAYUnitsLLY12mr);
        summary.setChurnInstallersAYUnitsAYytd(numChurnInsAYUnitsAYytd);
        summary.setChurnInstallersAYUnitsLYytd(numChurnInsAYUnitsLYytd);
        summary.setChurnInstallersAYUnitsLLYytd(numChurnInsAYUnitsLLYytd);

        //Churn LY
        summary.setChurnInstallersLY(numChurnInsLY);
        summary.setChurnInstallersLYUnitsAY12mr(numChurnInsLYUnitsAY12mr);
        summary.setChurnInstallersLYUnitsLY12mr(numChurnInsLYUnitsLY12mr);
        summary.setChurnInstallersLYUnitsLLY12mr(numChurnInsLYUnitsLLY12mr);
        summary.setChurnInstallersLYUnitsAYytd(numChurnInsLYUnitsAYytd);
        summary.setChurnInstallersLYUnitsLYytd(numChurnInsLYUnitsLYytd);
        summary.setChurnInstallersLYUnitsLLYytd(numChurnInsLYUnitsLLYytd);

        //Churn LLY
        summary.setChurnInstallersLLY(numChurnInsLLY);
        summary.setChurnInstallersLLYUnitsAY12mr(numChurnInsLLYUnitsAY12mr);
        summary.setChurnInstallersLLYUnitsLY12mr(numChurnInsLLYUnitsLY12mr);
        summary.setChurnInstallersLLYUnitsLLY12mr(numChurnInsLLYUnitsLLY12mr);
        summary.setChurnInstallersLLYUnitsAYytd(numChurnInsLLYUnitsAYytd);
        summary.setChurnInstallersLLYUnitsLYytd(numChurnInsLLYUnitsLYytd);
        summary.setChurnInstallersLLYUnitsLLYytd(numChurnInsLLYUnitsLLYytd);

        //Inactive

        //Inactive AY
        summary.setInactiveInstallersAY(numInactiveInsAY);
        summary.setInactiveInstallersAYUnitsAY12mr(numInactiveInsAYUnitsAY12mr);
        summary.setInactiveInstallersAYUnitsLY12mr(numInactiveInsAYUnitsLY12mr);
        summary.setInactiveInstallersAYUnitsLLY12mr(numInactiveInsAYUnitsLLY12mr);
        summary.setInactiveInstallersAYUnitsAYytd(numInactiveInsAYUnitsAYytd);
        summary.setInactiveInstallersAYUnitsLYytd(numInactiveInsAYUnitsLYytd);
        summary.setInactiveInstallersAYUnitsLLYytd(numInactiveInsAYUnitsLLYytd);

        //Inactive LY
        summary.setInactiveInstallersLY(numInactiveInsLY);
        summary.setInactiveInstallersLYUnitsAY12mr(numInactiveInsLYUnitsAY12mr);
        summary.setInactiveInstallersLYUnitsLY12mr(numInactiveInsLYUnitsLY12mr);
        summary.setInactiveInstallersLYUnitsLLY12mr(numInactiveInsLYUnitsLLY12mr);
        summary.setInactiveInstallersLYUnitsAYytd(numInactiveInsLYUnitsAYytd);
        summary.setInactiveInstallersLYUnitsLYytd(numInactiveInsLYUnitsLYytd);
        summary.setInactiveInstallersLYUnitsLLYytd(numInactiveInsLYUnitsLLYytd);

        //Inactive LLY
        summary.setInactiveInstallersLLY(numInactiveInsLLY);
        summary.setInactiveInstallersLLYUnitsAY12mr(numInactiveInsLLYUnitsAY12mr);
        summary.setInactiveInstallersLLYUnitsLY12mr(numInactiveInsLLYUnitsLY12mr);
        summary.setInactiveInstallersLLYUnitsLLY12mr(numInactiveInsLLYUnitsLLY12mr);
        summary.setInactiveInstallersLLYUnitsAYytd(numInactiveInsLLYUnitsAYytd);
        summary.setInactiveInstallersLLYUnitsLYytd(numInactiveInsLLYUnitsLYytd);
        summary.setInactiveInstallersLLYUnitsLLYytd(numInactiveInsLLYUnitsLLYytd);

        //Prospect

        //Prospect AY
        summary.setProspectInstallersAY(numProspectInsAY);
        summary.setProspectInstallersAYUnitsAY12mr(numProspectInsAYUnitsAY12mr);
        summary.setProspectInstallersAYUnitsLY12mr(numProspectInsAYUnitsLY12mr);
        summary.setProspectInstallersAYUnitsLLY12mr(numProspectInsAYUnitsLLY12mr);
        summary.setProspectInstallersAYUnitsAYytd(numProspectInsAYUnitsAYytd);
        summary.setProspectInstallersAYUnitsLYytd(numProspectInsAYUnitsLYytd);
        summary.setProspectInstallersAYUnitsLLYytd(numProspectInsAYUnitsLLYytd);

        //Prospect LY
        summary.setProspectInstallersLY(numProspectInsLY);
        summary.setProspectInstallersLYUnitsAY12mr(numProspectInsLYUnitsAY12mr);
        summary.setProspectInstallersLYUnitsLY12mr(numProspectInsLYUnitsLY12mr);
        summary.setProspectInstallersLYUnitsLLY12mr(numProspectInsLYUnitsLLY12mr);
        summary.setProspectInstallersLYUnitsAYytd(numProspectInsLYUnitsAYytd);
        summary.setProspectInstallersLYUnitsLYytd(numProspectInsLYUnitsLYytd);
        summary.setProspectInstallersLYUnitsLLYytd(numProspectInsLYUnitsLLYytd);

        //Prospect LLY
        summary.setProspectInstallersLLY(numProspectInsLLY);
        summary.setProspectInstallersLLYUnitsAY12mr(numProspectInsLLYUnitsAY12mr);
        summary.setProspectInstallersLLYUnitsLY12mr(numProspectInsLLYUnitsLY12mr);
        summary.setProspectInstallersLLYUnitsLLY12mr(numProspectInsLLYUnitsLLY12mr);
        summary.setProspectInstallersLLYUnitsAYytd(numProspectInsLLYUnitsAYytd);
        summary.setProspectInstallersLLYUnitsLYytd(numProspectInsLLYUnitsLYytd);
        summary.setProspectInstallersLLYUnitsLLYytd(numProspectInsLLYUnitsLLYytd);


        //Reactivated Churn

        //Reactivated Churn AY
        summary.setReactivatedChurnInstallersAY(numReactivatedChurnInsAY);
        summary.setReactivatedChurnInstallersAYUnitsAY12mr(numReactivatedChurnInsAYUnitsAY12mr);
        summary.setReactivatedChurnInstallersAYUnitsLY12mr(numReactivatedChurnInsAYUnitsLY12mr);
        summary.setReactivatedChurnInstallersAYUnitsLLY12mr(numReactivatedChurnInsAYUnitsLLY12mr);
        summary.setReactivatedChurnInstallersAYUnitsAYytd(numReactivatedChurnInsAYUnitsAYytd);
        summary.setReactivatedChurnInstallersAYUnitsLYytd(numReactivatedChurnInsAYUnitsLYytd);
        summary.setReactivatedChurnInstallersAYUnitsLLYytd(numReactivatedChurnInsAYUnitsLLYytd);

        //Reactivated Churn LY
        summary.setReactivatedChurnInstallersLY(numReactivatedChurnInsLY);
        summary.setReactivatedChurnInstallersLYUnitsAY12mr(numReactivatedChurnInsLYUnitsAY12mr);
        summary.setReactivatedChurnInstallersLYUnitsLY12mr(numReactivatedChurnInsLYUnitsLY12mr);
        summary.setReactivatedChurnInstallersLYUnitsLLY12mr(numReactivatedChurnInsLYUnitsLLY12mr);
        summary.setReactivatedChurnInstallersLYUnitsAYytd(numReactivatedChurnInsLYUnitsAYytd);
        summary.setReactivatedChurnInstallersLYUnitsLYytd(numReactivatedChurnInsLYUnitsLYytd);
        summary.setReactivatedChurnInstallersLYUnitsLLYytd(numReactivatedChurnInsLYUnitsLLYytd);

        //Reactivated Inactive

        //Reactivated Inactive AY
        summary.setReactivatedInactiveInstallersAY(numReactivatedInactiveInsAY);
        summary.setReactivatedInactiveInstallersAYUnitsAY12mr(numReactivatedInactiveInsAYUnitsAY12mr);
        summary.setReactivatedInactiveInstallersAYUnitsLY12mr(numReactivatedInactiveInsAYUnitsLY12mr);
        summary.setReactivatedInactiveInstallersAYUnitsLLY12mr(numReactivatedInactiveInsAYUnitsLLY12mr);
        summary.setReactivatedInactiveInstallersAYUnitsAYytd(numReactivatedInactiveInsAYUnitsAYytd);
        summary.setReactivatedInactiveInstallersAYUnitsLYytd(numReactivatedInactiveInsAYUnitsLYytd);
        summary.setReactivatedInactiveInstallersAYUnitsLLYytd(numReactivatedInactiveInsAYUnitsLLYytd);

        //Reactivated Inactive LY
        summary.setReactivatedInactiveInstallersLY(numReactivatedInactiveInsLY);
        summary.setReactivatedInactiveInstallersLYUnitsAY12mr(numReactivatedInactiveInsLYUnitsAY12mr);
        summary.setReactivatedInactiveInstallersLYUnitsLY12mr(numReactivatedInactiveInsLYUnitsLY12mr);
        summary.setReactivatedInactiveInstallersLYUnitsLLY12mr(numReactivatedInactiveInsLYUnitsLLY12mr);
        summary.setReactivatedInactiveInstallersLYUnitsAYytd(numReactivatedInactiveInsLYUnitsAYytd);
        summary.setReactivatedInactiveInstallersLYUnitsLYytd(numReactivatedInactiveInsLYUnitsLYytd);
        summary.setReactivatedInactiveInstallersLYUnitsLLYytd(numReactivatedInactiveInsLYUnitsLLYytd);


        //Waterfall-Werte
        summary.setAufsatzChurnInactiveAYLYUnitsYtd(aufsatzChurnInactiveAYLYUnitsYtd);
        summary.setAufsatzReactivatedChurnInactiveAYLYUnitsYtd(aufsatzReactivatedChurnInactiveAYLYUnitsYtd);
        summary.setAufsatzNewConvertedStableCustomerAYLYUnitsYtd(aufsatzNewConvertedStableCustomerAYLYUnitsYtd);
        summary.setAufsatzNewCustomerAYLYUnitsYtd(aufsatzNewCustomerAYLYUnitsYtd);
        summary.setAufsatzConvertedCustomerAYLYUnitsYtd(aufsatzConvertedCustomerAYLYUnitsYtd);
        summary.setAufsatzStableCustomerAYLYUnitsYtd(aufsatzStableCustomerAYLYUnitsYtd);

        //Installers pro Channel mit YTD-Absatz berechnen
        int heatingChannelInst = countChannelInstallersWithYtdAbsatz(finIns,"Heating");
        int electroChannelInst = countChannelInstallersWithYtdAbsatz(finIns,"Electro");
        int airconChannelInst = countChannelInstallersWithYtdAbsatz(finIns,"AirCon");
        int channelSum = heatingChannelInst+electroChannelInst+airconChannelInst;

        //set summary
        summary.setChannelHeatingInstallersYtd(heatingChannelInst);
        summary.setChannelElectroInstallersYtd(electroChannelInst);
        summary.setChannelAirConInstallersYtd(airconChannelInst);
        summary.setNumberInstallersWithSalesYtd(channelSum);

        //Installers pro Zielgruppe mit YTD-Absatz berechnen
        int fachhandwerkerInst = countZielgruppeInstallers(finIns,"Fachhandwerker");
        int grosshandelInst = countZielgruppeInstallers(finIns,"GROSSHANDEL");
        int projektInst = countZielgruppeInstallers(finIns,"Projekt");
        int sonstigesInst = countZielgruppeInstallers(finIns,"SONSTIGES");

        //set summary
        summary.setNumberZielgruppeFachhandwerkerWithSalesYtd(fachhandwerkerInst);
        summary.setNumberZielgruppeGrosshandelWithSalesYtd(grosshandelInst);
        summary.setNumberZielgruppeProjektWithSalesYtd(projektInst);
        summary.setNumberZielgruppeSonstigesWithSalesYtd(sonstigesInst);



    }

}