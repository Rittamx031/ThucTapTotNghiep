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
import stock.thuctap.stock.model.CustomerAddressDTO;
import stock.thuctap.stock.repos.CustomerRepository;
import stock.thuctap.stock.service.CustomerAddressService;
import stock.thuctap.stock.util.CustomCollectors;
import stock.thuctap.stock.util.WebUtils;


@Controller
@RequestMapping("/customerAddresses")
public class CustomerAddressController {

    private final CustomerAddressService customerAddressService;
    private final CustomerRepository customerRepository;

    public CustomerAddressController(final CustomerAddressService customerAddressService,
            final CustomerRepository customerRepository) {
        this.customerAddressService = customerAddressService;
        this.customerRepository = customerRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("customerValues", customerRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Customer::getId, Customer::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("customerAddresses", customerAddressService.findAll());
        return "customerAddress/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("customerAddress") final CustomerAddressDTO customerAddressDTO) {
        return "customerAddress/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("customerAddress") @Valid final CustomerAddressDTO customerAddressDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "customerAddress/add";
        }
        customerAddressService.create(customerAddressDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("customerAddress.create.success"));
        return "redirect:/customerAddresses";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id, final Model model) {
        model.addAttribute("customerAddress", customerAddressService.get(id));
        return "customerAddress/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id,
            @ModelAttribute("customerAddress") @Valid final CustomerAddressDTO customerAddressDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "customerAddress/edit";
        }
        customerAddressService.update(id, customerAddressDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("customerAddress.update.success"));
        return "redirect:/customerAddresses";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Integer id,
            final RedirectAttributes redirectAttributes) {
        customerAddressService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("customerAddress.delete.success"));
        return "redirect:/customerAddresses";
    }

}
