package chat.willow.kale.helper

import org.kodein.mock.Mock
import org.kodein.mock.tests.TestsWithMocks
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

class CaseInsensitiveNamedMapTests : TestsWithMocks() {
    override fun setUpMocks() = injectMocks(mocker)

    @Mock
    lateinit var mockMapper: ICaseMapper

    private val sut: CaseInsensitiveNamedMap<MockNamedThing> by withMocks {
        CaseInsensitiveNamedMap(mockMapper)
    }

    @BeforeTest
    fun setUp() {
        every { mockMapper.toLower(isAny()) } returns ""
    }

    @Test
    fun `get uses mapper`() {
        sut["something"]

        verify { mockMapper.toLower("something") }
    }

    @Test
    fun `put uses mapper`() {
        sut += MockNamedThing(name = "test")

        verify { mockMapper.toLower("test") }
    }

    @Test
    fun `getting named thing with different cases results in correct item`() {
        val namedThing = MockNamedThing()

        sut += namedThing
        val result = sut["a test name"]

        assertSame(result, namedThing)
    }

    @Test
    fun `removing named thing with different cases results in correct removal`() {
        val namedThing = MockNamedThing(name = "test name")
        sut += namedThing

        sut -= "TEST NAME"
        val result = sut["test name"]

        assertNull(result)
    }

    @Test
    fun `clear removes all named things`() {
        val namedThingOne = MockNamedThing(name = "test name 2")
        val namedThingTwo = MockNamedThing(name = "test name 1")
        sut += listOf(namedThingOne, namedThingTwo)

        sut.clear()
        val resultOne = sut["test name 1"]
        val resultTwo = sut["test name 2"]

        assertNull(resultOne)
        assertNull(resultTwo)
    }

    @Test
    fun `contains returns true for different cased things`() {
        val namedThing = MockNamedThing(name = "test name")
        sut += namedThing

        val result = sut.contains("TEST NAME")

        assertTrue(result)
    }

}

private class MockNamedThing(override val name: String = "test") : INamed