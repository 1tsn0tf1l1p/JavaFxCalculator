package app.model;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Database instance;
    List<Result> allResults;

    private Database() {
        allResults = new ArrayList<>();
        loadFiles();
    }

    private void loadFiles() {
        File file = new File("results.txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splited = line.split(";");
                BigDecimal value;
                try {
                    value = new BigDecimal(splited[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format: " + splited[1]);
                    continue;
                }
                LocalDateTime date;
                try {
                    date = LocalDateTime.parse(splited[2]);
                } catch (Exception e) {
                    System.err.println("Invalid date format: " + splited[2]);
                    continue;
                }
                Result r = new Result(splited[0], value, date);
                allResults.add(r);
            }
            System.out.println(allResults);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + file.getAbsolutePath(), e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + file.getAbsolutePath(), e);
        }
    }

    public void write(Result r) {
        if(r.getExpression().contains("/0")) {
            return;
        }else {
            File file = new File("results.txt");
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(r.getExpression()).append(";").append(r.getResult()).append(";")
                        .append(r.getDateTime()).append("\n");
                System.out.println(stringBuilder);
                bufferedWriter.write(stringBuilder.toString());
                bufferedWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Database getInstance() {
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void refreshDatabase() {
        allResults.clear();
        loadFiles();
    }

    public static void setInstance(Database instance) {
        Database.instance = instance;
    }

    public List<Result> getAllResults() {
        return allResults;
    }

    public void setAllResults(List<Result> allResults) {
        this.allResults = allResults;
    }
}
