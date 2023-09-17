package UTIL;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;

public class StatsInTxtFile implements Stats {
    @Override
    public void display(HashMap<String,String> statistics) {

        String filePath = "statistics.txt";

        String report="";

        for (String state : statistics.keySet()) {

            System.out.println(state + "    " + statistics.get(state));
            report = report.concat(state + "    " + statistics.get(state)+"\n");

        }


        try {

            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(report);
            bufferedWriter.newLine();
            bufferedWriter.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}

