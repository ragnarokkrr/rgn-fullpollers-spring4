package rgn.fullpollers.crud;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Rest Repository for {@link Task}.
 *
 * @author ragnarokkrr
 */
@RepositoryRestResource
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByTaskArchived(@Param("archivedFalse") int taskArchivedFalse);

    List<Task> findByTaskStatus(@Param("status") String taskStatus);
}
