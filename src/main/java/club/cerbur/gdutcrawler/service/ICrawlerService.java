package club.cerbur.gdutcrawler.service;

import club.cerbur.gdutcrawler.exception.EducationSystemException;
import club.cerbur.gdutcrawler.exception.LoginException;
import club.cerbur.gdutcrawler.exception.MaxFrequencyException;
import club.cerbur.gdutcrawler.exception.ParameterIsNullException;
import club.cerbur.gdutcrawler.jsoup.GdutCralwer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author cerbur
 */
@Component
public interface ICrawlerService {

    /**
     * 获取带登录cookies的请求体
     *
     * @param schoolId 学号
     * @param password 密码
     * @return 带登录cookies的请求体
     * @throws LoginException           登录异常/密码错误
     * @throws IOException              IO异常,格式转换错误
     * @throws ParameterIsNullException 参数为空异常/缺少参数
     * @throws EducationSystemException 教务系统异常/教务系统bug
     * @throws MaxFrequencyException    超过最大请求次数异常/没用使用到
     */
    GdutCralwer.Response getLoginResponse(String schoolId, String password) throws LoginException, IOException, ParameterIsNullException, EducationSystemException, MaxFrequencyException;

    /**
     * 获取课表信息
     *
     * @param response 带cookies的亲求体
     * @return 整理过的json字符串
     * @throws MaxFrequencyException   超过最大次数异常
     * @throws JsonProcessingException json转换异常
     */
    String getScheduleJson(GdutCralwer.Response response) throws MaxFrequencyException, JsonProcessingException;

    /**
     * 获取成绩
     *
     * @param response 带cookies的亲求体
     * @return 整理过的json字符串
     * @throws MaxFrequencyException   超过最大次数异常
     * @throws JsonProcessingException json转换异常
     */
    String getGradeJson(GdutCralwer.Response response) throws MaxFrequencyException, JsonProcessingException;

    String getExamJson(GdutCralwer.Response response) throws MaxFrequencyException, JsonProcessingException;

    String getCampusJson(GdutCralwer.Response response) throws MaxFrequencyException, JsonProcessingException;

    String getAllJson(GdutCralwer.Response response) throws MaxFrequencyException, JsonProcessingException;
}
