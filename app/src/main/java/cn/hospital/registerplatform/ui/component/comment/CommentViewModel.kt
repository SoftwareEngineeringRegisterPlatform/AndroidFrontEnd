package cn.hospital.registerplatform.ui.component.comment

import androidx.lifecycle.asLiveData
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.dto.EvaluateInfo
import cn.hospital.registerplatform.data.dto.UploadComment
import cn.hospital.registerplatform.data.repository.CommentRepository
import cn.hospital.registerplatform.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val commentRepository: CommentRepository,
    private val userPreference: UserPreference
) :
    BaseViewModel() {
    fun getCommentList(doctorId: Int, sort_method: String, sort_select: Int) = commentRepository.getCommentList(doctorId, sort_method, sort_select)

    fun uploadComment(
        recipeId: Int,
        rating: Int,
        comment: String
    ) = commentRepository.createComment(
        UploadComment(
            recipeId,
            userPreference.getCachedToken(),
            EvaluateInfo(
                rating,
                comment
            )
        )
    ).asLiveData()

}
