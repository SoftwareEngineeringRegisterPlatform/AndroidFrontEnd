package cn.hospital.registerplatform

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.dto.DoctorInfo
import cn.hospital.registerplatform.data.repository.HospitalRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.junit.*
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
class HiltUnitTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var userPreference: UserPreference

    @Inject
    lateinit var hospitalRepository: HospitalRepository

    @Before
    fun inject() {
        hiltRule.inject()
    }

    @After
    fun cleanUp() {

    }

    @Test
    fun userPreferenceTest() {
        Assert.assertEquals("杭州", userPreference.getCity())
    }

    @Test
    fun hospitalRepositoryTest() = runBlockingTest {
        hospitalRepository.getDoctorInfo(1).collect {
            it.doSuccess { info ->
                Assert.assertThat(info, object : BaseMatcher<DoctorInfo>() {
                    override fun describeTo(description: Description?) {
                    }

                    override fun matches(item: Any?): Boolean {
                        return item is DoctorInfo
                    }
                })
            }
        }
    }

}

