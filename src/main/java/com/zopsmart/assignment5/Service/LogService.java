package com.zopsmart.assignment5.Service;

import com.zopsmart.assignment5.Models.Commit;
import com.zopsmart.assignment5.Models.GitLogParsingException;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;


/**
 * LogService Class For Implementation Of Reading and Parsing file And Getting Information About Commit Status
 */
public class LogService {
    Map<String, Map<Date, Integer>> commitCounts = new HashMap<>();
    List<Commit> commits = new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * parseGitLog function For Implementation Of Reading and Parsing file
     */
    public List<Commit> parseGitLog(String pathname, Date sinceDate) throws GitLogParsingException, FileNotFoundException, ParseException {
        Map<String, Map<Date, Integer>> commitCounts = new HashMap<>();
        File file = new File("src/main/java/com/zopsmart/assignment5/resources/log.txt");
        HashMap<String, String> monthNumber = new HashMap<>();
        monthNumber.put("Jan", "01");
        monthNumber.put("Feb", "02");
        monthNumber.put("Mar", "03");
        monthNumber.put("Apr", "04");
        monthNumber.put("May", "05");
        monthNumber.put("Jun", "06");
        monthNumber.put("Jul", "07");
        monthNumber.put("Aug", "08");
        monthNumber.put("Sep", "09");
        monthNumber.put("Oct", "10");
        monthNumber.put("Nov", "11");
        monthNumber.put("Dec", "12");

        if (!file.exists()) {
            throw new FileNotFoundException("File not found at : " + pathname);
        }

        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("commit ");


        while (scanner.hasNext()) {
            String commitText = scanner.next();
            String[] lines = commitText.split("\n");

            if (lines.length < 4) {
                throw new GitLogParsingException("Incomplete commit information: " + commitText);
            }
            if (lines[1].contains("Merge")) {
                continue;
            }

            String author = lines[1].substring("Author: ".length());
            String[] dates = lines[2].split(" ");
            Date date = simpleDateFormat.parse(dates[7] + "-" + monthNumber.get(dates[4]) + "-" + dates[5]);
            String message = lines[3];

            if (date.before(sinceDate)) {
                continue;
            }

            Commit commit = new Commit(author, date, message);
            commits.add(commit);
        }

        scanner.close();

        if (commits.isEmpty()) {
            throw new GitLogParsingException("No commits found since " + sinceDate);
        }

        return commits;

    }

    /**
     * countCommitsByAuthor Function returns Number of commits since given date
     */

    public Map<String, Integer> countCommitsByAuthor(List<Commit> commits, Date sinceDate) {
        Map<String, Integer> commitCounts = new HashMap<>();

        for (Commit commit : commits) {
            if (commit.getCommitDate().after(sinceDate)) {
                String author = commit.getAuthor();
                int count = commitCounts.getOrDefault(author, 0);
                commitCounts.put(author, count + 1);
            }
        }

        return commitCounts;
    }


    /**
     * countCommitsByAuthorAndDate  returns commits by each developer since date d, for each day
     */
    public Map<String, Map<Date, Integer>> countCommitsByAuthorAndDate(List<Commit> commits, Date sinceDate) {


        for (Commit commit : commits) {
            if (commit.getCommitDate().before(sinceDate)) {
                continue;
            }
            String author = commit.getAuthor();


            commitCounts.put(author, new HashMap<>());


            Map<Date, Integer> authorCommitCounts = commitCounts.get(author);
            int count = authorCommitCounts.getOrDefault(sinceDate, 0);
            authorCommitCounts.put(sinceDate, count + 1);
        }
        return commitCounts;
    }
}
