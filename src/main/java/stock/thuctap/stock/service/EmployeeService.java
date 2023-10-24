package stock.thuctap.stock.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Bill;
import stock.thuctap.stock.domain.Employee;
import stock.thuctap.stock.domain.Role;
import stock.thuctap.stock.model.EmployeeDTO;
import stock.thuctap.stock.repos.BillRepository;
import stock.thuctap.stock.repos.EmployeeRepository;
import stock.thuctap.stock.repos.RoleRepository;
import stock.thuctap.stock.util.NotFoundException;
import stock.thuctap.stock.util.WebUtils;


@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final BillRepository billRepository;

    public EmployeeService(final EmployeeRepository employeeRepository,
            final RoleRepository roleRepository, final BillRepository billRepository) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.billRepository = billRepository;
    }

    public List<EmployeeDTO> findAll() {
        final List<Employee> employees = employeeRepository.findAll(Sort.by("id"));
        return employees.stream()
                .map(employee -> mapToDTO(employee, new EmployeeDTO()))
                .toList();
    }

    public EmployeeDTO get(final Integer id) {
        return employeeRepository.findById(id)
                .map(employee -> mapToDTO(employee, new EmployeeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final EmployeeDTO employeeDTO) {
        final Employee employee = new Employee();
        mapToEntity(employeeDTO, employee);
        return employeeRepository.save(employee).getId();
    }

    public void update(final Integer id, final EmployeeDTO employeeDTO) {
        final Employee employee = employeeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(employeeDTO, employee);
        employeeRepository.save(employee);
    }

    public void delete(final Integer id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeDTO mapToDTO(final Employee employee, final EmployeeDTO employeeDTO) {
        employeeDTO.setId(employee.getId());
        employeeDTO.setUpdatedAt(employee.getUpdatedAt());
        employeeDTO.setDeleted(employee.getDeleted());
        employeeDTO.setName(employee.getName());
        employeeDTO.setBirthday(employee.getBirthday());
        employeeDTO.setPhone(employee.getPhone());
        employeeDTO.setAddress(employee.getAddress());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setPassword(employee.getPassword());
        employeeDTO.setStatus(employee.getStatus());
        employeeDTO.setRole(employee.getRole() == null ? null : employee.getRole().getId());
        return employeeDTO;
    }

    private Employee mapToEntity(final EmployeeDTO employeeDTO, final Employee employee) {
        employee.setUpdatedAt(employeeDTO.getUpdatedAt());
        employee.setDeleted(employeeDTO.getDeleted());
        employee.setName(employeeDTO.getName());
        employee.setBirthday(employeeDTO.getBirthday());
        employee.setPhone(employeeDTO.getPhone());
        employee.setAddress(employeeDTO.getAddress());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPassword(employeeDTO.getPassword());
        employee.setStatus(employeeDTO.getStatus());
        final Role role = employeeDTO.getRole() == null ? null : roleRepository.findById(employeeDTO.getRole())
                .orElseThrow(() -> new NotFoundException("role not found"));
        employee.setRole(role);
        return employee;
    }

    public String getReferencedWarning(final Integer id) {
        final Employee employee = employeeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Bill employeeBill = billRepository.findFirstByEmployee(employee);
        if (employeeBill != null) {
            return WebUtils.getMessage("employee.bill.employee.referenced", employeeBill.getId());
        }
        return null;
    }

}
