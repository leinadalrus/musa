package ent.musa.musa.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ent.musa.musa.models.Customer;

interface CustomerRepository extends JpaRepository<Customer, Long> {}
