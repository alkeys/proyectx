package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pelicula_caracteristica", schema = "public")
public class PeliculaCaracteristica {
    @Id
    @Column(name = "id_pelicula_caracteristica", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPliculaCaracteristica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_pelicula")
    private TipoPelicula idTipoPelicula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pelicula")
    private Pelicula idPelicula;

    @Lob
    @Column(name = "valor")
    private String valor;

    public PeliculaCaracteristica(Long idPliculaCaracteristica) {
        this.idPliculaCaracteristica = idPliculaCaracteristica;
    }

    public PeliculaCaracteristica()
    {

    }

    public Long getIdPliculaCaracteristica() {
        return idPliculaCaracteristica;
    }

    public void setIdPliculaCaracteristica(Long id) {
        this.idPliculaCaracteristica = id;
    }

    public TipoPelicula getIdTipoPelicula() {
        return idTipoPelicula;
    }

    public void setIdTipoPelicula(TipoPelicula idTipoPelicula) {
        this.idTipoPelicula = idTipoPelicula;
    }

    public Pelicula getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Pelicula idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Object getId() {
        return idPliculaCaracteristica;
    }
}