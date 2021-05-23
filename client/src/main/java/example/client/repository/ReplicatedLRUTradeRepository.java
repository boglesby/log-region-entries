package example.client.repository;

import example.domain.Trade;
import org.springframework.data.gemfire.mapping.annotation.Region;
import org.springframework.data.gemfire.repository.GemfireRepository;

@Region("ReplicatedLRUTrade")
public interface ReplicatedLRUTradeRepository extends GemfireRepository<Trade, Object> {
}