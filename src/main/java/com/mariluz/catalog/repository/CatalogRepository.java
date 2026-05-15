package com.mariluz.catalog.repository;

import com.mariluz.catalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends JpaRepository<Product, Integer> {}
