package stock.thuctap.stock.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Account;
import stock.thuctap.stock.domain.Bill;
import stock.thuctap.stock.domain.Customer;
import stock.thuctap.stock.domain.CustomerAddress;
import stock.thuctap.stock.model.CustomerDTO;
import stock.thuctap.stock.repos.AccountRepository;
import stock.thuctap.stock.repos.BillRepository;
import stock.thuctap.stock.repos.CustomerAddressRepository;
import stock.thuctap.stock.repos.CustomerRepository;
import stock.thuctap.stock.util.NotFoundException;
import stock.thuctap.stock.util.WebUtils;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerAddressRepository customerAddressRepository;
    private final AccountRepository accountRepository;
    private final BillRepository billRepository;

    public CustomerService(final CustomerRepository customerRepository,
            final CustomerAddressRepository customerAddressRepository,
            final AccountRepository accountRepository, final BillRepository billRepository) {
        this.customerRepository = customerRepository;
        this.customerAddressRepository = customerAddressRepository;
        this.accountRepository = accountRepository;
        this.billRepository = billRepository;
    }

    public List<CustomerDTO> findAll() {
        final List<Customer> customers = customerRepository.findAll(Sort.by("id"));
        return customers.stream()
                .map(customer -> mapToDTO(customer, new CustomerDTO()))
                .toList();
    }

    public CustomerDTO get(final Integer id) {
        return customerRepository.findById(id)
                .map(customer -> mapToDTO(customer, new CustomerDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CustomerDTO customerDTO) {
        customerDTO.setUpdatedAt(LocalDateTime.now());
        final Customer customer = new Customer();
        mapToEntity(customerDTO, customer);
        return customerRepository.save(customer).getId();
    }

    public void update(final Integer id, final CustomerDTO customerDTO) {
        customerDTO.setUpdatedAt(LocalDateTime.now());
        final Customer customer = customerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(customerDTO, customer);
        customerRepository.save(customer);
    }

    public void delete(final Integer id) {
        customerRepository.deleteById(id);
    }

    private CustomerDTO mapToDTO(final Customer customer, final CustomerDTO customerDTO) {
        customerDTO.setId(customer.getId());
        customerDTO.setUpdatedAt(customer.getUpdatedAt());
        customerDTO.setDeleted(customer.getDeleted());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setBirthday(customer.getBirthday());
        customerDTO.setStatus(customer.getStatus());
        return customerDTO;
    }

    private Customer mapToEntity(final CustomerDTO customerDTO, final Customer customer) {
        customer.setUpdatedAt(customerDTO.getUpdatedAt());
        customer.setDeleted(customerDTO.getDeleted());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setBirthday(customerDTO.getBirthday());
        customer.setStatus(customerDTO.getStatus());
        return customer;
    }

    public String getReferencedWarning(final Integer id) {
        final Customer customer = customerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final CustomerAddress customerCustomerAddress = customerAddressRepository.findFirstByCustomer(customer);
        if (customerCustomerAddress != null) {
            return WebUtils.getMessage("customer.customerAddress.customer.referenced", customerCustomerAddress.getId());
        }
        final Account customerAccount = accountRepository.findFirstByCustomer(customer);
        if (customerAccount != null) {
            return WebUtils.getMessage("customer.account.customer.referenced", customerAccount.getId());
        }
        final Bill customerBill = billRepository.findFirstByCustomer(customer);
        if (customerBill != null) {
            return WebUtils.getMessage("customer.bill.customer.referenced", customerBill.getId());
        }
        return null;
    }

}
