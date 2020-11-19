package com.playdo.rest;

import com.playdo.model.Portfolio;
import com.playdo.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@BasePathAwareController
public class CustomPortfolioController {

    private final PortfolioRepository repo;

    @Autowired
    public CustomPortfolioController(PortfolioRepository repo) {
        this.repo = repo;
        System.out.println(CustomPortfolioController.class);
    }

    @GetMapping("/portfolios")
    @ResponseBody
    public Iterable<Portfolio> findAll() {
        Iterable<Portfolio> portfolios = repo.findAll();
        StreamSupport.stream(portfolios.spliterator(), false).forEach(portfolio -> {
            portfolio.setStocks(portfolio.getStocks().stream().map(str -> str.toLowerCase()+"-beta").collect(Collectors.toSet()));
        });
        return portfolios;
    }
}
