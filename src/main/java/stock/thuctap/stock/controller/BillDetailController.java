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
import stock.thuctap.stock.domain.Bill;
import stock.thuctap.stock.domain.BillDetailId;
import stock.thuctap.stock.domain.SockDetail;
import stock.thuctap.stock.model.BillDetailDTO;
import stock.thuctap.stock.repos.BillRepository;
import stock.thuctap.stock.repos.SockDetailRepository;
import stock.thuctap.stock.service.BillDetailService;
import stock.thuctap.stock.util.CustomCollectors;
import stock.thuctap.stock.util.WebUtils;

@Controller
@RequestMapping("/billDetails")
public class BillDetailController {

    private final BillDetailService billDetailService;
    private final BillRepository billRepository;
    private final SockDetailRepository sockDetailRepository;

    public BillDetailController(final BillDetailService billDetailService,
            final BillRepository billRepository, final SockDetailRepository sockDetailRepository) {
        this.billDetailService = billDetailService;
        this.billRepository = billRepository;
        this.sockDetailRepository = sockDetailRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("billValues", billRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Bill::getId, Bill::getStatus)));
        model.addAttribute("sockDetailValues", sockDetailRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SockDetail::getId, SockDetail::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("billDetails", billDetailService.findAll());
        return "billDetail/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("billDetail") final BillDetailDTO billDetailDTO) {
        return "billDetail/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("billDetail") @Valid final BillDetailDTO billDetailDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "billDetail/add";
        }
        billDetailService.create(billDetailDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("billDetail.create.success"));
        return "redirect:/billDetails";
    }

    @GetMapping("/edit/{idbill}/{idproduct}")
    public String edit(@PathVariable("idbill") final Integer idbill, @PathVariable("idproduct") final Integer idproduct,
            final Model model) {
        model.addAttribute("billDetail", billDetailService.get(new BillDetailId(idbill, idproduct)));
        return "billDetail/edit";
    }

    @PostMapping("/edit/{idbill}/{idproduct}")
    public String edit(@PathVariable("idbill") final Integer idbill,
            @PathVariable("idproduct") final Integer idproduct,
            @ModelAttribute("billDetail") @Valid final BillDetailDTO billDetailDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "billDetail/edit";
        }
        billDetailService.update(new BillDetailId(idbill, idproduct), billDetailDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("billDetail.update.success"));
        return "redirect:/billDetails";
    }

    @PostMapping("/delete/{idbill}/{idproduct}")
    public String delete(@PathVariable("idproduct") final Integer idproduct,
            @PathVariable("idbill") final Integer idbill,
            final RedirectAttributes redirectAttributes) {
        billDetailService.delete(new BillDetailId(idbill, idproduct));
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("billDetail.delete.success"));
        return "redirect:/billDetails";
    }

}
