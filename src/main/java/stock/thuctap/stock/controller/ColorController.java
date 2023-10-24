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
import stock.thuctap.stock.model.ColorDTO;
import stock.thuctap.stock.service.ColorService;
import stock.thuctap.stock.util.WebUtils;


@Controller
@RequestMapping("/colors")
public class ColorController {

    private final ColorService colorService;

    public ColorController(final ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("colors", colorService.findAll());
        return "color/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("color") final ColorDTO colorDTO) {
        return "color/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("color") @Valid final ColorDTO colorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "color/add";
        }
        colorService.create(colorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("color.create.success"));
        return "redirect:/colors";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id, final Model model) {
        model.addAttribute("color", colorService.get(id));
        return "color/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id,
            @ModelAttribute("color") @Valid final ColorDTO colorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "color/edit";
        }
        colorService.update(id, colorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("color.update.success"));
        return "redirect:/colors";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = colorService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            colorService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("color.delete.success"));
        }
        return "redirect:/colors";
    }

}
