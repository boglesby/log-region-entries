package example.server.function;

import org.apache.geode.cache.Region;
import org.apache.geode.internal.cache.CachedDeserializable;
import org.apache.geode.internal.cache.RegionEntry;
import org.apache.geode.internal.cache.StoreAllCachedDeserializable;

import java.text.Format;
import java.text.NumberFormat;

public abstract class AbstractRegionEntryLogger implements RegionEntryLogger {

  protected final Region region;

  protected static final Format format = NumberFormat.getInstance();

  public AbstractRegionEntryLogger(Region region) {
    this.region = region;
  }

  protected void addEntry(StringBuilder builder, String entryPrefix, RegionEntry regionEntry) {
    //Uncomment this line to see the deserialized value in the RegionEntry
    //this.region.get(regionEntry.getKey());
    Object value = regionEntry.getValue();
    builder
      .append(entryPrefix)
      .append("RegionEntry[")
      .append("regionEntryClass=")
      .append(regionEntry.getClass().getName())
      .append("; regionEntryKeyClass=")
      .append(regionEntry.getKey().getClass().getName())
      .append("; regionEntryKey=")
      .append(regionEntry.getKey())
      .append("; regionEntryValueClass=")
      .append(value.getClass().getName());
    if (value instanceof CachedDeserializable) {
      // Value is a wrapper
      CachedDeserializable cd = (CachedDeserializable) value;
      Object cdValue = ((CachedDeserializable) value).getValue();
      if (cdValue instanceof byte[]) {
        byte[] cdValueBytes = (byte[]) cdValue;
        builder
          .append("; cachedDeserializableValueClass=")
          .append(cdValueBytes.getClass().getName())
          .append("; cachedDeserializableValueBytes=")
          .append(cdValueBytes)
          .append("; cachedDeserializableValueBytesLength=")
          .append(cdValueBytes.length);
      } else {
        builder
          .append("; cachedDeserializableValueClass=")
          .append(cdValue.getClass().getName())
          .append("; cachedDeserializableValue=")
          .append(cdValue);
      }
      if (value instanceof StoreAllCachedDeserializable) {
        Object valueObj = cd.getDeserializedForReading();
        builder
          .append("; cachedDeserializableValueObjClass=")
          .append(valueObj.getClass().getName())
          .append("; cachedDeserializableValue=")
          .append(valueObj);
      }
    } else {
      // Value is a domain object
      builder
        .append("; value=")
        .append(value);
    }
    builder
      .append("]");
  }
}
