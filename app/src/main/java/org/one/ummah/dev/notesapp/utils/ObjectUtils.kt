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

        inline fun colorsStringToBase(color: Long): String {
            var colorStr = color.toString()
            colorStr = colorStr.replace("0x", "#")
            return colorStr
        }

        inline fun colorsBaseToFront(str: String?): Long {
            str?.let {

                if (it.isNotBlank()) {
                    return it.replace("0x", "#").substring(1).toLong(16)
                }

            }
            return 0xFF1A5F7A
        }
    }
}