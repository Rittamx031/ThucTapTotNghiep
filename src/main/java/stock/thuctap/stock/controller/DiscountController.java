package stock.thuctap.stock.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import stock.thuctap.stock.model.DiscountDTO;
import stock.thuctap.stock.service.DiscountService;
import stock.thuctap.stock.util.WebUtils;


@Controller
@RequestMapping("/discounts")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(final DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("discounts", discountService.findAll());
        return "discount/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("discount") final DiscountDTO discountDTO) {
        return "discount/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("discount") @Valid final DiscountDTO discountDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "discount/add";
        }
        discountService.create(discountDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("discount.create.success"));
        return "redirect:/discounts";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id, final Model model) {
        model.addAttribute("discount", discountService.get(id));
        return "discount/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id,
            @ModelAttribute("discount") @Valid final DiscountDTO discountDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "discount/edit";
        }
        discountService.update(id, discountDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("discount.update.success"));
        return "redirect:/discounts";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = discountService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            discountService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("discount.delete.success"));
        }
        return "redirect:/discounts";
    }

}
