package com.zopsmart.assignment5.Service;

import com.zopsmart.assignment5.Models.Commit;
import com.zopsmart.assignment5.Exceptions.GitLogParsingException;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * LogService Class For Implementation Of Reading and Parsing file And Getting Information About Commit Status
 */
public class LogService {
    Map<String, Map<Date, Integer>> commitCounts = new HashMap<>();
    List<Commit> commits = new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

  private static final String FILEPATH = "/home/raramuri/Java/zs-java-assignments-varun/src/main/java/com/zopsmart/assignment5/resources/log.txt";

    /**
     * parseGitLog function For Implementation Of Reading and Parsing file
     */
    public List<Commit> parseGitLog(Date sinceDate) throws GitLogParsingException, FileNotFoundException, ParseException {
        File file = new File(FILEPATH);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
            throw new GitLogParsingException("File not found at"+ FILEPATH);
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

    public Map<String, Integer> countCommitsByAuthor(Date sinceDate) {
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
    public Map<String, Map<Date, Integer>> countCommitsByAuthorAndDate(Date sinceDate) {
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

    /**
     * countDevelopers function List of developers who did not commit anything successively in 2 days
     */
    public ArrayList<String> countDevelopers(String date) throws GitLogParsingException {
        try {
            ArrayList<String> developers = new ArrayList<>();
            HashMap<String, ArrayList<String>> authorDateMap = new HashMap<>();
            ArrayList<String> dateList = null;
            for (Commit commit : commits) {
                Date d1 = simpleDateFormat.parse(commit.getCommitDate().toString());
                Date d2 = simpleDateFormat.parse(date);
                if (d1.compareTo(d2) >= 0) {
                    if (!authorDateMap.containsKey(commit.getAuthor())) {
                        dateList = new ArrayList<>();
                        dateList.add(commit.getCommitDate().toString());
                        authorDateMap.put(commit.getAuthor(), dateList);
                    } else {
                        if (!dateList.contains((commit.getCommitDate().toString()))) {
                            authorDateMap.get(commit.getAuthor()).add(commit.getCommitDate().toString());
                        }
                    }
                }
            }
            for (HashMap.Entry<String, ArrayList<String>> mapElement : authorDateMap.entrySet()) {
                for (int i = 0; i < mapElement.getValue().size() - 1; i++) {
                    Date dateBefore = simpleDateFormat.parse(mapElement.getValue().get(i));
                    Date dateAfter = simpleDateFormat.parse(mapElement.getValue().get(i + 1));
                    ;
                    long timeDiff = Math.abs(dateAfter.getTime() - dateBefore.getTime());
                    long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
                    if (daysDiff >= 2) {
                        developers.add(mapElement.getKey());
                    }
                }
            }
            return developers;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
