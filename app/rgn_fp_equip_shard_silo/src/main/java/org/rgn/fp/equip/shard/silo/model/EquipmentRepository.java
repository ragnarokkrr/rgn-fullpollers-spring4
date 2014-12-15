package org.rgn.fp.equip.shard.silo.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Rest Repository for {@link Equipment}.
 *
 * @author ragnarokkrr
 */
@RepositoryRestResource
public interface EquipmentRepository extends PagingAndSortingRepository<Equipment, Long> {

    Page<Equipment> findByCityName(@Param("cityName") String cityName, Pageable pageable);
    List<Equipment> findByName(@Param("name") String name);



}
