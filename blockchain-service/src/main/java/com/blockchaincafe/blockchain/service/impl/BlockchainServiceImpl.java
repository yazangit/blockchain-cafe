package com.blockchaincafe.blockchain.service.impl;

import com.blockchaincafe.blockchain.domain.model.BlockEntity;
import com.blockchaincafe.blockchain.dto.request.CreateBlockRequest;
import com.blockchaincafe.blockchain.dto.response.BlockResponse;
import com.blockchaincafe.blockchain.dto.response.CreateBlockResponse;
import com.blockchaincafe.blockchain.repository.BlockRepository;
import com.blockchaincafe.blockchain.service.BlockchainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlockchainServiceImpl implements BlockchainService {

    private final BlockRepository blockRepository;

    @Override
    public CreateBlockResponse createBlock(CreateBlockRequest request) {
        BlockEntity lastBlock = blockRepository.findTopByOrderByBlockIndexDesc().orElse(null);

        int nextIndex = lastBlock == null ? 1 : lastBlock.getBlockIndex() + 1;
        String previousHash = lastBlock == null ? "GENESIS" : lastBlock.getHashValue();
        Instant now = Instant.now();

        String hashInput = nextIndex + "|" +
                request.getOrderId() + "|" +
                request.getPaymentId() + "|" +
                request.getGrossTotal() + "|" +
                request.getNetTotal() + "|" +
                request.getTotalVat() + "|" +
                request.getPayload() + "|" +
                previousHash + "|" +
                now.toString();

        String currentHash = sha256(hashInput);

        BlockEntity entity = BlockEntity.builder()
                .id(UUID.randomUUID().toString())
                .blockIndex(nextIndex)
                .orderId(request.getOrderId())
                .paymentId(request.getPaymentId())
                .grossTotal(request.getGrossTotal())
                .netTotal(request.getNetTotal())
                .totalVat(request.getTotalVat())
                .payload(request.getPayload())
                .previousHash(previousHash)
                .hashValue(currentHash)
                .createdAt(now)
                .build();

        BlockEntity saved = blockRepository.save(entity);

        return CreateBlockResponse.builder()
                .blockId(saved.getId())
                .blockIndex(saved.getBlockIndex())
                .hash(saved.getHashValue())
                .previousHash(saved.getPreviousHash())
                .build();
    }

    @Override
    public boolean validateChain() {
        List<BlockEntity> chain = blockRepository.findAllByOrderByBlockIndexAsc();

        if (chain.isEmpty()) {
            return true;
        }

        for (int i = 1; i < chain.size(); i++) {
            BlockEntity current = chain.get(i);
            BlockEntity previous = chain.get(i - 1);

            if (!current.getPreviousHash().equals(previous.getHashValue())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public List<BlockResponse> getAllBlocks() {
        return blockRepository.findAllByOrderByBlockIndexAsc().stream()
                .map(block -> BlockResponse.builder()
                        .id(block.getId())
                        .blockIndex(block.getBlockIndex())
                        .orderId(block.getOrderId())
                        .paymentId(block.getPaymentId())
                        .grossTotal(block.getGrossTotal())
                        .netTotal(block.getNetTotal())
                        .totalVat(block.getTotalVat())
                        .payload(block.getPayload())
                        .previousHash(block.getPreviousHash())
                        .hash(block.getHashValue())
                        .createdAt(block.getCreatedAt())
                        .build())
                .toList();
    }

    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encoded = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(encoded);
        } catch (Exception e) {
            throw new RuntimeException("Failed to hash block", e);
        }
    }
}
