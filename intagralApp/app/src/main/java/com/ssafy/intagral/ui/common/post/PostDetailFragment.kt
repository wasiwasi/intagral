package com.ssafy.intagral.ui.common.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.ssafy.intagral.R
import com.ssafy.intagral.databinding.ViewPostDetailBinding
import com.ssafy.intagral.viewmodel.PostDetailViewModel

class PostDetailFragment: Fragment() {

    private var paramPostId: Int? = null

    private lateinit var binding: ViewPostDetailBinding
    private val postDetailViewModel: PostDetailViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramPostId = it.getInt(PARAM_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewPostDetailBinding.inflate(inflater, container, false)

        binding.include.profileSimpleFollowbtn.setOnClickListener {
            postDetailViewModel.toggleWriterFollow(paramPostId!!)
        }

        binding.buttonLike.setOnClickListener{
            postDetailViewModel.togglePostLike(paramPostId!!)
        }
        
        // TODO : 유저페이지로 이동하는 클릭 이벤트리스너

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPostDetail.background =  requireContext().getDrawable(R.drawable.bg_gradation)
        binding.viewPostDetail.visibility = View.GONE
        postDetailViewModel.getPostDetail().observe(
            viewLifecycleOwner
        ){
            it?.let {
                Glide.with(requireContext())
                    .load(it.writerImgPath)
                    .into(binding.include.profileSimpleImg)
                binding.include.profileSimpleNickname.text = it.writer

                if(it.isFollow){
                    binding.include.profileSimpleFollowbtn.text = "UNFOLLOW"
                } else {
                    binding.include.profileSimpleFollowbtn.text = "FOLLOW"
                }

                Glide.with(requireContext())
                    .load(it.imgPath)
                    .into(binding.postImage)
                binding.postLikeCount.text = "${it.likeCount}"
                if(it.tags.isNotEmpty()){
                    binding.postContent.text = it.tags.map { "#${it}" }.reduce { acc, s -> "$acc $s" }
                }
                binding.buttonLike.isChecked = it.isLike


                binding.viewPostDetail.visibility = View.VISIBLE
            }
        }

        postDetailViewModel.fetchPostDetail(paramPostId!!)

    }

    override fun onDestroy() {
        super.onDestroy()
        postDetailViewModel.getPostDetail().value = null
    }

    companion object {
        private const val PARAM_NAME = "paramPostId"
        @JvmStatic
        fun newInstance(postId: Int) =
            PostDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(PARAM_NAME, postId)
                }
            }
    }
}