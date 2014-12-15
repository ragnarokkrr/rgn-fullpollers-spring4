package org.rgn.fp.equip.shard.silo.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Repository for {@link Silo}.
 *
 * @author ragnarokkrr
 */
@RepositoryRestResource
public interface SiloRepository extends CrudRepository<Silo, Long> {
    List<Silo> findByName(@Param("name") String name);
}
