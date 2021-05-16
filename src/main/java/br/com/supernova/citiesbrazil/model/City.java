package br.com.supernova.citiesbrazil.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.geo.Point;

import javax.persistence.*;

@Entity(name = "City")
@Table(name = "cidade")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeDefs(value = {
        @TypeDef(name = "point", typeClass = PointType.class)
})
public class City {

    @Id
    @ApiModelProperty(notes = "Unique entity identifier", required = true)
    private Long id;

    @Column(name = "nome")
    @ApiModelProperty(notes = "Unique value where it cannot be null", required = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "uf", referencedColumnName = "id")
    @ApiModelProperty(notes = "Unique value where it cannot be null", required = true)
    private State state;

    @ApiModelProperty(notes = "Unique value where it cannot be null", required = true)
    private Integer ibge;

    @Column(name = "lat_lon")
    @ApiModelProperty(notes = "Unique value where it cannot be null", required = true)
    private String geolocation;

    @Type(type = "point")
    @Column(name = "lat_lon", updatable = false, insertable = false)
    private Point location;
}
