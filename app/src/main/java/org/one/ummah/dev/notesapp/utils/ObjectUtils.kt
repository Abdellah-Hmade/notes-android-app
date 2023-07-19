package org.one.ummah.dev.notesapp.utils

class ObjectUtils {
    companion object {

        inline fun <reified T> getListOfNullables(obj: T): List<String> {
            val arrayOfNullables = ArrayList<String>()
            T::class.members.forEach { callable ->
                if (callable.call(obj) == null) {
                    arrayOfNullables.add(callable.name)
                }
            }
            return arrayOfNullables
        }

        fun colorsStringToBase(color: Long): String {
            var colorStr = color.toString()
            if (colorStr.contains("0x"))
                colorStr = colorStr.replace("0x", "#")
            else {
                colorStr = StringBuilder()
                    .append("#")
                    .append(colorStr).toString()
            }
            return colorStr
        }

        fun colorStringToLong(color: String?): Long {
            color?.let {
                if (color.isNotBlank()) {
                    val colorStr = color.replace("#", "")
                    if (isNumeric(colorStr))
                        return colorStr.toLong(16)
                }
            }
            return -1
        }

        fun isNumeric(str: String): Boolean {
            return str.matches("[0-9A-Fa-f]+".toRegex())
        }

        fun colorsBaseToFront(str: String?): Long {
            str?.let {

                if (it.isNotBlank()) {
                    return it.replace("0x", "#").substring(1).toLong(16)
                }

            }
            return 0xFF1A5F7A
        }
    }
}