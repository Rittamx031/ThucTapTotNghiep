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
import stock.thuctap.stock.model.SizeDTO;
import stock.thuctap.stock.service.SizeService;
import stock.thuctap.stock.util.WebUtils;


@Controller
@RequestMapping("/sizes")
public class SizeController {

    private final SizeService sizeService;

    public SizeController(final SizeService sizeService) {
        this.sizeService = sizeService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("sizes", sizeService.findAll());
        return "size/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("size") final SizeDTO sizeDTO) {
        return "size/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("size") @Valid final SizeDTO sizeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "size/add";
        }
        sizeService.create(sizeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("size.create.success"));
        return "redirect:/sizes";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id, final Model model) {
        model.addAttribute("size", sizeService.get(id));
        return "size/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id,
            @ModelAttribute("size") @Valid final SizeDTO sizeDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "size/edit";
        }
        sizeService.update(id, sizeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("size.update.success"));
        return "redirect:/sizes";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = sizeService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            sizeService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("size.delete.success"));
        }
        return "redirect:/sizes";
    }

}
