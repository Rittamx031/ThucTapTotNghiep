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
import stock.thuctap.stock.model.PatternDTO;
import stock.thuctap.stock.service.PatternService;
import stock.thuctap.stock.util.WebUtils;


@Controller
@RequestMapping("/patterns")
public class PatternController {

    private final PatternService patternService;

    public PatternController(final PatternService patternService) {
        this.patternService = patternService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("patterns", patternService.findAll());
        return "pattern/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("pattern") final PatternDTO patternDTO) {
        return "pattern/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("pattern") @Valid final PatternDTO patternDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "pattern/add";
        }
        patternService.create(patternDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("pattern.create.success"));
        return "redirect:/patterns";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id, final Model model) {
        model.addAttribute("pattern", patternService.get(id));
        return "pattern/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id,
            @ModelAttribute("pattern") @Valid final PatternDTO patternDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "pattern/edit";
        }
        patternService.update(id, patternDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("pattern.update.success"));
        return "redirect:/patterns";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = patternService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            patternService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("pattern.delete.success"));
        }
        return "redirect:/patterns";
    }

}
