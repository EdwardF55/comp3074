package ca.gbc.comp3074.fuller_edward.lab4

data class CustomerModel
    (val id: Int = -1,
     val name: String,
     val age: Int,
     val active: Boolean){
    override fun toString(): String{
        return "$id $name ($age) [$active]"
    }
}