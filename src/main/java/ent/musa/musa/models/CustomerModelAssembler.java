package ent.musa.musa.models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentattionModelAssembler;
import org.springframework.stereotype.Component;

@Component
class CustomerModelAssembler
    implements RepresentattionModelAssembler<Customer,
               EntityModel<Customer>
> {
    @Override
    public EntityModel<Customer> toModel(Customer customer) {
      return EntityModel.of(customer,
          linkTo(methodOn(CustomerController.class).one(customer.getId())).withSelfRel(),
          linkTo(methodOn(CustomerController.class).all()).withRel("customers"));
    }
}
