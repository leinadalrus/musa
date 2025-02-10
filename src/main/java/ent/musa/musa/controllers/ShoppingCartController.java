package ent.musa.musa.controllers;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ent.musa.musa.data.repository.ShoppingCartRepository;
import ent.musa.musa.data.ShoppingCartNotFoundException;
import ent.musa.musa.integrals.Status;
import ent.musa.musa.models.ShoppingCart;
import ent.musa.musa.models.ShoppingCartAssembler;

@RestController
public class ShoppingCartController {
    final ShoppingCartRepository repository;
    final ShoppingCartAssembler assembler;

    public ShoppingCartController(ShoppingCartRepository repository,
        ShoppingCartAssembler assembler
    ) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/shoppingcart")
    public CollectionModel<EntityModel<ShoppingCart>> all() {
        List<EntityModel<ShoppingCart>> shoppingCart =
            repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(shoppingCart,
            linkTo(methodOn(ShoppingCartController.class).all()).withSelfRel());
    }

    @GetMapping("/shoppingcart/{id}")
    public EntityModel<ShoppingCart> findOne(@PathVariable Long id) {
        ShoppingCart shoppingCart = repository.findById(id)
            .orElseThrow(() -> new ShoppingCartNotFoundException(id));

        return assembler.toModel(shoppingCart);
    }

    @PostMapping("/shoppingcart")
    public ResponseEntity<EntityModel<ShoppingCart>> newShoppingCart(
        @RequestBody ShoppingCart shoppingCart
    ) {
        shoppingCart.setStatus(Status.IN_PROGRESS);

        ShoppingCart newShoppingCart = repository.save(shoppingCart);
        return ResponseEntity
            .created(linkTo(methodOn(ShoppingCartController.class)
                  .findOne(newShoppingCart.getId())).toUri())
            .body(assembler.toModel(newShoppingCart));
    }

    @PutMapping("/shoppingcart/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Long id) {
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
                    .withDetail("This form is incomplete with status...\t" +  shoppingCart.getStatus()));
    }

    @DeleteMapping("/shoppingcart")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
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
