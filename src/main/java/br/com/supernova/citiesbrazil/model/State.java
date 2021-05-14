package br.com.supernova.citiesbrazil.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Entity(name = "State")
@Table(name = "estado")
@Data
@NoArgsConstructor
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class State {

    @Id
    @ApiModelProperty(notes = "Unique entity identifier", required = true)
    private Long id;

    @Column(name = "nome")
    @ApiModelProperty(notes = "Unique value where it cannot be null", required = true)
    private String name;

    @ApiModelProperty(notes = "Unique value where it cannot be null", required = true)
    private String uf;

    @ApiModelProperty(notes = "Unique value where it cannot be null", required = true)
    private Integer ibge;

    @ManyToOne
    @JoinColumn(name = "pais", referencedColumnName = "id")
    private Country country;

    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "ddd", columnDefinition = "jsonb")
    private List<Integer> ddd;
}
