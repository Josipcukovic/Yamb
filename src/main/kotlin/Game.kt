class Game{
    private val dices = mutableListOf<Dice>()
    private var throwCounter = 1

    fun initializeGame(){
        dices.add(Dice(1))
        dices.add(Dice(2))
        dices.add(Dice(3))
        dices.add(Dice(4))
        dices.add(Dice(5))
        dices.add(Dice(6))
        println("Game is starting")
    }

    private fun rollDices() = dices.forEach{it.roll()}

    fun startGame(){
        println("...throwing dices")
        println("--------------------------------------------")
        rollDices()
        println("do you want to lock some of the dices?(write character 'y' if you want to lock/ 'n' if you don't want to lock dices)")
        printUnlockedDices()

        while(throwCounter <=3) {
            lockDices()
            if (throwCounter < 3) {
                println("-------------------------------------------- \n" + "Next throw" + "\n--------------------------------------------")
                rollDices()
                println("do you want to lock some of the dices?(write character 'y' if you want to lock/ 'n' if you don't want to lock dices)")
                printUnlockedDices()
                throwCounter++
            } else if (throwCounter == 3) {
                println("you had 3 throws, no throws left")
                printAllDiceValues()
                throwCounter++
            }
        }
    }

    private fun lockDices(){
        if (readLine()?.toLowerCase() == "y") {
            println("to choose a dice write its ID (example : 1,2,3 for first three dices)")
            pickDicesToLock()
            isThrowPossible()
            println("Locked dices:")
            printLockedDices()
        }
    }

    private fun isThrowPossible(){
        if(Dice.diceLockedCounter == 6){
            throwCounter = 4
            println("All dices are locked, no more throws")
        }
    }

    private fun pickDicesToLock(){
        val wantedDices: String = readLine() ?: ""
        val diceIDs = Regex("[1-6]").findAll(wantedDices,0)
        diceIDs.forEach { diceId -> dices.forEach { if (it.id == diceId.value.toInt()) it.lockDice() }}
    }

    private fun printUnlockedDices() = dices.forEach{ if(!it.isLocked){println("Value of dice is: ${it.value}, ID is ${it.id}")} }

    private fun printLockedDices() = dices.forEach { if(it.isLocked){println("dice value: ${it.value}, dice ID: ${it.id}, ")} }

    private fun printAllDiceValues() = dices.forEach{ println("Realised values: ${it.value}")}

    fun checkForCombinations(){
        val diceValues = mutableListOf<Int>()
        dices.forEach{diceValues.add(it.value)}
        diceValues.sortByDescending { it }
        val distinctValues = diceValues.distinct()
        checkForScale(distinctValues)
        checkForJamb(diceValues)
        checkForPoker(diceValues)
    }

    private fun getCountOfSix(values : List<Int>) : Int = values.filter { it == 6 }.count()
    private fun getCountOfFive(values : List<Int>) : Int = values.filter { it == 5 }.count()
    private fun getCountOfFour(values : List<Int>) : Int = values.filter { it == 4 }.count()
    private fun getCountOfThree(values : List<Int>) : Int = values.filter { it == 3 }.count()
    private fun getCountOfTwo(values : List<Int>) : Int = values.filter { it == 2 }.count()
    private fun getCountOfOne(values : List<Int>) : Int = values.filter { it == 1 }.count()


    private fun checkForJamb(values : List<Int>){
        if(getCountOfSix(values) >= 5
            || getCountOfFive(values) >= 5
            || getCountOfFour(values) >= 5
            || getCountOfThree(values) >= 5
            || getCountOfTwo(values) >= 5
            || getCountOfOne(values) >= 5) println("you got Yamb!!!")
    }

    private  fun checkForPoker(values : List<Int>){
        if(getCountOfSix(values) == 4
            || getCountOfFive(values) == 4
            || getCountOfFour(values) == 4
            || getCountOfThree(values) == 4
            || getCountOfTwo(values) == 4
            || getCountOfOne(values) == 4) println("You got Poker!!!")
    }

    private fun checkForScale(values : List<Int>){
        var highScale = 6
        var lowScale = 5
        var highScaleCounter = 0
        var lowScaleCounter = 0

        for(i in 0 until values.size){
            if(values.elementAt(i) == highScale){
                highScaleCounter++
                highScale--
            }else if(values.elementAt(i) == lowScale){
                lowScaleCounter++
                lowScale--
            }
            if(highScaleCounter == 5) println("you got Large Straight!!!")
            else if(lowScaleCounter == 5) println("you got Small Straight!!!")
        }
    }
}