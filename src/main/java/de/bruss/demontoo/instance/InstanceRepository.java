package de.bruss.demontoo.instance;

import de.bruss.demontoo.server.Server;
import de.bruss.demontoo.websockets.Instance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface InstanceRepository extends JpaRepository<Instance, Long>, JpaSpecificationExecutor<Instance> {
	List<Instance> findByServerAndIdentifier(Server server, String identifier);

    List<Instance> findByInstanceType(InstanceType type);

    List<Instance> findByInstanceTypeAndAndExcludeFromHealthcheckFalse(InstanceType type);

    List<Instance> findByAndExcludeFromHealthcheckFalse();
}
