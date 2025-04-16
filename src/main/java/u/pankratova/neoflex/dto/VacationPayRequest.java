package u.pankratova.neoflex.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class VacationPayRequest {
  private double average_salary;
  private Integer vacation_days;
  private LocalDate start_date;
  private LocalDate end_date;
}
