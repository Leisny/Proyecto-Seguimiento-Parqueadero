package com.example.parqueadero.controller;

import com.example.parqueadero.model.Vehiculo;
import com.example.parqueadero.repository.VehiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/parqueadero")
public class ParqueaderoController {

    private final VehiculoRepositorio vehiculoRepositorio;
    private final MessageSource messageSource;

    /**
     * Inyecto el repositorio de vehículos y la fuente de mensajes para manejar la internacionalización.
     */
    @Autowired
    public ParqueaderoController(VehiculoRepositorio vehiculoRepositorio, MessageSource messageSource) {
        this.vehiculoRepositorio = vehiculoRepositorio;
        this.messageSource = messageSource;
    }

    /**
     * Devuelvo la lista completa de vehículos registrados en el parqueadero.
     */
    @GetMapping
    public ResponseEntity<List<Vehiculo>> listarVehiculos() {
        return ResponseEntity.ok(vehiculoRepositorio.findAll());
    }

    /**
     * Registro la entrada de un vehículo al parqueadero.
     * Primero, verifico que la placa no esté registrada sin una hora de salida.
     * Luego, valido que el tipo de vehículo sea válido (carro o moto).
     * Finalmente, guardo el vehículo en la base de datos.
     */
    @PostMapping("/ingresar")
    public ResponseEntity<String> ingresarVehiculo(@RequestParam String placa, @RequestParam String tipo) {
        Locale locale = LocaleContextHolder.getLocale();
        String carType = messageSource.getMessage("vehicle.car", null, locale);
        String motoType = messageSource.getMessage("vehicle.motorcycle", null, locale);

        // Verifico si ya existe un vehículo con esa placa que aún no ha salido
        Optional<Vehiculo> vehiculoExistente = vehiculoRepositorio.findByPlacaAndHoraSalidaIsNull(placa);
        if (vehiculoExistente.isPresent()) {
            return ResponseEntity.badRequest().body(
                    messageSource.getMessage("parking.vehicle.exists", new Object[]{placa}, locale)
            );
        }

        // Valido que el tipo de vehículo sea correcto
        if (!tipo.equalsIgnoreCase(carType) && !tipo.equalsIgnoreCase(motoType)) {
            return ResponseEntity.badRequest().body(
                    messageSource.getMessage("parking.vehicle.type.invalid", null, locale)
            );
        }

        // Registro el nuevo vehículo con la hora actual
        Vehiculo vehiculo = new Vehiculo(placa, tipo, LocalDateTime.now());
        vehiculoRepositorio.save(vehiculo);
        return ResponseEntity.ok(
                messageSource.getMessage("parking.vehicle.registered", new Object[]{placa}, locale)
        );
    }

    /**
     * Registro la salida de un vehículo.
     * Busco el vehículo por su placa y verifico que no tenga una hora de salida registrada.
     * Calculo el tiempo transcurrido y aplico una tarifa dependiendo del tipo de vehículo.
     * Finalmente, actualizo el registro con la hora de salida y el costo total.
     */
    @PutMapping("/salida/{placa}")
    public ResponseEntity<String> registrarSalida(@PathVariable String placa) {
        Locale locale = LocaleContextHolder.getLocale();

        Optional<Vehiculo> vehiculoOpt = vehiculoRepositorio.findByPlacaAndHoraSalidaIsNull(placa);
        if (vehiculoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    messageSource.getMessage("parking.vehicle.notfound", null, locale)
            );
        }

        Vehiculo vehiculo = vehiculoOpt.get();
        LocalDateTime horaSalida = LocalDateTime.now();
        vehiculo.setHoraSalida(horaSalida);

        // Calculo el tiempo transcurrido en horas
        long horas = Duration.between(vehiculo.getHoraEntrada(), horaSalida).toHours();
        if (horas == 0) horas = 1; // Cobro mínimo de una hora

        // Determino la tarifa según el tipo de vehículo
        String carType = messageSource.getMessage("vehicle.car", null, locale);
        double tarifa = vehiculo.getTipo().equalsIgnoreCase(carType) ? 2000 : 1000;
        double costoTotal = horas * tarifa;
        vehiculo.setCostoTotal(costoTotal);

        // Guardo los cambios en la base de datos
        vehiculoRepositorio.save(vehiculo);
        return ResponseEntity.ok(
                messageSource.getMessage("parking.vehicle.exit", new Object[]{placa, costoTotal}, locale)
        );
    }

    /**
     * Busco un vehículo por su placa y verifico si aún no ha salido.
     * Si lo encuentro, devuelvo su información, de lo contrario, respondo con un error 404.
     */
    @GetMapping("/buscar/{placa}")
    public ResponseEntity<?> buscarVehiculo(@PathVariable String placa) {
        Optional<Vehiculo> vehiculo = vehiculoRepositorio.findByPlacaAndHoraSalidaIsNull(placa);
        if (vehiculo.isPresent()) {
            return ResponseEntity.ok(vehiculo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
