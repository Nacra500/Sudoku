import kotlin.random.Random

class Playfield(var values: Array<Array<Int>> = Array(9) { Array (9) { 0 } } , val displayed: Array<Int> = emptyArray()){

    fun mirror1(){
        values.reverse()
    }

    fun mirror2(){
        for(i in values.indices){
            values[i].reverse()
        }
    }

    fun exchange(row: Int){
        when(row){
            0 -> changeRow(0, 1)
            1 -> changeRow(1, 2)
            2 -> changeRow(2, 0)
            3 -> changeRow(3, 4)
            4 -> changeRow(4, 5)
            5 -> changeRow(5, 3)
            6 -> changeRow(6, 7)
            7 -> changeRow(7, 8)
            else -> changeRow(8, 6)
        }
    }

    fun changeRow(x: Int, y: Int){
        val temp = values[x]
        values[x] = values[y]
        values[y] = temp
    }

    fun getRow(x: Int) = values[x]
    fun getColumn(x: Int): Array<Int>{
        val res: Array<Int> = emptyArray()
        for(i in values){
            res.plus(i[x])
        }
        return res
    }
    fun getBox(x: Int): Array<Int>{
        val res: Array<Int> = emptyArray()
        for(y in (x/3)*3-3..(x/3)*3){
            for(z in (x/3)*3-3..(x/3)*3){
                res.plus(values[y][z])
            }
        }
        return res
    }

    fun getList(): Array<Int>{
        val res: Array<Int> = emptyArray()
        for(i in values){
            for(ii in i){
                res.plus(i)
            }
        }
        return res
    }

    fun setField(x: Int, y: Int, z: Int){
        values[x][y] = z
    }
    fun setField(x: Int, z: Int){
        values[x/9][x%9] = z
    }

    override fun toString(): String{
        var res = "Game:\n"
        var b = 0
        for(x in values){
            var a = 0
            for(y in x){
                a++
                if(Random.nextInt(0, 10) < 2) res += y else res += '*'
                if(a%3 == 0) res += "|"
            }
            b++
            res += "\n"
            if(b%3 == 0) res += "------------\n"
        }
        res += "\n"
        res += "\n"
        res += "Solution:\n"
        b = 0
        for(x in values){
            var a = 0
            for(y in x){
                a++
                res += y
                if(a%3 == 0) res += "|"
            }
            b++
            res += "\n"
            if(b%3 == 0) res += "------------\n"
        }
        return res
    }
}