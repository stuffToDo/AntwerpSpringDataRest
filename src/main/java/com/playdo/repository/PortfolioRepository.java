package com.playdo.repository;

import com.playdo.model.Portfolio;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PortfolioRepository extends CrudRepository<Portfolio, UUID> {
}