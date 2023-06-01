package main

import entities.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

@RestController
class CategoryController(
    @Autowired
    val managerBeans: ManagerBeans
) {
    private val log = Logger.getLogger(this.javaClass.name)
    private val categoryService = managerBeans.categoryService()

    @GetMapping("/categories")
    fun getCategories(): List<Category> {
        log.info("Request \"getCategories\" received")
        val result = categoryService.getCategories()
        log.info("Categories were received: $result")
        return result
    }

    @PostMapping("/categories/insert")
    fun addCategory(@RequestBody category: Category): Category {
        log.info("Category to add: $category")
        return categoryService.addCategory(category)
    }
}