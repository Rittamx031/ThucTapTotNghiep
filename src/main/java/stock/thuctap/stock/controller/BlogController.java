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
import stock.thuctap.stock.domain.SockDetail;
import stock.thuctap.stock.model.BlogDTO;
import stock.thuctap.stock.repos.SockDetailRepository;
import stock.thuctap.stock.service.BlogService;
import stock.thuctap.stock.util.CustomCollectors;
import stock.thuctap.stock.util.WebUtils;


@Controller
@RequestMapping("/blogs")
public class BlogController {

    private final BlogService blogService;
    private final SockDetailRepository sockDetailRepository;

    public BlogController(final BlogService blogService,
            final SockDetailRepository sockDetailRepository) {
        this.blogService = blogService;
        this.sockDetailRepository = sockDetailRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("sockDetailBlogSockDetailsValues", sockDetailRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SockDetail::getId, SockDetail::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("blogs", blogService.findAll());
        return "blog/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("blog") final BlogDTO blogDTO) {
        return "blog/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("blog") @Valid final BlogDTO blogDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "blog/add";
        }
        blogService.create(blogDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("blog.create.success"));
        return "redirect:/blogs";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id, final Model model) {
        model.addAttribute("blog", blogService.get(id));
        return "blog/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id,
            @ModelAttribute("blog") @Valid final BlogDTO blogDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "blog/edit";
        }
        blogService.update(id, blogDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("blog.update.success"));
        return "redirect:/blogs";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Integer id,
            final RedirectAttributes redirectAttributes) {
        blogService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("blog.delete.success"));
        return "redirect:/blogs";
    }

}
