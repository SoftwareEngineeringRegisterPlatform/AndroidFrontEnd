package cn.hospital.registerplatform.ui.component.comment

import androidx.lifecycle.asLiveData
import cn.hospital.registerplatform.data.dto.UploadComment
import cn.hospital.registerplatform.data.repository.CommentRepository
import cn.hospital.registerplatform.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(private val commentRepository: CommentRepository) :
    BaseViewModel() {
    fun getCommentList(doctorId: Int) = commentRepository.getCommentList(doctorId)

//    fun submitComment(
//        hospitalId: Int,
//        uploadComment: UploadComment
//    ) = commentRepository.createComment(hospitalId, uploadComment).asLiveData()

}
