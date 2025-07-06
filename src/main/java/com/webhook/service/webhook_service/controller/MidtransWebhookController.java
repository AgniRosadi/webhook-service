package com.webhook.service.webhook_service.controller;

import com.webhook.service.webhook_service.model.MidtransNotification;
import com.webhook.service.webhook_service.service.MidtransWebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook/v2")
public class MidtransWebhookController {
    @Autowired
    private MidtransWebhookService webhookService;

    public MidtransWebhookController(MidtransWebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @PostMapping("/payment")
    public ResponseEntity<String> handlePaymentWebhook(@RequestBody MidtransNotification notification) {
        System.out.println("✅ Webhook diterima: " + notification);

        boolean signatureValid = webhookService.isSignatureValid(
                notification.getOrderId(),
                notification.getStatusCode(),
                notification.getGrossAmount(),
                notification.getSignatureKey()
        );

        if (signatureValid) {
            System.out.println("✅ Signature valid, proses lanjut");
            return ResponseEntity.ok("Received & Verified");
        } else {
            System.out.println("❌ Signature tidak valid, abaikan");
            return ResponseEntity.status(403).body("Invalid Signature");
        }
    }
}
