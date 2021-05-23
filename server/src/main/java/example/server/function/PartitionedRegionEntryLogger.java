package example.server.function;

import org.apache.geode.cache.Region;

import org.apache.geode.internal.cache.BucketRegion;
import org.apache.geode.internal.cache.NonTXEntry;
import org.apache.geode.internal.cache.PartitionedRegion;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PartitionedRegionEntryLogger extends AbstractPartitionedRegionEntryLogger {

  public PartitionedRegionEntryLogger(Region region) {
    super(region);
  }

  @Override
  public void logEntries() {
    PartitionedRegion pr = (PartitionedRegion) this.region;
    StringBuilder builder = new StringBuilder();

    // Add the primary entries
    List<NonTXEntry> primaryEntries = getEntries(pr.getDataStore().getAllLocalPrimaryBucketRegions());
    addEntries(builder, primaryEntries, true);

    // Add the secondary entries
    List<NonTXEntry> secondaryEntries = getEntries(getLocalSecondaryBucketRegions(pr));
    addEntries(builder, secondaryEntries, false);

    // Log the results
    this.region.getCache().getLogger().info(builder.toString());
  }

  private List<NonTXEntry> getEntries(Set<BucketRegion> brs) {
    return (List<NonTXEntry>) brs
      .stream()
      .flatMap(br -> br.entrySet().stream())
      .collect(Collectors.toList());
  }

  private void addEntries(StringBuilder builder, List<NonTXEntry> entries, boolean isPrimary) {
    addHeader(builder, entries, isPrimary);
    entries.stream().forEach(entry -> addEntry(builder, "\n\t", entry.getRegionEntry()));
    builder.append("\n");
  }

  private void addHeader(StringBuilder builder, List<NonTXEntry> entries, boolean isPrimary) {
    builder
      .append("\nRegion ")
      .append(this.region.getFullPath())
      .append(" contains the following ")
      .append(format.format(entries.size()))
      .append(isPrimary ? " primary" : " secondary")
      .append(" entries:\n");
  }
}
