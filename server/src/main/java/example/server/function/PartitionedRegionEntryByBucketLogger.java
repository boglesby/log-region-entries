package example.server.function;

import org.apache.geode.cache.Region;
import org.apache.geode.internal.cache.BucketRegion;
import org.apache.geode.internal.cache.NonTXEntry;
import org.apache.geode.internal.cache.PartitionedRegion;

import java.util.Comparator;
import java.util.Set;

public class PartitionedRegionEntryByBucketLogger extends AbstractPartitionedRegionEntryLogger {

  public PartitionedRegionEntryByBucketLogger(Region region) {
    super(region);
  }

  @Override
  public void logEntries() {
    PartitionedRegion region = (PartitionedRegion) this.region;
    StringBuilder builder = new StringBuilder();

    // Add the primary entries
    Set<BucketRegion> primaryBrs = region.getDataStore().getAllLocalPrimaryBucketRegions();
    int primaryEntries = getNumEntries(primaryBrs);
    addEntries(builder, primaryBrs, primaryEntries, true);

    // Add the secondary entries
    Set<BucketRegion> secondaryBrs = getLocalSecondaryBucketRegions(region);
    int secondaryEntries = getNumEntries(secondaryBrs);
    addEntries(builder, secondaryBrs, secondaryEntries, false);

    // Log the results
    this.region.getCache().getLogger().info(builder.toString());
  }

  private int getNumEntries(Set<BucketRegion> brs) {
    return brs
      .stream()
      .mapToInt(br -> br.size())
      .sum();
  }

  private void addEntries(StringBuilder builder, Set<BucketRegion> brs, int numEntries, boolean isPrimary) {
    addHeader(builder, brs.size(), numEntries, isPrimary);

    brs
      .stream()
      .sorted(Comparator.comparingInt(BucketRegion::getId))
      .forEach(br -> addEntries(builder, br));
  }

  private void addHeader(StringBuilder builder, int numBuckets, int numEntries, boolean isPrimary) {
    builder
      .append("\nRegion ")
      .append(this.region.getFullPath())
      .append(" contains the following ")
      .append(numBuckets)
      .append(isPrimary ? " primary" : " secondary")
      .append(" buckets consisting of ")
      .append(numEntries)
      .append(" total entries grouped by bucket:\n");
  }

  private void addEntries(StringBuilder builder, BucketRegion br) {
    addHeader(builder, br);
    ((Set<NonTXEntry>) br.entrySet()).stream().forEach(entry -> addEntry(builder, "\n\t\t", entry.getRegionEntry()));
    builder.append("\n");
  }

  private void addHeader(StringBuilder builder, BucketRegion br) {
    builder
      .append("\n\tBucket ")
      .append(br.getId())
      .append(" contains the following ")
      .append(br.size())
      .append(" entries:\n");
  }
}
