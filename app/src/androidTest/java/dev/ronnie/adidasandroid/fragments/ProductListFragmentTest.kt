package dev.ronnie.adidasandroid.fragments

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import dev.ronnie.adidasandroid.MainActivity
import dev.ronnie.adidasandroid.R
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class ProductListFragmentTest {

    @Rule
    @JvmField
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun recyclerview_is_showing() {
        Espresso.onView(withId(R.id.list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun searchView_is_showing() {
        Espresso.onView(withId(R.id.searchView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun toolbar_is_showing(): Unit = runBlocking {
        Espresso.onView(withId(R.id.toolbar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
