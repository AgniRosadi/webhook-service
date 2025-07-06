package com.webhook.service.webhook_service.controller;

import com.webhook.service.webhook_service.dto.PaymentWebhookRequest;
import com.webhook.service.webhook_service.service.WebHookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class WebHookController {
    @Autowired
    private WebHookService webhookService;

    @PostMapping("/payment")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader HttpHeaders headers) throws Exception {
        String signatureHeader = headers.getFirst("X-Signature");

        System.out.println("Payload diterima: " + payload);
        System.out.println("Signature dari header: " + signatureHeader);

        if (webhookService.isSignatureValid(payload, signatureHeader)) {
            System.out.println("✅ Signature valid, proses lanjut");
            return ResponseEntity.ok("Received & Verified");
        } else {
            System.out.println("❌ Signature tidak valid, abaikan");
            return ResponseEntity.status(403).body("Invalid Signature");
        }
    }
}
