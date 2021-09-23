sealed class Result<T>
class Success<T>(val data : T) : Result<T>()
class Error<T>(val message  : String = "Ошибка. Лист пуст") : Result<T>()

enum class Operation{
    SORT_ASC
    {
        override fun <T : Comparable<T>> invoke(list: List<T>) : List<T>
        {
            return list.sorted()
        }
    },

    SORT_DESC
    {
        override fun <T : Comparable<T>> invoke(list: List<T>) : List<T>
        {
            return list.sortedDescending()
        }
    },

    SHUFFLE
    {
        override fun <T : Comparable<T>> invoke(list: List<T>) : List<T>
        {
            return list.shuffled()
        }
    };

    abstract operator fun <T : Comparable<T>> invoke(list: List<T>): List<T>
}

fun <T : Comparable<T>> List<T>.operate(operation: Operation): Result<List<T>>
{
    if (this.isEmpty()) return Error()
    when(operation){

        Operation.SORT_ASC -> return Success(Operation.SORT_ASC(this))

        Operation.SORT_DESC -> return Success(Operation.SORT_DESC(this))

        Operation.SHUFFLE -> return Success(Operation.SHUFFLE(this))
    }
}

fun generateStrings(stringsLength: Int, length : Int) : List<String>
{
    val latinChar = ('a'..'z')
    val list = mutableListOf<String>()
    for(i in 1..length) list.add((1..stringsLength).map { latinChar.random() }.joinToString(""))
    return list
}

fun generateIntegers(length : Int) : List<Int>
{
    return (0..100).shuffled().take(length)
}

fun <T : Comparable<T>> Result<List<T>>.print()
{
    when(this){
        is Error -> println(this.message)
        is Success -> println(this.data)
    }
}

fun main() {

    val listError1 = generateStrings(3,0)
    listError1.operate(Operation.SORT_ASC).print() // Ошибка

    val list1 = generateStrings(3,4)
    println(list1)

    list1.operate(Operation.SORT_ASC).print() // Успех
    list1.operate(Operation.SORT_DESC).print() // Успех
    list1.operate(Operation.SHUFFLE).print() // Успех

    val listError2 = generateIntegers(0)
    listError2.operate(Operation.SORT_ASC).print() // Ошибка

    val list2 = generateIntegers(5)
    println(list2)

    list2.operate(Operation.SORT_ASC).print() // Успех
    list2.operate(Operation.SORT_DESC).print() // Успех
    list2.operate(Operation.SHUFFLE).print() // Успех

}
