package club.cerbur.gdutcrawler;

import club.cerbur.gdutcrawler.exception.EducationSystemException;
import club.cerbur.gdutcrawler.exception.LoginException;
import club.cerbur.gdutcrawler.exception.MaxFrequencyException;
import club.cerbur.gdutcrawler.exception.ParameterIsNullException;
import club.cerbur.gdutcrawler.service.ICrawlerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
class GdutApplicationTests {

    @Resource
    private ICrawlerService crawlerService;

    @Test
    void contextLoads() throws IOException, LoginException, MaxFrequencyException, ParameterIsNullException, EducationSystemException {
        // System.out.println(crawlerService.getLoginResponse(" "," "));
    }

}
