package com.br.acmpay.application.domains.models;

import com.br.acmpay.application.domains.exceptions.BalanceWithdrawException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDomain {
    private long id;

    private String number;

    private String agency;

    private BigDecimal balance;

    private CustomerDomain customer;

    private List<CardDomain> cards;

    private List<String> transactions;

    private boolean close;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private List<AccountDomain> accountList;

    public void create(AccountDomain account) {
        this.setId(account.getId());
        this.setCreateAt(account.getCreateAt());
        this.setUpdateAt(account.getUpdateAt());
        this.setCustomer(account.getCustomer());
        this.setCards(account.getCards());
        this.setNumber(account.getNumber());
        this.setBalance(account.getBalance());
        this.setAgency(account.getAgency());
        this.setClose(account.isClose());
        this.transactions.add("Account created successfully " + LocalDateTime.now());
    }

    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
        this.transactions.add("Deposit successfully " + amount.toString() + " " + LocalDateTime.now());
    }

    public void withdraw(BigDecimal amount) throws BalanceWithdrawException {
        checkBalance(amount);

        this.balance = this.balance.subtract(amount);
        this.transactions.add("Withdraw successfully " + amount.toString() + " " + LocalDateTime.now());
    }

    public void createTransaction(BigDecimal amount, AccountDomain destinationAccount) throws BalanceWithdrawException {
        this.withdraw(amount);

        /*Transaction transaction = Transaction.builder().sourceAccount(this).amount(amount)
                .destinationAccount(destinationAccount).dataTransaction(LocalDateTime.now()).build();*/

        destinationAccount.deposit(amount);
//        transactions.add(transaction);

//        return transaction;
    }

    private void checkBalance(BigDecimal amount) throws BalanceWithdrawException {
        if (this.balance.compareTo(amount) < 0) {
            this.transactions.add("Withdraw error " + amount.toString() + " " + LocalDateTime.now());

            throw new BalanceWithdrawException("Error withdraw");
        }
    }
}