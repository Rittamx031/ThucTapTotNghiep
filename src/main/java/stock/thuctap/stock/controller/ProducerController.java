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
import stock.thuctap.stock.model.ProducerDTO;
import stock.thuctap.stock.service.ProducerService;
import stock.thuctap.stock.util.WebUtils;


@Controller
@RequestMapping("/producers")
public class ProducerController {

    private final ProducerService producerService;

    public ProducerController(final ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("producers", producerService.findAll());
        return "producer/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("producer") final ProducerDTO producerDTO) {
        return "producer/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("producer") @Valid final ProducerDTO producerDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "producer/add";
        }
        producerService.create(producerDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("producer.create.success"));
        return "redirect:/producers";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id, final Model model) {
        model.addAttribute("producer", producerService.get(id));
        return "producer/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id,
            @ModelAttribute("producer") @Valid final ProducerDTO producerDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "producer/edit";
        }
        producerService.update(id, producerDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("producer.update.success"));
        return "redirect:/producers";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = producerService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            producerService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("producer.delete.success"));
        }
        return "redirect:/producers";
    }

}
