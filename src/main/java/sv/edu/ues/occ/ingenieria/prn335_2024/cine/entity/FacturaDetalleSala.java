package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "factura_detalle_sala", schema = "public")
public class FacturaDetalleSala {
    @Id
    @Column(name = "id_factura_detalle_sala", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFacturaDetalleSala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_factura")
    private Factura idFactura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reserva_detalle")
    private ReservaDetalle idReservaDetalle;

    @Column(name = "monto", precision = 10, scale = 2)
    private BigDecimal monto;

    public FacturaDetalleSala(Long idFacturaDetalleSala) {
    this.idFacturaDetalleSala = idFacturaDetalleSala;
    }

    public FacturaDetalleSala() {

    }

    public Long getIdFacturaDetalleSala() {
        return idFacturaDetalleSala;
    }

    public void setIdFacturaDetalleSala(Long id) {
        this.idFacturaDetalleSala = id;
    }

    public Factura getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Factura idFactura) {
        this.idFactura = idFactura;
    }

    public ReservaDetalle getIdReservaDetalle() {
        return idReservaDetalle;
    }

    public void setIdReservaDetalle(ReservaDetalle idReservaDetalle) {
        this.idReservaDetalle = idReservaDetalle;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

}