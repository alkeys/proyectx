package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "asiento", schema = "public")
@NamedQueries({
        @NamedQuery(name = "Asiento.findByIdSala",query = "SELECT asi FROM Asiento asi WHERE asi.idSala.idSala=:idSala ORDER BY asi.idAsiento ASC "),
        @NamedQuery(name = "Asiento.countByIdSala", query = "SELECT COUNT(asi.idAsiento) FROM Asiento asi WHERE asi.idSala.idSala=:idSala")
})
public class Asiento {
    @Id
    @Column(name = "id_asiento", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsiento;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_sala")
    private Sala idSala;


    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "idAsiento")
    private List<ReservaDetalle> reservaDetalles;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "idAsiento")
    private List<AsientoCaracteristica> asientoCaracteristicaList;

    @Size(max = 155)
    @Column(name = "nombre", length = 155)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    public Long getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Long id) {
        this.idAsiento = id;
    }

    public Sala getIdSala() {
        return idSala;
    }

    public void setIdSala(Sala idSala) {
        this.idSala = idSala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<ReservaDetalle> getReservaDetalles() {
        return reservaDetalles;
    }

    public void setReservaDetalles(List<ReservaDetalle> reservaDetalles) {
        this.reservaDetalles = reservaDetalles;
    }

    public List<AsientoCaracteristica> getAsientoCaracteristicaList() {
        return asientoCaracteristicaList;
    }

    public void setAsientoCaracteristicaList(List<AsientoCaracteristica> asientoCaracteristicaList) {
        this.asientoCaracteristicaList = asientoCaracteristicaList;
    }
}