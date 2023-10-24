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
import stock.thuctap.stock.model.PayMethodDTO;
import stock.thuctap.stock.service.PayMethodService;
import stock.thuctap.stock.util.WebUtils;


@Controller
@RequestMapping("/payMethods")
public class PayMethodController {

    private final PayMethodService payMethodService;

    public PayMethodController(final PayMethodService payMethodService) {
        this.payMethodService = payMethodService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("payMethods", payMethodService.findAll());
        return "payMethod/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("payMethod") final PayMethodDTO payMethodDTO) {
        return "payMethod/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("payMethod") @Valid final PayMethodDTO payMethodDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "payMethod/add";
        }
        payMethodService.create(payMethodDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("payMethod.create.success"));
        return "redirect:/payMethods";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id, final Model model) {
        model.addAttribute("payMethod", payMethodService.get(id));
        return "payMethod/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id,
            @ModelAttribute("payMethod") @Valid final PayMethodDTO payMethodDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "payMethod/edit";
        }
        payMethodService.update(id, payMethodDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("payMethod.update.success"));
        return "redirect:/payMethods";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = payMethodService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            payMethodService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("payMethod.delete.success"));
        }
        return "redirect:/payMethods";
    }

}
