package ent.musa.musa.models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ent.musa.musa.controllers.ProducerController;

@Component
public class ProducerModelAssembler implements RepresentationModelAssembler<Producer, EntityModel<Producer>
> {
    @Override
    public EntityModel<Producer> toModel(Producer producer) {
        return EntityModel.of(producer,
                linkTo(methodOn(ProducerController.class).findOne(producer.getId())).withSelfRel(),
                linkTo(methodOn(ProducerController.class).all()).withRel("producers"));
    }
}
