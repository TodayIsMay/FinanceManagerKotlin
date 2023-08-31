package main

import entities.Category
import exceptions.UserNotAuthorizedException
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.logging.Logger

@RestController
@Tag(name = "Categories")
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

    @GetMapping("/categories/{id}")
    fun getCategoryById(@PathVariable id: Long): Category {
        log.info("Trying to find category with id $id")
        return categoryService.getCategoryById(id)
    }

    @PostMapping("/categories/insert")
    @SecurityRequirement(name = "basicAuth")
    fun addCategory(@RequestBody category: Category, @RequestHeader("Authorization") auth: String?): Category {
        if (auth == null) {
            log.severe("Authorization required")
            throw UserNotAuthorizedException("Authorization required")
        }
        log.info("Category to add: $category")
        return categoryService.addCategory(category, auth)
    }

    @DeleteMapping("/categories/delete/{id}")
    @SecurityRequirement(name = "basicAuth")
    fun deleteCategory(@PathVariable id: Long, @RequestHeader("Authorization") auth: String?): String {
        if (auth == null) {
            log.severe("Authorization required")
            throw UserNotAuthorizedException("Authorization required")
        }
        log.info("Category id to delete: $id")
        return categoryService.deleteCategory(id, auth)
    }
}