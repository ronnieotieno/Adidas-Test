package dev.ronnie.adidasandroid

import dev.ronnie.adidasandroid.data.entities.Product
import org.junit.Test

import org.junit.Assert.*


class FilterTest {
    @Test
    fun check_filter_isCorrect() {

        val product1 = Product("", "Perfect Shoe", "", "", "New Shoe", 0, emptyList())
        val product2 = Product("", "Old one", "", "", "I don't like this shoe", 0, emptyList())

        val prodList = listOf(product1, product2)
        val query1 = "Shoe"
        val query2 = "Old"
        val query3 = "Perfect"

        val queriedList1 = prodList.filter {
            it.name.contains(
                query1,
                ignoreCase = true
            ) || it.description.contains(query1, ignoreCase = true)
        }

        val queriedList2 = prodList.filter {
            it.name.contains(
                query2,
                ignoreCase = true
            ) || it.description.contains(query2, ignoreCase = true)
        }

        val queriedList3 = prodList.filter {
            it.name.contains(
                query3,
                ignoreCase = true
            ) || it.description.contains(query3, ignoreCase = true)
        }

        assertEquals(queriedList1.size, 2)
        assertEquals(queriedList2.size, 1)
        assertEquals(queriedList3.size, 1)
    }
}