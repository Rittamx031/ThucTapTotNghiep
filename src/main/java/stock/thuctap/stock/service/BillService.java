package stock.thuctap.stock.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Bill;
import stock.thuctap.stock.domain.BillDetail;
import stock.thuctap.stock.domain.Customer;
import stock.thuctap.stock.domain.Employee;
import stock.thuctap.stock.domain.PayMethod;
import stock.thuctap.stock.model.BillDTO;
import stock.thuctap.stock.repos.BillDetailRepository;
import stock.thuctap.stock.repos.BillRepository;
import stock.thuctap.stock.repos.CustomerRepository;
import stock.thuctap.stock.repos.EmployeeRepository;
import stock.thuctap.stock.repos.PayMethodRepository;
import stock.thuctap.stock.util.NotFoundException;
import stock.thuctap.stock.util.WebUtils;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final PayMethodRepository payMethodRepository;
    private final BillDetailRepository billDetailRepository;

    public BillService(final BillRepository billRepository,
            final EmployeeRepository employeeRepository,
            final CustomerRepository customerRepository,
            final PayMethodRepository payMethodRepository,
            final BillDetailRepository billDetailRepository) {
        this.billRepository = billRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.payMethodRepository = payMethodRepository;
        this.billDetailRepository = billDetailRepository;
    }

    public List<BillDTO> findAll() {
        final List<Bill> bills = billRepository.findAll(Sort.by("id"));
        return bills.stream()
                .map(bill -> mapToDTO(bill, new BillDTO()))
                .toList();
    }

    public BillDTO get(final Integer id) {
        return billRepository.findById(id)
                .map(bill -> mapToDTO(bill, new BillDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final BillDTO billDTO) {
        final Bill bill = new Bill();
        mapToEntity(billDTO, bill);
        return billRepository.save(bill).getId();
    }

    public void update(final Integer id, final BillDTO billDTO) {
        final Bill bill = billRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(billDTO, bill);
        billRepository.save(bill);
    }

    public void delete(final Integer id) {
        billRepository.deleteById(id);
    }

    private BillDTO mapToDTO(final Bill bill, final BillDTO billDTO) {
        billDTO.setId(bill.getId());
        billDTO.setUpdatedAt(bill.getUpdatedAt());
        billDTO.setDeleted(bill.getDeleted());
        billDTO.setDateCreate(bill.getDateCreate());
        billDTO.setDatePayment(bill.getDatePayment());
        billDTO.setNote(bill.getNote());
        billDTO.setStatus(bill.getStatus());
        billDTO.setEmployee(bill.getEmployee() == null ? null : bill.getEmployee().getId());
        billDTO.setCustomer(bill.getCustomer() == null ? null : bill.getCustomer().getId());
        billDTO.setPayMethod(bill.getPayMethod() == null ? null : bill.getPayMethod().getId());
        return billDTO;
    }

    private Bill mapToEntity(final BillDTO billDTO, final Bill bill) {
        bill.setUpdatedAt(billDTO.getUpdatedAt());
        bill.setDeleted(billDTO.getDeleted());
        bill.setDateCreate(billDTO.getDateCreate());
        bill.setDatePayment(billDTO.getDatePayment());
        bill.setNote(billDTO.getNote());
        bill.setStatus(billDTO.getStatus());
        final Employee employee = billDTO.getEmployee() == null ? null : employeeRepository.findById(billDTO.getEmployee())
                .orElseThrow(() -> new NotFoundException("employee not found"));
        bill.setEmployee(employee);
        final Customer customer = billDTO.getCustomer() == null ? null : customerRepository.findById(billDTO.getCustomer())
                .orElseThrow(() -> new NotFoundException("customer not found"));
        bill.setCustomer(customer);
        final PayMethod payMethod = billDTO.getPayMethod() == null ? null : payMethodRepository.findById(billDTO.getPayMethod())
                .orElseThrow(() -> new NotFoundException("payMethod not found"));
        bill.setPayMethod(payMethod);
        return bill;
    }

    public String getReferencedWarning(final Integer id) {
        final Bill bill = billRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final BillDetail billBillDetail = billDetailRepository.findFirstByBill(bill);
        if (billBillDetail != null) {
            return WebUtils.getMessage("bill.billDetail.bill.referenced", billBillDetail.getId());
        }
        return null;
    }

}
