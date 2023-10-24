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
import stock.thuctap.stock.domain.Color;
import stock.thuctap.stock.domain.Discount;
import stock.thuctap.stock.domain.ImageSockDetail;
import stock.thuctap.stock.domain.Material;
import stock.thuctap.stock.domain.Pattern;
import stock.thuctap.stock.domain.Size;
import stock.thuctap.stock.domain.Sock;
import stock.thuctap.stock.model.SockDetailDTO;
import stock.thuctap.stock.repos.ColorRepository;
import stock.thuctap.stock.repos.DiscountRepository;
import stock.thuctap.stock.repos.ImageSockDetailRepository;
import stock.thuctap.stock.repos.MaterialRepository;
import stock.thuctap.stock.repos.PatternRepository;
import stock.thuctap.stock.repos.SizeRepository;
import stock.thuctap.stock.repos.SockRepository;
import stock.thuctap.stock.service.SockDetailService;
import stock.thuctap.stock.util.CustomCollectors;
import stock.thuctap.stock.util.WebUtils;


@Controller
@RequestMapping("/sockDetails")
public class SockDetailController {

    private final SockDetailService sockDetailService;
    private final SockRepository sockRepository;
    private final PatternRepository patternRepository;
    private final MaterialRepository materialRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final DiscountRepository discountRepository;
    private final ImageSockDetailRepository imageSockDetailRepository;

    public SockDetailController(final SockDetailService sockDetailService,
            final SockRepository sockRepository, final PatternRepository patternRepository,
            final MaterialRepository materialRepository, final SizeRepository sizeRepository,
            final ColorRepository colorRepository, final DiscountRepository discountRepository,
            final ImageSockDetailRepository imageSockDetailRepository) {
        this.sockDetailService = sockDetailService;
        this.sockRepository = sockRepository;
        this.patternRepository = patternRepository;
        this.materialRepository = materialRepository;
        this.sizeRepository = sizeRepository;
        this.colorRepository = colorRepository;
        this.discountRepository = discountRepository;
        this.imageSockDetailRepository = imageSockDetailRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("sockValues", sockRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Sock::getId, Sock::getCode)));
        model.addAttribute("patternValues", patternRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Pattern::getId, Pattern::getCode)));
        model.addAttribute("materialValues", materialRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Material::getId, Material::getCode)));
        model.addAttribute("sizeValues", sizeRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Size::getId, Size::getCode)));
        model.addAttribute("colorValues", colorRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Color::getId, Color::getCode)));
        model.addAttribute("discountValues", discountRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Discount::getId, Discount::getCouponCode)));
        model.addAttribute("imageValues", imageSockDetailRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(ImageSockDetail::getId, ImageSockDetail::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("sockDetails", sockDetailService.findAll());
        return "sockDetail/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("sockDetail") final SockDetailDTO sockDetailDTO) {
        return "sockDetail/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("sockDetail") @Valid final SockDetailDTO sockDetailDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sockDetail/add";
        }
        sockDetailService.create(sockDetailDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sockDetail.create.success"));
        return "redirect:/sockDetails";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id, final Model model) {
        model.addAttribute("sockDetail", sockDetailService.get(id));
        return "sockDetail/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id,
            @ModelAttribute("sockDetail") @Valid final SockDetailDTO sockDetailDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sockDetail/edit";
        }
        sockDetailService.update(id, sockDetailDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sockDetail.update.success"));
        return "redirect:/sockDetails";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = sockDetailService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            sockDetailService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("sockDetail.delete.success"));
        }
        return "redirect:/sockDetails";
    }

}
