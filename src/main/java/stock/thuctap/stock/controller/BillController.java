package stock.thuctap.stock.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import stock.thuctap.stock.domain.Customer;
import stock.thuctap.stock.domain.Employee;
import stock.thuctap.stock.domain.PayMethod;
import stock.thuctap.stock.model.BillDTO;
import stock.thuctap.stock.repos.CustomerRepository;
import stock.thuctap.stock.repos.EmployeeRepository;
import stock.thuctap.stock.repos.PayMethodRepository;
import stock.thuctap.stock.service.BillService;
import stock.thuctap.stock.util.CustomCollectors;
import stock.thuctap.stock.util.WebUtils;


@Controller
@RequestMapping("/bills")
public class BillController {

    private final BillService billService;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final PayMethodRepository payMethodRepository;

    public BillController(final BillService billService,
            final EmployeeRepository employeeRepository,
            final CustomerRepository customerRepository,
            final PayMethodRepository payMethodRepository) {
        this.billService = billService;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.payMethodRepository = payMethodRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("employeeValues", employeeRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Employee::getId, Employee::getName)));
        model.addAttribute("customerValues", customerRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Customer::getId, Customer::getName)));
        model.addAttribute("payMethodValues", payMethodRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(PayMethod::getId, PayMethod::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("bills", billService.findAll());
        return "bill/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("bill") final BillDTO billDTO) {
        return "bill/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("bill") @Valid final BillDTO billDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bill/add";
        }
        billService.create(billDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bill.create.success"));
        return "redirect:/bills";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id, final Model model) {
        model.addAttribute("bill", billService.get(id));
        return "bill/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id,
            @ModelAttribute("bill") @Valid final BillDTO billDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bill/edit";
        }
        billService.update(id, billDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bill.update.success"));
        return "redirect:/bills";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = billService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            billService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("bill.delete.success"));
        }
        return "redirect:/bills";
    }

}
