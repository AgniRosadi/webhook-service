package com.webhook.service.webhook_service.controller;

import com.webhook.service.webhook_service.service.WebHookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class WebHookController {
    @Autowired
    private WebHookService webhookService;

    @PostMapping("/payment")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload,
                                                @RequestHeader(value = "X-Signature", required = false) String signatureHeader) {
        System.out.println("Payload diterima: " + payload);
        System.out.println("Signature dari header: " + signatureHeader);

        try {
            if (webhookService.isSignatureValid(payload, signatureHeader)) {
                System.out.println("✅ Signature valid, proses lanjut");
                return ResponseEntity.ok("Received & Verified");
            } else {
                System.out.println("❌ Signature tidak valid, abaikan");
                return ResponseEntity.status(403).body("Invalid Signature");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

}
