package com.earthquake.tracker.quaker

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.accessibility.AccessibilityChecks
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.earthquake.tracker.quaker.mvp.view.EarthquakeListActivity
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyQuakerViewTest {

    @Rule
    @JvmField
    var mActivityTestRule: ActivityTestRule<EarthquakeListActivity> = ActivityTestRule(EarthquakeListActivity::class.java)

    @Test
    fun activityLaunches() {
        onView(withText(R.string.text_for_radio_button)).check(matches(isDisplayed()))
    }

    @Test
    fun verifyRadioButtonsAppear() {
        onView(withId(R.id.radioGroup)).check(matches(isDisplayed()))
        //TODO assert the count of radioButtons
    }

    @Test
    fun validateGreaterThan1() {
        onView(withId(R.id.oneAndAbove)).check(matches(isDisplayed()))
        onView(withId(R.id.oneAndAbove)).perform(click())
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
        onView(withId(R.id.quakerRV)).check(matches(isDisplayed()))
    }
//
//
//    @Test
//    fun validateSignificant() {
//
//    }
//
//    @Test
//    fun validateAll() {
//
//    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun enableAccessibilityChecks() {
            AccessibilityChecks.enable().setRunChecksFromRootView(true)
        }
    }
}