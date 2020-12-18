package com.example.tddconcepts.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.tddconcepts.R
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ResourceComparerTest {

    private lateinit var resourceComparer : ResourceComparer

    @Before
    fun setUp(){
        resourceComparer = ResourceComparer()
    }

    @Test
    fun stringResourceSameAsGivenString_returnsTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result  = resourceComparer.isEqual(context, R.string.app_name,"TDD Concepts")
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceDifferentGivenString_returnsTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result  = resourceComparer.isEqual(context, R.string.app_name,"different")
        assertThat(result).isFalse()
    }
}