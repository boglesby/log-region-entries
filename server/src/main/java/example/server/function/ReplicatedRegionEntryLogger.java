package example.server.function;

import org.apache.geode.cache.Region;
import org.apache.geode.internal.cache.NonTXEntry;

import java.util.Set;

public class ReplicatedRegionEntryLogger extends AbstractRegionEntryLogger {

  public ReplicatedRegionEntryLogger(Region region) {
    super(region);
  }

  @Override
  public void logEntries() {
    StringBuilder builder = new StringBuilder();

    // Add header
    addHeader(builder);

    // Add entries
    ((Set<NonTXEntry>) this.region.entrySet())
      .stream()
      .forEach(entry -> addEntry(builder, "\n\t", entry.getRegionEntry()));

    // Log the results
    this.region.getCache().getLogger().info(builder.toString());
  }

  private void addHeader(StringBuilder builder) {
    builder
      .append("\nRegion ")
      .append(this.region.getFullPath())
      .append(" contains the following ")
      .append(format.format(this.region.size()))
      .append(" entries:\n");
  }
}
