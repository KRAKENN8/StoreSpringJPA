package ee.ivkhk.sportstoreNPTV23.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AppService<T, ID> extends JpaRepository<T, ID> {
}
