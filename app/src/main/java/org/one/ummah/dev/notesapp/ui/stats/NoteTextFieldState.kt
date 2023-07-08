package org.one.ummah.dev.notesapp.ui.stats

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

data class NoteTextFieldState(
    private val _text: MutableState<String> = mutableStateOf(""),
    val text: State<String> = _text,
    val hint: String = "",
    private val _isFocused: MutableLiveData<Boolean> = MutableLiveData(false),
    val isFocused: LiveData<Boolean> = _isFocused,
    var isHintVisible: Boolean = true
) {
    init {
        setHintToTextValue()
        isFocused.observeForever {
            if (it) {
                if (text.value == hint) {
                    _text.value = ""
                    isHintVisible = false
                }
            } else {
                if (text.value.isBlank()) {
                    _text.value = hint
                    isHintVisible = true
                }
            }
        }

    }

    fun setTextValue(value: String?) {
        value?.let {
            _text.value = value
        } ?: run {
            if (isFocused.value == true)
                _text.value = ""
            else if (isFocused.value == false)
                _text.value = hint
        }
    }

    fun setIsFocused(value: Boolean?) {
        value?.let {
            _isFocused.value = value
        }
    }

    fun setHintToTextValue() {
        _text.value = hint
        isHintVisible = true
    }
}
