package com.blockchaincafe.nfcbridge.service;

import com.blockchaincafe.nfcbridge.dto.request.NfcCheckoutIntentRequest;
import com.blockchaincafe.nfcbridge.dto.response.NfcCheckoutIntentResponse;

public interface NfcCheckoutIntentService {
    NfcCheckoutIntentResponse storeIntent(NfcCheckoutIntentRequest request);
}
