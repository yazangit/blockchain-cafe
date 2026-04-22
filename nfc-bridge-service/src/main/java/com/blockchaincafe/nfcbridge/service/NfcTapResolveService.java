package com.blockchaincafe.nfcbridge.service;

import com.blockchaincafe.nfcbridge.dto.request.NfcTapRequest;
import com.blockchaincafe.nfcbridge.dto.response.NfcTapResolveResponse;

public interface NfcTapResolveService {
    NfcTapResolveResponse tapAndResolve(NfcTapRequest request);
}
