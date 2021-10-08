package com.fs.authDb.config;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

/**
 * @author: fengchangxin
 * @create: 2021-09-26 20:28
 * @description: 自定义实现通过clientid查询客户端配置信息
 */
//@Service
//public class ClientDetailsServiceImpl implements ClientDetailsService {
//    @Override
//    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
//        //TODO 自定义实现数据库查询
//        BaseClientDetails clientDetails = new BaseClientDetails();
//        return clientDetails;
//    }
//}
