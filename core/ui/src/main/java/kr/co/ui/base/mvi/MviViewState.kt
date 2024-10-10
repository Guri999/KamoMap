package kr.co.ui.base.mvi

import android.os.Bundle

interface MviViewState

interface MviViewStateSaver<S: MviViewState> {
    fun S.toBundle(): Bundle

    fun restore(bundle: Bundle?): S
}