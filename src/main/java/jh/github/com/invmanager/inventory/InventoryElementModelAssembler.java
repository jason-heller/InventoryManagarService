package jh.github.com.invmanager.inventory;

import jh.github.com.invmanager.item.ItemHandler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class InventoryElementModelAssembler implements RepresentationModelAssembler<InventoryElement, EntityModel<InventoryElement>> {

    @Override
    public EntityModel<InventoryElement> toModel(InventoryElement element) {

        return EntityModel.of(element, //
                WebMvcLinkBuilder.linkTo(methodOn(ItemHandler.class).one(element.getId())).withSelfRel(),
                linkTo(methodOn(ItemHandler.class).all()).withRel("items"));
    }
}
