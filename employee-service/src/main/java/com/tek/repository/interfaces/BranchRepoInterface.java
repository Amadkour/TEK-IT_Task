package com.tek.repository.interfaces;

import com.tek.entity.Branch;

import java.util.List;
import java.util.Optional;

public interface BranchRepoInterface {
    Branch save(Branch branch);
    Optional<Branch> findById(Long id);
    public Optional<Branch> findByName(String name);
    List<Branch> findAll();
    void deleteById(Long id);

}