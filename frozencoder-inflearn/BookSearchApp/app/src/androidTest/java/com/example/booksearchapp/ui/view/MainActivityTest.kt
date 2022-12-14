package com.example.booksearchapp.ui.view

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.booksearchapp.R
import com.example.booksearchapp.ui.adapter.BookSearchViewHolder
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest

// @RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {

    /*private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }*/

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    @SmallTest
    fun test_activity_state() {
        // val activityState = activityScenario.state.name
        val activityState = activityScenarioRule.scenario.state.name
        assertThat(activityState).isEqualTo("RESUMED")
    }

    @Test
    @LargeTest
    fun from_SearchFragment_to_FavoriteFragmnet_Ui_Operation() {

        // 1. SearchFragment
        // 1-1) ?????????????????? ?????? `"No Result"`??? ??????????????? ??????
        onView(withId(R.id.tv_emptylist))
            .check(matches(withText("No result")))

        // 1-2) ???????????? `"android"`??? ??????
        onView(withId(R.id.et_search))
            .perform(typeText("android"))
        onView(isRoot()).perform(waitFor(3000))

        // 1-3) ?????????????????? ????????? ??????
        onView(withId(R.id.rv_search_result))
            .check(matches(isDisplayed()))

        // 1-4) ????????? ???????????? ??????
        onView(withId(R.id.rv_search_result))
            .perform(actionOnItemAtPosition<BookSearchViewHolder>(0, click()))
        onView(isRoot()).perform(waitFor(1000))

        // 1-5) BookFragment ????????? ??????
        onView(withId(R.id.fab_favorite))
            .perform(click())

        // 1-6) ?????? ???????????? ?????????
        pressBack()

        // 1-7) SnackBar??? ????????? ????????? ??????
        onView(isRoot()).perform(waitFor(3000))
        onView(withId(R.id.rv_search_result))
            .check(matches(isDisplayed()))

        // 2. FavoriteFragment
        // 2-1) FavoriteFragment??? ??????
        onView(withId(R.id.fragment_favorite))
            .perform(click())

        // 2-2) ?????????????????? ????????? ??????
        onView(withId(R.id.rv_favorite_books))
            .check(matches(isDisplayed()))

        // 2-3) ????????? ???????????? ?????????????????? ??????
        onView(withId(R.id.rv_favorite_books)).perform(
            actionOnItemAtPosition<BookSearchViewHolder>(0, swipeLeft())
        )
    }

    private fun waitFor(delay: Long) : ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}