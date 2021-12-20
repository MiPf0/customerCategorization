package org.company;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class InstallersFileReader {

    public static InstallersFileReader instance;

    private InstallersFileReader() {
        //
    }

    public static InstallersFileReader getInstance() {
        if(instance == null) {
            instance = new InstallersFileReader();
        }
        return instance;
    }

    public List<DTOInstaller> loadInstallers(Path installerPath,Path fishPath) throws IOException {

        List<String> lines = null;
        lines = Files.readAllLines(installerPath, StandardCharsets.ISO_8859_1);
        lines.remove(0);

        List<String> lines2 = null;
        lines2 = Files.readAllLines(fishPath, StandardCharsets.ISO_8859_1);
        lines2.remove(0);

        List<DTOInstaller> installers = new ArrayList<>();
        for(String line : lines) {
            String[] part = line.split(";");
            String kdnr = part[2];
            String kdname = part[3];
            String vad = part[1];
            String kundenGruppe = part[6];
            String channel = part[7];
            String zielgruppe = part[8];
            DTOInstaller installer = new DTOInstaller(kdnr,kdname,vad,kundenGruppe,channel,zielgruppe,"");
            installers.add(installer);
        }

        for(String line : lines2) {
            String[] part = line.split(";");
            String programme = part[0];
            String kdnr = part[1];
            for(DTOInstaller i : installers) {
                if(i.getKdnr().equals(kdnr)) {
                    i.setProgramme(programme);
                }
            }
        }
        return installers;
    }

    public List<DTOAbsatz> loadAbsaetze(Path path) throws IOException {

        List<String> lines = null;
        lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
        lines.remove(0);

        List<DTOAbsatz> absaetze = new ArrayList<>();
        for(String line : lines) {
            String[] part = line.split(";");
            String kdnr = part[2];
            String produktgruppe = part[10];
            int absatz = Integer.parseInt(part[11]);
            int zeitpunkt = Integer.parseInt(part[12]);
            DTOAbsatz absatzObjekt = new DTOAbsatz(kdnr,produktgruppe,absatz,zeitpunkt);
            absaetze.add(absatzObjekt);
        }
        return absaetze;
    }

    public List<DTOPotential> loadPotentials(Path path) throws IOException {

        List<String> lines = null;
        lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
        lines.remove(0);

        List<DTOPotential> potentials = new ArrayList<>();
        for(String line : lines) {
            String[] part = line.split(";");
            String kdnr = part[0];
            String dateOfCreation = part[8];
            String potentialAnalysisId = part[9];
            String brand = part[14];
            int whbAndFscu = Integer.parseInt(part[15]);
            int heatPump = Integer.parseInt(part[16]);
            String dateLatestChange = part[19];

            DTOPotential potentialObject = new DTOPotential(kdnr,dateOfCreation,dateLatestChange,potentialAnalysisId,brand,whbAndFscu,heatPump);
            potentials.add(potentialObject);
        }
        return potentials;
    }

    public List<DTOibn> loadIbn(Path path) throws IOException {

        List<String> lines = null;
        lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
        lines.remove(0);

        List<DTOibn> ibns = new ArrayList<>();
        for(String line : lines) {
            String[] part = line.split(";");
            String kdnr = part[16];
            String produktGruppe = part[20];
            int zeitpunkt = Integer.parseInt(part[21]);

            DTOibn ibnObject = new DTOibn(kdnr,produktGruppe,zeitpunkt);
            ibns.add(ibnObject);
        }
        return ibns;
    }

    public List<DTOVisit> loadVisits(Path path) throws IOException {

        List<String> lines = null;
        lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
        lines.remove(0);

        List<DTOVisit> visits = new ArrayList<>();
        for(String line : lines) {
            String[] part = line.split(";");
            String kdnr = part[0];
            int date = Integer.parseInt(part[2]);

            DTOVisit visitObject = new DTOVisit(kdnr,date);
            visits.add(visitObject);
        }
        return visits;
    }


}