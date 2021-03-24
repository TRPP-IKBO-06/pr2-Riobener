package ru.mirea.trppsecond13.controllers;


import com.opencsv.bean.CsvToBeanBuilder;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import ru.mirea.trppsecond13.entity.Car;


import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

/** Контроллер для работы с автомобилями. */
@Controller("/car")
public class CarController {

    /** Список автомобилей. */
    private final List<Car> carList;

    /** Конструктор. */
    public CarController() {
        carList = new CsvToBeanBuilder<Car>(new InputStreamReader(this.getClass()
                .getResourceAsStream("/MOCK_DATA.csv")))
                .withType(Car.class).build().parse();
    }

    /**
     * Получить список автомобилей.
     * @return список автомобилей
     */
    @Get()
    public HttpResponse<List<Car>> getCars() {
        return HttpResponse.ok(carList);
    }

    /**
     * Найти автомобиль по идентификатору.
     * @param id идентификатор автомобиля
     * @return Автомобиль, если есть, иначе 404 ошибка
     */
    @Get("/{id}")
    public HttpResponse<Car> findById(Long id) {
        Optional<Car> result = carList.stream().filter(it -> it.getId().equals(id)).findAny();
        if (result.isPresent()) {
            return HttpResponse.ok(result.get());
        } else {
            return HttpResponse.notFound();
        }
    }
}
