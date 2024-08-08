package com.br.acmpay.application.domains.models;

import com.br.acmpay.application.domains.exceptions.DocumentInvalidException;
import com.br.acmpay.application.domains.exceptions.EmailInvalidException;
import com.br.acmpay.application.domains.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDomain {
    private long id;

    private String name;

    private String email;

    private List<AccountDomain> accounts;

    private String phone;

    private String document;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private List<CustomerDomain> customerList;

    public CustomerDomain create(CustomerDomain customer) throws EmailInvalidException, DocumentInvalidException {
        checkEmail(customer.getEmail());
        checkDocument(customer.getDocument());

        this.setId(customer.getId());
        this.setName(customer.getName());
        this.setEmail(customer.getEmail());
        this.setPhone(customer.getPhone());
        this.setDocument(customer.getDocument());
        this.setAccounts(customer.getAccounts());
        this.setCreateAt(customer.getCreateAt());
        this.setUpdateAt(customer.getUpdateAt());
        this.customerList.add(this);

        return this;
    }

    public List<CustomerDomain> list() {
        return this.getCustomerList();
    }

    public CustomerDomain delete(long id) throws NotFoundException {
        CustomerDomain deleteCustomer = this.customerList.stream().filter(customer -> customer.getId() == id).findFirst().orElseThrow(() -> new NotFoundException("Customer not found"));
        this.customerList.remove(deleteCustomer);

        return deleteCustomer;
    }

    public CustomerDomain update(CustomerDomain customer) throws NotFoundException, EmailInvalidException, DocumentInvalidException {
        checkEmailWithExcludeCustomerId(customer.getEmail(), customer.getId());
        checkDocumentWithExcludeCustomerId(customer.getDocument(), customer.getId());

        CustomerDomain updateCustomer = getCustomerById(customer.getId());
        updateCustomer.setName(customer.getName());
        updateCustomer.setEmail(customer.getEmail());
        updateCustomer.setDocument(customer.getDocument());
        updateCustomer.setPhone(customer.getPhone());
        updateCustomer.setAccounts(customer.getAccounts());
        updateCustomer.setUpdateAt(LocalDateTime.now());

        return updateCustomer;
    }

    public CustomerDomain getCustomerById(long id) throws NotFoundException {
        return this.customerList.stream().filter(customerFilter -> customerFilter.getId() == id).findFirst().orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    public CustomerDomain getCustomerByDocument(String document) throws NotFoundException {
        return this.customerList.stream().filter(customerFilter -> customerFilter.getDocument().equals(document)).findFirst().orElseThrow(() -> new NotFoundException("Customer not found by document"));
    }

    private void checkEmail(String email) throws EmailInvalidException {
        this.customerList.stream().filter(customerFilter -> customerFilter.getEmail().equals(email)).findFirst().orElseThrow(() -> new EmailInvalidException("Email already existent"));
    }

    private void checkEmailWithExcludeCustomerId(String email, long excludeCustomerId) throws EmailInvalidException {
        this.customerList.stream().filter(customerFilter -> customerFilter.getId() != excludeCustomerId && customerFilter.getEmail().equals(email)).findFirst().orElseThrow(() -> new EmailInvalidException("Email already existent"));
    }

    private void checkDocument(String document) throws DocumentInvalidException {
        this.customerList.stream().filter(customerFilter -> customerFilter.getDocument().equals(document)).findFirst().orElseThrow(() -> new DocumentInvalidException("Document already existent"));
    }

    private void checkDocumentWithExcludeCustomerId(String document, long excludeCustomerId) throws DocumentInvalidException {
        this.customerList.stream().filter(customerFilter -> customerFilter.getId() != excludeCustomerId && customerFilter.getDocument().equals(document)).findFirst().orElseThrow(() -> new DocumentInvalidException("Document already existent"));
    }
}
