package example.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Trade {

  @NonNull
  private Object id;

  @NonNull
  private String cusip;

  @NonNull
  private Integer shares;

  @NonNull
  private BigDecimal price;
}
