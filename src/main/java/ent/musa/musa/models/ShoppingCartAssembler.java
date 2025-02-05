package ent.musa.musa.models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class ShoppingCartAssembler
    implements RepresentationModelAssembler<ShoppingCart, EntityModel<ShoppingCart>
> {
    @Override EntityModel<ShoppingCart> toModel(ShoppingCart shoppingCart) {
        EntityModel<ShoppingCart> model = EntityModel.of(shoppingCart,
            linkTo(methodOn(ShoppingCartController.class).one(shoppingCart.getId())).withSelfRel(),
            linkTo(methodOn(ShoppingCartController.class).all()).withRel("shoppingcart"));

        if (shoppingCart.getStatus() == Status.IN_PROGRESS) {
            model.add(linkTo(methodOn(ShoppingCartController.class)
                  .cancel(shoppingCart.getId())).withRel("cancel"));
            model.add(linkTo(methodOn(ShoppingCartController.class)
                  .complete(shoppingCart.getId())).withRel("complete"));
        }

        return model;
    }
}
