package com.blockchaincafe.order.controller;




import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/nfc")
@CrossOrigin(origins = "*")
public class NfcController {

    private final Map<String, Map<String, Object>> nfcPayments = new ConcurrentHashMap<>();

    @PostMapping("/tap-and-resolve")
    public ResponseEntity<?> tapAndResolve(@RequestBody Map<String, Object> request) {
        String cardToken = String.valueOf(request.get("cardToken"));

        Map<String, Object> result = Map.of(
                "paid", true,
                "status", "NFC_PAYMENT_CONFIRMED",
                "message", "NFC payment confirmed successfully",
                "cardToken", cardToken,
                "resolvedCustomer", "Demo Customer",
                "amount", 7.60,
                "payerType", "PRIVATE",
                "timestamp", Instant.now().toString()
        );

        nfcPayments.put(cardToken, result);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/status/{cardToken}")
    public ResponseEntity<?> getStatus(@PathVariable String cardToken) {
        Map<String, Object> result = nfcPayments.get(cardToken);

        if (result == null) {
            return ResponseEntity.ok(Map.of(
                    "paid", false,
                    "cardToken", cardToken,
                    "status", "WAITING_FOR_NFC"
            ));
        }

        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/status/{cardToken}")
public ResponseEntity<?> resetStatus(@PathVariable String cardToken) {
    nfcPayments.remove(cardToken);
    return ResponseEntity.ok(Map.of(
            "paid", false,
            "cardToken", cardToken,
            "status", "RESET"
    ));
}
}
