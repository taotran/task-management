package com.pycogroup.taotran.task.management.core.config;

import com.pycogroup.taotran.task.management.core.constant.RoleConst;
import com.pycogroup.taotran.task.management.core.service.auth.MyBasicLookupStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ACLConfig extends GlobalMethodSecurityConfiguration {

    //@formatter:off
    private static final String DATASOURCE_DRIVER   = "datasource.driver"   ;
    private static final String DATASOURCE_URL      = "datasource.url"      ;
    private static final String DATASOURCE_USER     = "datasource.user"     ;
    private static final String DATASOURCE_PASSWORD = "datasource.password" ;
    //@formatter:on

    @Autowired
    private MethodSecurityExpressionHandler
            defaultMethodSecurityExpressionHandler;

    @Resource
    private Environment env;

    @Bean
    //TODO: check the reason why SpringBoot cannot create dataSource bean from configuration file (application.yml)
    public DataSource dataSource() {
        //@formatter:off
        return DataSourceBuilder.create()
                .driverClassName(env.getProperty(DATASOURCE_DRIVER  ))
                .username       (env.getProperty(DATASOURCE_USER    ))
                .password       (env.getProperty(DATASOURCE_PASSWORD))
                .url            (env.getProperty(DATASOURCE_URL     ))
                .build()
                ;
        //@formatter:on
    }


    /* ACL Configuration */
    @Bean
    public MutableAclService aclService() {
        final JdbcMutableAclService aclService = new JdbcMutableAclService(
                dataSource(), lookupStrategy(), aclCache());
        aclService.setClassIdentityQuery("SELECT @@IDENTITY");
        aclService.setSidIdentityQuery("SELECT @@IDENTITY");
        return aclService;
    }

    @Bean
    public MethodSecurityExpressionHandler
    defaultMethodSecurityExpressionHandler() {
        final DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        final AclPermissionEvaluator permissionEvaluator = new AclPermissionEvaluator(aclService());
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        return expressionHandler;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return defaultMethodSecurityExpressionHandler;
    }

    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(
                new SimpleGrantedAuthority(RoleConst.ADMIN));
    }

    @Bean
    public PermissionGrantingStrategy permissionGrantingStrategy() {
        return new DefaultPermissionGrantingStrategy(
                new ConsoleAuditLogger());
    }

    @Bean
    public EhCacheBasedAclCache aclCache() {
        return new EhCacheBasedAclCache(
                aclEhCacheFactoryBean().getObject(),
                permissionGrantingStrategy(),
                aclAuthorizationStrategy()
        );
    }

    @Bean
    public EhCacheFactoryBean aclEhCacheFactoryBean() {
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
        ehCacheFactoryBean.setCacheManager(aclCacheManager().getObject());
        ehCacheFactoryBean.setCacheName("aclCache");
        return ehCacheFactoryBean;
    }

    @Bean("aclCacheManager")
    public EhCacheManagerFactoryBean aclCacheManager() {
        final EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }

    @Bean
    public LookupStrategy lookupStrategy() {
        return new MyBasicLookupStrategy(
                dataSource(),
                aclCache(),
                aclAuthorizationStrategy(),
                new ConsoleAuditLogger()
        );
    }

    @Bean
    public JdbcOperations jdbcOperations() {
        return new JdbcTemplate(dataSource());
    }
}
