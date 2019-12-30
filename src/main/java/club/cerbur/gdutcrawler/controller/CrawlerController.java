package club.cerbur.gdutcrawler.controller;

import club.cerbur.gdutcrawler.exception.EducationSystemException;
import club.cerbur.gdutcrawler.exception.LoginException;
import club.cerbur.gdutcrawler.exception.MaxFrequencyException;
import club.cerbur.gdutcrawler.exception.ParameterIsNullException;
import club.cerbur.gdutcrawler.jsoup.GdutCrawler;
import club.cerbur.gdutcrawler.result.GdutResult;
import club.cerbur.gdutcrawler.result.ResultStatus;
import club.cerbur.gdutcrawler.result.ResultUtil;
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
    public GdutResult<Object> getScheduleJson(String schoolId, String password) {
        try {
            GdutCrawler.Response response = crawlerService.getLoginResponse(schoolId, password);
            if (response == null) {
                return ResultUtil.error(ResultStatus.JSOUPERROR);
            }
            return ResultUtil.success(crawlerService.getScheduleJson(response));
        } catch (MaxFrequencyException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.MAXFREQUENCYERROR);
        } catch (LoginException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.LOGINERROR);
        } catch (ParameterIsNullException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.PARAMMISSERROR);
        } catch (EducationSystemException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.EDUCATIONSYSTEMERROR);
        }
    }

    @PostMapping("/grade")
    public GdutResult<Object> getGradeJson(String schoolId, String password) {
        try {
            GdutCrawler.Response response = crawlerService.getLoginResponse(schoolId, password);
            if (response == null) {
                return ResultUtil.error(ResultStatus.JSOUPERROR);
            }
            return ResultUtil.success(crawlerService.getGradeJson(response));
        } catch (MaxFrequencyException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.MAXFREQUENCYERROR);
        } catch (LoginException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.LOGINERROR);
        } catch (ParameterIsNullException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.PARAMMISSERROR);
        } catch (EducationSystemException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.EDUCATIONSYSTEMERROR);
        }
    }

    @PostMapping("/exam")
    public GdutResult<Object> getExamJson(String schoolId, String password) {
        try {
            GdutCrawler.Response response = crawlerService.getLoginResponse(schoolId, password);
            if (response == null) {
                return ResultUtil.error(ResultStatus.JSOUPERROR);
            }
            return ResultUtil.success(crawlerService.getExamJson(response));
        } catch (MaxFrequencyException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.MAXFREQUENCYERROR);
        } catch (LoginException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.LOGINERROR);
        } catch (ParameterIsNullException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.PARAMMISSERROR);
        } catch (EducationSystemException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.EDUCATIONSYSTEMERROR);
        }
    }

    @PostMapping("/campus")
    public GdutResult<Object> getCampusJson(String schoolId, String password) {
        try {
            GdutCrawler.Response response = crawlerService.getLoginResponse(schoolId, password);
            if (response == null) {
                return ResultUtil.error(ResultStatus.JSOUPERROR);
            }
            return ResultUtil.success(crawlerService.getCampusJson(response));
        } catch (MaxFrequencyException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.MAXFREQUENCYERROR);
        } catch (LoginException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.LOGINERROR);
        } catch (ParameterIsNullException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.PARAMMISSERROR);
        } catch (EducationSystemException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.EDUCATIONSYSTEMERROR);
        }
    }

    @PostMapping("/all")
    public GdutResult<Object> getAllJson(String schoolId, String password) {
        try {
            GdutCrawler.Response response = crawlerService.getLoginResponse(schoolId, password);
            if (response == null) {
                return ResultUtil.error(ResultStatus.JSOUPERROR);
            }
            return ResultUtil.success(crawlerService.getAllJson(response));
        } catch (MaxFrequencyException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.MAXFREQUENCYERROR);
        } catch (LoginException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.LOGINERROR);
        } catch (ParameterIsNullException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.PARAMMISSERROR);
        } catch (EducationSystemException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultStatus.EDUCATIONSYSTEMERROR);
        }
    }
}