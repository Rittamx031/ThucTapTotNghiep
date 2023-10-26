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
import stock.thuctap.stock.domain.Cart;
import stock.thuctap.stock.domain.CartDetailId;
import stock.thuctap.stock.domain.SockDetail;
import stock.thuctap.stock.model.CartDetailDTO;
import stock.thuctap.stock.repos.CartRepository;
import stock.thuctap.stock.repos.SockDetailRepository;
import stock.thuctap.stock.service.CartDetailService;
import stock.thuctap.stock.util.CustomCollectors;
import stock.thuctap.stock.util.WebUtils;

@Controller
@RequestMapping("/cartDetails")
public class CartDetailController {

    private final CartDetailService cartDetailService;
    private final CartRepository cartRepository;
    private final SockDetailRepository sockDetailRepository;

    public CartDetailController(final CartDetailService cartDetailService,
            final CartRepository cartRepository, final SockDetailRepository sockDetailRepository) {
        this.cartDetailService = cartDetailService;
        this.cartRepository = cartRepository;
        this.sockDetailRepository = sockDetailRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("cartValues", cartRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Cart::getId, Cart::getStatus)));
        model.addAttribute("sockDetailValues", sockDetailRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SockDetail::getId, SockDetail::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("cartDetails", cartDetailService.findAll());
        return "cartDetail/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("cartDetail") final CartDetailDTO cartDetailDTO) {
        return "cartDetail/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("cartDetail") @Valid final CartDetailDTO cartDetailDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cartDetail/add";
        }
        cartDetailService.create(cartDetailDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("cartDetail.create.success"));
        return "redirect:/cartDetails";
    }

    @GetMapping("/edit/{idcart}/{idproduct}")
    public String edit(@PathVariable final Long idcart, @PathVariable final Long idproduct, final Model model) {
        model.addAttribute("cartDetail", cartDetailService.get(new CartDetailId(idcart, idproduct)));
        return "cartDetail/edit";
    }

    @PostMapping("/edit/{idcart}/{idproduct}")
    public String edit(@PathVariable final Long idcart, @PathVariable final Long idproduct,
            @ModelAttribute("cartDetail") @Valid final CartDetailDTO cartDetailDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cartDetail/edit";
        }
        cartDetailService.update(new CartDetailId(idcart, idproduct), cartDetailDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("cartDetail.update.success"));
        return "redirect:/cartDetails";
    }

    @PostMapping("/delete/{idcart}/{idproduct}")
    public String delete(@PathVariable final Long idcart, @PathVariable final Long idproduct, final RedirectAttributes redirectAttributes) {
        cartDetailService.delete(new CartDetailId(idcart, idproduct));
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("cartDetail.delete.success"));
        return "redirect:/cartDetails";
    }

}
