package com.github.jcapitanmoreno.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "huella", schema = "carbono")
public class Huella {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private com.github.jcapitanmoreno.entities.Usuario idUsuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_actividad")
    private Actividad idActividad;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "unidad", nullable = false, length = 50)
    private String unidad;

    @ColumnDefault("current_timestamp()")
    @Column(name = "fecha")
    private Instant fecha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public com.github.jcapitanmoreno.entities.Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(com.github.jcapitanmoreno.entities.Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Actividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Actividad idActividad) {
        this.idActividad = idActividad;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Huella huella = (Huella) o;
        return Objects.equals(id, huella.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Huella{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idActividad=" + idActividad +
                ", valor=" + valor +
                ", unidad='" + unidad + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}