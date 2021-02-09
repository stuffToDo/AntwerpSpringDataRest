# AntwerpSpringDataRest 

## Overview
Just wanted to create a simple SpringBoot Spring Data Rest project. Seems some developers think Spring Rest and Spring Data Rest are the same but are actually very different in what is deployed OOTB (out-of-the-box). Spring Data Rest deploys endpoints OOTB (ie. GET, POST, PUT, DELETE etc.) for Spring repositories that extend CrudRepository, JpaRepository or others such as MongoDB and Redis. The endpoints follow the HAETOAS pattern, providing link navigation within the resulting JSON. 

## Override predefined endpoints
An interesting aspect of Spring Dats Rest is the ability to replace the OOTB endpoints with implementations of your own.  See the CustomPortfolioController as an example of replacing the plural endpoint-point for GET /portfolios. All other OOTB endpoints for portfolios are still present (ie. POST, PUT and DELETE etc.). This allows developers to target endpoints that need custom logic to support the business requirements. 

```
@BasePathAwareController
public class CustomPortfolioController {

    ...
 
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
```
