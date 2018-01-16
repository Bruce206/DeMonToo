package de.bruss.demontoo.instance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.bruss.demontoo.common.CustomLocalDateTimeDeserializer;
import de.bruss.demontoo.common.CustomLocalDateTimeSerializer;
import de.bruss.demontoo.common.MonitoredSuperEntity;
import de.bruss.demontoo.domain.Domain;
import de.bruss.demontoo.server.Server;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Instance extends MonitoredSuperEntity {
	
	@Id
	@GeneratedValue
	private Long id;
		
	private String identifier;
	private String version;
	private String licensedFor;

	@ManyToOne
    @JsonIgnoreProperties("instances")
	private InstanceType instanceType;

	@Transient
    private String type;

	@Column(name = "prod", columnDefinition = "boolean NOT NULL DEFAULT false")
	private boolean prod;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="instance", fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Domain> domains = new HashSet<>();

    @OneToMany(cascade=CascadeType.ALL, mappedBy="instance", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<InstanceDetail> details = new HashSet<>();

	@ManyToOne
    @JsonIgnoreProperties("instances")
	private Server server;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime lastMessage;

	@Override
	public String toString() {
		return this.server.getIp() + "\t" + this.server.getServerName() + "\t" + this.identifier;
	}

    public void addDomain(Domain domain) {
	    this.domains.add(domain);
    }

    public Map<String, List<InstanceDetail>> getInstanceDetailsByCategory() {
	    return this.details.stream().sorted(Comparator.comparing(InstanceDetail::getCategory)).collect(Collectors.groupingBy(InstanceDetail::getCategory));
    }

    public Map<String, List<InstanceDetail>> getInstanceDetailsByKey() {
        return this.details.stream().sorted(Comparator.comparing(InstanceDetail::getKey)).collect(Collectors.groupingBy(InstanceDetail::getKey));
    }
}
