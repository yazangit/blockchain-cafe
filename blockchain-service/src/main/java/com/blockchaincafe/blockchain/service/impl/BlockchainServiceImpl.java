package com.blockchaincafe.blockchain.service.impl;

import com.blockchaincafe.blockchain.domain.model.BlockEntity;
import com.blockchaincafe.blockchain.dto.request.CreateBlockRequest;
import com.blockchaincafe.blockchain.dto.response.BlockResponse;
import com.blockchaincafe.blockchain.dto.response.BlockValidationResponse;
import com.blockchaincafe.blockchain.dto.response.BlockchainOverviewResponse;
import com.blockchaincafe.blockchain.dto.response.CreateBlockResponse;
import com.blockchaincafe.blockchain.exception.BlockNotFoundException;
import com.blockchaincafe.blockchain.repository.BlockRepository;
import com.blockchaincafe.blockchain.service.BlockchainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;

import java.math.BigDecimal;
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

    Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    String currentHash = sha256(buildHashInput(
            nextIndex,
            request.getOrderId(),
            request.getPaymentId(),
            request.getGrossTotal(),
            request.getNetTotal(),
            request.getTotalVat(),
            request.getPayload(),
            previousHash,
            now
    ));

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
        return validateChainDetailed().isValid();
    }

    @Override
    public BlockValidationResponse validateChainDetailed() {
        List<BlockEntity> chain = blockRepository.findAllByOrderByBlockIndexAsc();

        if (chain.isEmpty()) {
            return BlockValidationResponse.builder()
                    .valid(true)
                    .message("Blockchain is empty")
                    .chainSize(0)
                    .lastBlockHash(null)
                    .build();
        }

        for (int i = 0; i < chain.size(); i++) {
            BlockEntity current = chain.get(i);

            String recalculatedHash = sha256(buildHashInput(
                    current.getBlockIndex(),
                    current.getOrderId(),
                    current.getPaymentId(),
                    current.getGrossTotal(),
                    current.getNetTotal(),
                    current.getTotalVat(),
                    current.getPayload(),
                    current.getPreviousHash(),
                    current.getCreatedAt()
            ));

            if (!recalculatedHash.equals(current.getHashValue())) {
                return BlockValidationResponse.builder()
                        .valid(false)
                        .message("Stored hash does not match recalculated hash")
                        .chainSize(chain.size())
                        .invalidBlockIndex(current.getBlockIndex())
                        .lastBlockHash(chain.get(chain.size() - 1).getHashValue())
                        .storedHash(current.getHashValue())
                        .recalculatedHash(recalculatedHash)
                        .build();
            }

            if (i == 0) {
                if (!"GENESIS".equals(current.getPreviousHash())) {
                    return BlockValidationResponse.builder()
                            .valid(false)
                            .message("Genesis block previousHash must be GENESIS")
                            .chainSize(chain.size())
                            .invalidBlockIndex(current.getBlockIndex())
                            .lastBlockHash(chain.get(chain.size() - 1).getHashValue())
                            .storedHash(current.getHashValue())
                            .recalculatedHash(recalculatedHash)
                            .expectedPreviousHash("GENESIS")
                            .actualPreviousHash(current.getPreviousHash())
                            .build();
                }
            } else {
                BlockEntity previous = chain.get(i - 1);

                if (!current.getPreviousHash().equals(previous.getHashValue())) {
                    return BlockValidationResponse.builder()
                            .valid(false)
                            .message("Previous hash linkage is invalid")
                            .chainSize(chain.size())
                            .invalidBlockIndex(current.getBlockIndex())
                            .lastBlockHash(chain.get(chain.size() - 1).getHashValue())
                            .storedHash(current.getHashValue())
                            .recalculatedHash(recalculatedHash)
                            .expectedPreviousHash(previous.getHashValue())
                            .actualPreviousHash(current.getPreviousHash())
                            .build();
                }
            }
        }

        return BlockValidationResponse.builder()
                .valid(true)
                .message("Blockchain is valid")
                .chainSize(chain.size())
                .lastBlockHash(chain.get(chain.size() - 1).getHashValue())
                .storedHash(chain.get(chain.size() - 1).getHashValue())
                .recalculatedHash(chain.get(chain.size() - 1).getHashValue())
                .build();
    }

    @Override
    public List<BlockResponse> getAllBlocks() {
        return blockRepository.findAllByOrderByBlockIndexAsc().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public BlockResponse getLatestBlock() {
        BlockEntity latest = blockRepository.findTopByOrderByBlockIndexDesc()
                .orElseThrow(() -> new BlockNotFoundException("No blocks found"));
        return toResponse(latest);
    }

    @Override
    public BlockResponse getBlockByOrderId(String orderId) {
        BlockEntity block = blockRepository.findByOrderId(orderId)
                .orElseThrow(() -> new BlockNotFoundException("No block found for orderId: " + orderId));
        return toResponse(block);
    }

    @Override
    public BlockchainOverviewResponse getOverview() {
        List<BlockEntity> blocks = blockRepository.findAllByOrderByBlockIndexAsc();

        BigDecimal grossTotal = blocks.stream()
                .map(block -> block.getGrossTotal() == null ? BigDecimal.ZERO : block.getGrossTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netTotal = blocks.stream()
                .map(block -> block.getNetTotal() == null ? BigDecimal.ZERO : block.getNetTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalVat = blocks.stream()
                .map(block -> block.getTotalVat() == null ? BigDecimal.ZERO : block.getTotalVat())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BlockEntity latest = blocks.isEmpty() ? null : blocks.get(blocks.size() - 1);

        return BlockchainOverviewResponse.builder()
                .totalBlocks(blocks.size())
                .latestBlockIndex(latest != null ? latest.getBlockIndex() : 0)
                .latestHash(latest != null ? latest.getHashValue() : "-")
                .grossTotal(grossTotal)
                .netTotal(netTotal)
                .totalVat(totalVat)
                .build();
    }

    private BlockResponse toResponse(BlockEntity block) {
        return BlockResponse.builder()
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
                .build();
    }

    private String buildHashInput(
            Integer blockIndex,
            String orderId,
            String paymentId,
            BigDecimal grossTotal,
            BigDecimal netTotal,
            BigDecimal totalVat,
            String payload,
            String previousHash,
            Instant createdAt
    ) {
        return blockIndex + "|" +
                safe(orderId) + "|" +
                safe(paymentId) + "|" +
                grossTotal + "|" +
                netTotal + "|" +
                totalVat + "|" +
                safe(payload) + "|" +
                safe(previousHash) + "|" +
                createdAt.toString();
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

    private String safe(String value) {
        return value == null ? "" : value;
    }
}