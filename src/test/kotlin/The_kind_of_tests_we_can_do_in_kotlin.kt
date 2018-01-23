import junit.framework.Assert.assertTrue
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

public class The_kind_of_tests_we_can_do_in_kotlin {

    @Test
    fun `should do some basic asserts`() {
        assertThat("plip").isEqualToIgnoringCase("PLIP")

        val theList = listOf("a", "b", "c", "d")
        assertThat(theList).contains("b", "c")
    }

    @Test
    fun `should do things on object`() {
        val someInstanceOfAnObjectWithDefaultValues = AnObject()
        val someInstanceOfAnObject = AnObject("plop", 2)

        // initialized wth default values
        assertThat(someInstanceOfAnObjectWithDefaultValues.firstParameter).isEqualTo("some default value")
        assertThat(someInstanceOfAnObjectWithDefaultValues.secondParameter).isNull()
        assertThat(someInstanceOfAnObject).isNotEqualTo(someInstanceOfAnObjectWithDefaultValues)

        // parameters could be named, use a default value, order can change ...
        val anotherInstanceOfAnObject = AnObject(secondParameter = 2, firstParameter = "plop")
        // equal, hashcode and toString methods are generated in data class
        assertThat(someInstanceOfAnObject).isEqualTo(anotherInstanceOfAnObject)
        assertThat(someInstanceOfAnObject.toString()).isEqualTo("AnObject(firstParameter=plop, secondParameter=2)")

        //someInstanceOfAnObject.firstParameter = "lala"        // can not compile : val cannot be change
        someInstanceOfAnObject.secondParameter = 3          // can be change
    }

    @Test
    fun `should do things with lists`() {
        val sum = listOf(1, 2, 3, 4, 5, 6).sum()
        assertThat(sum).isEqualTo(21)

        val listOf = listOf("aa", "bb", "cc", "dd")

        val listLala = listOf.map { s -> s + "lala" }
        assertThat(listLala).containsExactly("aalala", "bblala", "cclala", "ddlala")
        // use a method reference
        val listUpper = listOf.map(String::toUpperCase)
        assertThat(listUpper).containsExactly("AA", "BB", "CC", "DD")
        assertTrue(listUpper.contains("BB"))

        val joinToString = "abcdefg".split("").map(String::toUpperCase).reversed().joinToString("")
        assertThat(joinToString).isEqualTo("GFEDCBA")
    }


    @Test
    fun `should do things with lists of objects`() {
        val object1 = AnObject(secondParameter = 1)
        val object2 = AnObject(secondParameter = 2)
        val object3 = AnObject(secondParameter = 3)
        val object4 = AnObject(secondParameter = 4)
        val object5 = AnObject(secondParameter = 5)
        val listOfObjects = listOf(object1, object2, object3, object4, object5)

        // !! is a null check
        val filteredList = listOfObjects.filter { it.secondParameter!! % 2 == 0 }
        assertThat(filteredList).containsOnly(object2, object4)
    }

}

data class AnObject(val firstParameter: String = "some default value", var secondParameter: Int? = null)
