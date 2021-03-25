class Dice(val id : Int) {
    var isLocked: Boolean = false
    var value = 0

    companion object{
        var diceLockedCounter = 0
        private fun countLockedDices(){
            diceLockedCounter++
        }
    }

    fun roll(){
        if(!this.isLocked){
            val randomNumber = (1..6).random()
            this.value = randomNumber
        }
    }
    fun lockDice(){
        if(!this.isLocked) {
            this.isLocked = true
            countLockedDices()
        }
    }

}