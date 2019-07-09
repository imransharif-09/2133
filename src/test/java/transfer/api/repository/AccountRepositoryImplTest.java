package transfer.api.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import transfer.api.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class AccountRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private AccountRepositoryImpl underTest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findById() {
        underTest.findById("123");
        verify(entityManager, times(1)).find(Account.class, "123");
    }

    @Test
    public void findAll() {
        List<Account> list = new ArrayList<>();
        Query query = Mockito.mock(Query.class);
        when(entityManager.createQuery(ArgumentMatchers.anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(list);
        underTest.findAll();
        verify(entityManager, times(1)).createQuery(ArgumentMatchers.eq("SELECT p FROM Account p"));
    }

    @Test
    public void saveAll() {
        Account account = new Account();

        underTest.saveAll(Arrays.asList(account));
        verify(entityManager, times(1)).persist(ArgumentMatchers.refEq(account));
    }

    @Test(expected=Exception.class)
    public void testPerformTransactionThrowExceptionWhenSomethingWentWrong() {

        Account fromAccount = new Account();
        Account toAccount = new Account();

        when(entityManager.merge(ArgumentMatchers.refEq(fromAccount))).thenThrow(Exception.class);
        underTest.performTransaction(fromAccount, toAccount);

        verify(entityManager, times(1)).merge(ArgumentMatchers.refEq(fromAccount));
        verify(entityManager, never()).merge(ArgumentMatchers.refEq(toAccount));
    }


    @Test
    public void testPerformTransactionWhenAllAccountsAreValid() {
        Account fromAccount = new Account();
        Account toAccount = new Account();
        underTest.performTransaction(fromAccount, toAccount);
        verify(entityManager, times(2)).merge(ArgumentMatchers.any());
    }

    @Test
    public void add() {
        Account account = new Account();
        underTest.add(account);
        verify(entityManager, times(1)).persist(account);
    }
}