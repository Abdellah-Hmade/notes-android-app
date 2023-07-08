package org.one.ummah.dev.notesapp.dtos

class NotesDto(
    var id:Int?=null,
    var title:String?=null,
    var content:String?=null,
    var color:String?=null,
    var date:Long?=System.currentTimeMillis()
)