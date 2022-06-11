package pt.isec.pa.utils;

import pt.isec.pa.model.data.AppData;
import pt.isec.pa.model.data.Branches;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CSVModder {
    private AppData data;

    public CSVModder(AppData data) {
        this.data = data;
    }

    public boolean SUpload(File file) {
        int contador=0;
        try {

            if(!file.exists())
                return false;

            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String commaDelimited  = myReader.nextLine();
                String[] values = commaDelimited.split(",");
                if(!data.addStudent(values[1],values[2], Long.parseLong(values[0]), values[3], values[4], Double.parseDouble(values[5]), Boolean.parseBoolean(values[6])))
                    contador++;

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }

        if(contador>0)
            return false;

        return true;
    }

    public boolean TUpload(File file) {
        int contador=0;
        try {


            if(!file.exists())
                return false;
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String commaDelimited  = myReader.nextLine();
                String[] values = commaDelimited.split(",");
                if(values.length!=2)
                    return false;
                if(!data.addTeacher(values[0], values[1]))
                contador++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }

        if(contador>0)
            return false;

        return true;
    }

    public boolean PIUpload(File file) {
        int contador=0;
        try {
            if(!file.exists())
                return false;
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String commaDelimited  = myReader.nextLine();
                String[] values = commaDelimited.split(",");
                long nr = -1;
                List<String> branch = new ArrayList<>();
                switch(values[0]) {
                    case "T3" -> {
                        if(!data.addSelfProp(values[1] /*idCode*/, Long.parseLong(values[3]) /*number*/, values[2] /*title*/))
                            contador++;
                    }    //T3
                    default -> {
                        if(values.length == 6)
                            nr = Long.parseLong(values[5]);

                        if(values[2].equalsIgnoreCase("DA") || values[2].equalsIgnoreCase("RAS") || values[2].equalsIgnoreCase("SI"))
                            branch.add(values[2]);
                        else{
                            String[] tokens=values[2].split("\\|");
                            branch.addAll(Arrays.asList(tokens));

                        }
                        if(values[0].equalsIgnoreCase("T1")){
                            if(!data.addInternship(values[1] /*idCode*/, nr /*number*/, stringTOEnumList( branch) /*branch*/, values[3] /*title*/, values[4] /*local*/))
                                contador++;//T1
                        }
                        else
                            if(!data.addProject(values[1] /*idCode*/, nr /*number*/, stringTOEnumList( branch) /*branch*/, values[3] /*title*/, values[4] /*tEmail*/))
                                contador++;//T2
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }

        if(contador>0)
            return false;

        return true;
    }

    private List<Branches> stringTOEnumList(List<String> branch){
        List<Branches> branchesEnum = new ArrayList<>();
        for(String s: branch){
            switch (s){
                case "DA"-> branchesEnum.add(Branches.DA);
                case "RAS"-> branchesEnum.add(Branches.RAS);
                case "SI"-> branchesEnum.add(Branches.SI);
            }
        }
        return branchesEnum;
    }

    public boolean CUpload(File file) {
        int contador=0;
        try {

            if(!file.exists())
                return false;
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String commaDelimited  = myReader.nextLine();
                String[] values = commaDelimited.split(",");

                if(!data.addCandidature(values))
                    contador++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }

        if(contador>0)
            return false;

        return true;
    }

    public boolean ExportPhase(String nametxt, String print) {
        try {
            FileWriter write = new FileWriter(nametxt);
            write.write(print);
            write.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
