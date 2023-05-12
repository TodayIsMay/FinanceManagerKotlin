package entities

data class Expense(var amount: Double,
                   var comment: String?,
                   var categoryId: Int)