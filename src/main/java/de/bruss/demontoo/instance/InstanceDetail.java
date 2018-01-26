package de.bruss.demontoo.instance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class InstanceDetail {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Instance instance;

    private String category;
    private String key;
    private String value;

    public InstanceDetail(String category, String key, String value) {
        this.category = category;
        this.key = key;
        this.value = value;
    }
}
