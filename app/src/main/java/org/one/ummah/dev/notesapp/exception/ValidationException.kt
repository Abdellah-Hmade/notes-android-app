package org.one.ummah.dev.notesapp.exception


class ValidationException(
    var fields:List<String>?
) : CustomException("validation error", "a field isn't valid") {

    companion object{
        val PREFIX_MESSAGE = "not valid"
        val SUFFIX_MESSAGE = "field"
        val SUFFIX_MESSAGE_PLURAL = "s"
    }

    init{
        var newMessage = PREFIX_MESSAGE
        fields?.let { listFields ->
            listFields.forEachIndexed { index, field ->
                if(index != listFields.lastIndex){
                    newMessage = newMessage.plus(" $field,")
                }
                else{
                    newMessage = newMessage.plus(" $field")
                }

            }
            newMessage = newMessage.plus(" ").plus(SUFFIX_MESSAGE)
            if(listFields.count() > 1){
                newMessage.plus(SUFFIX_MESSAGE_PLURAL)
            }
        } ?: kotlin.run {
            newMessage = SUFFIX_MESSAGE.plus(SUFFIX_MESSAGE_PLURAL)
        }
        message = newMessage
    }
}