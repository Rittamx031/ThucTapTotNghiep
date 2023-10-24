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
import stock.thuctap.stock.model.MaterialDTO;
import stock.thuctap.stock.service.MaterialService;
import stock.thuctap.stock.util.WebUtils;


@Controller
@RequestMapping("/materials")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(final MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("materials", materialService.findAll());
        return "material/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("material") final MaterialDTO materialDTO) {
        return "material/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("material") @Valid final MaterialDTO materialDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "material/add";
        }
        materialService.create(materialDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("material.create.success"));
        return "redirect:/materials";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id, final Model model) {
        model.addAttribute("material", materialService.get(id));
        return "material/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id,
            @ModelAttribute("material") @Valid final MaterialDTO materialDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "material/edit";
        }
        materialService.update(id, materialDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("material.update.success"));
        return "redirect:/materials";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = materialService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            materialService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("material.delete.success"));
        }
        return "redirect:/materials";
    }

}
