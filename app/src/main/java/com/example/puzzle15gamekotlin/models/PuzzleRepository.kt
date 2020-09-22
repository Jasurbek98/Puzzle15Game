package com.example.puzzle15gamekotlin.models

import com.example.puzzle15gamekotlin.contracts.PuzzleContract
import kotlin.collections.ArrayList

open class PuzzleRepository : PuzzleContract.Model {
    private val list = ArrayList<Int>()

    init {
        for (i in 1..15) {
            list.add(i)
        }
    }


    override fun getNumbers(): ArrayList<Int> {

       // list.shuffle()
        return list

    }


}