package org.one.ummah.dev.notesapp.exception

abstract class CustomException(
    var title:String?,
    override var message:String?
):Exception()