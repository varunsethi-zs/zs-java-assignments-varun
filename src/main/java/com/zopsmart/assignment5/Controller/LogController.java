package com.zopsmart.assignment5.Controller;

import com.zopsmart.assignment5.Models.Commit;
import com.zopsmart.assignment5.Models.GitLogParsingException;
import com.zopsmart.assignment5.Service.LogService;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * LogController Class For Taking User Input
 */
public class LogController {


    /**
     * logInputs Function For Taking User Input
     */
    public void logInputs() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter date (yyyy-MM-dd) since which commits are to be considered: ");
        String sinceDateString = scanner.nextLine();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sinceDate = simpleDateFormat.parse(sinceDateString);


        List<Commit> commits = null;
        LogService logService = new LogService();


        try {
            commits = logService.parseGitLog("src/main/java/com/zopsmart/assignment5/resources/log.txt", sinceDate);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: at ");
        } catch (GitLogParsingException e) {
            System.out.println("Invalid Git log file format: " + e.getMessage());
        }
        assert commits != null;
        Map<String, Integer> countByAuthor = logService.countCommitsByAuthor(commits, sinceDate);
        System.out.println("Total count of commits by author since " + sinceDate + ":");
        System.out.println(countByAuthor);

        Map<String, Map<Date, Integer>> countByAuthorAndDate = logService.countCommitsByAuthorAndDate(commits, sinceDate);
        System.out.println("Count of commits by author and date since " + sinceDate + ":");
        System.out.println(countByAuthorAndDate);

    }
}
