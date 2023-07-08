package org.one.ummah.dev.notesapp.exception

class OperationTruncatedException(val causedBy:String?):CustomException("operation failed",
    "operation failed because of "+(causedBy ?: "internal error")
) {
}