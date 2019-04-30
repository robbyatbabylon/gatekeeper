package com.babylonhealth.bamstemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class BamsTemplateApplication {

  public static void main(String[] args) {
    SpringApplication.run(BamsTemplateApplication.class, args);
  }

  /**
   * Template method to ensure it is responsive when deployed on dev environment. Not required for
   * real services.
   */
  @Scheduled(fixedRate = 600000)
  void scheduleAlive() {
    log.info("I am alive!");
  }
}
