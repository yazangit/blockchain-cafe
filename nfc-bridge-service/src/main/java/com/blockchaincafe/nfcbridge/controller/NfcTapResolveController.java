package com.blockchaincafe.nfcbridge.controller;

import com.blockchaincafe.nfcbridge.dto.request.NfcTapRequest;
import com.blockchaincafe.nfcbridge.dto.response.NfcTapResolveResponse;
import com.blockchaincafe.nfcbridge.service.NfcTapResolveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nfc")
public class NfcTapResolveController {

    private final NfcTapResolveService nfcTapResolveService;

    @PostMapping("/tap-and-resolve")
    public NfcTapResolveResponse tapAndResolve(@Valid @RequestBody NfcTapRequest request) {
        return nfcTapResolveService.tapAndResolve(request);
    }
}
