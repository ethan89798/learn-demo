package org.ethan.demo.zookeeper.d03;

import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;

public class ACLPwdTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ACLPwdTest.class);
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String digest = DigestAuthenticationProvider.generateDigest("123456");
        LOGGER.info("digest={}", digest);
    }
}
