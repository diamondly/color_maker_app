package edu.fullerton.fz.persistencecolor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyViewModel: ViewModel() {

    private val prefs = MyPreferencesRepo.get()

    fun saveInput(value: String, color: String) {
        viewModelScope.launch {
            prefs.saveInput(value, color)
            Log.v(LOG_TAG, "Input saved $color#$value")
        }
    }

    fun loadInputs(act: MainActivity) {
        viewModelScope.launch {
            prefs.redView.collectLatest {
                act.redView.setText(it)
                Log.v(LOG_TAG, "Collected redView")
            }
        }
        viewModelScope.launch {
            prefs.greenView.collectLatest {
                act.greenView.setText(it)
                Log.v(LOG_TAG, "Collected greenView")
            }
        }
        viewModelScope.launch {
            prefs.blueView.collectLatest {
                act.blueView.setText(it)
                Log.v(LOG_TAG, "Collected blueView")
            }
        }
    }
}