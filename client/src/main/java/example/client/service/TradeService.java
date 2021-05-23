package example.client.service;

import example.client.repository.*;
import example.domain.CusipHelper;
import example.domain.Trade;
import example.client.function.AllServersFunctions;
import example.domain.TradeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;

@Service
public class TradeService {

  @Autowired
  private ReplicatedTradeRepository replicatedRepository;

  @Autowired
  private ReplicatedOverflowTradeRepository replicatedOverflowRepository;

  @Autowired
  private ReplicatedPersistentTradeRepository replicatedPersistentRepository;

  @Autowired
  private ReplicatedLRUTradeRepository replicatedLRURepository;

  @Autowired
  private ReplicatedPersistentOverflowTradeRepository replicatedPersistentOverflowRepository;

  @Autowired
  private PartitionedTradeRepository partitionedRepository;

  @Autowired
  private PartitionedOverflowTradeRepository partitionedOverflowRepository;

  @Autowired
  private PartitionedPersistentTradeRepository partitionedPersistentRepository;

  @Autowired
  private PartitionedLRUTradeRepository partitionedLRURepository;

  @Autowired
  private PartitionedPersistentOverflowTradeRepository partitionedPersistentOverflowRepository;

  @Autowired
  private AllServersFunctions allServersFunctions;

  private static final Random random = new Random();

  private static final Logger logger = LoggerFactory.getLogger(TradeService.class);

  public void put(int numEntries) {
    logger.info("Putting {} trades", numEntries);
    for (int i=0; i<numEntries; i++) {
      createAndSaveTrade(String.valueOf(i));
    }
  }

  public void putDifferentKeyTypes() {
    // Save Trade with String with 1-7 characters key
    createAndSaveTrade("0");

    // Save Trade with String with 8-15 characters key
    createAndSaveTrade("00000000");

    // Save Trade with String with 16 characters key
    createAndSaveTrade("0000000000000000");

    // Save Trade with Integer key
    createAndSaveTrade(0);

    // Save Trade with Long key
    createAndSaveTrade(0L);

    // Save Trade with UUID key
    createAndSaveTrade(UUID.randomUUID());

    // Save Trade with TradeKey key
    createAndSaveTrade(new TradeKey("0"));
  }

  public void logRegionEntries(String regionNames, boolean groupByBucket) {
    Object result = this.allServersFunctions.logRegionEntries(regionNames, groupByBucket);
    logger.info("Logged entries for regionNames={}; result={}", regionNames, result);
  }

  private void createAndSaveTrade(Object key) {
    saveTrade(new Trade(key, CusipHelper.getCusip(), random.nextInt(100), new BigDecimal(BigInteger.valueOf(random.nextInt(100000)), 2)));
  }

  private void saveTrade(Trade trade) {
    // Save the trade in the replicated repositories
    this.replicatedRepository.save(trade);
    this.replicatedOverflowRepository.save(trade);
    this.replicatedPersistentRepository.save(trade);
    this.replicatedLRURepository.save(trade);
    this.replicatedPersistentOverflowRepository.save(trade);

    // Save the trade in the partitioned repositories
    this.partitionedRepository.save(trade);
    this.partitionedOverflowRepository.save(trade);
    this.partitionedPersistentRepository.save(trade);
    this.partitionedLRURepository.save(trade);
    this.partitionedPersistentOverflowRepository.save(trade);

    logger.info("Saved " + trade);
  }
}
