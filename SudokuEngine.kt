import kotlin.random.Random

object SudokuEngine {

    private val x = Playfield()

    fun create(mode: GameMode = GameMode.NORMAL, difficult: GameDifficult = GameDifficult.NORMAL): Playfield{
        val restNumbers = mutableListOf(1,2,3,4,5,6,7,8,9)
        for(i in 0..8){
            val index = Random.nextInt(0, restNumbers.size)
            x.setField(i, restNumbers[index])
            for(ii in 1..8){
                x.setField(ii, i, if(restNumbers[index]+ii <= 9) restNumbers[index]+ii else restNumbers[index]+ii-9)
            }
            restNumbers.removeAt(index)
        }
        for(i in 0..Random.nextInt(10, 20)){
            when(Random.nextInt(0, 5)){
                0 -> x.mirror1()
                1 -> x.mirror2()
                else -> x.exchange(Random.nextInt(0, 10))
            }
        }
        return x
    }
}

enum class GameMode{NORMAL}
enum class GameDifficult{EASY, NORMAL, HARD}