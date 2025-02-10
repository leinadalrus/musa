package ent.musa.musa.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import ent.musa.musa.models.Customer;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import ent.musa.musa.data.repository.ProducerRepository;
import ent.musa.musa.data.ProducerNotFoundException;
import ent.musa.musa.models.Producer;
import ent.musa.musa.models.ProducerModelAssembler;

@RestController
public class ProducerController {
    final ProducerRepository repository;
    final ProducerModelAssembler assembler;

    public ProducerController(ProducerRepository repository,
                              ProducerModelAssembler assembler
    ) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/producers")
    public CollectionModel<EntityModel<Producer>> all() {
        List<EntityModel<Producer>> producers = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(producers,
                linkTo(methodOn(ProducerController.class).all()).withSelfRel());
    }

    @GetMapping("/producers/{id}")
    public EntityModel<Producer> findOne(@PathVariable Long id) {

        Class<ProducerController> controller = ProducerController.class;
        linkTo(methodOn(controller).findOne(id)).withSelfRel();

        var producer = repository.findById(id)
                .orElseThrow(() -> new ProducerNotFoundException(id));

        return assembler.toModel(producer);
    }
}
