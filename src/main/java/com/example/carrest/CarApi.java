package com.example.carrest;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/cars", produces = {MediaType.APPLICATION_JSON_VALUE})


public class CarApi {

    private List<Car> carList;

    public CarApi(List<Car> carList) {
        this.carList = carList;
        carList.add(new Car(1L, "Skoda", "Kodiaq", "black"));
        carList.add(new Car(2L, "Tesla", "X", "red"));
        carList.add(new Car(3L, "Honda", "CR-V", "blue"));
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCars() {
        return new ResponseEntity<>(carList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Optional<Car> firstId = carList.stream()
                .filter(car -> car.getId() == id)
                .findFirst();
        if (firstId.isPresent()) {
            return new ResponseEntity<>(firstId.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // wszystkie kolory zrobic, przetestować
    @GetMapping("/color/{color}")
    public ResponseEntity<Car> getCarByColor(@PathVariable String color) {
        Optional<Car> firstFoundColor = carList.stream()
                .filter(car -> car.getColor().equals(color))
                .findFirst();
        if (firstFoundColor.isPresent()) {
            return new ResponseEntity<>(firstFoundColor.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity addCar(@RequestBody Car newCar) {
        boolean add = carList.add(newCar);
        if (add) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Car> modifyCar(@RequestBody Car newCar){
        Optional<Car> first = carList.stream()
                .filter(car -> car.getId() == newCar.getId())
                .findFirst();
        if(first.isPresent()) {
            carList.remove(first.get());
            carList.add(newCar);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/edit/{id}/{color}")
    public ResponseEntity modifyCarByColor(@RequestBody Car newCar, @PathVariable Long id, @PathVariable String color){
        Optional<Car> first = carList.stream()
                .filter(car -> car.getId() == newCar.getId())
                .findFirst();
        if(first.isPresent()) {
            first.get().setColor(color);
            carList.remove(id);
            carList.add(first.get());
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Car> deleteCarById(@PathVariable Long id) {
        Optional<Car> first = carList.stream()
                .filter(video -> video.getId() == id)
                .findFirst();
        if(first.isPresent()){
            carList.remove(first.get());
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
