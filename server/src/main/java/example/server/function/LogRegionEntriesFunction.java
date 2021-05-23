package example.server.function;

import org.apache.geode.cache.Declarable;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.partition.PartitionRegionHelper;

import java.util.Arrays;

public class LogRegionEntriesFunction implements Function, Declarable {
  
  @Override
  public void execute(FunctionContext context) {
    // Get the region names argument
    Object[] arguments = (Object[]) context.getArguments();
    String regionNamesArg = (String) arguments[0];
    String[] regionNames = regionNamesArg.split(",");
    boolean groupByBucket = (Boolean) arguments[1];
    context.getCache().getLogger().info("Executing function=" + getId() + "; regionNames=" + Arrays.toString(regionNames) + "; groupByBucket=" + groupByBucket);

    // Dump the entries for each region
    for (String regionName : regionNames) {
      // Get the region
      Region region = context.getCache().getRegion(regionName);

      // Dump the entries
      if (region == null) {
        context.getCache().getLogger().warning("Region named " + region + " does not exist");
      } else {
        getLogger(region, groupByBucket).logEntries();
      }
    }

      // Send the response
    context.getResultSender().lastResult(true);
  }

  private RegionEntryLogger getLogger(Region region, boolean groupByBucket) {
    RegionEntryLogger logger = null;
    if (PartitionRegionHelper.isPartitionedRegion(region)) {
      logger = groupByBucket ? new PartitionedRegionEntryByBucketLogger(region) : new PartitionedRegionEntryLogger(region);
    } else {
      logger = new ReplicatedRegionEntryLogger(region);
    }
    return logger;
  }

  @Override
  public String getId() {
    return getClass().getSimpleName();
  }
}
