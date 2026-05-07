package com.blockchaincafe.nfcbridge.controller;

import com.blockchaincafe.nfcbridge.dto.response.NfcTapResolveResponse;
import com.blockchaincafe.nfcbridge.state.NfcResolutionState;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nfc")
public class NfcLastResolutionController {

    private final NfcResolutionState resolutionState;

    @GetMapping("/last-resolution")
    public NfcTapResolveResponse getLastResolution() {
        NfcTapResolveResponse last = resolutionState.getLastResolution();

        if (last == null) {
            return NfcTapResolveResponse.builder()
                    .accepted(false)
                    .resolved(false)
                    .resolutionStatus("NO_RESOLUTION_YET")
                    .message("No NFC resolution has been recorded yet")
                    .build();
        }

        return last;
    }
}
