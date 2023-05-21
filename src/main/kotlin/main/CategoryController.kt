package main

import entities.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryController(
    @Autowired
    val managerBeans: ManagerBeans
) {
    private val categoryService = managerBeans.categoryService()

    @GetMapping("/categories")
    fun getCategories(): List<Category> {
        return categoryService.getCategories()
    }

    @PostMapping("/categories/insert")
    fun addCategory(@RequestBody category: Category): Category {
        return categoryService.addCategory(category)
    }
}