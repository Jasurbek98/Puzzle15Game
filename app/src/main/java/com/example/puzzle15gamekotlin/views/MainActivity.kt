package com.example.puzzle15gamekotlin.views

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.puzzle15gamekotlin.R
import com.example.puzzle15gamekotlin.SharedPreference.AppSharedPreference
import com.example.puzzle15gamekotlin.contracts.PuzzleContract
import com.example.puzzle15gamekotlin.models.Coordinate
import com.example.puzzle15gamekotlin.models.PuzzleRepository
import com.example.puzzle15gamekotlin.presenters.PuzzlePresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PuzzleContract.View {
    private lateinit var buttons: Array<Array<Button>>
    private var space = Coordinate(3, 3)
    private lateinit var listButton: Array<String>


    private lateinit var model: PuzzleContract.Model
    private lateinit var presenter: PuzzleContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Puzzle 15"
        presenter = PuzzlePresenter(this, PuzzleRepository())
        loadViews()
        presenter.startGame()
    }

    override fun onResume() {
        super.onResume()
        textTimer.start()
    }

    override fun onPause() {
        super.onPause()
        textTimer.stop()
    }

    override fun onStop() {
        super.onStop()
        presenter.saveData()
        textTimer.stop()
    }

    private fun loadViews() {
        listButton = Array(16) { "" }
        buttons = Array(4) { Array(4) { Button(this) } }
        buttonFinish.setOnClickListener { presenter.finish() }
        buttonRestart.setOnClickListener { presenter.restart() }
        continueButton.setOnClickListener { presenter.restart() }
        cancelButton.setOnClickListener { presenter.onClickNoConfirm() }
        for (i in 0 until group.childCount) {
            buttons[i / 4][i % 4] = group.getChildAt(i) as Button
            val button = buttons[i / 4][i % 4]
            if (button != null) {
                button.tag = Coordinate(i / 4, i % 4)
                button.setOnClickListener { v -> presenter.click(v.tag as Coordinate) }
            }


        }
    }


    override fun finishGame() {
        finish()
    }

    override fun loadData(lists: List<String>) {
        for (i in lists.indices) {
            buttons[i / 4][i % 4]!!.text = lists[i].toString()
        }
    }

    override fun setElementText(coordinate: Coordinate, s: String) {
        buttons[coordinate.x][coordinate.y]!!.text = s
    }

    override fun getElementText(coordinate: Coordinate): String {
        return buttons[coordinate.x][coordinate.y]!!.text.toString()

    }

    override fun setScore(score: Int) {
        textScore.text = score.toString()
    }

    override fun showWin() {
        val intent = Intent(this@MainActivity, RecordActivity::class.java)
        var a = Integer.parseInt(textScore.text.toString())
        intent.putExtra("SCORE", a)
        intent.putExtra("TIME", textTimer.text)
        startActivityForResult(intent, 1)
        presenter.restart()
    }

    override fun startTimer(base: Long) {
        if(base == 0L){
            textTimer.stop()
            textTimer.base = SystemClock.elapsedRealtime()
            textTimer.start()
        }else{
            textTimer.base = base
            textTimer.start()
        }

    }

    override fun setSpace(coordinate: Coordinate) {
        space = coordinate
    }

    override fun getSpace(): Coordinate {
        return space
    }

    override fun setBackground(coordinate: Coordinate?) {
        buttons[coordinate!!.x][coordinate.y]!!.visibility = View.VISIBLE
    }

    override fun clearBackground(coordinate: Coordinate) {
        buttons[coordinate.x][coordinate.y]!!.visibility = View.INVISIBLE
    }

    override fun showConfirmDialog() {
        confirm_layout.visibility = View.VISIBLE
    }

    override fun hideConfirmDialog() {
        confirm_layout.visibility = View.INVISIBLE
    }

    override fun getBaseTime() = SystemClock.elapsedRealtime()


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1) {
            finish()
        }
        if (resultCode == 2) {
            presenter = PuzzlePresenter(this, PuzzleRepository())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        for (i in 0 until 16) {
            listButton[i] = buttons[i / 4][i % 4].text.toString()
        }
        outState.putStringArray("BUTTONS", listButton)
        outState.putLong("TIME", textTimer.base)
        outState.putString("SCORE", textScore.text as String)
        outState.putInt("SPACEX", space.x)
        outState.putInt("SPACEY", space.y)
        outState.putInt("STEP", presenter.step)


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        space = buttons[savedInstanceState.getInt("SPACEX")][savedInstanceState.getInt("SPACEY")]!!.tag as Coordinate
        buttons[3][3].visibility = View.VISIBLE
        buttons[space.x][space.y].visibility = View.INVISIBLE
        buttons[space.x][space.y].text = ""

        presenter.restart()
    }


}

