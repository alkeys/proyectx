package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "asiento_caracteristica", schema = "public")
@NamedQueries({
        @NamedQuery(name = "AsientoCaracteristica.findByIdAsiento",query = "SELECT asiC FROM AsientoCaracteristica asiC WHERE asiC.idAsiento.idAsiento=:idAsiento ORDER BY asiC.idAsientoCaracteristica ASC "),
        @NamedQuery(name = "AsientoCaracteristica.countByIdAsiento", query = "SELECT COUNT(asiC.idAsientoCaracteristica) FROM AsientoCaracteristica asiC WHERE asiC.idAsiento.idAsiento=:idAsiento")
})
public class AsientoCaracteristica {
    @Id
    @Column(name = "id_asiento_caracteristica", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsientoCaracteristica;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_asiento")
    private Asiento idAsiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_asiento")
    private TipoAsiento idTipoAsiento;

    @Lob
    @Column(name = "valor")
    private String valor;

    public Long getIdAsientoCaracteristica() {
        return idAsientoCaracteristica;
    }

    public void setIdAsientoCaracteristica(Long id) {
        this.idAsientoCaracteristica = id;
    }

    public Asiento getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Asiento idAsiento) {
        this.idAsiento = idAsiento;
    }

    public TipoAsiento getIdTipoAsiento() {
        return idTipoAsiento;
    }

    public void setIdTipoAsiento(TipoAsiento idTipoAsiento) {
        this.idTipoAsiento = idTipoAsiento;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}