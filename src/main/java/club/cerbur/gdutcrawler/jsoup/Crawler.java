package club.cerbur.gdutcrawler.jsoup;

import club.cerbur.gdutcrawler.exception.EducationSystemException;
import club.cerbur.gdutcrawler.exception.LoginException;
import club.cerbur.gdutcrawler.exception.MaxFrequencyException;
import club.cerbur.gdutcrawler.exception.ParameterIsNullException;

import java.io.IOException;

/**
 * @author cerbur
 */
public interface Crawler {
    int DEFAULT_OUT_TIME = 5000;
    int DEFAULT_MAX_FREQUENCY = 3;

    /**
     * 链接请求
     *
     * @return 结果集合
     */
    Crawler.Response connect() throws IOException, ParameterIsNullException, EducationSystemException, LoginException, MaxFrequencyException;

    Crawler timeout(int i);


    interface Response {
        //最长请求时间，单位毫秒
        int DEFAULT_OUT_TIME = 5000;
        //最大请求次数
        int DEFAULT_MAX_FREQUENCY = 3;
    }
}
