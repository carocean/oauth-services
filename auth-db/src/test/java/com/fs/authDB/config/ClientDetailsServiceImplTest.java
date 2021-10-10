package com.fs.authDB.config;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientDetailsServiceImplTest {
    @Autowired
    private ClientDetailsService clientDetailsManager;

    @Test
    public void loadClientByClientId() {
        clientDetailsManager.loadClientByClientId("client1");
    }
}