package com.enjoyu.admin.config;

import com.enjoyu.admin.components.shiro.ExtDbRealm;
import com.enjoyu.admin.components.shiro.ShiroUtil;
import com.enjoyu.admin.repository.SysPermissionRepository;
import com.enjoyu.admin.repository.SysRoleRepository;
import com.enjoyu.admin.repository.SysUserRepository;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration;
import org.apache.shiro.spring.boot.autoconfigure.ShiroBeanAutoConfiguration;
import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebAutoConfiguration;
import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebMvcAutoConfiguration;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroWebFilterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * #{@link ShiroAutoConfiguration}
 * #{@link ShiroBeanAutoConfiguration}
 * #{@link ShiroWebMvcAutoConfiguration}
 * #{@link ShiroWebAutoConfiguration}
 * #{@link ShiroWebFilterConfiguration}
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private SysUserRepository userRepository;
    @Autowired
    private SysRoleRepository roleRepository;
    @Autowired
    private SysPermissionRepository permissionRepository;

    @Bean
    public Realm myRealm() {
        ExtDbRealm realm = new ExtDbRealm(userRepository, roleRepository, permissionRepository);
        realm.setCredentialsMatcher(credentialsMatcher());
        return realm;
    }

    @Bean
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(ShiroUtil.alg);
        matcher.setStoredCredentialsHexEncoded(ShiroUtil.isHex);
        matcher.setHashIterations(ShiroUtil.iterations);
        return matcher;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/login", "anon");
        chainDefinition.addPathDefinition("/register", "anon");
        chainDefinition.addPathDefinition("/captcha", "anon");

        chainDefinition.addPathDefinition("/logout", "logout");

        chainDefinition.addPathDefinition("/dist/**", "anon");
        chainDefinition.addPathDefinition("/js/**", "anon");
        chainDefinition.addPathDefinition("/plugins/**", "anon");
        chainDefinition.addPathDefinition("/favicon.ico**", "anon");

        chainDefinition.addPathDefinition("/**", "authc"); // all paths are managed via annotations
        return chainDefinition;
    }

}
