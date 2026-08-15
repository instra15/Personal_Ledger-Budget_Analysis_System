package com.plbas.plbas.repository;

import com.plbas.plbas.entity.Account;
import com.plbas.plbas.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {


    boolean existsByNameAndType(@NotBlank String name, @NotNull AccountType type);

}
