package com.fs.authDb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: fengchangxin
 * @create: 2021-09-26 20:28
 * @description: 自定义实现通过clientid查询客户端配置信息
 */
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        //TODO 自定义实现数据库查询
        String sql = "select id,client_id,client_secret from client_details_test where client_id=?";
        return jdbcTemplate.queryForObject(sql,new ClientDetailsTestRowMapper(),clientId);
    }

    private static class ClientDetailsTestRowMapper implements RowMapper<ClientDetails> {
        @Override
        public ClientDetails mapRow(ResultSet rs, int i) throws SQLException {
            BaseClientDetails clientDetails = new BaseClientDetails();
            clientDetails.setClientId(rs.getString(1));
            clientDetails.setClientSecret(rs.getString(2));
            System.out.println("结果"+rs.getString(1));
            System.out.println(rs.getString(2));
            return clientDetails;
        }
    }
}
