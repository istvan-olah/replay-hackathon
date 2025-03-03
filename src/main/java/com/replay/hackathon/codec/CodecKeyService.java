package com.replay.hackathon.codec;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CodecKeyService {
    private static final Logger log = LoggerFactory.getLogger(CodecKeyService.class);

    public record EncodingKey(String keyId, SecretKey key) {}

    private static final String AES = "AES";

    private String currentKeyId;

    public CodecKeyService() {
        // initialize with a new key or take newest key from repository
        this.currentKeyId = Long.toString(System.currentTimeMillis());
    }

    @Scheduled(fixedRate = 5000)
    public void rotateKey() {
        this.currentKeyId = Long.toString(System.currentTimeMillis());
        log.info("The key has been rotated");
        //save the new key in the key repository
    }

    public SecretKey getSecretKey(String keyId) {
        // should be
        // keyRepository.getSecretKey(keyId);
        String key = keyId + "-secret-key";
        return new SecretKeySpec(key.getBytes(UTF_8), AES);
    }

    public EncodingKey getCurrentKey() {
        return new EncodingKey(currentKeyId, getSecretKey(currentKeyId));
    }
}