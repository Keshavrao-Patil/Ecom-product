package com.edu.Ecom_product.Service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {

    // token -> expiryEpochMillis
    private final Map<String, Long> blacklist = new ConcurrentHashMap<>();

    public void blacklistToken(String token, long expiryEpochMillis) {
        if (token == null) return;
        blacklist.put(token, expiryEpochMillis);
    }

    public boolean isBlacklisted(String token) {
        if (token == null) return false;
        Long expiry = blacklist.get(token);
        if (expiry == null) return false;
        if (expiry < System.currentTimeMillis()) {
            // cleanup expired entry
            blacklist.remove(token);
            return false;
        }
        return true;
    }
}

