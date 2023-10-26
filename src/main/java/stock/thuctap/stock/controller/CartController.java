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
import stock.thuctap.stock.domain.Account;
import stock.thuctap.stock.model.CartDTO;
import stock.thuctap.stock.repos.AccountRepository;
import stock.thuctap.stock.service.CartService;
import stock.thuctap.stock.util.CustomCollectors;
import stock.thuctap.stock.util.WebUtils;

@Controller
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final AccountRepository accountRepository;

    public CartController(final CartService cartService,
            final AccountRepository accountRepository) {
        this.cartService = cartService;
        this.accountRepository = accountRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("accountValues", accountRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Account::getId, Account::getEmail)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("carts", cartService.findAll());
        return "cart/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("cart") final CartDTO cartDTO) {
        return "cart/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("cart") @Valid final CartDTO cartDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cart/add";
        }
        cartService.create(cartDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("cart.create.success"));
        return "redirect:/carts";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id, final Model model) {
        model.addAttribute("cart", cartService.get(id));
        return "cart/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id,
            @ModelAttribute("cart") @Valid final CartDTO cartDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cart/edit";
        }
        cartService.update(id, cartDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("cart.update.success"));
        return "redirect:/carts";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Integer id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = cartService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            cartService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("cart.delete.success"));
        }
        return "redirect:/carts";
    }

}
