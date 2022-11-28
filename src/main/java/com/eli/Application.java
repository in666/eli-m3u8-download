package com.eli;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动程序
 *
 * @author eli
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        LoggerFactory.getLogger(Application.class).info(">>>>>>  The app service started successfully.  >>>>>>  应用服务启动成功！");
    }
}
