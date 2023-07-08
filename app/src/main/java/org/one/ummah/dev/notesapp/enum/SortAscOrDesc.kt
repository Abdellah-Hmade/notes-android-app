package org.one.ummah.dev.notesapp.enum

enum class SortAscOrDesc(val code: Int, val Label: String) {
    ASC(1, "ASC"),
    DESC(0, "DESC");

    companion object {
        val DEFAULT_SORT: SortAscOrDesc = DESC
    }

}