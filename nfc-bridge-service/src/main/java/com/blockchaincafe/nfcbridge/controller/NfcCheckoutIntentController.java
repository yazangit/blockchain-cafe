package com.blockchaincafe.nfcbridge.controller;

import com.blockchaincafe.nfcbridge.dto.request.NfcCheckoutIntentRequest;
import com.blockchaincafe.nfcbridge.dto.response.NfcCheckoutIntentResponse;
import com.blockchaincafe.nfcbridge.service.NfcCheckoutIntentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nfc")
public class NfcCheckoutIntentController {

    private final NfcCheckoutIntentService nfcCheckoutIntentService;

    @PostMapping("/checkout-intent")
    public NfcCheckoutIntentResponse checkoutIntent(@Valid @RequestBody NfcCheckoutIntentRequest request) {
        return nfcCheckoutIntentService.storeIntent(request);
    }
}
