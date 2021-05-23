package example.server.function;

import org.apache.geode.cache.Region;
import org.apache.geode.internal.cache.BucketRegion;
import org.apache.geode.internal.cache.PartitionedRegion;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractPartitionedRegionEntryLogger extends AbstractRegionEntryLogger {

  public AbstractPartitionedRegionEntryLogger(Region region) {
    super(region);
  }

  protected Set<BucketRegion> getLocalSecondaryBucketRegions(PartitionedRegion region) {
    Set<BucketRegion> primaryBucketRegions = region.getDataStore().getAllLocalPrimaryBucketRegions();
    Set<BucketRegion> allBucketRegions = new HashSet<>(region.getDataStore().getAllLocalBucketRegions());
    allBucketRegions.removeAll(primaryBucketRegions);
    return allBucketRegions;
  }
}
