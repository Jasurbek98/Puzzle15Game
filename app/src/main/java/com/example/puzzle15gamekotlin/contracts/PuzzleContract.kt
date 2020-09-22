package com.example.puzzle15gamekotlin.contracts

import com.example.puzzle15gamekotlin.models.Coordinate


interface PuzzleContract {

    interface Model {
        fun getNumbers(): List<Int?>


    }

    interface View {
        fun finishGame()
        fun loadData(lists: List<String>)
        fun setElementText(coordinate: Coordinate, s: String)
        fun getElementText(coordinate: Coordinate): String
        fun setScore(score: Int)
        fun showWin()
        fun startTimer(base:Long = 0)
        fun setSpace(coordinate: Coordinate)
        fun getSpace(): Coordinate
        fun setBackground(coordinate: Coordinate?)
        fun clearBackground(coordinate: Coordinate)
        fun showConfirmDialog()
        fun hideConfirmDialog()
        fun getBaseTime():Long

    }

    interface Presenter {
        fun finish()
        fun restart()
        fun click(coordinate: Coordinate)
        fun saveData()
        fun startGame()
        fun onClickNoConfirm()
        var step:Int
    }


}