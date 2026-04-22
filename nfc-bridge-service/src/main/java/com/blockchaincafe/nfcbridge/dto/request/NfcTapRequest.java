package com.blockchaincafe.nfcbridge.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NfcTapRequest {

    @NotBlank
    private String cardToken;

    @NotBlank
    private String deviceId;

    @NotBlank
    private String readerType;

    @NotBlank
    private String timestamp;
}
