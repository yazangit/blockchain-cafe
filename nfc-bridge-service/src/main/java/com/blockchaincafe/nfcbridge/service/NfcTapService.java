package com.blockchaincafe.nfcbridge.service;

import com.blockchaincafe.nfcbridge.dto.request.NfcTapRequest;
import com.blockchaincafe.nfcbridge.dto.response.NfcTapResponse;

public interface NfcTapService {
    NfcTapResponse acceptTap(NfcTapRequest request);
}
