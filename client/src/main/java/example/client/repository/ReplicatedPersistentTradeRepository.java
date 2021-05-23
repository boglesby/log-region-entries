package example.client.repository;

import example.domain.Trade;
import org.springframework.data.gemfire.mapping.annotation.Region;
import org.springframework.data.gemfire.repository.GemfireRepository;

@Region("ReplicatedPersistentTrade")
public interface ReplicatedPersistentTradeRepository extends GemfireRepository<Trade, Object> {
}