package ent.musa.musa.models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ent.musa.musa.controllers.ShoppingCartController;

@Component
public class ShoppingCartAssembler
    implements RepresentationModelAssembler<ShoppingCart, EntityModel<ShoppingCart>
> {
    @Override
    public EntityModel<ShoppingCart> toModel(ShoppingCart shoppingCart) {
        return EntityModel.of(shoppingCart,
                linkTo(methodOn(ShoppingCartController.class).findOne(shoppingCart.getId())).withSelfRel(),
                linkTo(methodOn(ShoppingCartController.class).all()).withRel("customers"));
    }
}
