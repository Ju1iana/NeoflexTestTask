package u.pankratova.neoflex.service;

import org.springframework.stereotype.Service;
import u.pankratova.neoflex.dto.VacationPayRequest;
import java.time.DayOfWeek;
import java.util.List;
import java.time.LocalDate;

@Service
public class VacationPayService {
  private static final double DAYS_PER_MONTH = 29.3;

  private static final List<LocalDate> HOLIDAYS = List.of(
    LocalDate.of(2025, 3, 8),    // 8 марта
    LocalDate.of(2025, 2, 23),   // 23 февраля
    LocalDate.of(2025, 1, 1)     // НГ
  );

  public double calculateVacationPay(double average_salary, int vacation_days) {
    return average_salary / DAYS_PER_MONTH * vacation_days;
  }

  public double calculateVacationPay(VacationPayRequest request) {
    if (request.getStart_date() == null || request.getEnd_date() == null) {
      return calculateVacationPay(request.getAverage_salary(), request.getVacation_days());
    } else {
      int work_days = calculateWorkDays(request.getStart_date(), request.getEnd_date());
      return calculateVacationPay(request.getAverage_salary(), work_days);
    }
  }

  public int calculateWorkDays(LocalDate start_date, LocalDate end_date) {
    int work_days = 0;
    LocalDate current_data = start_date;
    while (current_data.isBefore(end_date)){
      if (!isHoliday(current_data) && !isWeekend(current_data)) {
        work_days++;
      }
      current_data = current_data.plusDays(1);
    }
    return work_days;
  }

  public boolean isWeekend(LocalDate localDate) {
    DayOfWeek dayOfWeek = localDate.getDayOfWeek();
    return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
  }

  public boolean isHoliday(LocalDate localDate){
    return HOLIDAYS.contains(localDate);
  }
}

