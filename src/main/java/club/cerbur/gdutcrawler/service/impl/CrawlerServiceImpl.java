package club.cerbur.gdutcrawler.service.impl;

import club.cerbur.gdutcrawler.exception.EducationSystemException;
import club.cerbur.gdutcrawler.exception.LoginException;
import club.cerbur.gdutcrawler.exception.MaxFrequencyException;
import club.cerbur.gdutcrawler.exception.ParameterIsNullException;
import club.cerbur.gdutcrawler.jsoup.GdutCrawler;
import club.cerbur.gdutcrawler.model.Exam;
import club.cerbur.gdutcrawler.model.Grade;
import club.cerbur.gdutcrawler.model.Schedule;
import club.cerbur.gdutcrawler.service.ICrawlerService;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author cerbur
 */
@Service
public class CrawlerServiceImpl implements ICrawlerService {

    @Override
    public GdutCrawler.Response getLoginResponse(String schoolId, String password) throws ParameterIsNullException, EducationSystemException, LoginException {
        GdutCrawler crawler = new GdutCrawler(schoolId, password);
        try {
            try {
                return crawler.timeout(50000).connect();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } catch (ParameterIsNullException | EducationSystemException | LoginException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public String getScheduleJson(GdutCrawler.Response response) throws MaxFrequencyException {
        String res;
        try {
            res = response.timeout(50000).getSchedule();
        } catch (MaxFrequencyException e) {
            e.printStackTrace();
            throw e;
        }
        List<Schedule> schedules = JSON.parseArray(res, Schedule.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            res = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(schedules);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String getGradeJson(GdutCrawler.Response response) throws MaxFrequencyException {
        String res;
        try {
            res = response.timeout(500000).getGrade();
        } catch (MaxFrequencyException e) {
            e.printStackTrace();
            throw e;
        }
        List<Grade> grades = JSON.parseArray(res, Grade.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            res = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(grades);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String getExamJson(GdutCrawler.Response response) throws MaxFrequencyException {
        String res;
        try {
            res = response.timeout(500000).getExam();
        } catch (MaxFrequencyException e) {
            e.printStackTrace();
            throw e;
        }
        List<Exam> exams = JSON.parseArray(res, Exam.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            res = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exams);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String getCampusJson(GdutCrawler.Response response) throws MaxFrequencyException {
        String res;
        try {
            res = response.getCampus();
        } catch (MaxFrequencyException e) {
            e.printStackTrace();
            throw e;
        }
        return "{\"campus\":\"" + res + "\"}";
    }

    @Override
    public String getAllJson(GdutCrawler.Response response) throws MaxFrequencyException {
        String curriculum = getScheduleJson(response);
        String exam = getExamJson(response);
        String campus = getCampusJson(response);
        return "{\"curriculum\":" + curriculum + ",\"exam\":" + exam + ",\" campus\":" + campus + "}";
    }
}
