package cn.hospital.registerplatform

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import cn.hospital.registerplatform.api.doSuccess
import cn.hospital.registerplatform.data.dto.DoctorInfo
import cn.hospital.registerplatform.data.repository.HospitalRepository
import cn.hospital.registerplatform.ui.component.hospital.HospitalViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.junit.*
import javax.inject.Inject

class OneTimeObserver<T>(private val handler: (T) -> Unit) : Observer<T>, LifecycleOwner {
    private val lifecycle = LifecycleRegistry(this)

    init {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycle

    override fun onChanged(t: T) {
        handler(t)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
    val observer = OneTimeObserver(handler = onChangeHandler)
    observe(observer, observer)
}

@HiltAndroidTest
@ExperimentalCoroutinesApi
class ViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var hospitalRepository: HospitalRepository

    lateinit var hospitalViewModel: HospitalViewModel

    @Before
    fun inject() {
        hiltRule.inject()
        hospitalViewModel = HospitalViewModel(hospitalRepository)
    }

    @After
    fun cleanUp() {

    }

    @Test
    fun hospitalViewModelTest() {
        hospitalViewModel.getDoctorInfo(1).observeOnce {
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

