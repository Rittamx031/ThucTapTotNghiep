package stock.thuctap.stock.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Account;
import stock.thuctap.stock.domain.Cart;
import stock.thuctap.stock.domain.Customer;
import stock.thuctap.stock.model.AccountDTO;
import stock.thuctap.stock.repos.AccountRepository;
import stock.thuctap.stock.repos.CartRepository;
import stock.thuctap.stock.repos.CustomerRepository;
import stock.thuctap.stock.util.NotFoundException;
import stock.thuctap.stock.util.WebUtils;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;

    public AccountService(final AccountRepository accountRepository,
            final CustomerRepository customerRepository, final CartRepository cartRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
    }

    public List<AccountDTO> findAll() {
        final List<Account> accounts = accountRepository.findAll(Sort.by("id"));
        return accounts.stream()
                .map(account -> mapToDTO(account, new AccountDTO()))
                .toList();
    }

    public AccountDTO get(final Integer id) {
        return accountRepository.findById(id)
                .map(account -> mapToDTO(account, new AccountDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final AccountDTO accountDTO) {
        accountDTO.setUpdatedAt(LocalDateTime.now());

        final Account account = new Account();
        mapToEntity(accountDTO, account);
        return accountRepository.save(account).getId();
    }

    public void update(final Integer id, final AccountDTO accountDTO) {
        accountDTO.setUpdatedAt(LocalDateTime.now());

        final Account account = accountRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(accountDTO, account);
        accountRepository.save(account);
    }

    public void delete(final Integer id) {
        accountRepository.deleteById(id);
    }

    private AccountDTO mapToDTO(final Account account, final AccountDTO accountDTO) {
        accountDTO.setId(account.getId());
        accountDTO.setUpdatedAt(account.getUpdatedAt());
        accountDTO.setDeleted(account.getDeleted());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setPassword(account.getPassword());
        accountDTO.setStatus(account.getStatus());
        accountDTO.setCustomer(account.getCustomer() == null ? null : account.getCustomer().getId());
        return accountDTO;
    }

    private Account mapToEntity(final AccountDTO accountDTO, final Account account) {
        account.setUpdatedAt(accountDTO.getUpdatedAt());
        account.setDeleted(accountDTO.getDeleted());
        account.setEmail(accountDTO.getEmail());
        account.setPassword(accountDTO.getPassword());
        account.setStatus(accountDTO.getStatus());
        final Customer customer = accountDTO.getCustomer() == null ? null
                : customerRepository.findById(accountDTO.getCustomer())
                        .orElseThrow(() -> new NotFoundException("customer not found"));
        account.setCustomer(customer);
        return account;
    }

    public String getReferencedWarning(final Integer id) {
        final Account account = accountRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Cart accountCart = cartRepository.findFirstByAccount(account);
        if (accountCart != null) {
            return WebUtils.getMessage("account.cart.account.referenced", accountCart.getId());
        }
        return null;
    }

}
