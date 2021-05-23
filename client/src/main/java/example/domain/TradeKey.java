package example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.geode.DataSerializable;
import org.apache.geode.DataSerializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeKey implements DataSerializable {

  @NonNull
  private Object id;

  @Override
  public void toData(DataOutput out) throws IOException {
    DataSerializer.writeObject(this.id, out);
  }

  @Override
  public void fromData(DataInput in) throws IOException, ClassNotFoundException {
    this.id = DataSerializer.readObject(in);
  }
}
