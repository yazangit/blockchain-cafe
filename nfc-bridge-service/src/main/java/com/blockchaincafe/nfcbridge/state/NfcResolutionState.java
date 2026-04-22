package com.blockchaincafe.nfcbridge.state;

import com.blockchaincafe.nfcbridge.dto.response.NfcTapResolveResponse;
import org.springframework.stereotype.Component;

@Component
public class NfcResolutionState {

    private volatile NfcTapResolveResponse lastResolution;

    public NfcTapResolveResponse getLastResolution() {
        return lastResolution;
    }

    public void setLastResolution(NfcTapResolveResponse lastResolution) {
        this.lastResolution = lastResolution;
    }
}
