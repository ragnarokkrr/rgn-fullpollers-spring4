package rgn.fullpollers.hello;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * PersonRepository.
 *
 * @author ragnarokkrr
 */

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

    List<Person> findByLastName(@Param("name") String name);


    @Query("select p from Person p where p.firstName = :name")
    List<Person> findByFirstName(@Param("name")String firstname);

}
