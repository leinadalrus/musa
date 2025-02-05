package ent.musa.musa.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import ent.musa.musa.data.repository.CustomerRepository;
import ent.musa.musa.data.CustomerNotFoundException;
import ent.musa.musa.models.Customer;

@RestController
class CustomerController {
    private final CustomerRepository repository;
    private final CustomerModelAssembler assembler;

    CustomerController(CustomerRepository repository,
            CustomerModelAssembler assembler
    ) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @RequestMapping("/customers")
    public HttpEntity<Customer> customer(@RequestParam(value = "username",
                defaultValue = "JonDoe1999") String username
    ) {
        Customer customer = new Customer(username);
        customer.add(linkTo(methodOn(CustomerController.class)
                    .customer(username)).withSelfRel());

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/customers")
    List<EntityModel<Customer>> all() {
        List<EntityModel<Customer>> customers = repository.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel
            .of(customers, linkTo(methodOn(CustomerController.class)
                        .all())).withSelfRel();
    }

    @PostMapping("/customers")
    ResponseEntity<?> newCustomer(@RequestBody Customer newCustomer) {
        EntityModel<Customer> entityModel =
            assembler.toModel(repository.save(newCustomer));

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @GetMapping("/customers/{id}")
    EntityModel<Customer> findOne(@PathVariable Long id) {

        Class<CustomerController> controller = CustomerController.class;
        Link findOneLink = linkTo(methodOn(controller).findOne(id)).withSelfRel();

        repository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException(id));

        return assembler.toModel(customer);
    }

    @PutMapping("/customers/{id}")
    ResponseEntity<?> replaceCustomer(@RequestBody Customer newCustomer,
        @PathVariable Long id
    ) {
        Customer updatedCustomer = repository.findById(id)
            .map(customer -> {
                customer.setUsername(newCustomer.getUsername());
                return repository.save(customer);
            })
            .orElseGet(() -> {
                return repository.save(newCustomer);
            });

        EntityModel<Customer> model = assembler.toModel(updatedCustomer);

        return ResponseEntity
            .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(model);
    }

    @DeleteMapping("/customers/{id}")
    ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
