package com.example.parqueadero.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity // Indico que esta clase es una entidad y se mapeará a una tabla en la base de datos
public class Vehiculo {

    @Id // Especifico que esta es la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genero automáticamente el ID de cada vehículo
    private Long id; // Identificador único del vehículo en la base de datos

    private String placa; // Almaceno la placa del vehículo

    private String tipo; // Especifico el tipo de vehículo, ya sea "carro" o "moto"

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // Defino el formato de fecha y hora para la entrada
    private LocalDateTime horaEntrada; // Registro la hora en la que el vehículo ingresa al parqueadero

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // Defino el formato de fecha y hora para la salida
    private LocalDateTime horaSalida; // Registro la hora en la que el vehículo sale del parqueadero

    private double costoTotal; // Almaceno el costo total del estacionamiento según el tiempo transcurrido

    /**
     * Constructor vacío requerido por JPA para instanciar objetos sin parámetros.
     */
    public Vehiculo() {}

    /**
     * Constructor que me permite crear un objeto `Vehiculo` con información inicial.
     * @param placa Placa del vehículo
     * @param tipo Tipo de vehículo (carro o moto)
     * @param horaEntrada Hora en la que el vehículo ingresa al parqueadero
     */
    public Vehiculo(String placa, String tipo, LocalDateTime horaEntrada) {
        this.placa = placa;
        this.tipo = tipo;
        this.horaEntrada = horaEntrada;
    }

    /**
     * Obtengo el identificador único del vehículo.
     * @return ID del vehículo.
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigno un identificador único al vehículo.
     * @param id Identificador único del vehículo.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtengo la placa del vehículo.
     * @return Placa del vehículo.
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Asigno una placa al vehículo.
     * @param placa Placa del vehículo.
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * Obtengo el tipo de vehículo.
     * @return Tipo de vehículo ("carro" o "moto").
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Defino el tipo de vehículo.
     * @param tipo Tipo de vehículo ("carro" o "moto").
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtengo la hora de entrada del vehículo al parqueadero.
     * @return Hora de entrada del vehículo.
     */
    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    /**
     * Establezco la hora de entrada del vehículo al parqueadero.
     * @param horaEntrada Hora de entrada del vehículo.
     */
    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    /**
     * Obtengo la hora de salida del vehículo del parqueadero.
     * @return Hora de salida del vehículo.
     */
    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    /**
     * Registro la hora de salida del vehículo del parqueadero.
     * @param horaSalida Hora de salida del vehículo.
     */
    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    /**
     * Obtengo el costo total del estacionamiento.
     * @return Costo total del tiempo de parqueo.
     */
    public double getCostoTotal() {
        return costoTotal;
    }

    /**
     * Establezco el costo total del estacionamiento según el tiempo de uso.
     * @param costoTotal Costo total calculado.
     */
    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }
}
