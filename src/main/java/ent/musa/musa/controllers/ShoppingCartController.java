package ent.musa.musa.controllers;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ent.musa.musa.models.ShoppingCart;
import ent.musa.musa.data.repository.ShoppingCartRepository;
import ent.musa.musa.data.ShoppingCartAssembler;

@RestController
class ShoppingCartController {
    private final ShoppingCartRepository repository;
    private final ShoppingCartAssembler assembler;

    ShoppingCartController(ShoppingCartRepository repository
        ShoppingCartAssembler assembler
    ) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/shoppingcart")
    CollectionModel<EntityModel<ShoppingCart>> all() {
        List<EntityModel<ShoppingCart>> shoppingCart =
            repository.findAll().stream()
                .map(assembler.toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(shoppingCart,
            linkTo(methodOn(ShoppingCartController.class).all()).withSelfRel();
    }

    @GetMapping("/shoppingcart/{id}")
    EntityModel<ShoppingCart> findOne(@PathVariable Long id) {
        ShoppingCart shoppingCart = repository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(id));

        return assembler.toModel(shoppingCart);
    }

    @PostMapping("/shoppingcart")
    ResponseEntity<EntityModel<ShoppingCart>> newShoppingCart(
        @RequestBody ShoppingCart shoppingCart
    ) {
        shoppingCart.setStatus(Status.IN_PROGRESS);
        ShoppingCart shoppingCart = repository.save(shoppingCart);

        return ResponseEntity
            .created(linkTo(methodOn(ShoppingCartController.class)
                  .one(newShoppingCart.getId())).toUri())
            .body(assembler.toModel(newShoppingCart));
    }

    @PutMapping("/shoppingcart/{id}/complete")
    ResponseEntity<?> complete(@PathVariable Long id) {
        ShoppingCart shoppingCart = repository.findById(id)
            .orElseThrow(() -> new ShoppingCartNotFoundException(id));

        if (shoppingCart.getStatus() == Status.IN_PROGRESS) {
            shoppingCart.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toModel(repository.save(shoppingCart)));
        }

        return ResponseEntity.
            status(HttpStatus.METHOD_NOT_ALLOWED)
            .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
            .body(Problem.create()
                    .withTitle("Method not followed")
                    .withDetail("This form is incomplete with status...\t" +  shoppingCart.getStatus());
    }

    @DeleteMapping("/shoppingcart")
    ResponseEntity<?> cancel(@PathVariable Long id) {
        ShoppingCart shoppingCart = repository.findById(id)
            .orElseThrow(() -> new ShoppingCartNotFoundException(id));

        if (shoppingCart.getStatus() == Status.IN_PROGRESS) {
            shoppingCart.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(repository.save(shoppingCart)));
        }

        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
            .body(Problem.create()
                    .withTitle("Method not allowed")
                    .withDetail(
                        "Cannot cancel this shopping cart with status:...\t"
                            + shoppingCart.getStatus()));
    }
}
