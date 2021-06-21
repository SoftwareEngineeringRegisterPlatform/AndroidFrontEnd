package cn.hospital.registerplatform.ui.component.comment

import androidx.lifecycle.asLiveData
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.dto.EvaluateInfo
import cn.hospital.registerplatform.data.dto.UploadComment
import cn.hospital.registerplatform.data.repository.CommentRatingFilter
import cn.hospital.registerplatform.data.repository.CommentRepository
import cn.hospital.registerplatform.data.repository.CommentSortMethod
import cn.hospital.registerplatform.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val commentRepository: CommentRepository,
    private val userPreference: UserPreference
) : BaseViewModel() {

    var mSortMethod: CommentSortMethod = CommentSortMethod.NEWEST_COMMENT_FIRST
    var mRatingFilter: CommentRatingFilter = CommentRatingFilter.NO_SELECT_FILTER

    fun getCommentList(doctorId: Int) =
        commentRepository.getCommentList(doctorId, mSortMethod, mRatingFilter)

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
