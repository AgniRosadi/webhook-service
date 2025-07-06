package com.webhook.service.webhook_service.service;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
public class WebHookService {
    private static final String SECRET = "RAHASIA-BERSAMA";

    public boolean isSignatureValid(String payload, String signatureHeader) throws Exception {
        String calculatedSignature = calculateHMAC(payload, SECRET);
        return calculatedSignature.equals(signatureHeader);
    }

    private String calculateHMAC(String data, String secret) throws Exception {
        /*  Convert secret jadi array byte dengan algortima HmacSHA256 */
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        /*  Created Object Mac (Message Authentication Code) */
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKey);
        /* Convert data payload jadi array byte */
        byte[] hmacBytes = mac.doFinal(data.getBytes());
        return Hex.encodeHexString(hmacBytes);
    }
}
