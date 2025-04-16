package u.pankratova.neoflex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import u.pankratova.neoflex.dto.VacationPayRequest;
import u.pankratova.neoflex.service.VacationPayService;
import java.time.LocalDate;

@SpringBootTest
public class SpringBootAppApplicationTest {

  @Autowired
  private VacationPayService vacationPayService;

  @Test
  public void testVacationPayWithoutStartAndEndDates() {
    double result = vacationPayService.calculateVacationPay(100_000, 14);
    Assertions.assertEquals(47_781.57, result, 0.01);
  }

  @Test
  public void testHolidaysAndWeekends() {
    LocalDate startDate = LocalDate.of(2025, 3, 1);
    LocalDate endDate = LocalDate.of(2025, 3, 10);
    int workDays = vacationPayService.calculateWorkDays(startDate, endDate);
    Assertions.assertEquals(5, workDays);  // Рабочие дни: 3-7 (1-2,9 - выходные, 8 марта - праздник)
  }

  @Test
  public void testVacationPayWithStartAndEndDates() {
    VacationPayRequest request = new VacationPayRequest();
    request.setAverage_salary(100_000);
    request.setStart_date(LocalDate.of(2025, 3, 1));
    request.setEnd_date(LocalDate.of(2025, 3, 10));

    double result = vacationPayService.calculateVacationPay(request);
    Assertions.assertEquals(17_064.85, result, 0.01); // 100 000 / 29.3 * 5
  }
}
