package club.cerbur.gdutcrawler.controller;

import club.cerbur.gdutcrawler.service.ICrawlerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author cerbur
 */
@RestController
public class CrawlerController {
    @Resource
    private ICrawlerService crawlerService;

    @PostMapping("/class")
    public String getScheduleJson(String schoolId, String password) {
        try {
            return crawlerService.getScheduleJson(crawlerService.getLoginResponse(schoolId, password));
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @PostMapping("/grade")
    public String getGradeJson(String schoolId, String password) {
        try {
            return crawlerService.getGradeJson(crawlerService.getLoginResponse(schoolId, password));
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @PostMapping("/exam")
    public String getExamJson(String schoolId, String password) {
        try {
            return crawlerService.getExamJson(crawlerService.getLoginResponse(schoolId, password));
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @PostMapping("/campus")
    public String getCampusJson(String schoolId, String password) {
        try {
            return crawlerService.getCampusJson(crawlerService.getLoginResponse(schoolId, password));
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @PostMapping("/all")
    public String getAllJson(String schoolId, String password) {
        try {
            return crawlerService.getAllJson(crawlerService.getLoginResponse(schoolId, password));
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}