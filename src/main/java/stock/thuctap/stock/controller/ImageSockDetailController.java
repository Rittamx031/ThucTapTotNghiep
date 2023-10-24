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
import stock.thuctap.stock.model.ImageSockDetailDTO;
import stock.thuctap.stock.service.ImageSockDetailService;
import stock.thuctap.stock.util.WebUtils;


@Controller
@RequestMapping("/imageSockDetails")
public class ImageSockDetailController {

    private final ImageSockDetailService imageSockDetailService;

    public ImageSockDetailController(final ImageSockDetailService imageSockDetailService) {
        this.imageSockDetailService = imageSockDetailService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("imageSockDetails", imageSockDetailService.findAll());
        return "imageSockDetail/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("imageSockDetail") final ImageSockDetailDTO imageSockDetailDTO) {
        return "imageSockDetail/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("imageSockDetail") @Valid final ImageSockDetailDTO imageSockDetailDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "imageSockDetail/add";
        }
        imageSockDetailService.create(imageSockDetailDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("imageSockDetail.create.success"));
        return "redirect:/imageSockDetails";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id, final Model model) {
        model.addAttribute("imageSockDetail", imageSockDetailService.get(id));
        return "imageSockDetail/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id,
            @ModelAttribute("imageSockDetail") @Valid final ImageSockDetailDTO imageSockDetailDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "imageSockDetail/edit";
        }
        imageSockDetailService.update(id, imageSockDetailDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("imageSockDetail.update.success"));
        return "redirect:/imageSockDetails";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = imageSockDetailService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            imageSockDetailService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("imageSockDetail.delete.success"));
        }
        return "redirect:/imageSockDetails";
    }

}
