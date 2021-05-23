package example.client;

import example.client.service.TradeService;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.geode.boot.autoconfigure.ContinuousQueryAutoConfiguration;

import java.util.List;

@SpringBootApplication(exclude = ContinuousQueryAutoConfiguration.class) // disable subscriptions
public class Client {

  @Autowired
  private TradeService service;

  public static void main(String[] args) {
    new SpringApplicationBuilder(Client.class)
      .build()
      .run(args);
  }

  @Bean
  ApplicationRunner runner() {
    return args -> {
      List<String> operations = args.getOptionValues("operation");
      String operation = operations.get(0);
      String parameter1 = (args.containsOption("parameter1")) ? args.getOptionValues("parameter1").get(0) : null;
      String parameter2 = (args.containsOption("parameter2")) ? args.getOptionValues("parameter2").get(0) : null;
      switch (operation) {
        case "load-regions":
          this.service.put(Integer.parseInt(parameter1));
          break;
        case "load-regions-different-key-types":
          this.service.putDifferentKeyTypes();
          break;
        case "log-region-entries":
          this.service.logRegionEntries(parameter1, Boolean.parseBoolean(parameter2));
          break;
    }};
  }

  @Bean("ReplicatedTrade")
  ClientRegionFactoryBean replicatedTradeRegion(GemFireCache cache) {
    ClientRegionFactoryBean<?, ?> regionFactory = new ClientRegionFactoryBean<>();
    regionFactory.setCache(cache);
    regionFactory.setShortcut(ClientRegionShortcut.PROXY);
    return regionFactory;
  }

  @Bean("ReplicatedOverflowTrade")
  ClientRegionFactoryBean replicatedOverflowTradeRegion(GemFireCache cache) {
    ClientRegionFactoryBean<?, ?> regionFactory = new ClientRegionFactoryBean<>();
    regionFactory.setCache(cache);
    regionFactory.setShortcut(ClientRegionShortcut.PROXY);
    return regionFactory;
  }

  @Bean("ReplicatedPersistentTrade")
  ClientRegionFactoryBean replicatedPersistentTradeRegion(GemFireCache cache) {
    ClientRegionFactoryBean<?, ?> regionFactory = new ClientRegionFactoryBean<>();
    regionFactory.setCache(cache);
    regionFactory.setShortcut(ClientRegionShortcut.PROXY);
    return regionFactory;
  }

  @Bean("ReplicatedLRUTrade")
  ClientRegionFactoryBean replicatedLRUTradeRegion(GemFireCache cache) {
    ClientRegionFactoryBean<?, ?> regionFactory = new ClientRegionFactoryBean<>();
    regionFactory.setCache(cache);
    regionFactory.setShortcut(ClientRegionShortcut.PROXY);
    return regionFactory;
  }

  @Bean("ReplicatedPersistentOverflowTrade")
  ClientRegionFactoryBean replicatedPersistentOverflowTradeRegion(GemFireCache cache) {
    ClientRegionFactoryBean<?, ?> regionFactory = new ClientRegionFactoryBean<>();
    regionFactory.setCache(cache);
    regionFactory.setShortcut(ClientRegionShortcut.PROXY);
    return regionFactory;
  }

  @Bean("PartitionedTrade")
  ClientRegionFactoryBean partitionedTradeRegion(GemFireCache cache) {
    ClientRegionFactoryBean<?, ?> regionFactory = new ClientRegionFactoryBean<>();
    regionFactory.setCache(cache);
    regionFactory.setShortcut(ClientRegionShortcut.PROXY);
    return regionFactory;
  }

  @Bean("PartitionedOverflowTrade")
  ClientRegionFactoryBean partitionedOverflowTradeRegion(GemFireCache cache) {
    ClientRegionFactoryBean<?, ?> regionFactory = new ClientRegionFactoryBean<>();
    regionFactory.setCache(cache);
    regionFactory.setShortcut(ClientRegionShortcut.PROXY);
    return regionFactory;
  }

  @Bean("PartitionedPersistentTrade")
  ClientRegionFactoryBean partitionedPersistentTradeRegion(GemFireCache cache) {
    ClientRegionFactoryBean<?, ?> regionFactory = new ClientRegionFactoryBean<>();
    regionFactory.setCache(cache);
    regionFactory.setShortcut(ClientRegionShortcut.PROXY);
    return regionFactory;
  }

  @Bean("PartitionedLRUTrade")
  ClientRegionFactoryBean partitionedLRUTradeRegion(GemFireCache cache) {
    ClientRegionFactoryBean<?, ?> regionFactory = new ClientRegionFactoryBean<>();
    regionFactory.setCache(cache);
    regionFactory.setShortcut(ClientRegionShortcut.PROXY);
    return regionFactory;
  }

  @Bean("PartitionedPersistentOverflowTrade")
  ClientRegionFactoryBean partitionedPersistentOverflowTradeRegion(GemFireCache cache) {
    ClientRegionFactoryBean<?, ?> regionFactory = new ClientRegionFactoryBean<>();
    regionFactory.setCache(cache);
    regionFactory.setShortcut(ClientRegionShortcut.PROXY);
    return regionFactory;
  }
}
