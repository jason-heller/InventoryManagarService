package jh.github.com.dao;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import jh.github.com.IdentifiableEntity;

@Component
public class DaoAssembler implements RepresentationModelAssembler<IdentifiableEntity, EntityModel<IdentifiableEntity>> {

    @Override
    public EntityModel<IdentifiableEntity> toModel(IdentifiableEntity identifiable) {

        return EntityModel.of(identifiable,
                linkTo(methodOn(Dao.class).getById(identifiable.getId())).withSelfRel(),
                linkTo(methodOn(Dao.class).getAll()).withRel(identifiable.getRel()));
    }

    public ResponseEntity<?> toResponse(IdentifiableEntity identifiable) {
        EntityModel<IdentifiableEntity> entityModel = toModel(identifiable);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}