package stock.thuctap.stock.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Customer;
import stock.thuctap.stock.domain.CustomerAddress;
import stock.thuctap.stock.model.CustomerAddressDTO;
import stock.thuctap.stock.repos.CustomerAddressRepository;
import stock.thuctap.stock.repos.CustomerRepository;
import stock.thuctap.stock.util.NotFoundException;


@Service
public class CustomerAddressService {

    private final CustomerAddressRepository customerAddressRepository;
    private final CustomerRepository customerRepository;

    public CustomerAddressService(final CustomerAddressRepository customerAddressRepository,
            final CustomerRepository customerRepository) {
        this.customerAddressRepository = customerAddressRepository;
        this.customerRepository = customerRepository;
    }

    public List<CustomerAddressDTO> findAll() {
        final List<CustomerAddress> customerAddresses = customerAddressRepository.findAll(Sort.by("id"));
        return customerAddresses.stream()
                .map(customerAddress -> mapToDTO(customerAddress, new CustomerAddressDTO()))
                .toList();
    }

    public CustomerAddressDTO get(final Integer id) {
        return customerAddressRepository.findById(id)
                .map(customerAddress -> mapToDTO(customerAddress, new CustomerAddressDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CustomerAddressDTO customerAddressDTO) {
        final CustomerAddress customerAddress = new CustomerAddress();
        mapToEntity(customerAddressDTO, customerAddress);
        return customerAddressRepository.save(customerAddress).getId();
    }

    public void update(final Integer id, final CustomerAddressDTO customerAddressDTO) {
        final CustomerAddress customerAddress = customerAddressRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(customerAddressDTO, customerAddress);
        customerAddressRepository.save(customerAddress);
    }

    public void delete(final Integer id) {
        customerAddressRepository.deleteById(id);
    }

    private CustomerAddressDTO mapToDTO(final CustomerAddress customerAddress,
            final CustomerAddressDTO customerAddressDTO) {
        customerAddressDTO.setId(customerAddress.getId());
        customerAddressDTO.setUpdatedAt(customerAddress.getUpdatedAt());
        customerAddressDTO.setDeleted(customerAddress.getDeleted());
        customerAddressDTO.setAddress(customerAddress.getAddress());
        customerAddressDTO.setCity(customerAddress.getCity());
        customerAddressDTO.setCountry(customerAddress.getCountry());
        customerAddressDTO.setCustomer(customerAddress.getCustomer() == null ? null : customerAddress.getCustomer().getId());
        return customerAddressDTO;
    }

    private CustomerAddress mapToEntity(final CustomerAddressDTO customerAddressDTO,
            final CustomerAddress customerAddress) {
        customerAddress.setUpdatedAt(customerAddressDTO.getUpdatedAt());
        customerAddress.setDeleted(customerAddressDTO.getDeleted());
        customerAddress.setAddress(customerAddressDTO.getAddress());
        customerAddress.setCity(customerAddressDTO.getCity());
        customerAddress.setCountry(customerAddressDTO.getCountry());
        final Customer customer = customerAddressDTO.getCustomer() == null ? null : customerRepository.findById(customerAddressDTO.getCustomer())
                .orElseThrow(() -> new NotFoundException("customer not found"));
        customerAddress.setCustomer(customer);
        return customerAddress;
    }

}
