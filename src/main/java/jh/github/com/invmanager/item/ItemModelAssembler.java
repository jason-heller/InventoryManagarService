package jh.github.com.invmanager.item;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import jh.github.com.invmanager.item.Item;
import jh.github.com.invmanager.item.ItemHandler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ItemModelAssembler implements RepresentationModelAssembler<Item, EntityModel<Item>> {

    @Override
    public EntityModel<Item> toModel(Item item) {

        return EntityModel.of(item, //
                linkTo(methodOn(ItemHandler.class).one(item.getId())).withSelfRel(),
                linkTo(methodOn(ItemHandler.class).all()).withRel("items"));
    }
}
