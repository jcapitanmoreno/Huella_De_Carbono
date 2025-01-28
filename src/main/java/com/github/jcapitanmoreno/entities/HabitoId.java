package com.github.jcapitanmoreno.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class HabitoId implements java.io.Serializable {
    private static final long serialVersionUID = 6289302179993494193L;
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "id_actividad", nullable = false)
    private Integer idActividad;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HabitoId entity = (HabitoId) o;
        return Objects.equals(this.idActividad, entity.idActividad) &&
                Objects.equals(this.idUsuario, entity.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idActividad, idUsuario);
    }

    @Override
    public String toString() {
        return "HabitoId{" +
                "idUsuario=" + idUsuario +
                ", idActividad=" + idActividad +
                '}';
    }
}