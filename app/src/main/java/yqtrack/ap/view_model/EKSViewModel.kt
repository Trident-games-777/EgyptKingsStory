package yqtrack.ap.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import yqtrack.ap.complex.ComplexManager
import yqtrack.ap.file.FileManager
import yqtrack.ap.utils.helpers.makeLink
import yqtrack.ap.utils.helpers.sendOneSignalTag
import javax.inject.Inject

@HiltViewModel
class EKSViewModel @Inject constructor(
    private val fileManager: FileManager
) : ViewModel() {
    private val _link: MutableLiveData<String> = MutableLiveData()
    val link: LiveData<String> = _link

    fun takeLink(complexManager: ComplexManager) {
        if (fileManager.takeFileData() != null) {
            _link.postValue(fileManager.takeFileData())
        } else {
            viewModelScope.launch {
                val complexData = complexManager.getComplexData()
                _link.postValue(makeLink(complexData))
                sendOneSignalTag(complexData)
            }
        }
    }
}