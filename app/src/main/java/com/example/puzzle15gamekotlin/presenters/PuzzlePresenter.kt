package com.example.puzzle15gamekotlin.presenters

import android.util.Log
import com.example.puzzle15gamekotlin.SharedPreference.AppSharedPreference
import com.example.puzzle15gamekotlin.contracts.PuzzleContract
import com.example.puzzle15gamekotlin.models.Coordinate
import java.lang.Math.abs
import kotlin.math.abs as abs1

@Suppress("UNCHECKED_CAST")
open class PuzzlePresenter(
    private val view: PuzzleContract.View,
    private val model: PuzzleContract.Model
) : PuzzleContract.Presenter {
    override var step = 0

    override fun finish() {
        view.finishGame()
    }

    override fun restart() {
        view.hideConfirmDialog()
        if (AppSharedPreference.instance.isSaved) {
            view.setScore(AppSharedPreference.instance.gameScore)
            step = AppSharedPreference.instance.gameScore

            val setTime =
                view.getBaseTime() - (AppSharedPreference.instance.endTime - AppSharedPreference.instance.beginTime)
            view.startTimer(setTime)
            AppSharedPreference.instance.beginTime =
                view.getBaseTime() - (AppSharedPreference.instance.endTime - AppSharedPreference.instance.beginTime)
            view.setSpace(
                Coordinate(
                    AppSharedPreference.instance.coordinateX,
                    AppSharedPreference.instance.coordinateY
                )
            )
            view.setBackground(Coordinate(3,3))
            view.clearBackground(view.getSpace())
            view.loadData(AppSharedPreference.instance.buttonPos)
        } else {
            step = 0
            view.setScore(step)
            view.setBackground(view.getSpace())
            view.setSpace(Coordinate(3, 3))
            view.clearBackground(view.getSpace())
            view.setElementText(view.getSpace(), "")
            view.loadData(getNumberToString())
            view.startTimer()
            AppSharedPreference.instance.beginTime = view.getBaseTime()

        }
        AppSharedPreference.instance.isSaved = false
    }

    private fun getNumberToString(): ArrayList<String> {
        val ls = ArrayList<String>()
        for (i in model.getNumbers()) {
            ls.add(i.toString())
        }
        return ls
    }

    override fun click(coordinate: Coordinate) {
        val dx: Int = kotlin.math.abs(view.getSpace().x - coordinate.x)
        val dy: Int = kotlin.math.abs(view.getSpace().y - coordinate.y)
        if (dx + dy == 1) {
            step++
            view.setScore(step)
            val t1 = view.getElementText(coordinate)
            view.setElementText(view.getSpace(), t1)
            view.setElementText(coordinate, "")
            view.setBackground(view.getSpace())
            view.setSpace(coordinate)
            view.clearBackground(view.getSpace())
            if (isWin) {
                view.showWin()
                restart()
            }
        }
    }

    override fun saveData() {
        AppSharedPreference.instance.isSaved = true
        AppSharedPreference.instance.gameScore = step
        AppSharedPreference.instance.coordinateX = view.getSpace().x
        AppSharedPreference.instance.coordinateY = view.getSpace().y
        AppSharedPreference.instance.endTime = view.getBaseTime()
        AppSharedPreference.instance.buttonPos = getButtonPos()
    }

    private fun getButtonPos(): ArrayList<String> {
        val ls = ArrayList<String>()
        for (i in 0..15) {
            ls.add(view.getElementText(Coordinate(i / 4, i % 4)))
        }
        return ls
    }


    override fun startGame() {
        Log.d("TTT", "${AppSharedPreference.instance.isSaved}")
        if (AppSharedPreference.instance.isSaved) {
            view.showConfirmDialog()
        } else {
            restart()
        }
    }

    override fun onClickNoConfirm() {
        AppSharedPreference.instance.isSaved = false
        restart()
    }


    private val isWin: Boolean
        private get() {
            if (view.getSpace().y != 3 || view.getSpace().x != 3) return false
            for (i in 0..14) {
                val text = view.getElementText(Coordinate(i / 4, i % 4))
                if (text != (i + 1).toString()) {
                    return false
                }
            }
            return true
        }

}