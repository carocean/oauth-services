package com.fs.auth.config;

import com.fs.auth.mobile.SmsCodeTokenGranter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RedisAuthCodeStoreServices redisAuthCodeStoreServices;
    @Autowired
    ClientDetailsService remoteClientDetailsService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenStore tokenStore;
    @Autowired
    WebResponseExceptionTranslator customExceptionTranslator;
    @Autowired
    AuthenticationEntryPoint customAuthenticationEntryPoint;
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        CustomClientCredentialsTokenEndpointFilter endpointFilter = new CustomClientCredentialsTokenEndpointFilter(security);
        endpointFilter.afterPropertiesSet();
        endpointFilter.setAuthenticationEntryPoint(customAuthenticationEntryPoint);
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()")
//                .tokenKeyAccess("isAuthenticated()")
                .tokenKeyAccess("permitAll()")
                .addTokenEndpointAuthenticationFilter(endpointFilter);
        ;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(remoteClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        HandlerInterceptor interceptor = new MyWebRequestInterceptor();
        endpoints.addInterceptor(interceptor);
        endpoints.authenticationManager(authenticationManager);
        endpoints.userDetailsService(userDetailsService);
        endpoints.tokenStore(tokenStore);
        endpoints.tokenEnhancer(createTokenEnhancer());
        endpoints.tokenGranter(tokenGranter(endpoints));
        endpoints.authorizationCodeServices(redisAuthCodeStoreServices);
        endpoints.exceptionTranslator(customExceptionTranslator);
//        refresh token有两种使用方式：重复使用(true)、非重复使用(false)，默认为true
//        - 重复使用：access token过期刷新时， refresh token过期时间未改变，仍以初次生成的时间为准
//        - 非重复使用：access token过期刷新时， refresh token过期时间延续，在refresh token有效期内刷新便永不失效达到
        endpoints.reuseRefreshTokens(false);
        super.configure(endpoints);
    }
//添加granters

    /*
     * 添加granter传入认证管理器，认证管理器的一个认证提供器链在认证时会被执行。对应可再添加其authProvider和authDetailsServices.
     * 扩展的授权类型不走认证入口点(authEntrypoint)流程，即：不能访问/oauth/authorize。只能向地址/oauth/token直接请求令牌，即只走(tokenEntrypoint。
     * 系统自带的5种都走认证入口点流程，但简化模式已证实不能走令牌入口点(/oauth/token)
     * @param endpoints
     * @return
     */
    private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>(Arrays.asList(endpoints.getTokenGranter()));// 获取默认的granter集合
        granters.add(new SmsCodeTokenGranter(authenticationManager, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        return new CompositeTokenGranter(granters);
    }

    private TokenEnhancer createTokenEnhancer() {
        return (accessToken, authentication) -> {
            if (accessToken instanceof DefaultOAuth2AccessToken) {
                DefaultOAuth2AccessToken token = ((DefaultOAuth2AccessToken) accessToken);
                token.setValue(createNewToken());
                token.setRefreshToken(new DefaultOAuth2RefreshToken(createNewToken()));
                token.setAdditionalInformation(accessToken.getAdditionalInformation());
                return token;
            }
            return accessToken;
        };
    }

    private String createNewToken() {
        return DigestUtils.md5Hex(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
    }
}
