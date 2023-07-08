package org.one.ummah.dev.notesapp.enum

enum class SortByColumn(val code:Int,val label:String) {
    TITLE(0,"title"),
    DATE(1,"date"),
    COLOR(2,"color");

    companion object{
        val DEFAULT_SORT :SortByColumn = DATE;
    }

}