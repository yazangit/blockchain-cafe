package com.blockchaincafe.nfcbridge.service;

import com.blockchaincafe.nfcbridge.dto.response.NfcPendingIntentResponse;

public interface NfcCheckoutIntentQueryService {
    NfcPendingIntentResponse getPendingIntent(String cardToken);
}
