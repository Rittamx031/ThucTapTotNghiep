package stock.thuctap.stock.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Employee;
import stock.thuctap.stock.domain.Role;
import stock.thuctap.stock.model.RoleDTO;
import stock.thuctap.stock.repos.EmployeeRepository;
import stock.thuctap.stock.repos.RoleRepository;
import stock.thuctap.stock.util.NotFoundException;
import stock.thuctap.stock.util.WebUtils;


@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;

    public RoleService(final RoleRepository roleRepository,
            final EmployeeRepository employeeRepository) {
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<RoleDTO> findAll() {
        final List<Role> roles = roleRepository.findAll(Sort.by("id"));
        return roles.stream()
                .map(role -> mapToDTO(role, new RoleDTO()))
                .toList();
    }

    public RoleDTO get(final Integer id) {
        return roleRepository.findById(id)
                .map(role -> mapToDTO(role, new RoleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final RoleDTO roleDTO) {
        final Role role = new Role();
        mapToEntity(roleDTO, role);
        return roleRepository.save(role).getId();
    }

    public void update(final Integer id, final RoleDTO roleDTO) {
        final Role role = roleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(roleDTO, role);
        roleRepository.save(role);
    }

    public void delete(final Integer id) {
        roleRepository.deleteById(id);
    }

    private RoleDTO mapToDTO(final Role role, final RoleDTO roleDTO) {
        roleDTO.setId(role.getId());
        roleDTO.setUpdatedAt(role.getUpdatedAt());
        roleDTO.setDeleted(role.getDeleted());
        roleDTO.setName(role.getName());
        roleDTO.setPermissions(role.getPermissions());
        roleDTO.setRoles(role.getRoles());
        roleDTO.setStatus(role.getStatus());
        return roleDTO;
    }

    private Role mapToEntity(final RoleDTO roleDTO, final Role role) {
        role.setUpdatedAt(roleDTO.getUpdatedAt());
        role.setDeleted(roleDTO.getDeleted());
        role.setName(roleDTO.getName());
        role.setPermissions(roleDTO.getPermissions());
        role.setRoles(roleDTO.getRoles());
        role.setStatus(roleDTO.getStatus());
        return role;
    }

    public String getReferencedWarning(final Integer id) {
        final Role role = roleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Employee roleEmployee = employeeRepository.findFirstByRole(role);
        if (roleEmployee != null) {
            return WebUtils.getMessage("role.employee.role.referenced", roleEmployee.getId());
        }
        return null;
    }

}
