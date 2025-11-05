package com.smartfuture.mycarkeeper.application.web

import com.smartfuture.mycarkeeper.application.mappers.toDTO
import com.smartfuture.mycarkeeper.core.domain.Car
import com.smartfuture.mycarkeeper.core.domain.repositories.CarRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*


@Controller
@RequestMapping("/web/cars")
class CarWebController(
    private val carRepository: CarRepository
) {

    @GetMapping
    fun listCars(model: Model): String {
        val cars = carRepository.getAllCars()
        model.addAttribute("cars", cars)
        return "cars/list"
    }

    @GetMapping("/new")
    fun showCreateForm(model: Model): String {
        model.addAttribute("car", CarForm())
        model.addAttribute("isEdit", false)
        return "cars/form"
    }

    @PostMapping
    fun createCar(
        @ModelAttribute carForm: CarForm,
        redirectAttributes: RedirectAttributes
    ): String {
        val car = Car(
            id = UUID.randomUUID().toString(),
            make = carForm.make,
            model = carForm.model,
            year = carForm.year,
            vin = carForm.vin
        )
        carRepository.save(car)
        redirectAttributes.addFlashAttribute("message", "Car created successfully!")
        return "redirect:/web/cars"
    }

    @GetMapping("/{id}")
    fun showCarDetails(@PathVariable id: String, model: Model): String {
        val car = carRepository.findByIdWithDetails(id)?.toDTO()
        return if (car != null) {
            model.addAttribute("car", car)
            "cars/details"
        } else {
            model.addAttribute("error", "Car not found")
            "error"
        }
    }

    @GetMapping("/{id}/edit")
    fun showEditForm(@PathVariable id: String, model: Model): String {
        val car = carRepository.findById(id)
        return if (car != null) {
            model.addAttribute(
                "car", CarForm(
                    id = car.id,
                    make = car.make,
                    model = car.model,
                    year = car.year,
                    vin = car.vin
                )
            )
            model.addAttribute("isEdit", true)
            "cars/form"
        } else {
            model.addAttribute("error", "Car not found")
            "error"
        }
    }

    @PostMapping("/{id}")
    fun updateCar(
        @PathVariable id: String,
        @ModelAttribute carForm: CarForm,
        redirectAttributes: RedirectAttributes
    ): String {
        val existingCar = carRepository.findById(id)
        return if (existingCar != null) {
            val updatedCar = existingCar.copy(
                make = carForm.make,
                model = carForm.model,
                year = carForm.year,
                vin = carForm.vin
            )
            carRepository.save(updatedCar)
            redirectAttributes.addFlashAttribute("message", "Car updated successfully!")
            "redirect:/web/cars"
        } else {
            redirectAttributes.addFlashAttribute("error", "Car not found")
            "redirect:/web/cars"
        }
    }

    @PostMapping("/{id}/delete")
    fun deleteCar(
        @PathVariable id: String,
        redirectAttributes: RedirectAttributes
    ): String {
        val deleted = carRepository.delete(id)
        if (deleted) {
            redirectAttributes.addFlashAttribute("message", "Car deleted successfully!")
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to delete car")
        }
        return "redirect:/web/cars"
    }
}

data class CarForm(
    val id: String = "",
    val make: String = "",
    val model: String = "",
    val year: Int = 2024,
    val vin: String = ""
)
