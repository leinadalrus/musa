package ent.musa.musa.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import ent.musa.musa.data.CustomerNotFoundException;
import ent.musa.musa.models.Customer;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import ent.musa.musa.data.repository.ProductRepository;
import ent.musa.musa.data.ProductNotFoundException;
import ent.musa.musa.models.Product;
import ent.musa.musa.models.ProductModelAssembler;

@RestController
public class ProductController {
    final ProductRepository repository;
    final ProductModelAssembler assembler;

    public ProductController(ProductRepository repository, ProductModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/products/{id}")
    public EntityModel<Product> findOne(@PathVariable Long id) {

        Class<ProductController> controller = ProductController.class;
        linkTo(methodOn(controller).findOne(id)).withSelfRel();

        var product = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        return assembler.toModel(product);
    }

    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> all() {
        List<EntityModel<Product>> products = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(products,
                linkTo(methodOn(ProductController.class).all()).withSelfRel());
    }

}
