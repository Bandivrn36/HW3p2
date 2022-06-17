fun main() {
    val transferAmount = 680000.0
    val amountPerDay = 0.0
    val monthlyAmount = 0.0
    val paymentSystem = "VK Pay"  //VK Pay/Mastercard/Maestro/Visa/Mir
    val stock = true
    println("Сумма: ${transferAmount / 100} рублей")
    println("Комиссия составит: ${comission(transferAmount, amountPerDay, paymentSystem,stock) / 100} рублей.")
    println("Сумма перевода составит: ${transfer(transferAmount, monthlyAmount, paymentSystem, stock, amountPerDay) / 100} рублей.")
}
fun comission(transferAmount:Double, monthlyAmount:Double, paymentSystem:String, stock:Boolean): Double {
    val comission:Double = when (paymentSystem) {
        "Mastercard" -> {
            when (stock) {
                true -> MastercardOrMaestroStock(transferAmount, monthlyAmount)
                else -> MastercardOrMaestro(transferAmount)
            }
        }
        "Maestro" -> {
            when (stock) {
                true -> MastercardOrMaestroStock(transferAmount, monthlyAmount)
                else -> MastercardOrMaestro(transferAmount)
            }
        }
        "Visa" -> visaAndMir(transferAmount)
        "Mir" -> visaAndMir(transferAmount)


        else -> 0.0
    }
    return comission
    }




fun MastercardOrMaestroStock(transferAmount:Double,monthlyAmount: Double): Double {

    if (monthlyAmount in 30000.0..7500000.0) {
        println("Комисиия не взимается")
        return 0.0
    } else {

        return MastercardOrMaestro(transferAmount)
    }
}
fun MastercardOrMaestro(transferAmount: Double): Double {
    return (transferAmount * 0.006 + 2000)
}

fun visaAndMir(transferAmount: Double): Double {
   return when {
        (transferAmount * 0.0075 > 3500) -> transferAmount * 0.0075
        else -> 3500.0
    }
}

fun transfer(transferAmount:Double, monthlyAmount:Double, paymentSystem:String, stock: Boolean, amountPerDay:Double): Double {
    val transfer = when (paymentSystem) {
        "VK Pay" -> if(transferAmount > 1500000) {
            println("Ошибка: Максимальная сумма транзакции 15000 руб.")
            0.0
        } else {
            transferAmount - comission(transferAmount, monthlyAmount, paymentSystem, stock)
        }
        else -> if((transferAmount + amountPerDay> 1500000) || (transferAmount + monthlyAmount > 6000000)){
            println("Ошибка: Максимальная сумма перевода в сутки 15000 руб. За месяц 60000 руб.")
            0.0
        } else { transferAmount - comission(transferAmount, monthlyAmount, paymentSystem, stock)
        }

    }
    return transfer
}