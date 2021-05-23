package example.domain;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
public class Trade {

  @Id
  @NonNull
  private final Object id;

  @NonNull
  private final String cusip;

  @NonNull
  private final Integer shares;

  @NonNull
  private final BigDecimal price;
}
