package br.com.supernova.citiesbrazil.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Country")
@Table(name = "pais")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    @Id
    @ApiModelProperty(notes = "Unique entity identifier", required = true)
    private Long id;

    @Column(name = "nome", nullable = false)
    @ApiModelProperty(notes = "Unique value where it cannot be null", required = true)
    private String name;

    @Column(name = "nome_pt", nullable = false)
    @ApiModelProperty(notes = "Unique value where it cannot be null", required = true)
    private String portugueseName;

    @Column(name = "sigla", nullable = false)
    @ApiModelProperty(notes = "Unique value where it cannot be null", required = true)
    private String code;

    @ApiModelProperty(notes = "Unique value where it cannot be null", required = true)
    private Integer bacen;
}
