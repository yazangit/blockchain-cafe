package com.blockchaincafe.nfcbridge.controller;

import com.blockchaincafe.nfcbridge.dto.request.NfcTapRequest;
import com.blockchaincafe.nfcbridge.dto.response.NfcTapResponse;
import com.blockchaincafe.nfcbridge.service.NfcTapService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nfc")
public class NfcTapController {

    private final NfcTapService nfcTapService;

    @PostMapping("/tap")
    public NfcTapResponse tap(@Valid @RequestBody NfcTapRequest request) {
        return nfcTapService.acceptTap(request);
    }
}
