package entities

class Category(var id: Long, var name: String) {

    override fun toString(): String {
        return "Category(id=$id, name='$name')"
    }
}