package com.example.puzzle15gamekotlin.SharedPreference

import android.content.Context


class AppSharedPreference private constructor(context: Context) {
    companion object {
        lateinit var instance: AppSharedPreference; private set

        fun init(context: Context) {
            instance = AppSharedPreference(context)
        }
    }

    val pref = context.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)

    var isSaved: Boolean
        set(value) = pref.edit().putBoolean("IS_SAVED", value).apply()
        get() = pref.getBoolean("IS_SAVED", false)

    var beginTime: Long
        set(value) = pref.edit().putLong("BEGIN_TIME", value).apply()
        get() = pref.getLong("BEGIN_TIME", 0)

    var endTime: Long
        set(value) = pref.edit().putLong("END_TIME", value).apply()
        get() = pref.getLong("END_TIME", 0)

    var gameScore: Int
        set(value) = pref.edit().putInt("SCORE", value).apply()
        get() = pref.getInt("SCORE", 0)

    var coordinateX: Int
        set(value) = pref.edit().putInt("COORDINATE_X", value).apply()
        get() = pref.getInt("COORDINATE_X", 3)

    var coordinateY: Int
        set(value) = pref.edit().putInt("COORDINATE_Y", value).apply()
        get() = pref.getInt("COORDINATE_Y", 3)

    var buttonPos: ArrayList<String>
        set(value) {
            val e = pref.edit()
            e.putInt("SIZE_OF_BUTTONS", value.size)
            value.forEachIndexed { index, i ->
                e.putString("BUTTON_$index", i)
            }
            e.apply()
        }
        get() {
            val ls = ArrayList<String>()
            val size = pref.getInt("SIZE_OF_BUTTONS", 0)
            for (index in 0 until size) {
                val t = pref.getString("BUTTON_$index", "")
                ls.add(t.toString())
            }
            return ls
        }
}

