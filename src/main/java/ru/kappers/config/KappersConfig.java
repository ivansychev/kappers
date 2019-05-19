package ru.kappers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@PropertySource("classpath:/application.properties")
@EnableScheduling
@EnableConfigurationProperties({KappersProperties.class})
public class KappersConfig {

    private Environment environment;
    private KappersProperties kappersProperties;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Autowired
    public void setKappersProperties(KappersProperties kappersProperties) {
        this.kappersProperties = kappersProperties;
    }

    @Bean
    public TaskScheduler mainScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(kappersProperties.getTaskSchedulerPoolSize());
        return scheduler;
    }
}