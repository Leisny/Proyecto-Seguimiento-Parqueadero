package com.example.parqueadero.repository;

import com.example.parqueadero.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Indico que esta interfaz es un componente de acceso a datos en Spring
public interface VehiculoRepositorio extends JpaRepository<Vehiculo, Long> {

    /**
     * Defino un método para buscar un vehículo por su placa.
     * @param placa Placa del vehículo que quiero buscar.
     * @return Un `Optional<Vehiculo>` que puede contener el vehículo si existe.
     */
    Optional<Vehiculo> findByPlaca(String placa);

    /**
     * Defino un método para buscar un vehículo por su placa,
     * pero solo si aún no ha registrado una hora de salida.
     * Esto me ayuda a verificar si un vehículo sigue en el parqueadero.
     * @param placa Placa del vehículo que quiero buscar.
     * @return Un `Optional<Vehiculo>` con el vehículo si aún está dentro.
     */
    Optional<Vehiculo> findByPlacaAndHoraSalidaIsNull(String placa);

}
