package u.pankratova.neoflex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import u.pankratova.neoflex.dto.VacationPayRequest;
import u.pankratova.neoflex.service.VacationPayService;
import java.time.LocalDate;

@Controller
@RequestMapping("/")
public class VacationController {

  @Autowired
  VacationPayService vacationPayService;

  @GetMapping
  public String get(){
    return "index";
  }

  @GetMapping("/calculate")
  public String get(@RequestParam double average_salary,
                    @RequestParam(required = false) Integer vacation_days,
                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start_date,
                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end_date,
                    Model model) {

    VacationPayRequest request = new VacationPayRequest();
    request.setAverage_salary(average_salary);
    request.setVacation_days(vacation_days);
    request.setStart_date(start_date);
    request.setEnd_date(end_date);

    if ((vacation_days == null || vacation_days < 0) && (start_date == null || end_date == null)){
      model.addAttribute("error", "Укажите дни отпуска или даты начала/окончания");
      return "index";
    }

    if (start_date != null && end_date != null && end_date.isBefore(start_date)) {
      model.addAttribute("error", "Дата окончания не может быть раньше начала");
      return "index";
    }

    try {
      double result = vacationPayService.calculateVacationPay(request);
      model.addAttribute("result", String.format("%.2f", result));
    } catch (IllegalArgumentException e) {
      model.addAttribute("error", e.getMessage());
    }
    return "index";
  }
}
