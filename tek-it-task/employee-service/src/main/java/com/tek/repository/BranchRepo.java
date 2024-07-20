package com.tek.repository;

import com.tek.entity.Branch;
import com.tek.exception.ClientException;
import com.tek.repository.interfaces.BranchRepoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BranchRepo implements BranchRepoInterface {
    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    @Override
    public Branch save(Branch branch) {
        if (branch.getId() == null) {
            entityManager.persist(branch);
            return branch;
        } else {
            return entityManager.merge(branch);
        }
    }

    @Override
    public Optional<Branch> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Branch.class, id));
    }

    @Override
    public List<Branch> findAll() {
        return entityManager.createQuery("from Branch", Branch.class).getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Branch branch = findById(id).orElseThrow(() -> new ClientException("branch", "this branch not available"));
        if (branch != null) {
            entityManager.remove(branch);
        }
    }

    @Override
    public Optional<Branch> findByName(String name) {
        try {
            return Optional.ofNullable(entityManager.createQuery("from Branch where name = :name", Branch.class)
                    .setParameter("name", name)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
