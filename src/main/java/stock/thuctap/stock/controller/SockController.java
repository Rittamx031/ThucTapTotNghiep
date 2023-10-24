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
import stock.thuctap.stock.domain.Category;
import stock.thuctap.stock.domain.Producer;
import stock.thuctap.stock.model.SockDTO;
import stock.thuctap.stock.repos.CategoryRepository;
import stock.thuctap.stock.repos.ProducerRepository;
import stock.thuctap.stock.service.SockService;
import stock.thuctap.stock.util.CustomCollectors;
import stock.thuctap.stock.util.WebUtils;


@Controller
@RequestMapping("/socks")
public class SockController {

    private final SockService sockService;
    private final CategoryRepository categoryRepository;
    private final ProducerRepository producerRepository;

    public SockController(final SockService sockService,
            final CategoryRepository categoryRepository,
            final ProducerRepository producerRepository) {
        this.sockService = sockService;
        this.categoryRepository = categoryRepository;
        this.producerRepository = producerRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("categoryValues", categoryRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Category::getId, Category::getCode)));
        model.addAttribute("producerValues", producerRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Producer::getId, Producer::getCode)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("socks", sockService.findAll());
        return "sock/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("sock") final SockDTO sockDTO) {
        return "sock/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("sock") @Valid final SockDTO sockDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sock/add";
        }
        sockService.create(sockDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sock.create.success"));
        return "redirect:/socks";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id, final Model model) {
        model.addAttribute("sock", sockService.get(id));
        return "sock/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id,
            @ModelAttribute("sock") @Valid final SockDTO sockDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sock/edit";
        }
        sockService.update(id, sockDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sock.update.success"));
        return "redirect:/socks";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = sockService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            sockService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("sock.delete.success"));
        }
        return "redirect:/socks";
    }

}
