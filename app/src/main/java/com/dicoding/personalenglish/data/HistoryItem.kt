package com.dicoding.personalenglish.data

abstract class HistoryItem {

    companion object {
        val VIEWTYPE_DATE: Int = 0
        val VIEWTYPE_WORD: Int = 1
    }

    abstract fun getType(): Int
}

class HistoryWord(
    var word: String
): HistoryItem() {

    override fun getType(): Int {
        return VIEWTYPE_WORD
    }
}

class HistoryDate (
    var date: String
): HistoryItem() {

    override fun getType(): Int {
        return VIEWTYPE_DATE
    }
}