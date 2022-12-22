package com.example.booksearchapp.ui.view

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

/*@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @Test
    @SmallTest
    fun test_activity_state() {
        val controller = Robolectric.setupActivity(MainActivity::class.java) // 빌드 안됨. maxSDK 32까지만 됨.
        val activityState = controller.lifecycle.currentState.name
        assertThat(activityState).isEqualTo("RESUMED")
    }
}*/

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }


    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    @SmallTest
    fun test_activity_state() {
        // val activityState = activityScenario.state.name
        val activityState = activityScenarioRule.scenario.state.name
        assertThat(activityState).isEqualTo("RESUMED")
    }
}
