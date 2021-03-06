package com.bejamas.springbootselenium.cucumberTask;

import com.bejamas.springbootselenium.annotations.LazyAutowired;
import com.bejamas.springbootselenium.utils.ScreenshotUtil;
import io.cucumber.java.*;
import org.openqa.selenium.WebDriver;
import org.springframework.context.ApplicationContext;

import java.time.Duration;

public class CucumberHooks {

    @LazyAutowired
    private ScreenshotUtil screenshotUtil;

    @LazyAutowired
    private ApplicationContext applicationContext;

    @LazyAutowired
    WebDriver driver;

    @Before
    public void setup(Scenario scenario){
        driver.manage().window().maximize();
        driver.manage().timeouts().getPageLoadTimeout();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        System.out.println("         ---->"+scenario.getName()+"<----");
    }

    @AfterStep
    public void afterStep(Scenario scenario){
        if(scenario.isFailed()){
            scenario.attach(this.screenshotUtil.getScreenshot(), "image/png", scenario.getName());
        }
    }

    @After
    public void afterScenario(){
        this.applicationContext.getBean(WebDriver.class).quit();
    }

}
