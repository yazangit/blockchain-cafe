package com.blockchaincafe.nfcbridge.controller;

import com.blockchaincafe.nfcbridge.dto.response.NfcPendingIntentResponse;
import com.blockchaincafe.nfcbridge.service.NfcCheckoutIntentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nfc")
public class NfcPendingIntentController {

    private final NfcCheckoutIntentQueryService nfcCheckoutIntentQueryService;

    @GetMapping("/pending/{cardToken}")
    public NfcPendingIntentResponse getPending(@PathVariable String cardToken) {
        return nfcCheckoutIntentQueryService.getPendingIntent(cardToken);
    }
}
