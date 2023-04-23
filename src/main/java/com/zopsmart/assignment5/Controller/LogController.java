package com.zopsmart.assignment5.Controller;

import com.zopsmart.assignment5.Exceptions.GitLogParsingException;
import com.zopsmart.assignment5.Service.LogService;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * LogController Class For Taking User Input
 */
public class LogController {

    private static final Logger logger = Logger.getLogger(LogController.class.getName());
    /**
     * logInputs Function For Taking User Input
     */
    public void logInputs() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter date (yyyy-MM-dd) since which commits are to be considered: ");
            String sinceDateString = scanner.nextLine();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date sinceDate = simpleDateFormat.parse(sinceDateString);
            LogService logService = new LogService();


            try {
                logger.info(String.valueOf(logService.parseGitLog(sinceDate)));
            } catch (FileNotFoundException e) {
                System.out.println("File not found:");
            } catch (GitLogParsingException e) {
                System.out.println("Invalid Git log file format: " + e.getMessage());
            }
     logger.info(String.valueOf(logService.countCommitsByAuthor(sinceDate)));
          logger.info(String.valueOf(logService.countCommitsByAuthorAndDate(sinceDate)));
          logger.info(String.valueOf(logService.countDevelopers(sinceDateString)));
        } catch (ParseException parseException) {
            Logger.getLogger(parseException.getMessage());
        }
    }
}
