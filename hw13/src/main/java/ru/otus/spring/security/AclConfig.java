package ru.otus.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.AuditLogger;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;

@EnableCaching
@Configuration
public class AclConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CacheManager cacheManager;

    @Bean
    public AuditLogger createAuditLogger() {
        return new ConsoleAuditLogger();
    }

    @Bean
    public AclAuthorizationStrategy createAclAuthorizationStrategy() {
        String role = "ROLE_ADMIN";
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
        return new AclAuthorizationStrategyImpl(grantedAuthority);
    }

    @Bean
    public PermissionGrantingStrategy createPermissionGrantingStrategy() {
        return new DefaultPermissionGrantingStrategy(createAuditLogger());
    }

    @Bean
    public AclCache createAclCache() {
        Cache cache = cacheManager.getCache("aclCache");
        return new SpringCacheBasedAclCache(cache, createPermissionGrantingStrategy(),
                createAclAuthorizationStrategy());
    }

    @Bean
    public LookupStrategy createLookupStrategy() {
        BasicLookupStrategy basicLookupStrategy = new BasicLookupStrategy(dataSource, createAclCache(),
                createAclAuthorizationStrategy(), createPermissionGrantingStrategy());
        return basicLookupStrategy;
    }

    @Bean
    public MethodSecurityExpressionHandler createMethodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        AclPermissionEvaluator permissionEvaluator = new AclPermissionEvaluator(createAclService());
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        expressionHandler.setPermissionCacheOptimizer(new AclPermissionCacheOptimizer(createAclService()));
        return expressionHandler;
    }

    @Bean
    public AclService createAclService() {
        JdbcMutableAclService jdbcMutableAclService = new JdbcMutableAclService(dataSource, createLookupStrategy(),
                createAclCache());
        return jdbcMutableAclService;
    }
}