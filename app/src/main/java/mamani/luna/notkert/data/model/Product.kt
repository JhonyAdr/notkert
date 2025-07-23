package mamani.luna.notkert.data.model

data class Product(
    val id: String = "",
    val nombre: String = "",
    val descripcion: String = "",
    val precio: Double = 0.0,
    val categoria: String = "",
    val stock: Int = 0,
    val condicion: String = "",
    val imagePath: String = "",
    val userId: String = "" // usuario que subió el producto
)