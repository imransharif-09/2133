package transfer.api.repository;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import transfer.api.model.Account;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Singleton
public class AccountRepositoryImpl implements AccountRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public AccountRepositoryImpl(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Account findById(String accountNumber) {
        return entityManager.find(Account.class, accountNumber);
    }

    @Override
    @Transactional
    public List<Account> findAll() {
        return entityManager.createQuery(
                "SELECT p FROM Account p").getResultList();
    }

    @Override
    @Transactional
    public void saveAll(List<Account> accounts) {
        for(Account account : accounts) {
            entityManager.persist(account);
        }
    }

    @Override
    @Transactional // This annotation is intentional and is for explicit to achieve transaction
    public void performTransaction(Account fromAccount, Account toAccount) {
        entityManager.merge(fromAccount);
        entityManager.merge(toAccount);
    }

    @Override
    @Transactional
    public void add(Account account) {
        entityManager.persist(account);
    }
}
