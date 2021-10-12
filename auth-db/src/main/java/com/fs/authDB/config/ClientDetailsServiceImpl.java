package com.fs.authDB.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: fengchangxin
 * @create: 2021-09-26 20:28
 * @description: 自定义实现通过clientid查询客户端配置信息
 */
@Service("clientDetailsManager")
public class ClientDetailsServiceImpl implements ClientDetailsService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        //TODO 自定义实现数据库查询
        String sql = "select client_id,client_secret,resource_ids, scope, authorized_grant_types, web_server_redirect_uri, " +
                "authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove from client_details_test where client_id=?";
        return jdbcTemplate.queryForObject(sql,new ClientDetailsTestRowMapper(),clientId);
    }

    private static class ClientDetailsTestRowMapper implements RowMapper<ClientDetails> {
        @Override
        public ClientDetails mapRow(ResultSet rs, int i) throws SQLException {
            BaseClientDetails details = new BaseClientDetails(rs.getString(1), rs.getString(3), rs.getString(4),
                    rs.getString(5), rs.getString(7), rs.getString(6));
            details.setClientSecret(rs.getString(2));
            if (rs.getObject(8) != null) {
                details.setAccessTokenValiditySeconds(rs.getInt(8));
            }
            if (rs.getObject(9) != null) {
                details.setRefreshTokenValiditySeconds(rs.getInt(9));
            }
            String scopes = rs.getString(11);
            if (scopes != null) {
                details.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(scopes));
            }
            return details;
        }
    }
}
