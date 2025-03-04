package com.classroom.config.jpa;

import com.classroom.constant.CacheConstants;
import com.classroom.domain.base.ApplicationAuditorAware;
import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This class holds JPA configurations for this application.
 *
 * @author Md Jewel
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableCaching
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories(
    repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class,
    basePackages = "com.classroom.repository")
@EntityScan(basePackages = "com.classroom.domain")
public class JpaConfig {

  /**
   * AuditorAware bean used for auditing.
   *
   * @return Application implementation of AuditorAware.
   */
  @Bean
  public AuditorAware<String> auditorAware() {
    return new ApplicationAuditorAware();
  }

  /**
   * Creates maps to manage the cacheable objects.
   *
   * @return the cacheManager
   */
  @Bean
  public CacheManager cacheManager() {
    SimpleCacheManager cacheManager = new SimpleCacheManager();
    cacheManager.setCaches(
        List.of(
            new ConcurrentMapCache( CacheConstants.USERS),
            new ConcurrentMapCache(CacheConstants.USER_DETAILS),
            new ConcurrentMapCache(CacheConstants.ROLES)));
    return cacheManager;
  }
}
