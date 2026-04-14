package com.blockchaincafe.blockchain.exception;

public class BlockNotFoundException extends RuntimeException {
    public BlockNotFoundException(String message) {
        super(message);
    }
}
