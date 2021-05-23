package example.client.function;

import org.springframework.data.gemfire.function.annotation.FunctionId;
import org.springframework.data.gemfire.function.annotation.OnServers;

@OnServers(resultCollector = "allServersResultCollector")
public interface AllServersFunctions {

  @FunctionId("LogRegionEntriesFunction")
  Object logRegionEntries(String regionNames, boolean groupByBucket);
}
