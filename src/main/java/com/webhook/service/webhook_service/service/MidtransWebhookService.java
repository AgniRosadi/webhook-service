package com.webhook.service.webhook_service.service;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
public class MidtransWebhookService {

    @Value("${midtrans.server-key}")
    private String serverKey;

    public boolean isSignatureValid(String orderId, String statusCode, String grossAmount, String signatureKeyFromBody) {
        try {
            String rawData = orderId + statusCode + grossAmount + serverKey;

            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = md.digest(rawData.getBytes(StandardCharsets.UTF_8));
            String generatedSignature = Hex.encodeHexString(hashBytes);

            return generatedSignature.equals(signatureKeyFromBody);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
