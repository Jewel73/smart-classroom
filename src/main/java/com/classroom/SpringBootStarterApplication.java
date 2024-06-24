package com.classroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The class has the main method to get the application started.
 *
 * @author Md Jewel
 * @version 1.0
 * @since 1.0
 */
@EnableScheduling
@SpringBootApplication
public class SpringBootStarterApplication extends SpringBootServletInitializer {

  /**
   * The application's entry point.
   *
   * @param args an array of command-line arguments for the application
   */
  public static void main(final String[] args) {
    SpringApplication.run(SpringBootStarterApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(SpringBootStarterApplication.class);
  }
}
